package operations;

import base.Wrappers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

;

public class RecruiterCandidateDBAsMetric extends Wrappers {
    private final String sourceRecruiterCandidateDBAsFile = "RECRUITER_CANDIDATE/RecruiterCandidateSourceData.csv";
    private final String finalRecruiterCandidateDBAsFile = "RECRUITER_CANDIDATE/RecruiterCandidateFinalData.csv";
    private final String querySource = "PIPELINE_CLEAN.BULLHORN.NOTES WHERE IS_DELETED = FALSE AND ACTION = 'Screened' ORDER BY ID";
    private final String queryFinal = "METRICS_PIPELINE_UAT.METRICS_UAT.RECRUITING_DBAS ORDER BY ID";
    List<String> recruiterCandidateDBAsColumns = new ArrayList<>();


    public RecruiterCandidateDBAsMetric() throws SQLException {
    }

   public void extractSourceFile(){

       recruiterCandidateDBAsColumns.add("ID");

        extractFile(sourceRecruiterCandidateDBAsFile, recruiterCandidateDBAsColumns, querySource, "SELECT DISTINCT ");
    }

    public void extractFinalFile(){

        recruiterCandidateDBAsColumns.add("ID");

        extractFile(finalRecruiterCandidateDBAsFile, recruiterCandidateDBAsColumns, queryFinal, "SELECT DISTINCT ");
    }

    public boolean comparingRecruiterCandidateDBAsFiles_Successful(){

       return compareCSVFiles(sourceRecruiterCandidateDBAsFile, finalRecruiterCandidateDBAsFile);
    }

    public boolean expectedColumnsOnRecruiterCandidateDBAsFinalTable() throws SQLException {

        List<String> recruiterCandidateDBAsExpectedColumns =  new ArrayList<>();

        recruiterCandidateDBAsExpectedColumns.add("ID");
        recruiterCandidateDBAsExpectedColumns.add("ACTION");
        recruiterCandidateDBAsExpectedColumns.add("CANDIDATES");
        recruiterCandidateDBAsExpectedColumns.add("CANDIDATES_ID");
        recruiterCandidateDBAsExpectedColumns.add("CORPORATE_USERS");
        recruiterCandidateDBAsExpectedColumns.add("CORPORATE_USERS_ID");
        recruiterCandidateDBAsExpectedColumns.add("DATE_ADDED");
        recruiterCandidateDBAsExpectedColumns.add("DATE_LAST_MODIFIED");
        recruiterCandidateDBAsExpectedColumns.add("IS_DELETED");

        return areAllExpectedColumns(recruiterCandidateDBAsExpectedColumns, getColumnsName("SELECT * FROM METRICS_PIPELINE_UAT.METRICS_UAT.RECRUITING_DBAS"));
    }

}
