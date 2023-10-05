import base.TestReport;
import base.Wrappers;
import operations.InHousesMetric;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.sql.SQLException;

public class InHousesTestCases extends Wrappers {

    InHousesMetric inHousesMetric;

    public InHousesTestCases() throws SQLException {
        inHousesMetric = new InHousesMetric();
    }

    @BeforeClass
    public void initializeSuiteReport(){

        TestReport.initialize("InHouses");
    }

    @BeforeMethod
    public void createTestReport(ITestResult result) {
        TestReport.createTest(result.getMethod().getDescription());
    }

    @Test (description = "Validating that in Houses Source and Final files match")
    public void sourceAndFinalFilesMatch() throws SQLException {

        inHousesMetric.extractSourceFile();
        inHousesMetric.extractFinalFile();
        closeConnections();
        Assert.assertTrue(inHousesMetric.comparingInHousesFiles_Successful(), "Files are not equal");
    }

    @Test (description = "Validating that expected columns are included on In Houses metrics table")
    public void columnsAreOnFinal() throws SQLException {

        Assert.assertTrue(inHousesMetric.expectedColumnsOnInHousesFinalTable(), "Not all expected columns are in the Final file");
    }

    @AfterMethod
    public void tearDown(Method method) {

        TestReport.flushReport();
    }

}
