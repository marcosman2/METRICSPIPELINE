package operations;

import base.Wrappers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

;

public class JobsMetric extends Wrappers {
    private final String sourceJobsFile = "JOBS/JobsSourceData.csv";
    private final String finalJobsFile = "JOBS/JobsFinalData.csv";
    private final String querySource = "PIPELINE_CLEAN.BULLHORN.JOB_ORDERS WHERE IS_DELETED = FALSE ORDER BY ID";
    private final String queryFinal = "METRICS_PIPELINE_UAT.METRICS_UAT.JOBS ORDER BY ID";
    List<String> jobsColumns = new ArrayList<>();


    public JobsMetric() throws SQLException {
    }

   public void extractSourceFile(){

       jobsColumns.add("ID");
       jobsColumns.add("OWNER");

        extractFile(sourceJobsFile, jobsColumns, querySource, "SELECT DISTINCT ");
    }

    public void extractFinalFile(){

        jobsColumns.add("ID");
        jobsColumns.add("OWNER");

        extractFile(finalJobsFile, jobsColumns, queryFinal, "SELECT DISTINCT ");
    }

    public boolean comparingJobsFiles_Successful(){

       return compareCSVFiles(sourceJobsFile, finalJobsFile);
    }

    public boolean expectedColumnsOnJobsFinalTable() throws SQLException {

        List<String> jobsExpectedColumns =  new ArrayList<>();

        jobsExpectedColumns.add("OWNER");
        jobsExpectedColumns.add("ID");
        jobsExpectedColumns.add("IS_DELETED");
        jobsExpectedColumns.add("SENDOUTS");
        jobsExpectedColumns.add("SPECIALTIES");
        jobsExpectedColumns.add("REASON_CLOSED");
        jobsExpectedColumns.add("SUBMISSIONS");
        jobsExpectedColumns.add("START_DATE");
        jobsExpectedColumns.add("STATUS");
        jobsExpectedColumns.add("DATE_END");
        jobsExpectedColumns.add("CATEGORIES");
        jobsExpectedColumns.add("HOURS_PER_WEEK_2");
        jobsExpectedColumns.add("SOURCE");
        jobsExpectedColumns.add("DATE_ADDED");
        jobsExpectedColumns.add("INTERVIEWS");
        jobsExpectedColumns.add("JOB_ORIGIN");
        jobsExpectedColumns.add("PLACEMENTS");
        jobsExpectedColumns.add("BDM");
        jobsExpectedColumns.add("TITLE");
        jobsExpectedColumns.add("FULL_TIME_OR_PART_TIME");
        jobsExpectedColumns.add("JOB_PROCESS");
        jobsExpectedColumns.add("IS_OPEN_TIMES");
        jobsExpectedColumns.add("CROSS_SELL_LEAD_FROM");
        jobsExpectedColumns.add("IS_GO_LIVE_JOB");
        jobsExpectedColumns.add("PROGRAM");
        jobsExpectedColumns.add("GOV_DIVISION");
        jobsExpectedColumns.add("IS_JOB_FROM_PIPELINE");
        jobsExpectedColumns.add("INTERN_ORIGIN");
        jobsExpectedColumns.add("IS_EXCLUSIVE_JOB");
        jobsExpectedColumns.add("VMS_ID");
        jobsExpectedColumns.add("EMPLOYMENT_TYPE");
        jobsExpectedColumns.add("DURATION");
        jobsExpectedColumns.add("REPORT_TO");
        jobsExpectedColumns.add("OWNER_NAME");
        jobsExpectedColumns.add("CATEGORY_NAME");
        jobsExpectedColumns.add("SENDOUTS_TOTAL");
        jobsExpectedColumns.add("SPECIALTIES_ID");
        jobsExpectedColumns.add("SPECIALTIES_NAME");
        jobsExpectedColumns.add("CLIENT_CONTACT");
        jobsExpectedColumns.add("APPOINTMENTS_TOTAL");
        jobsExpectedColumns.add("CLIENT_CORPORATION");
        jobsExpectedColumns.add("ASSIGNED_USERS");
        jobsExpectedColumns.add("SUBMISSIONS_TOTAL");
        jobsExpectedColumns.add("CATEGORIES_ID");
        jobsExpectedColumns.add("CATEGORIES_NAME");
        jobsExpectedColumns.add("INTERVIEWS_TOTAL");
        jobsExpectedColumns.add("PLACEMENTS_TOTAL");

        return areAllExpectedColumns(jobsExpectedColumns, getColumnsName("SELECT * FROM METRICS_PIPELINE_UAT.METRICS_UAT.JOBS"));
    }

}
