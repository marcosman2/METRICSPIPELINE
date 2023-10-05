import base.TestReport;
import base.Wrappers;
import operations.JobsMetric;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.sql.SQLException;

public class JobsTestCases extends Wrappers {

    JobsMetric jobsMetric;

    public JobsTestCases() throws SQLException {
        jobsMetric = new JobsMetric();
    }

    @BeforeClass
    public void initializeSuiteReport(){

        TestReport.initialize("Jobs");
    }

    @BeforeMethod
    public void createTestReport(ITestResult result) {
        TestReport.createTest(result.getMethod().getDescription());
    }

    @Test (description = "Validating that Jobs Source and Final files match")
    public void sourceAndFinalFilesMatch() throws SQLException {

        jobsMetric.extractSourceFile();
        jobsMetric.extractFinalFile();
        closeConnections();
        Assert.assertTrue(jobsMetric.comparingJobsFiles_Successful(), "Files are not equal");
    }

    @Test (description = "Validating that expected columns are included on Jobs metrics table")
    public void columnsAreOnFinal() throws SQLException {

        Assert.assertTrue(jobsMetric.expectedColumnsOnJobsFinalTable(), "Not all expected columns are in the Final file");
    }

    @AfterMethod
    public void tearDown(Method method) {

        TestReport.flushReport();
    }

}
