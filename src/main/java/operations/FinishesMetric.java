package operations;

import base.Wrappers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

;

public class FinishesMetric extends Wrappers {
    private final String sourceFinishesFile = "FINISHES/FinishesSourceData.csv";
    private final String finalFinishesFile = "FINISHES/FinishesFinalData.csv";
    private final String querySource = "PIPELINE_CLEAN.BULLHORN.PLACEMENTS\n" +
            "WHERE STATUS IN ('Submitted', 'AM Submitted', 'Onboarding', 'Approved', 'Approved-HSG', 'PERM', 'Terminated', 'Completed') AND (is_count_as_a_deal is null or is_count_as_a_deal = 'Yes') and date_end is not null order by 1";
    private final String queryFinal = "METRICS_PIPELINE_UAT.METRICS_UAT.AWO_FINISHES ORDER BY 1";
    List<String> finishesColumns = new ArrayList<>();


    public FinishesMetric() throws SQLException {
    }

   public void extractSourceFile(){

       finishesColumns.add("ID");

        extractFile(sourceFinishesFile, finishesColumns, querySource, "SELECT DISTINCT ");
    }

    public void extractFinalFile(){

        finishesColumns.add("ID");

        extractFile(finalFinishesFile, finishesColumns, queryFinal, "SELECT DISTINCT ");
    }

    public boolean comparingFinishesFiles_Successful(){

       return compareCSVFiles(sourceFinishesFile, finalFinishesFile);
    }

    public boolean expectedColumnsOnFinishesFinalTable() throws SQLException {

        List<String> finishesExpectedColumns =  new ArrayList<>();

        finishesExpectedColumns.add("CANDIDATE");
        finishesExpectedColumns.add("DIVISION");
        finishesExpectedColumns.add("CLIENT_CORPORATION");
        finishesExpectedColumns.add("CLIENT_CONTACT");
        finishesExpectedColumns.add("JOB_ORDER");
        finishesExpectedColumns.add("VENDOR_ID");
        finishesExpectedColumns.add("EMPLOYEE_TYPE");
        finishesExpectedColumns.add("DISCOUNT_FOR_SPREAD_REPORT");
        finishesExpectedColumns.add("DATE_BEGIN");
        finishesExpectedColumns.add("DATE_END");
        finishesExpectedColumns.add("HOURS_OF_OPERATION");
        finishesExpectedColumns.add("ISSUED_EQUIPMENT");
        finishesExpectedColumns.add("WORKING_IN_STATE");
        finishesExpectedColumns.add("INTERN_LOCATION");
        finishesExpectedColumns.add("IS_CANADA_INVOLVED");
        finishesExpectedColumns.add("BDM");
        finishesExpectedColumns.add("IS_GO_LIVE_JOB");
        finishesExpectedColumns.add("ZONE");

        return areAllExpectedColumns(finishesExpectedColumns, getColumnsName("SELECT * FROM METRICS_PIPELINE_UAT.METRICS_UAT.AWO_FINISHES"));
    }

}
