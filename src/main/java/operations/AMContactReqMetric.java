package operations;

import base.Wrappers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

;

public class AMContactReqMetric extends Wrappers {
    private final String sourceAMContactReqFile = "AM_CONTACTS_REQ/AMContactsReqSourceData.csv";
    private final String finalAMContactReqFile = "AM_CONTACTS_REQ/AMContactsReqFinalData.csv";
    private final String querySource = "PIPELINE_CLEAN.BULLHORN.NOTES WHERE IS_DELETED = FALSE AND ACTION = 'Requalified DBA' ORDER BY ID";
    private final String queryFinal = "METRICS_PIPELINE_UAT.METRICS_UAT.ACCOUNT_MANAGEMENT_REQUALED_DBAS ORDER BY ID";
    List<String> AMContactReqColumns = new ArrayList<>();


    public AMContactReqMetric() throws SQLException {
    }

   public void extractSourceFile(){

       AMContactReqColumns.add("ID");

        extractFile(sourceAMContactReqFile, AMContactReqColumns, querySource, "SELECT DISTINCT ");
    }

    public void extractFinalFile(){

        AMContactReqColumns.add("ID");

        extractFile(finalAMContactReqFile, AMContactReqColumns, queryFinal, "SELECT DISTINCT ");
    }

    public boolean comparingAMContactReqFiles_Successful(){

       return compareCSVFiles(sourceAMContactReqFile, finalAMContactReqFile);
    }

    public boolean expectedColumnsOnAMContactReqFinalTable() throws SQLException {

        List<String> AMContactReqExpectedColumns =  new ArrayList<>();

        AMContactReqExpectedColumns.add("ID");
        AMContactReqExpectedColumns.add("ACTION");
        AMContactReqExpectedColumns.add("CANDIDATES");
        AMContactReqExpectedColumns.add("CANDIDATES_ID");
        AMContactReqExpectedColumns.add("CORPORATE_USERS");
        AMContactReqExpectedColumns.add("CORPORATE_USERS_ID");
        AMContactReqExpectedColumns.add("DATE_ADDED");
        AMContactReqExpectedColumns.add("DATE_LAST_MODIFIED");
        AMContactReqExpectedColumns.add("IS_DELETED");

        return areAllExpectedColumns(AMContactReqExpectedColumns, getColumnsName("SELECT * FROM METRICS_PIPELINE_UAT.METRICS_UAT.ACCOUNT_MANAGEMENT_REQUALED_DBAS"));
    }

}
