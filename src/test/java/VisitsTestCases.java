import base.TestReport;
import base.Wrappers;
import operations.DealsMetric;
import operations.VisitsMetric;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.sql.SQLException;

public class VisitsTestCases extends Wrappers {

    VisitsMetric visitsMetric;

    public VisitsTestCases() throws SQLException {
        visitsMetric = new VisitsMetric();
    }

    @BeforeClass
    public void initializeSuiteReport(){

        TestReport.initialize("Visits");
    }

    @BeforeMethod
    public void createTestReport(ITestResult result) {
        TestReport.createTest(result.getMethod().getDescription());
    }

    @Test (description = "Validating that Visits Source and Final files match")
    public void sourceAndFinalFilesMatch() throws SQLException {

        visitsMetric.extractSourceFile();
        visitsMetric.extractFinalFile();
        closeConnections();
        Assert.assertTrue(visitsMetric.comparingVisitsFiles_Successful(), "Files are not equal");
    }

    @Test (description = "Validating that expected columns are included on Visits metrics table")
    public void columnsAreOnFinal() throws SQLException {

        Assert.assertTrue(visitsMetric.expectedColumnsOnVisitsFinalTable(), "Not all expected columns are in the Final file");
    }

    @AfterMethod
    public void tearDown(Method method) {

        TestReport.flushReport();
    }

}
