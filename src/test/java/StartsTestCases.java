import base.TestReport;
import base.Wrappers;
import operations.InHousesMetric;
import operations.StartsMetric;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.sql.SQLException;

public class StartsTestCases extends Wrappers {

    StartsMetric startsMetric;

    public StartsTestCases() throws SQLException {
        startsMetric = new StartsMetric();
    }

    @BeforeClass
    public void initializeSuiteReport(){

        TestReport.initialize("Starts");
    }

    @BeforeMethod
    public void createTestReport(ITestResult result) {
        TestReport.createTest(result.getMethod().getDescription());
    }

    @Test (description = "Validating that Starts Source and Final files match")
    public void sourceAndFinalFilesMatch() throws SQLException {

        startsMetric.extractSourceFile();
        startsMetric.extractFinalFile();
        closeConnections();
        Assert.assertTrue(startsMetric.comparingStartsFiles_Successful(), "Files are not equal");
    }

    @Test (description = "Validating that expected columns are included on Starts metrics table")
    public void columnsAreOnFinal() throws SQLException {

        Assert.assertTrue(startsMetric.expectedColumnsOnStartsFinalTable(), "Not all expected columns are in the Final file");
    }

    @AfterMethod
    public void tearDown(Method method) {

        TestReport.flushReport();
    }

}
