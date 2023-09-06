package base;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.*;

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

    public void extractFile(String fileName, String columnLabel, String query){

        try (FileWriter writer = new FileWriter(fileName)){

            rs = stmt.executeQuery(query);

            while (rs.next()) {

                String column1 = rs.getString(columnLabel);

                writer.append(column1 + "\n");
            }

            rs.close();
        }
        catch(Exception e){

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
}
