package operations;

import base.Wrappers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

;

public class StartsMetric extends Wrappers {
    private final String sourceStartsFile = "STARTS/StartsSourceData.csv";
    private final String finalStartsFile = "STARTS/StartsFinalData.csv";
    private final String querySource = "PIPELINE_CLEAN.BULLHORN.PLACEMENTS\n" +
            "WHERE STATUS IN ('Submitted', 'AM Submitted', 'Onboarding', 'Approved', 'Approved-HSG', 'PERM', 'Terminated', 'Completed') AND (is_count_as_a_deal is null or is_count_as_a_deal = 'Yes') ORDER BY 1";
    private final String queryFinal = "METRICS_PIPELINE_UAT.METRICS_UAT.AWO_STARTS ORDER BY 1";
    List<String> startsColumns = new ArrayList<>();


    public StartsMetric() throws SQLException {
    }

   public void extractSourceFile(){

       startsColumns.add("ID");

        extractFile(sourceStartsFile, startsColumns, querySource, "SELECT DISTINCT ");
    }

    public void extractFinalFile(){

        startsColumns.add("ID");

        extractFile(finalStartsFile, startsColumns, queryFinal, "SELECT DISTINCT ");
    }

    public boolean comparingStartsFiles_Successful(){

       return compareCSVFiles(sourceStartsFile, finalStartsFile);
    }

    public boolean expectedColumnsOnStartsFinalTable() throws SQLException {

        List<String> startsExpectedColumns =  new ArrayList<>();

        startsExpectedColumns.add("CANDIDATE");
        startsExpectedColumns.add("DIVISION");
        startsExpectedColumns.add("CLIENT_CORPORATION");
        startsExpectedColumns.add("CLIENT_CONTACT");
        startsExpectedColumns.add("JOB_ORDER");
        startsExpectedColumns.add("VENDOR_ID");
        startsExpectedColumns.add("EMPLOYEE_TYPE");
        startsExpectedColumns.add("DISCOUNT_FOR_SPREAD_REPORT");
        startsExpectedColumns.add("DATE_BEGIN");
        startsExpectedColumns.add("DATE_END");
        startsExpectedColumns.add("HOURS_OF_OPERATION");
        startsExpectedColumns.add("ISSUED_EQUIPMENT");
        startsExpectedColumns.add("WORKING_IN_STATE");
        startsExpectedColumns.add("INTERN_LOCATION");
        startsExpectedColumns.add("IS_CANADA_INVOLVED");
        startsExpectedColumns.add("BDM");
        startsExpectedColumns.add("IS_GO_LIVE_JOB");
        startsExpectedColumns.add("ZONE");

        return areAllExpectedColumns(startsExpectedColumns, getColumnsName("SELECT * FROM METRICS_PIPELINE_UAT.METRICS_UAT.AWO_STARTS"));
    }

}
