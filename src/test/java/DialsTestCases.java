import base.TestReport;
import base.Wrappers;
import operations.DialsMetric;
import operations.LeadMetric;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.sql.SQLException;

public class DialsTestCases extends Wrappers {

    DialsMetric dialsMetric;

    public DialsTestCases() throws SQLException {
        dialsMetric = new DialsMetric();
    }

    @BeforeClass
    public void initializeSuiteReport(){

        TestReport.initialize("Lead");
    }

    @BeforeMethod
    public void createTestReport(ITestResult result) {
        TestReport.createTest(result.getMethod().getDescription());
    }

    @Test (description = "Validating that Dials Source and Final files match")
    public void sourceAndFinalFilesMatch() throws SQLException {

        dialsMetric.extractSourceFile();
        dialsMetric.extractFinalFile();
        closeConnections();
        Assert.assertTrue(dialsMetric.comparingDialsFiles_Successful(), "Files are not equal");
    }

    @Test (description = "Validating that expected columns are included on Leads metrics table")
    public void columnsAreOnFinal() throws SQLException {

        Assert.assertTrue(dialsMetric.expectedColumnsOnDialsFinalTable(), "Not all expected columns are in the Final file");
    }

    @AfterMethod
    public void tearDown(Method method) {

        TestReport.flushReport();
    }

}
