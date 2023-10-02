package operations;

import base.Wrappers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

;

public class YellowZoneMetric extends Wrappers {
    private final String sourceYellowZoneFile = "YELLOWZONE/YellowZoneSourceData.csv";
    private final String finalYellowZoneFile = "YELLOWZONE/YellowZoneFinalData.csv";
    private final String querySource = "PIPELINE_CLEAN.BULLHORN.PLACEMENTS WHERE STATUS IN ('Submitted', 'AM Submitted', 'Onboarding', 'Approved', 'Approved-HSG', 'PERM', 'Terminated', 'Completed') AND (is_count_as_a_deal is null or is_count_as_a_deal = 'Yes') AND date_end is not null order by id";
    private final String queryFinal = "METRICS_PIPELINE_UAT.METRICS_UAT.AWO_YELLOWS ORDER BY ID";

    private final String queryMaxDiffWeeks = "select CAST(MAX(datediff(DAY, date_attributed_to, TO_TIMESTAMP(DATE_END/1000)))/7 AS INT) as Max_Difference_In_weeks FROM METRICS_PIPELINE_UAT.METRICS_UAT.AWO_YELLOWS";
    List<String> yellowZoneColumns = new ArrayList<>();


    public YellowZoneMetric() throws SQLException {
    }

   public void extractSourceFile(){

       yellowZoneColumns.add("ID");

        extractFile(sourceYellowZoneFile, yellowZoneColumns, querySource, "SELECT DISTINCT ");
    }

    public void extractFinalFile(){

        yellowZoneColumns.add("ID");

        extractFile(finalYellowZoneFile, yellowZoneColumns, queryFinal, "SELECT DISTINCT ");
    }

    public boolean comparingYellowZoneFiles_Successful(){

       return compareCSVFiles(sourceYellowZoneFile, finalYellowZoneFile);
    }

    public boolean expectedColumnsOnYellowZoneFinalTable() throws SQLException {

        List<String> yellowZoneExpectedColumns =  new ArrayList<>();

        yellowZoneExpectedColumns.add("CANDIDATE");
        yellowZoneExpectedColumns.add("DIVISION");
        yellowZoneExpectedColumns.add("CLIENT_CORPORATION");
        yellowZoneExpectedColumns.add("CLIENT_CONTACT");
        yellowZoneExpectedColumns.add("JOB_ORDER");
        yellowZoneExpectedColumns.add("VENDOR_ID");
        yellowZoneExpectedColumns.add("EMPLOYEE_TYPE");
        yellowZoneExpectedColumns.add("DISCOUNT_FOR_SPREAD_REPORT");
        yellowZoneExpectedColumns.add("DATE_BEGIN");
        yellowZoneExpectedColumns.add("DATE_END");
        yellowZoneExpectedColumns.add("HOURS_OF_OPERATION");
        yellowZoneExpectedColumns.add("ISSUED_EQUIPMENT");
        yellowZoneExpectedColumns.add("WORKING_IN_STATE");
        yellowZoneExpectedColumns.add("INTERN_LOCATION");
        yellowZoneExpectedColumns.add("IS_CANADA_INVOLVED");
        yellowZoneExpectedColumns.add("BDM");
        yellowZoneExpectedColumns.add("IS_GO_LIVE_JOB");
        yellowZoneExpectedColumns.add("ZONE");

        return areAllExpectedColumns(yellowZoneExpectedColumns, getColumnsName("SELECT * FROM METRICS_PIPELINE_UAT.METRICS_UAT.AWO_YELLOWS"));
    }

    public int getMaxWeeksDiff(){

        return getColumnValue(queryMaxDiffWeeks);
    }

}
