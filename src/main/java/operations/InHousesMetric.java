package operations;

import base.Wrappers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

;

public class InHousesMetric extends Wrappers {
    private final String sourceInHousesFile = "IN_HOUSES/InHousesSourceData.csv";
    private final String finalInHousesFile = "IN_HOUSES/InHousesFinalData.csv";
    private final String querySource = "PIPELINE_CLEAN.BULLHORN_CLEAN.NOTES WHERE ACTION = 'InHouse Interview' AND IS_DELETED = FALSE ORDER BY 1";
    private final String queryFinal = "METRICS_PIPELINE_UAT.METRICS_UAT.INHOUSE_INTERVIEWS ORDER BY 1";
    List<String> inHousesColumns = new ArrayList<>();


    public InHousesMetric() throws SQLException {
    }

   public void extractSourceFile(){

       inHousesColumns.add("ID");
       inHousesColumns.add("ACTION");
       inHousesColumns.add("IS_DELETED");

        extractFile(sourceInHousesFile, inHousesColumns, querySource);
    }

    public void extractFinalFile(){

        inHousesColumns.add("ID");
        inHousesColumns.add("ACTION");
        inHousesColumns.add("IS_DELETED");

        extractFile(finalInHousesFile, inHousesColumns, queryFinal);
    }

    public boolean comparingInHousesFiles_Successful(){

       return compareCSVFiles(sourceInHousesFile, finalInHousesFile);
    }

    public boolean expectedColumnsOnInHousesFinalTable() throws SQLException {

        List<String> visitsExpectedColumns =  new ArrayList<>();

        visitsExpectedColumns.add("ID");
        visitsExpectedColumns.add("ACTION");
        visitsExpectedColumns.add("CANDIDATES");
        visitsExpectedColumns.add("CANDIDATES_ID");
        visitsExpectedColumns.add("CORPORATE_USERS");
        visitsExpectedColumns.add("CORPORATE_USERS_ID");
        visitsExpectedColumns.add("DATE_ADDED");
        visitsExpectedColumns.add("DATE_LAST_MODIFIED");
        visitsExpectedColumns.add("IS_DELETED");

        return areAllExpectedColumns(visitsExpectedColumns, getColumnsName("SELECT * FROM METRICS_PIPELINE_UAT.METRICS_UAT.INHOUSE_INTERVIEWS"));
    }

}
