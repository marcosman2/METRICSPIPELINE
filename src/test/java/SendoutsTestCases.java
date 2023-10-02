import base.Wrappers;
import operations.JobSubmissionMetric;
import operations.SendoutsMetric;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.SQLException;

public class SendoutsTestCases extends Wrappers {

    SendoutsMetric sendoutsMetric;

    public SendoutsTestCases() throws SQLException {
        sendoutsMetric = new SendoutsMetric();
    }

    @Test (description = "Validating that information of both Source and Final files are equal")
    public void sourceAndFinalFilesMatch() throws SQLException, IOException {

        sendoutsMetric.extractSourceFile();
        sendoutsMetric.extractFinalFile();
        closeConnections();
        Assert.assertTrue(sendoutsMetric.comparingSendoutsFiles_Successful(), "Files are not equal");
    }

    @Test (description = "Validating that expected columns are included on Final file")
    public void columnsAreOnFinal() throws SQLException {

        Assert.assertTrue(sendoutsMetric.expectedColumnsOnSendoutsFinalTable(), "Not all expected columns are in the Final file");
    }

}
