package operations;

import base.Wrappers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

;

public class ScheduledCallsMetric extends Wrappers {
    private final String sourceScheduledCallsFile = "SCHEDULEDCALLS/ScheduledCallsSourceData.csv";
    private final String finalScheduledCallsFile = "SCHEDULEDCALLS/ScheduledCallsFinalData.csv";
    private final String querySource = "PIPELINE_CLEAN.BULLHORN.NOTES WHERE ACTION = 'Scheduled Call' AND IS_DELETED = 'false'";
    private final String queryFinal = "METRICS_PIPELINE_UAT.METRICS_UAT.SCHEDULED_CALLS";
    List<String> scheduledCallsColumns = new ArrayList<>();


    public ScheduledCallsMetric() throws SQLException {
    }

   public void extractSourceFile(){

        scheduledCallsColumns.add("ID");

        extractFile(sourceScheduledCallsFile, scheduledCallsColumns, querySource, "SELECT DISTINCT ");
    }

    public void extractFinalFile(){

        scheduledCallsColumns.add("ID");

        extractFile(finalScheduledCallsFile, scheduledCallsColumns, queryFinal, "SELECT DISTINCT ");
    }

    public boolean comparingScheduledCallsFiles(){

       return compareCSVFiles(sourceScheduledCallsFile, finalScheduledCallsFile);
    }

    public boolean expectedColumnsOnScheduledCallsFinalTable() throws SQLException {

        List<String> scheduledCallsExpectedColumns =  new ArrayList<>();

        scheduledCallsExpectedColumns.add("ID");
        scheduledCallsExpectedColumns.add("ACTION");
        scheduledCallsExpectedColumns.add("CANDIDATES");
        scheduledCallsExpectedColumns.add("CANDIDATES_ID");
        scheduledCallsExpectedColumns.add("CORPORATE_USERS");
        scheduledCallsExpectedColumns.add("CORPORATE_USERS_ID");
        scheduledCallsExpectedColumns.add("DATE_ADDED");
        scheduledCallsExpectedColumns.add("DATE_LAST_MODIFIED");
        scheduledCallsExpectedColumns.add("IS_DELETED");

        return areAllExpectedColumns(scheduledCallsExpectedColumns, getColumnsName("SELECT * FROM METRICS_PIPELINE_UAT.METRICS_UAT.SCHEDULED_CALLS"));
    }

}
