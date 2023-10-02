import base.Wrappers;
import operations.AMContactDBAsMetric;
import operations.RecruiterCandidateDBAsMetric;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;

public class RecruiterCandidateDBAsTestCases extends Wrappers {

    RecruiterCandidateDBAsMetric recruiterCandidateDBAsMetric;

    public RecruiterCandidateDBAsTestCases() throws SQLException {
        recruiterCandidateDBAsMetric = new RecruiterCandidateDBAsMetric();
    }

    @Test (description = "Validating that information of both Source and Final files are equal")
    public void sourceAndFinalFilesMatch() throws SQLException {

        recruiterCandidateDBAsMetric.extractSourceFile();
        recruiterCandidateDBAsMetric.extractFinalFile();
        closeConnections();
        Assert.assertTrue(recruiterCandidateDBAsMetric.comparingRecruiterCandidateDBAsFiles_Successful(), "Files are not equal");
    }

    @Test (description = "Validating that expected columns are included on Final file")
    public void columnsAreOnFinal() throws SQLException {

        Assert.assertTrue(recruiterCandidateDBAsMetric.expectedColumnsOnRecruiterCandidateDBAsFinalTable(), "Not all expected columns are in the Final file");
    }

}
