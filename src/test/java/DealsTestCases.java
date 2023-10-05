import base.TestReport;
import base.Wrappers;
import operations.DealsMetric;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.sql.SQLException;

public class DealsTestCases extends Wrappers {

    DealsMetric dealsMetric;

    public DealsTestCases() throws SQLException {
        dealsMetric = new DealsMetric();
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

        dealsMetric.extractSourceFile();
        dealsMetric.extractFinalFile();
        closeConnections();
        Assert.assertTrue(dealsMetric.comparingDealsFiles_Successful(), "Files are not equal");
    }

    @Test (description = "Validating that expected columns are included on Deals metrics table")
    public void columnsAreOnFinal() throws SQLException {

        Assert.assertTrue(dealsMetric.expectedColumnsOnDealsFinalTable(), "Not all expected columns are in the Final file");
    }

    @AfterMethod
    public void tearDown(Method method) {

        TestReport.flushReport();
    }

}
