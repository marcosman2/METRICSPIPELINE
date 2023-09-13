import base.Wrappers;
import operations.JobsMetric;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;

public class JobsTestCases extends Wrappers {

    JobsMetric jobsMetric;

    public JobsTestCases() throws SQLException {
        jobsMetric = new JobsMetric();
    }

    @Test (description = "Validating that information of both Source and Final files are equal")
    public void sourceAndFinalFilesMatch() throws SQLException {

        jobsMetric.extractSourceFile();
        jobsMetric.extractFinalFile();
        closeConnections();
        Assert.assertTrue(jobsMetric.comparingJobsFiles_Successful(), "Files are not equal");
    }

    @Test (description = "Validating that expected columns are included on Final file")
    public void columnsAreOnFinal() throws SQLException {

        Assert.assertTrue(jobsMetric.expectedColumnsOnJobsFinalTable(), "Not all expected columns are in the Final file");
    }

}
