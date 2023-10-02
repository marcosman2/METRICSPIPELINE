package operations;

import base.Wrappers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

;

public class SendoutsMetric extends Wrappers {
    private final String sourceSendoutsFile = "SENDOUTS/SendoutsSourceData.csv";
    private final String finalSendoutsFile = "SENDOUTS/SendoutsFinalData.csv";
    private final String querySource = "WITH StatusChange AS (\n" +
            "  SELECT DISTINCT\n" +
            "    ID,\n" +
            "    STATUS AS CurrentStatus,\n" +
            "    LAG(STATUS) OVER (PARTITION BY ID ORDER BY DATE_LAST_MODIFIED) AS PreviousStatus    \n" +
            "  FROM METRIC_PIPELINE_SNAPSHOTS.SNAPSHOTS.SCD_JOB_SUBMISSIONS\n" +
            "  WHERE IS_DELETED = FALSE\n" +
            ")\n" +
            "SELECT DISTINCT t1.ID\n" +
            "FROM StatusChange t1\n" +
            "WHERE t1.CurrentStatus = 'Interview Scheduled'\n" +
            "OR (t1.CurrentStatus NOT IN ('Interview Scheduled', 'Submitted', 'Client Submission', 'Sales Rep Rejected')\n" +
            "AND t1.PreviousStatus = 'Interview Scheduled') ORDER BY t1.ID";
    private final String queryFinal = "METRICS_PIPELINE_UAT.METRICS_UAT.SENDOUTS order by id";
    List<String> sendoutsColumns = new ArrayList<>();


    public SendoutsMetric() throws SQLException {
    }

   public void extractSourceFile() throws SQLException, IOException {

       exportQueryResult(querySource, sourceSendoutsFile);
    }

    public void extractFinalFile(){

        sendoutsColumns.add("ID");

        extractFile(finalSendoutsFile, sendoutsColumns, queryFinal, "SELECT DISTINCT ");
    }

    public boolean comparingSendoutsFiles_Successful(){

       return compareCSVFiles(sourceSendoutsFile, finalSendoutsFile);
    }

    public boolean expectedColumnsOnSendoutsFinalTable() throws SQLException {

        List<String> sendoutsExpectedColumns =  new ArrayList<>();

        sendoutsExpectedColumns.add("BILL_RATE");
        sendoutsExpectedColumns.add("CANDIDATE");
        sendoutsExpectedColumns.add("DATE_ADDED");
        sendoutsExpectedColumns.add("DATE_LAST_MODIFIED");
        sendoutsExpectedColumns.add("ID");
        sendoutsExpectedColumns.add("IS_DELETED");
        sendoutsExpectedColumns.add("JOB_ORDER");
        sendoutsExpectedColumns.add("OWNERS");
        sendoutsExpectedColumns.add("OWNERS_ID");
        sendoutsExpectedColumns.add("PAY_RATE");
        sendoutsExpectedColumns.add("SENDING_USER");
        sendoutsExpectedColumns.add("SOURCE");
        sendoutsExpectedColumns.add("CURRENT_STATUS");

        return areAllExpectedColumns(sendoutsExpectedColumns, getColumnsName("SELECT * FROM METRICS_PIPELINE_UAT.METRICS_UAT.SENDOUTS"));
    }

}
