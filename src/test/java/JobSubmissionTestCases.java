import base.Wrappers;
import operations.AMContactReqMetric;
import operations.JobSubmissionMetric;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;

public class JobSubmissionTestCases extends Wrappers {

    JobSubmissionMetric jobSubmissionMetric;

    public JobSubmissionTestCases() throws SQLException {
        jobSubmissionMetric = new JobSubmissionMetric();
    }

    @Test (description = "Validating that information of both Source and Final files are equal")
    public void sourceAndFinalFilesMatch() throws SQLException {

        jobSubmissionMetric.extractSourceFile();
        jobSubmissionMetric.extractFinalFile();
        closeConnections();
        Assert.assertTrue(jobSubmissionMetric.comparingJobSubmissionFiles_Successful(), "Files are not equal");
    }

    @Test (description = "Validating that expected columns are included on Final file")
    public void columnsAreOnFinal() throws SQLException {

        Assert.assertTrue(jobSubmissionMetric.expectedColumnsOnJobSubmissionFinalTable(), "Not all expected columns are in the Final file");
    }

}
