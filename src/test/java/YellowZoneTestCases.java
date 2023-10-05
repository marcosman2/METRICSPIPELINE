import base.TestReport;
import base.Wrappers;
import operations.YellowZoneMetric;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.sql.SQLException;

public class YellowZoneTestCases extends Wrappers {

    YellowZoneMetric yellowZoneMetric;

    public YellowZoneTestCases() throws SQLException {
        yellowZoneMetric = new YellowZoneMetric();
    }

    @BeforeClass
    public void initializeSuiteReport(){

        TestReport.initialize("YellowZone");
    }

    @BeforeMethod
    public void createTestReport(ITestResult result) {
        TestReport.createTest(result.getMethod().getDescription());
    }

    @Test (description = "Validating that information of both Source and Final files are equal")
    public void sourceAndFinalFilesMatch() throws SQLException {

        yellowZoneMetric.extractSourceFile();
        yellowZoneMetric.extractFinalFile();
        closeConnections();
        Assert.assertTrue(yellowZoneMetric.comparingYellowZoneFiles_Successful(), "Files are not equal");
    }

    @Test (description = "Validating that Yellow Zone Source and Final files match")
    public void columnsAreOnFinal() throws SQLException {

        Assert.assertTrue(yellowZoneMetric.expectedColumnsOnYellowZoneFinalTable(), "Not all expected columns are in the Final file");
    }

    @Test (description = "Validating that expected columns are included on Yellow Zone metrics table")
    public void maxWeeksDiff() throws SQLException {

        Assert.assertTrue(yellowZoneMetric.getMaxWeeksDiff() <= 6);
        closeConnections();
    }

    @AfterMethod
    public void tearDown(Method method) {

        TestReport.flushReport();
    }

}
