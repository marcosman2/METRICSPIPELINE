import base.TestReport;
import base.Wrappers;
import operations.LeadMetric;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.sql.SQLException;

public class LeadTestCases extends Wrappers {

    LeadMetric leadMetric;

    public LeadTestCases() throws SQLException {
        leadMetric = new LeadMetric();
    }

    @BeforeClass
    public void initializeSuiteReport(){

        TestReport.initialize("Lead");
    }

    @BeforeMethod
    public void createTestReport(ITestResult result) {
        TestReport.createTest(result.getMethod().getDescription());
    }

    @Test (description = "Validating that Leads Source and Final files match")
    public void sourceAndFinalFilesMatch() throws SQLException {

        leadMetric.extractSourceFile();
        leadMetric.extractFinalFile();
        closeConnections();
        Assert.assertTrue(leadMetric.comparingLeadsFiles_Successful(), "Files are not equal");
    }

    @Test (description = "Validating that expected columns are included on Leads metrics table")
    public void columnsAreOnFinal() throws SQLException {

        Assert.assertTrue(leadMetric.expectedColumnsOnLeadsFinalTable(), "Not all expected columns are in the Final file");
    }

    @AfterMethod
    public void tearDown(Method method) {

        TestReport.flushReport();
    }

}
