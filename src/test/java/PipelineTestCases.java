import base.Wrappers;
import operations.PipelineMetric;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;

public class PipelineTestCases extends Wrappers {

    PipelineMetric pipelineMetric;

    public PipelineTestCases() throws SQLException {
        pipelineMetric = new PipelineMetric();
    }

    @Test (description = "Validating that information of both Source and Final files are equal")
    public void sourceAndFinalFilesMatch() throws SQLException {

        pipelineMetric.extractSourceFile();
        pipelineMetric.extractFinalFile();
        closeConnections();
        Assert.assertTrue(pipelineMetric.comparingPipelinesFiles(), "Files are not equal");
    }

    @Test (description = "Validating that expected columns are included on Final file")
    public void columnsAreOnFinal() throws SQLException {

        Assert.assertTrue(pipelineMetric.expectedColumnsOnPipelinesFinalTable(), "Not all expected columns are in the Final file");
    }

}
