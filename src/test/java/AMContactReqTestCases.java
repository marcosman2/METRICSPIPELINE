import base.Wrappers;
import operations.AMContactReqMetric;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;

public class AMContactReqTestCases extends Wrappers {

    AMContactReqMetric AMContactReqMetric;

    public AMContactReqTestCases() throws SQLException {
        AMContactReqMetric = new AMContactReqMetric();
    }

    @Test (description = "Validating that information of both Source and Final files are equal")
    public void sourceAndFinalFilesMatch() throws SQLException {

        AMContactReqMetric.extractSourceFile();
        AMContactReqMetric.extractFinalFile();
        closeConnections();
        Assert.assertTrue(AMContactReqMetric.comparingAMContactReqFiles_Successful(), "Files are not equal");
    }

    @Test (description = "Validating that expected columns are included on Final file")
    public void columnsAreOnFinal() throws SQLException {

        Assert.assertTrue(AMContactReqMetric.expectedColumnsOnAMContactReqFinalTable(), "Not all expected columns are in the Final file");
    }

}
