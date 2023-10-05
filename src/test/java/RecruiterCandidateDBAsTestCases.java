import base.TestReport;
import base.Wrappers;
import operations.AMContactDBAsMetric;
import operations.RecruiterCandidateDBAsMetric;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.sql.SQLException;

public class RecruiterCandidateDBAsTestCases extends Wrappers {

    RecruiterCandidateDBAsMetric recruiterCandidateDBAsMetric;

    public RecruiterCandidateDBAsTestCases() throws SQLException {
        recruiterCandidateDBAsMetric = new RecruiterCandidateDBAsMetric();
    }

    @BeforeClass
    public void initializeSuiteReport(){

        TestReport.initialize("RecruiterCandidateDBAs");
    }

    @BeforeMethod
    public void createTestReport(ITestResult result) {
        TestReport.createTest(result.getMethod().getDescription());
    }

    @Test (description = "Validating that Recruiter Candidate DBAs Source and Final files match")
    public void sourceAndFinalFilesMatch() throws SQLException {

        recruiterCandidateDBAsMetric.extractSourceFile();
        recruiterCandidateDBAsMetric.extractFinalFile();
        closeConnections();
        Assert.assertTrue(recruiterCandidateDBAsMetric.comparingRecruiterCandidateDBAsFiles_Successful(), "Files are not equal");
    }

    @Test (description = "Validating that expected columns are included on Recruiter Candidate DBAs metrics table")
    public void columnsAreOnFinal() throws SQLException {

        Assert.assertTrue(recruiterCandidateDBAsMetric.expectedColumnsOnRecruiterCandidateDBAsFinalTable(), "Not all expected columns are in the Final file");
    }

    @AfterMethod
    public void tearDown(Method method) {

        TestReport.flushReport();
    }

}
