import base.Wrappers;
import operations.AMContactDBAsMetric;
import operations.FinishesMetric;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;

public class AMContactDBAsTestCases extends Wrappers {

    AMContactDBAsMetric AMContactDBAsMetric;

    public AMContactDBAsTestCases() throws SQLException {
        AMContactDBAsMetric = new AMContactDBAsMetric();
    }

    @Test (description = "Validating that information of both Source and Final files are equal")
    public void sourceAndFinalFilesMatch() throws SQLException {

        AMContactDBAsMetric.extractSourceFile();
        AMContactDBAsMetric.extractFinalFile();
        closeConnections();
        Assert.assertTrue(AMContactDBAsMetric.comparingAMContactDBAsFiles_Successful(), "Files are not equal");
    }

    @Test (description = "Validating that expected columns are included on Final file")
    public void columnsAreOnFinal() throws SQLException {

        Assert.assertTrue(AMContactDBAsMetric.expectedColumnsOnAMContactDBAsFinalTable(), "Not all expected columns are in the Final file");
    }

}
