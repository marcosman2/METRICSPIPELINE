package operations;

import base.Wrappers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

;

public class DealsMetric extends Wrappers {
    private final String sourceDealsFile = "DEALS/DealsSourceData.csv";
    private final String finalDealsFile = "DEALS/DealsFinalData.csv";
    private final String querySource = "PIPELINE_CLEAN.BULLHORN_CLEAN.PLACEMENTS WHERE (is_count_as_a_deal is null or is_count_as_a_deal = 'Yes')\n" +
            "AND status in ('Submitted', 'AM Submitted', 'Onboarding', 'Approved', 'Approved-HSG', 'PERM', 'Terminated', 'Completed') ORDER BY 1";
    private final String queryFinal = "METRICS_PIPELINE_UAT.METRICS_UAT.DEALS ORDER BY 1";
    List<String> dealsColumns = new ArrayList<>();


    public DealsMetric() throws SQLException {
    }

   public void extractSourceFile(){

       dealsColumns.add("ID");
       dealsColumns.add("IS_COUNT_AS_A_DEAL");
       dealsColumns.add("STATUS");

        extractFile(sourceDealsFile, dealsColumns, querySource);
    }

    public void extractFinalFile(){

        dealsColumns.add("ID");
        dealsColumns.add("IS_COUNT_AS_A_DEAL");
        dealsColumns.add("STATUS");

        extractFile(finalDealsFile, dealsColumns, queryFinal);
    }

    public boolean comparingDealsFiles_Successful(){

       return compareCSVFiles(sourceDealsFile, finalDealsFile);
    }

    public boolean expectedColumnsOnDealsFinalTable() throws SQLException {

        List<String> dealsExpectedColumns =  new ArrayList<>();

        dealsExpectedColumns.add("ID");
        dealsExpectedColumns.add("ACCOUNT_BREAKER");
        dealsExpectedColumns.add("ACCOUNT_MANAGER_PROGRAM_MANAGER_MAINTENANCE_P");
        dealsExpectedColumns.add("ACTUAL_END_DATE");
        dealsExpectedColumns.add("ACTUAL_START_DATE");
        dealsExpectedColumns.add("APPROVING_CLIENT_CONTACT");
        dealsExpectedColumns.add("BACKUP_APPROVING_CLIENT_CONTACT");
        dealsExpectedColumns.add("BDM");
        dealsExpectedColumns.add("BILLING_CLIENT_CONTACT");
        dealsExpectedColumns.add("BILLING_CONTACT");
        dealsExpectedColumns.add("CANDIDATE");
        dealsExpectedColumns.add("CANDIDATE_SOURCE");
        dealsExpectedColumns.add("CLIENT_CONTACT");
        dealsExpectedColumns.add("CLIENT_CORPORATION");
        dealsExpectedColumns.add("CROSS_SELL_LEAD_FROM");
        dealsExpectedColumns.add("CUSTOMER_ID");
        dealsExpectedColumns.add("DATE_ADDED");
        dealsExpectedColumns.add("DATE_BEGIN");
        dealsExpectedColumns.add("DIVISION");
        dealsExpectedColumns.add("FINISH_COMMENTS");
        dealsExpectedColumns.add("INTERN_LOCATION");
        dealsExpectedColumns.add("INTERN_ORIGIN");
        dealsExpectedColumns.add("IS_COUNT_AS_A_DEAL");
        dealsExpectedColumns.add("IS_GO_LIVE_JOB");
        dealsExpectedColumns.add("JOB_ORDER");
        dealsExpectedColumns.add("JOB_ORIGIN");
        dealsExpectedColumns.add("JOB_SUBMISSION");
        dealsExpectedColumns.add("ORIGINAL_START_DATE");
        dealsExpectedColumns.add("OWNER");
        dealsExpectedColumns.add("OWNER_NAME");
        dealsExpectedColumns.add("OWNERS_ID");
        dealsExpectedColumns.add("PERSONNEL_COORDINATOR");
        dealsExpectedColumns.add("PROGRAM");
        dealsExpectedColumns.add("PROGRAM_ID");
        dealsExpectedColumns.add("RDM");
        dealsExpectedColumns.add("RECRUITER_MAINTENANCE");
        dealsExpectedColumns.add("STATUS");
        dealsExpectedColumns.add("TERMINATION_REASON");
        dealsExpectedColumns.add("WORKING_IN_STATE");
        dealsExpectedColumns.add("WORKSITE_TYPE");

        return areAllExpectedColumns(dealsExpectedColumns, getColumnsName("SELECT * FROM METRICS_PIPELINE_UAT.METRICS_UAT.DEALS"));
    }

}
