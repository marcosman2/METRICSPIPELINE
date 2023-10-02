package operations;

import base.Wrappers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

;

public class VisitsMetric extends Wrappers {
    private final String sourceVisitsFile = "VISITS/VisitsSourceData.csv";
    private final String finalVisitsFile = "VISITS/VisitsFinalData.csv";
    private final String querySource = "PIPELINE_CLEAN.BULLHORN.NOTES WHERE ACTION = 'Client Visit' AND IS_DELETED = FALSE ORDER BY 1";
    private final String queryFinal = "METRICS_PIPELINE_UAT.METRICS_UAT.CLIENT_VISITS ORDER BY 1";
    List<String> visitsColumns = new ArrayList<>();


    public VisitsMetric() throws SQLException {
    }

   public void extractSourceFile(){

       visitsColumns.add("ID");
       visitsColumns.add("ACTION");
       visitsColumns.add("IS_DELETED");

        extractFile(sourceVisitsFile, visitsColumns, querySource, "SELECT DISTINCT ");
    }

    public void extractFinalFile(){

        visitsColumns.add("ID");
        visitsColumns.add("ACTION");
        visitsColumns.add("IS_DELETED");

        extractFile(finalVisitsFile, visitsColumns, queryFinal, "SELECT DISTINCT ");
    }

    public boolean comparingVisitsFiles_Successful(){

       return compareCSVFiles(sourceVisitsFile, finalVisitsFile);
    }

    public boolean expectedColumnsOnVisitsFinalTable() throws SQLException {

        List<String> visitsExpectedColumns =  new ArrayList<>();

        visitsExpectedColumns.add("ID");
        visitsExpectedColumns.add("ACTION");
        visitsExpectedColumns.add("CLIENT_CONTACTS");
        visitsExpectedColumns.add("CLIENT_CONTACTS_ID");
        visitsExpectedColumns.add("CORPORATE_USERS");
        visitsExpectedColumns.add("CORPORATE_USERS_ID");
        visitsExpectedColumns.add("DATE_ADDED");
        visitsExpectedColumns.add("DATE_LAST_MODIFIED");
        visitsExpectedColumns.add("IS_DELETED");

        return areAllExpectedColumns(visitsExpectedColumns, getColumnsName("SELECT * FROM METRICS_PIPELINE_UAT.METRICS_UAT.CLIENT_VISITS"));
    }

}
