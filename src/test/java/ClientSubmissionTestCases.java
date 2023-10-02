import base.Wrappers;
import operations.ClientSubmissionMetric;
import operations.JobSubmissionMetric;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;

public class ClientSubmissionTestCases extends Wrappers {

    ClientSubmissionMetric clientSubmissionMetric;

    public ClientSubmissionTestCases() throws SQLException {
        clientSubmissionMetric = new ClientSubmissionMetric();
    }

    @Test (description = "Validating that information of both Source and Final files are equal")
    public void sourceAndFinalFilesMatch() throws SQLException {

        clientSubmissionMetric.extractSourceFile();
        clientSubmissionMetric.extractFinalFile();
        closeConnections();
        Assert.assertTrue(clientSubmissionMetric.comparingClientSubmissionFiles_Successful(), "Files are not equal");
    }

    @Test (description = "Validating that expected columns are included on Final file")
    public void columnsAreOnFinal() throws SQLException {

        Assert.assertTrue(clientSubmissionMetric.expectedColumnsOnClientSubmissionFinalTable(), "Not all expected columns are in the Final file");
    }

}
