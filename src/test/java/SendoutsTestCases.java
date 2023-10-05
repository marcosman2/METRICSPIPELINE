import base.TestReport;
import base.Wrappers;
import operations.JobSubmissionMetric;
import operations.SendoutsMetric;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;

public class SendoutsTestCases extends Wrappers {

    SendoutsMetric sendoutsMetric;

    public SendoutsTestCases() throws SQLException {
        sendoutsMetric = new SendoutsMetric();
    }

    @BeforeClass
    public void initializeSuiteReport(){

        TestReport.initialize("Sendouts");
    }

    @BeforeMethod
    public void createTestReport(ITestResult result) {
        TestReport.createTest(result.getMethod().getDescription());
    }

    @Test (description = "Validating that Sendouts Source and Final files match")
    public void sourceAndFinalFilesMatch() throws SQLException, IOException {

        sendoutsMetric.extractSourceFile();
        sendoutsMetric.extractFinalFile();
        closeConnections();
        Assert.assertTrue(sendoutsMetric.comparingSendoutsFiles_Successful(), "Files are not equal");
    }

    @Test (description = "Validating that expected columns are included on Sendouts metrics table")
    public void columnsAreOnFinal() throws SQLException {

        Assert.assertTrue(sendoutsMetric.expectedColumnsOnSendoutsFinalTable(), "Not all expected columns are in the Final file");
    }

    @AfterMethod
    public void tearDown(Method method) {

        TestReport.flushReport();
    }

}
