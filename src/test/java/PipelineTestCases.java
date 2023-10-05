import base.TestReport;
import base.Wrappers;
import operations.PipelineMetric;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.sql.SQLException;

public class PipelineTestCases extends Wrappers {

    PipelineMetric pipelineMetric;

    public PipelineTestCases() throws SQLException {
        pipelineMetric = new PipelineMetric();
    }

    @BeforeClass
    public void initializeSuiteReport(){

        TestReport.initialize("Pipeline");
    }

    @BeforeMethod
    public void createTestReport(ITestResult result) {
        TestReport.createTest(result.getMethod().getDescription());
    }

    @Test (description = "Validating that Pipelines Source and Final files match")
    public void sourceAndFinalFilesMatch() throws SQLException {

        pipelineMetric.extractSourceFile();
        pipelineMetric.extractFinalFile();
        closeConnections();
        Assert.assertTrue(pipelineMetric.comparingPipelinesFiles(), "Files are not equal");
    }

    @Test (description = "Validating that expected columns are included on Pipelines metrics table")
    public void columnsAreOnFinal() throws SQLException {

        Assert.assertTrue(pipelineMetric.expectedColumnsOnPipelinesFinalTable(), "Not all expected columns are in the Final file");
    }

    @AfterMethod
    public void tearDown(Method method) {

        TestReport.flushReport();
    }

}
