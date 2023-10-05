import base.TestReport;
import base.Wrappers;
import operations.AMContactReqMetric;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.sql.SQLException;

public class AMContactReqTestCases extends Wrappers {

    AMContactReqMetric AMContactReqMetric;

    public AMContactReqTestCases() throws SQLException {
        AMContactReqMetric = new AMContactReqMetric();
    }

    @BeforeClass
    public void initializeSuiteReport(){

        TestReport.initialize("AMContactReqDBAs");
    }

    @BeforeMethod
    public void createTestReport(ITestResult result) {
        TestReport.createTest(result.getMethod().getDescription());
    }

    @Test (description = "Validating that AM Contact Req Source and Final files match")
    public void sourceAndFinalFilesMatch() throws SQLException {

        AMContactReqMetric.extractSourceFile();
        AMContactReqMetric.extractFinalFile();
        closeConnections();
        Assert.assertTrue(AMContactReqMetric.comparingAMContactReqFiles_Successful(), "Files are not equal");
    }

    @Test (description = "Validating that expected columns are included on AM Contact Req metrics table")
    public void columnsAreOnFinal() throws SQLException {

        Assert.assertTrue(AMContactReqMetric.expectedColumnsOnAMContactReqFinalTable(), "Not all expected columns are in the Final file");
    }

    @AfterMethod
    public void tearDown(Method method) {

        TestReport.flushReport();
    }

}
