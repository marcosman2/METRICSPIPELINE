package operations;

import base.Wrappers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

;

public class AMContactDBAsMetric extends Wrappers {
    private final String sourceAMContactDBAsFile = "AM_CONTACTS/AMContactsSourceData.csv";
    private final String finalAMContactDBAsFile = "AM_CONTACTS/AMContactsFinalData.csv";
    private final String querySource = "PIPELINE_CLEAN.BULLHORN.CONTACTS WHERE IS_DELETED = FALSE ORDER BY ID";
    private final String queryFinal = "METRICS_PIPELINE_UAT.METRICS_UAT.ACCOUNT_MANAGEMENT_CONTACT_DBAS ORDER BY ID";
    List<String> AMContactDBAsColumns = new ArrayList<>();


    public AMContactDBAsMetric() throws SQLException {
    }

   public void extractSourceFile(){

       AMContactDBAsColumns.add("ID");

        extractFile(sourceAMContactDBAsFile, AMContactDBAsColumns, querySource, "SELECT ");
    }

    public void extractFinalFile(){

        AMContactDBAsColumns.add("ID");

        extractFile(finalAMContactDBAsFile, AMContactDBAsColumns, queryFinal, "SELECT ");
    }

    public boolean comparingAMContactDBAsFiles_Successful(){

       return compareCSVFiles(sourceAMContactDBAsFile, finalAMContactDBAsFile);
    }

    public boolean expectedColumnsOnAMContactDBAsFinalTable() throws SQLException {

        List<String> AMContactDBAsExpectedColumns =  new ArrayList<>();

        AMContactDBAsExpectedColumns.add("ACTIVE_PLACEMENTS_TOTAL");
        AMContactDBAsExpectedColumns.add("ADDED_BY_USER");
        AMContactDBAsExpectedColumns.add("ADDRESS_ADDRESS1");
        AMContactDBAsExpectedColumns.add("ADDRESS_ADDRESS2");
        AMContactDBAsExpectedColumns.add("ADDRESS_CITY");
        AMContactDBAsExpectedColumns.add("ADDRESS_COUNTRYCODE");
        AMContactDBAsExpectedColumns.add("ADDRESS_COUNTRYID");
        AMContactDBAsExpectedColumns.add("ADDRESS_COUNTRYNAME");
        AMContactDBAsExpectedColumns.add("ADDRESS_STATE");
        AMContactDBAsExpectedColumns.add("ADDRESS_TIMEZONE");
        AMContactDBAsExpectedColumns.add("ADDRESS_ZIP");
        AMContactDBAsExpectedColumns.add("APPOINTMENTS_TOTAL");
        AMContactDBAsExpectedColumns.add("CATEGORIES_ID");
        AMContactDBAsExpectedColumns.add("CATEGORY");
        AMContactDBAsExpectedColumns.add("CLIENT_CONTACT_ID");
        AMContactDBAsExpectedColumns.add("CLIENT_CORPORATION");
        AMContactDBAsExpectedColumns.add("CLIENT_LOCATIONS");
        AMContactDBAsExpectedColumns.add("COMPANY_NAME");
        AMContactDBAsExpectedColumns.add("DATE_ADDED");
        AMContactDBAsExpectedColumns.add("DIVISION");
        AMContactDBAsExpectedColumns.add("DIVISION_MANAGER");
        AMContactDBAsExpectedColumns.add("EMAIL");
        AMContactDBAsExpectedColumns.add("FIRST_NAME");
        AMContactDBAsExpectedColumns.add("ID");
        AMContactDBAsExpectedColumns.add("INTERN_ORIGIN");
        AMContactDBAsExpectedColumns.add("INTERVIEWS_TOTAL");
        AMContactDBAsExpectedColumns.add("IS_DELETED");
        AMContactDBAsExpectedColumns.add("JOB_ORDERS_TOTAL");
        AMContactDBAsExpectedColumns.add("JOB_SUBMISSIONS_TOTAL");
        AMContactDBAsExpectedColumns.add("LAST_NAME");
        AMContactDBAsExpectedColumns.add("LEAD_FROM");
        AMContactDBAsExpectedColumns.add("LEADS");
        AMContactDBAsExpectedColumns.add("MOBILE");
        AMContactDBAsExpectedColumns.add("NAME");
        AMContactDBAsExpectedColumns.add("NAME_SUFFIX");
        AMContactDBAsExpectedColumns.add("NUM_EMPLOYEES");
        AMContactDBAsExpectedColumns.add("OCCUPATION");
        AMContactDBAsExpectedColumns.add("OPPORTUNITIES_TOTAL");
        AMContactDBAsExpectedColumns.add("OWNER");
        AMContactDBAsExpectedColumns.add("PHONE");
        AMContactDBAsExpectedColumns.add("PLACEMENTS_TOTAL");
        AMContactDBAsExpectedColumns.add("PROGRAM");
        AMContactDBAsExpectedColumns.add("SENDOUTS_TOTAL");
        AMContactDBAsExpectedColumns.add("SOURCE");
        AMContactDBAsExpectedColumns.add("SPECIALTIES");
        AMContactDBAsExpectedColumns.add("SPECIALTIES_ID");
        AMContactDBAsExpectedColumns.add("SPECIALTIES_NAME");
        AMContactDBAsExpectedColumns.add("SPECIALTIES_TOTAL");
        AMContactDBAsExpectedColumns.add("STATUS");
        AMContactDBAsExpectedColumns.add("USER_DATE_ADDED");
        AMContactDBAsExpectedColumns.add("USERNAME");

        return areAllExpectedColumns(AMContactDBAsExpectedColumns, getColumnsName("SELECT * FROM METRICS_PIPELINE_UAT.METRICS_UAT.ACCOUNT_MANAGEMENT_CONTACT_DBAS"));
    }

}
