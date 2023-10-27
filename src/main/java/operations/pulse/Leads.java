package operations.pulse;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.opencsv.CSVWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Leads {

    String jsonFilePath = "PULSE/LEADS/LeadsPulse.json";
    String csvFilePath = "PULSE/LEADS/LeadsSF.csv";

    static float recordsOnSF;


    public Leads() throws IOException {
    }

    public void convertJsonIntoCsv() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        List<Map<String, Object>> itemization = objectMapper.readValue(new File(jsonFilePath),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class));

        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {
            // Write CSV header
            String[] header = itemization.get(0).keySet().toArray(new String[0]);
            writer.writeNext(header);

            for (Map<String, Object> item : itemization) {
                String[] row = new String[header.length];
                int i = 0;
                for (String key : header) {
                    Object value = item.get(key);
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
        }

        idsInFileA.retainAll(idsInFileB);

        System.out.println("Number of records on SF file: "+Math.round(recordsOnSF));

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedValue = decimalFormat.format((idsInFileA.size()/recordsOnSF)*100);
        float accuracy = Float.parseFloat(formattedValue);

        return accuracy;
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
