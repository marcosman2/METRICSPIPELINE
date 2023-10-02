package operations;

import base.Wrappers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

;

public class JobSubmissionMetric extends Wrappers {
    private final String sourceJobSubmissionFile = "JOB_SUBMISSION/JobSubmissionSourceData.csv";
    private final String finalJobSubmissionFile = "JOB_SUBMISSION/JobSubmissionFinalData.csv";
    private final String querySource = "PIPELINE_CLEAN.BULLHORN.JOB_SUBMISSIONS WHERE IS_DELETED = FALSE ORDER BY ID";
    private final String queryFinal = "METRICS_PIPELINE_UAT.METRICS_UAT.JOB_SUBMISSIONS ORDER BY ID";
    List<String> jobSubmissionColumns = new ArrayList<>();


    public JobSubmissionMetric() throws SQLException {
    }

   public void extractSourceFile(){

       jobSubmissionColumns.add("ID");
       jobSubmissionColumns.add("STATUS");

       extractFile(sourceJobSubmissionFile, jobSubmissionColumns, querySource, "SELECT DISTINCT ");
    }

    public void extractFinalFile(){

        jobSubmissionColumns.add("ID");
        jobSubmissionColumns.add("STATUS");

        extractFile(finalJobSubmissionFile, jobSubmissionColumns, queryFinal, "SELECT DISTINCT ");
    }

    public boolean comparingJobSubmissionFiles_Successful(){

       return compareCSVFiles(sourceJobSubmissionFile, finalJobSubmissionFile);
    }

    public boolean expectedColumnsOnJobSubmissionFinalTable() throws SQLException {

        List<String> jobSubmissionExpectedColumns =  new ArrayList<>();

        jobSubmissionExpectedColumns.add("BILL_RATE");
        jobSubmissionExpectedColumns.add("CANDIDATE");
        jobSubmissionExpectedColumns.add("DATE_ADDED");
        jobSubmissionExpectedColumns.add("DATE_LAST_MODIFIED");
        jobSubmissionExpectedColumns.add("ID");
        jobSubmissionExpectedColumns.add("IS_DELETED");
        jobSubmissionExpectedColumns.add("JOB_ORDER");
        jobSubmissionExpectedColumns.add("OWNERS");
        jobSubmissionExpectedColumns.add("OWNERS_ID");
        jobSubmissionExpectedColumns.add("PAY_RATE");
        jobSubmissionExpectedColumns.add("SENDING_USER");
        jobSubmissionExpectedColumns.add("SOURCE");
        jobSubmissionExpectedColumns.add("STATUS");

        return areAllExpectedColumns(jobSubmissionExpectedColumns, getColumnsName("SELECT * FROM METRICS_PIPELINE_UAT.METRICS_UAT.JOB_SUBMISSIONS"));
    }

}
