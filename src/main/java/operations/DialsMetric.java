package operations;

import base.Wrappers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

;

public class DialsMetric extends Wrappers {
    private final String sourceDialsFile = "DIALS/DialsSourceData.csv";
    private final String finalDialsFile = "DIALS/DialsFinalData.csv";
    private final String querySource = "PIPELINE_CLEAN.FUZE.CALLS ORDER BY ID";
    private final String queryFinal = "METRIC_PIPELINE_PROD.METRICS_PROD.DIALS ORDER BY ID";
    List<String> dialsColumns = new ArrayList<>();


    public DialsMetric() throws SQLException {
    }

   public void extractSourceFile(){

        dialsColumns.add("ID");

        extractFile(sourceDialsFile, dialsColumns, querySource, "SELECT DISTINCT ");
    }

    public void extractFinalFile(){

        dialsColumns.add("ID");

        extractFile(finalDialsFile, dialsColumns, queryFinal, "SELECT DISTINCT ");
    }

    public boolean comparingDialsFiles_Successful(){

       return compareCSVFiles(sourceDialsFile, finalDialsFile);
    }

    public boolean expectedColumnsOnDialsFinalTable() throws SQLException {

        List<String> dialsExpectedColumns =  new ArrayList<>();

        dialsExpectedColumns.add("ID");
        dialsExpectedColumns.add("STARTED_AT");
        dialsExpectedColumns.add("FROM_NAME");
        dialsExpectedColumns.add("FROM_USERNAME");
        dialsExpectedColumns.add("FROM_DEPARTMENTNAME");

        return areAllExpectedColumns(dialsExpectedColumns, getColumnsName("SELECT * FROM METRIC_PIPELINE_PROD.METRICS_PROD.DIALS"));
    }

}
