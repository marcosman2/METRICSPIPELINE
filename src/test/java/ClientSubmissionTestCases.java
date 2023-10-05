import base.TestReport;
import base.Wrappers;
import operations.ClientSubmissionMetric;
import operations.JobSubmissionMetric;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.sql.SQLException;

public class ClientSubmissionTestCases extends Wrappers {

    ClientSubmissionMetric clientSubmissionMetric;

    public ClientSubmissionTestCases() throws SQLException {
        clientSubmissionMetric = new ClientSubmissionMetric();
    }

    @BeforeClass
    public void initializeSuiteReport(){

        TestReport.initialize("ClientSubmission");
    }

    @BeforeMethod
    public void createTestReport(ITestResult result) {
        TestReport.createTest(result.getMethod().getDescription());
    }

    @Test (description = "Validating that Client Submission Source and Final files match")
    public void sourceAndFinalFilesMatch() throws SQLException {

        clientSubmissionMetric.extractSourceFile();
        clientSubmissionMetric.extractFinalFile();
        closeConnections();
        Assert.assertTrue(clientSubmissionMetric.comparingClientSubmissionFiles_Successful(), "Files are not equal");
    }

    @Test (description = "Validating that expected columns are included on Client Submission metrics table")
    public void columnsAreOnFinal() throws SQLException {

        Assert.assertTrue(clientSubmissionMetric.expectedColumnsOnClientSubmissionFinalTable(), "Not all expected columns are in the Final file");
    }

    @AfterMethod
    public void tearDown(Method method) {

        TestReport.flushReport();
    }

}
