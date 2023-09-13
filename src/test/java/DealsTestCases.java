import base.Wrappers;
import operations.DealsMetric;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;

public class DealsTestCases extends Wrappers {

    DealsMetric dealsMetric;

    public DealsTestCases() throws SQLException {
        dealsMetric = new DealsMetric();
    }

    @Test (description = "Validating that information of both Source and Final files are equal")
    public void sourceAndFinalFilesMatch() throws SQLException {

        dealsMetric.extractSourceFile();
        dealsMetric.extractFinalFile();
        closeConnections();
        Assert.assertTrue(dealsMetric.comparingDealsFiles_Successful(), "Files are not equal");
    }

    @Test (description = "Validating that expected columns are included on Final file")
    public void columnsAreOnFinal() throws SQLException {

        Assert.assertTrue(dealsMetric.expectedColumnsOnDealsFinalTable(), "Not all expected columns are in the Final file");
    }

}
