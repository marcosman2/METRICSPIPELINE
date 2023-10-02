import base.Wrappers;
import operations.InHousesMetric;
import operations.StartsMetric;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;

public class StartsTestCases extends Wrappers {

    StartsMetric startsMetric;

    public StartsTestCases() throws SQLException {
        startsMetric = new StartsMetric();
    }

    @Test (description = "Validating that information of both Source and Final files are equal")
    public void sourceAndFinalFilesMatch() throws SQLException {

        startsMetric.extractSourceFile();
        startsMetric.extractFinalFile();
        closeConnections();
        Assert.assertTrue(startsMetric.comparingStartsFiles_Successful(), "Files are not equal");
    }

    @Test (description = "Validating that expected columns are included on Final file")
    public void columnsAreOnFinal() throws SQLException {

        Assert.assertTrue(startsMetric.expectedColumnsOnStartsFinalTable(), "Not all expected columns are in the Final file");
    }

}
