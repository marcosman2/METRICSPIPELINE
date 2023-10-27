import base.TestReport;
import base.Wrappers;
import operations.ScheduledCallsMetric;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.sql.SQLException;

public class ScheduledCallsTestCases extends Wrappers {

    ScheduledCallsMetric scheduledCallsMetric;

    public ScheduledCallsTestCases() throws SQLException {
        scheduledCallsMetric = new ScheduledCallsMetric();
    }

    @BeforeClass
    public void initializeSuiteReport(){

        TestReport.initialize("Deals");
    }

    @BeforeMethod
    public void createTestReport(ITestResult result) {
        TestReport.createTest(result.getMethod().getDescription());
    }

    @Test (description = "Validating that Deals Source and Final files match")
    public void sourceAndFinalFilesMatch() throws SQLException {

        scheduledCallsMetric.extractSourceFile();
        scheduledCallsMetric.extractFinalFile();
        closeConnections();
        Assert.assertTrue(scheduledCallsMetric.comparingScheduledCallsFiles(), "Files are not equal");
    }

    @Test (description = "Validating that expected columns are included on Deals metrics table")
    public void columnsAreOnFinal() throws SQLException {

        Assert.assertTrue(scheduledCallsMetric.expectedColumnsOnScheduledCallsFinalTable(), "Not all expected columns are in the Final file");
    }

    @AfterMethod
    public void tearDown(Method method) {

        TestReport.flushReport();
    }

}
