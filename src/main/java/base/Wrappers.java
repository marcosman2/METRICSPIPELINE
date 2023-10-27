package base;

import java.io.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class Wrappers {

    String roleName = "METRICS_TRANSFORMER";
    String warehouse = "PIPELINE_WAREHOUSE";
    String url = "jdbc:snowflake://nwb18845.us-east-1.snowflakecomputing.com/?ROLE="+roleName+"&warehouse="+warehouse;
    String user = "MMANRIQUE";
    String pwd = "Javier128500*";
    Connection conn = DriverManager.getConnection(url, user, pwd);
    Statement stmt = conn.createStatement();
    ResultSet rs;
    boolean areEq = true;

    boolean areColumns = true;
    int maxValue;

    static float recordsOnSF;
    static float recordsOnPulse;

    public Wrappers() throws SQLException {
    }

    public void extractFile(String fileName, List<String> columns, String query, String queryStart) {

        try {

            StringBuilder queryBuilder = new StringBuilder(queryStart);
            for (String column : columns) {
                queryBuilder.append(column).append(",");
            }

            queryBuilder.deleteCharAt(queryBuilder.length() - 1); // Remove the trailing comma
            queryBuilder.append(" FROM ").append(query);

            rs = stmt.executeQuery(queryBuilder.toString());

            try (FileWriter csvWriter = new FileWriter(fileName)) {
                // Write column headers
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    csvWriter.append(metaData.getColumnName(i));
                    if (i < columnCount) {
                        csvWriter.append(",");
                    }
                }
                csvWriter.append("\n");

                if(columns.get(0).equalsIgnoreCase("*")){

                    columns.clear();

                    for (int i = 1; i <= columnCount; i++) {

                        columns.add(metaData.getColumnName(i));
                    }
                }

                // Write data rows
                while (rs.next()) {
                    for (String column : columns) {
                        csvWriter.append(rs.getString(column));
                        if (columns.indexOf(column) < columns.size() - 1) {
                            csvWriter.append(",");
                        }
                    }
                    csvWriter.append("\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            columns.clear();
            rs.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean compareCSVFiles(String sourceFile, String finalFile){

        try (BufferedReader reader1 = new BufferedReader(new FileReader(sourceFile));
             BufferedReader reader2 = new BufferedReader(new FileReader(finalFile))) {

            String line1, line2;
            while ((line1 = reader1.readLine()) != null && (line2 = reader2.readLine()) != null) {

                if (!line1.equals(line2)) {

                    areEq = false;
                    //System.out.println("SOURCE: "+line1+"\t"+"METRIC: "+line2);
                }
            }

            if(areEq){
                TestReport.logPass("Source and Final files match");
            }
            else{

                TestReport.logFail("Source and Final files do not match");
            }

            return reader1.readLine() == null && reader2.readLine() == null;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return areEq;
    }

    public void closeConnections() throws SQLException {

        stmt.close();
        conn.close();
    }

    public String[] getColumnsName(String query) throws SQLException {

        rs = stmt.executeQuery(query);

        ResultSetMetaData metaData = rs.getMetaData();

        int columnCount = metaData.getColumnCount();

        String[] columnHeaders = new String[columnCount];

        for (int i = 1; i <= columnCount; i++) {
            columnHeaders[i - 1] = metaData.getColumnName(i);
            System.out.println("Column: "+columnHeaders[i - 1]);
        }

        return columnHeaders;
    }

    public boolean areAllExpectedColumns(List<String> expectedColumns, String[] actualColumns){

        List<String> actualColumnsList = Arrays.asList(actualColumns);

        for(String column: expectedColumns){
            if(!actualColumnsList.contains(column)){
                areColumns = false;
                System.out.println("Column not present: "+column);
            }
        }

        if(areColumns){

            TestReport.logPass("All expected columns are included on Metrics table");
        }
        else{

            TestReport.logFail("Not all expected column are included on Metrics table");
        }

        return areColumns;
    }

    public int getColumnValue(String query){

        try{

            rs = stmt.executeQuery(query);

            if (rs.next())
                maxValue = rs.getInt("MAX_DIFFERENCE_IN_WEEKS");
        }
        catch(Exception e){

            e.printStackTrace();
        }

        return maxValue;
    }

    public void exportQueryResult(String query, String csvFilePath){

        try(Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            FileWriter fileWriter = new FileWriter(csvFilePath);
            CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader(rs))){


                while (rs.next()) {
                    csvPrinter.printRecord(rs.getInt("ID"));
                }
        }
        catch(Exception e){

            e.printStackTrace();
        }
    }

    public void convertJsonIntoCsv(String jsonFilePath, String csvFilePath) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        List<Map<String, Object>> itemization = objectMapper.readValue(new File(jsonFilePath),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));

        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {
            // Write CSV header
            String[] header = itemization.get(0).keySet().toArray(new String[0]);

            for (int i = 0; i < header.length; i++) {
                header[i] = header[i].toUpperCase();
            }

            writer.writeNext(header);

            for (Map<String, Object> item : itemization) {
                String[] row = new String[header.length];
                int i = 0;
                for (String key : header) {
                    Object value = item.get(key.toLowerCase());
                    row[i++] = (value != null) ? value.toString() : "";
                }
                writer.writeNext(row);
            }
        }

    }

    public static float countMatchingIds(String fileAPath, String fileBPath) throws IOException {

        Set<String> idsInFileA = new HashSet<>();
        Set<String> idsInFileB = new HashSet<>();

        try (CSVParser parserA = new CSVParser(new FileReader(fileAPath), CSVFormat.DEFAULT.withHeader());
             CSVParser parserB = new CSVParser(new FileReader(fileBPath), CSVFormat.DEFAULT.withHeader())) {

            for (CSVRecord record : parserA) {
                idsInFileA.add(record.get("ID"));
            }

            recordsOnSF = idsInFileA.size();

            for (CSVRecord record : parserB) {
                idsInFileB.add(record.get("ID"));
            }

            recordsOnPulse = idsInFileB.size();
        }

        //idsInFileA.retainAll(idsInFileB);

        System.out.println("Number of records on SF file: "+Math.round(recordsOnSF));
        System.out.println("Number of records on Pulse file: "+Math.round(recordsOnPulse));

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedValue;

        if(recordsOnSF > recordsOnPulse){
            idsInFileA.retainAll(idsInFileB);
            formattedValue = decimalFormat.format((idsInFileA.size()/recordsOnSF)*100);
        }
        else{
            idsInFileB.retainAll(idsInFileA);
            formattedValue = decimalFormat.format((idsInFileB.size()/recordsOnPulse)*100);
        }

        //float accuracy = Float.parseFloat(formattedValue);

        return Float.parseFloat(formattedValue);
    }

    public static Set<String> getIdsInFileANotInFileB(String fileAPath, String fileBPath) throws IOException {

        Set<String> idsInFileA = new HashSet<>();
        Set<String> idsInFileB = new HashSet<>();

        try (CSVParser parserA = new CSVParser(new FileReader(fileAPath), CSVFormat.DEFAULT.withHeader());
             CSVParser parserB = new CSVParser(new FileReader(fileBPath), CSVFormat.DEFAULT.withHeader())) {

            for (CSVRecord record : parserA) {
                idsInFileA.add(record.get("ID"));
            }

            for (CSVRecord record : parserB) {
                idsInFileB.add(record.get("ID"));
            }
        }

        // Calculate the difference between sets to find IDs in fileA but not in fileB
        idsInFileA.removeAll(idsInFileB);

        return idsInFileA;
    }

    public static Set<String> getIdsInFileBNotInFileA(String fileAPath, String fileBPath) throws IOException {

        Set<String> idsInFileA = new HashSet<>();
        Set<String> idsInFileB = new HashSet<>();

        try (CSVParser parserA = new CSVParser(new FileReader(fileAPath), CSVFormat.DEFAULT.withHeader());
             CSVParser parserB = new CSVParser(new FileReader(fileBPath), CSVFormat.DEFAULT.withHeader())) {

            for (CSVRecord record : parserA) {
                idsInFileA.add(record.get("ID"));
            }

            for (CSVRecord record : parserB) {
                idsInFileB.add(record.get("ID"));
            }
        }

        idsInFileB.removeAll(idsInFileA);

        return idsInFileB;
    }

}
