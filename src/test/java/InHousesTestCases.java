import base.Wrappers;
import operations.InHousesMetric;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;

public class InHousesTestCases extends Wrappers {

    InHousesMetric inHousesMetric;

    public InHousesTestCases() throws SQLException {
        inHousesMetric = new InHousesMetric();
    }

    @Test (description = "Validating that information of both Source and Final files are equal")
    public void sourceAndFinalFilesMatch() throws SQLException {

        inHousesMetric.extractSourceFile();
        inHousesMetric.extractFinalFile();
        closeConnections();
        Assert.assertTrue(inHousesMetric.comparingInHousesFiles_Successful(), "Files are not equal");
    }

    @Test (description = "Validating that expected columns are included on Final file")
    public void columnsAreOnFinal() throws SQLException {

        Assert.assertTrue(inHousesMetric.expectedColumnsOnInHousesFinalTable(), "Not all expected columns are in the Final file");
    }

}
