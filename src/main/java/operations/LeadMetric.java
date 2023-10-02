package operations;

import base.Wrappers;

;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeadMetric extends Wrappers {
    private final String sourceLeadsFile = "LEADS/LeadsSourceData.csv";
    private final String finalLeadsFile = "LEADS/LeadsFinalData.csv";
    private final String querySource = "PIPELINE_CLEAN.BULLHORN.LEADS where IS_DELETED = false AND CATEGORIES_ID != '[]' ORDER BY LEAD_ID";
    private final String queryFinal = "METRICS_PIPELINE_UAT.METRICS_UAT.LEADS ORDER BY LEAD_ID";
    List<String> leadsColumns = new ArrayList<>();


    public LeadMetric() throws SQLException {
    }

   public void extractSourceFile(){

        leadsColumns.add("LEAD_ID");

        extractFile(sourceLeadsFile, leadsColumns, querySource, "SELECT DISTINCT ");
    }

    public void extractFinalFile(){

        leadsColumns.add("LEAD_ID");

        extractFile(finalLeadsFile, leadsColumns, queryFinal, "SELECT DISTINCT ");
    }

    public boolean comparingLeadsFiles_Successful(){

       return compareCSVFiles(sourceLeadsFile, finalLeadsFile);
    }

    public boolean expectedColumnsOnLeadsFinalTable() throws SQLException {

        List<String> leadsExpectedColumns =  new ArrayList<>();

        leadsExpectedColumns.add("RECRUITER_ID");
        leadsExpectedColumns.add("SPECIALTIES_NAME");
        leadsExpectedColumns.add("SPECIALTIES_ID");
        leadsExpectedColumns.add("SECURITY_CLEARANCE");
        leadsExpectedColumns.add("TYPE");
        leadsExpectedColumns.add("JOB_TITLE");
        leadsExpectedColumns.add("PROGRAM");
        leadsExpectedColumns.add("NEW_HIRING_MANAGER");
        leadsExpectedColumns.add("NEW_COMPANY");
        leadsExpectedColumns.add("NEW_CANDIDATE");
        leadsExpectedColumns.add("LEAD_SOURCE");
        leadsExpectedColumns.add("LEAD_ID");
        leadsExpectedColumns.add("INTERN_ORIGIN");
        leadsExpectedColumns.add("EXISTING_HIRING_MANAGER");
        leadsExpectedColumns.add("EXISTING_CANDIDATE");
        leadsExpectedColumns.add("DATE_ADDED");
        leadsExpectedColumns.add("COMPANY_NAME");
        leadsExpectedColumns.add("CLIENT_CORPORATION");
        leadsExpectedColumns.add("CATEGORY_ID");
        leadsExpectedColumns.add("ASSIGNED_TO_ID");
        leadsExpectedColumns.add("COMPANY_PHONE_NUMBER");
        leadsExpectedColumns.add("HIRING_MANAGER_PHONE_NUMBER");
        leadsExpectedColumns.add("IS_DELETED");

        return areAllExpectedColumns(leadsExpectedColumns, getColumnsName("SELECT * FROM METRICS_PIPELINE_UAT.METRICS_UAT.LEADS"));
    }

}
