package operations;

import base.Wrappers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

;

public class PipelineMetric extends Wrappers {
    private final String sourcePipelinesFile = "PIPELINES/PipelinesSourceData.csv";
    private final String finalPipelinesFile = "PIPELINES/PipelinesFinalData.csv";
    private final String querySource = "PIPELINE_CLEAN.BULLHORN.PIPELINES WHERE IS_DELETED = FALSE ORDER BY ID;";
    private final String queryFinal = "METRICS_PIPELINE_UAT.METRICS_UAT.PIPELINES ORDER BY ID";
    List<String> pipelinesColumns = new ArrayList<>();


    public PipelineMetric() throws SQLException {
    }

   public void extractSourceFile(){

        pipelinesColumns.add("ID");

        extractFile(sourcePipelinesFile, pipelinesColumns, querySource, "SELECT ");
    }

    public void extractFinalFile(){

        pipelinesColumns.add("ID");

        extractFile(finalPipelinesFile, pipelinesColumns, queryFinal, "SELECT ");
    }

    public boolean comparingPipelinesFiles(){

       return compareCSVFiles(sourcePipelinesFile, finalPipelinesFile);
    }

    public boolean expectedColumnsOnPipelinesFinalTable() throws SQLException {

        List<String> pipelinesExpectedColumns =  new ArrayList<>();

        pipelinesExpectedColumns.add("OWNER");
        pipelinesExpectedColumns.add("CATEGORIES_ID");
        pipelinesExpectedColumns.add("DATE_ADDED");
        pipelinesExpectedColumns.add("ID");
        pipelinesExpectedColumns.add("CLIENT_CONTACT");

        return areAllExpectedColumns(pipelinesExpectedColumns, getColumnsName("SELECT * FROM METRICS_PIPELINE_UAT.METRICS_UAT.PIPELINES"));
    }

}
