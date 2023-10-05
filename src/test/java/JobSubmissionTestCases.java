import base.TestReport;
import base.Wrappers;
import operations.AMContactReqMetric;
import operations.JobSubmissionMetric;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.sql.SQLException;

public class JobSubmissionTestCases extends Wrappers {

    JobSubmissionMetric jobSubmissionMetric;

    public JobSubmissionTestCases() throws SQLException {
        jobSubmissionMetric = new JobSubmissionMetric();
    }

    @BeforeClass
    public void initializeSuiteReport(){

        TestReport.initialize("JobSubmission");
    }

    @BeforeMethod
    public void createTestReport(ITestResult result) {
        TestReport.createTest(result.getMethod().getDescription());
    }

    @Test (description = "Validating that Job Submission Source and Final files match")
    public void sourceAndFinalFilesMatch() throws SQLException {

        jobSubmissionMetric.extractSourceFile();
        jobSubmissionMetric.extractFinalFile();
        closeConnections();
        Assert.assertTrue(jobSubmissionMetric.comparingJobSubmissionFiles_Successful(), "Files are not equal");
    }

    @Test (description = "Validating that expected columns are included on Job Submission metrics table")
    public void columnsAreOnFinal() throws SQLException {

        Assert.assertTrue(jobSubmissionMetric.expectedColumnsOnJobSubmissionFinalTable(), "Not all expected columns are in the Final file");
    }

    @AfterMethod
    public void tearDown(Method method) {

        TestReport.flushReport();
    }

}
