package base;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.List;

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

    public Wrappers() throws SQLException {
    }


    public void extractFile(String fileName, List<String> columns, String query) {

        try {

            StringBuilder queryBuilder = new StringBuilder("SELECT ");
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
                    return areEq = false;
                }
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

    public void getColumnsName() throws SQLException {

        String query = "SELECT * FROM METRICS_PIPELINE_UAT.METRICS_UAT.LEADS ORDER BY LEAD_ID";
        rs = stmt.executeQuery(query);

        ResultSetMetaData metaData = rs.getMetaData();

        int columnCount = metaData.getColumnCount();

        String[] columnHeaders = new String[columnCount];

        for (int i = 1; i <= columnCount; i++) {
            columnHeaders[i - 1] = metaData.getColumnName(i);
        }
    }

}
