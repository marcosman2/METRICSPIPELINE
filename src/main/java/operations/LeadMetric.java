package operations;

import base.Wrappers;

;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeadMetric extends Wrappers {
    private final String sourceLeadsFile = "LEADS/LeadsSourceData.csv";
    private final String finalLeadsFile = "LEADS/LeadsFinalData.csv";
    private final String sourceLeadsFileMissing = "LEADS/LeadsSourceData_MissingRows.csv";
    private final String finalLeadsFileMissing = "LEADS/LeadsFinalData_MissingRows.csv";
    private final String sourceLeadsFileDataError = "LEADS/LeadsSourceData_DataError.csv";
    private final String finalLeadsFileDataError = "LEADS/LeadsFinalData_DataError.csv";
    private final String querySource = "PIPELINE_CLEAN.BULLHORN_CLEAN.LEADS where IS_DELETED = false AND CATEGORIES_ID != '[]' ORDER BY LEAD_ID";
    private final String queryFinal = "METRICS_PIPELINE_UAT.METRICS_UAT.LEADS ORDER BY LEAD_ID";
    List<String> leadsColumns = new ArrayList<>();


    public LeadMetric() throws SQLException {
    }

   public void extractSourceFile(){

        leadsColumns.add("LEAD_ID");

        extractFile(sourceLeadsFile, leadsColumns, querySource);
    }

    public void extractFinalFile(){

        leadsColumns.add("LEAD_ID");

        extractFile(finalLeadsFile, leadsColumns, queryFinal);
    }

    public boolean comparingLeadsFiles_Successful(){

       return compareCSVFiles(sourceLeadsFile, finalLeadsFile);
    }

    public boolean comparingLeadsFiles_MissingRows(){

        return compareCSVFiles(sourceLeadsFileMissing, finalLeadsFileMissing);
    }

    public boolean comparingLeadsFiles_DataError(){

        return compareCSVFiles(sourceLeadsFileDataError, finalLeadsFileDataError);
    }

}
