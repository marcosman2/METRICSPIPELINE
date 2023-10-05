import base.TestReport;
import base.Wrappers;
import operations.FinishesMetric;
import operations.StartsMetric;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.sql.SQLException;

public class FinishesTestCases extends Wrappers {

    FinishesMetric finishesMetric;

    public FinishesTestCases() throws SQLException {
        finishesMetric = new FinishesMetric();
    }

    @BeforeClass
    public void initializeSuiteReport(){

        TestReport.initialize("Finishes");
    }

    @BeforeMethod
    public void createTestReport(ITestResult result) {
        TestReport.createTest(result.getMethod().getDescription());
    }

    @Test (description = "Validating that Finishes Source and Final files match")
    public void sourceAndFinalFilesMatch() throws SQLException {

        finishesMetric.extractSourceFile();
        finishesMetric.extractFinalFile();
        closeConnections();
        Assert.assertTrue(finishesMetric.comparingFinishesFiles_Successful(), "Files are not equal");
    }

    @Test (description = "Validating that expected columns are included on Finishes metrics table")
    public void columnsAreOnFinal() throws SQLException {

        Assert.assertTrue(finishesMetric.expectedColumnsOnFinishesFinalTable(), "Not all expected columns are in the Final file");
    }

    @AfterMethod
    public void tearDown(Method method) {

        TestReport.flushReport();
    }

}
