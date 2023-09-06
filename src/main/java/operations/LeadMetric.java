package operations;

import base.Wrappers;

;
import java.sql.SQLException;
import java.sql.Statement;

public class LeadMetric extends Wrappers {
    private final String sourceLeadsFile = "LEADS/LeadsSourceData.csv";
    private final String finalLeadsFile = "LEADS/LeadsFinalData.csv";
    private final String sourceLeadsFileMissing = "LEADS/LeadsSourceData_MissingRows.csv";
    private final String finalLeadsFileMissing = "LEADS/LeadsFinalData_MissingRows.csv";
    private final String sourceLeadsFileDataError = "LEADS/LeadsSourceData_DataError.csv";
    private final String finalLeadsFileDataError = "LEADS/LeadsFinalData_DataError.csv";
    private final String querySource = "SELECT * FROM PIPELINE_CLEAN.BULLHORN_CLEAN.LEADS where IS_DELETED = false AND CATEGORIES_ID != '[]' ORDER BY LEAD_ID";
    private final String queryFinal = "SELECT * FROM METRICS_PIPELINE_UAT.METRICS_UAT.LEADS ORDER BY LEAD_ID";


    public LeadMetric() throws SQLException {
    }

    public void extractSourceFile(){

        extractFile(sourceLeadsFile, "LEAD_ID", querySource);
    }

    public void extractFinalFile(){

        extractFile(finalLeadsFile, "LEAD_ID", queryFinal);
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
