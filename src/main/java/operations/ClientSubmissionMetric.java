package operations;

import base.Wrappers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

;

public class ClientSubmissionMetric extends Wrappers {
    private final String sourceClientSubmissionFile = "CLIENT_SUBMISSION/ClientSubmissionSourceData.csv";
    private final String finalClientSubmissionFile = "CLIENT_SUBMISSION/ClientSubmissionFinalData.csv";
    private final String querySource = "METRIC_PIPELINE_SNAPSHOTS.SNAPSHOTS.SCD_JOB_SUBMISSIONS\n" +
            "WHERE ID NOT IN(\n" +
            "    SELECT ID FROM METRIC_PIPELINE_SNAPSHOTS.SNAPSHOTS.SCD_JOB_SUBMISSIONS\n" +
            "WHERE STATUS IN ('Submitted', 'Sales Rep Rejected')  \n" +
            ") AND STATUS = 'Client Submission' AND IS_DELETED = FALSE ORDER BY ID";
    private final String queryFinal = "METRICS_PIPELINE_UAT.METRICS_UAT.CLIENT_SUBMISSIONS ORDER BY ID";
    List<String> clientSubmissionColumns = new ArrayList<>();


    public ClientSubmissionMetric() throws SQLException {
    }

   public void extractSourceFile(){

       clientSubmissionColumns.add("ID");

       extractFile(sourceClientSubmissionFile, clientSubmissionColumns, querySource, "SELECT DISTINCT ");
    }

    public void extractFinalFile(){

        clientSubmissionColumns.add("ID");

        extractFile(finalClientSubmissionFile, clientSubmissionColumns, queryFinal, "SELECT DISTINCT ");
    }

    public boolean comparingClientSubmissionFiles_Successful(){

       return compareCSVFiles(sourceClientSubmissionFile, finalClientSubmissionFile);
    }

    public boolean expectedColumnsOnClientSubmissionFinalTable() throws SQLException {

        List<String> clientSubmissionExpectedColumns =  new ArrayList<>();

        clientSubmissionExpectedColumns.add("BILL_RATE");
        clientSubmissionExpectedColumns.add("CANDIDATE");
        clientSubmissionExpectedColumns.add("DATE_ADDED");
        clientSubmissionExpectedColumns.add("DATE_LAST_MODIFIED");
        clientSubmissionExpectedColumns.add("ID");
        clientSubmissionExpectedColumns.add("IS_DELETED");
        clientSubmissionExpectedColumns.add("JOB_ORDER");
        clientSubmissionExpectedColumns.add("OWNERS");
        clientSubmissionExpectedColumns.add("OWNERS_ID");
        clientSubmissionExpectedColumns.add("PAY_RATE");
        clientSubmissionExpectedColumns.add("SENDING_USER");
        clientSubmissionExpectedColumns.add("SOURCE");
        clientSubmissionExpectedColumns.add("CURRENT_STATUS");

        return areAllExpectedColumns(clientSubmissionExpectedColumns, getColumnsName("SELECT * FROM METRICS_PIPELINE_UAT.METRICS_UAT.CLIENT_SUBMISSIONS"));
    }

}
