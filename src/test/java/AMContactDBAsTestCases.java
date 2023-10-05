import base.TestReport;
import base.Wrappers;
import operations.AMContactDBAsMetric;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.sql.SQLException;

public class AMContactDBAsTestCases extends Wrappers {

    AMContactDBAsMetric AMContactDBAsMetric;

    public AMContactDBAsTestCases() throws SQLException {
        AMContactDBAsMetric = new AMContactDBAsMetric();
    }

    @BeforeClass
    public void initializeSuiteReport(){

        TestReport.initialize("AMContactDBAs");
    }

    @BeforeMethod
    public void createTestReport(ITestResult result) {
        TestReport.createTest(result.getMethod().getDescription());
    }

    @Test (description = "Validating that AM Contact DBAs Source and Final files match")
    public void sourceAndFinalFilesMatch() throws SQLException {

        AMContactDBAsMetric.extractSourceFile();
        AMContactDBAsMetric.extractFinalFile();
        closeConnections();
        Assert.assertTrue(AMContactDBAsMetric.comparingAMContactDBAsFiles_Successful(), "Files are not equal");
    }

    @Test (description = "Validating that expected columns are included on AM Contact DBAs metrics table")
    public void columnsAreOnFinal() throws SQLException {

        Assert.assertTrue(AMContactDBAsMetric.expectedColumnsOnAMContactDBAsFinalTable(), "Not all expected columns are in the Final file");
    }

    @AfterMethod
    public void tearDown(Method method) {

        TestReport.flushReport();
    }

}
