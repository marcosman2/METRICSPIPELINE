import base.Wrappers;
import operations.YellowZoneMetric;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;

public class YellowZoneTestCases extends Wrappers {

    YellowZoneMetric yellowZoneMetric;

    public YellowZoneTestCases() throws SQLException {
        yellowZoneMetric = new YellowZoneMetric();
    }

    @Test (description = "Validating that information of both Source and Final files are equal")
    public void sourceAndFinalFilesMatch() throws SQLException {

        yellowZoneMetric.extractSourceFile();
        yellowZoneMetric.extractFinalFile();
        closeConnections();
        Assert.assertTrue(yellowZoneMetric.comparingYellowZoneFiles_Successful(), "Files are not equal");
    }

    @Test (description = "Validating that expected columns are included on Final file")
    public void columnsAreOnFinal() throws SQLException {

        Assert.assertTrue(yellowZoneMetric.expectedColumnsOnYellowZoneFinalTable(), "Not all expected columns are in the Final file");
    }

    @Test (description = "Validating that expected columns are included on Final file")
    public void maxWeeksDiff() throws SQLException {

        Assert.assertTrue(yellowZoneMetric.getMaxWeeksDiff() <= 6);
        closeConnections();
    }

}
