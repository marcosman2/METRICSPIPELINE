import base.Wrappers;
import operations.DealsMetric;
import operations.VisitsMetric;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;

public class VisitsTestCases extends Wrappers {

    VisitsMetric visitsMetric;

    public VisitsTestCases() throws SQLException {
        visitsMetric = new VisitsMetric();
    }

    @Test (description = "Validating that information of both Source and Final files are equal")
    public void sourceAndFinalFilesMatch() throws SQLException {

        visitsMetric.extractSourceFile();
        visitsMetric.extractFinalFile();
        closeConnections();
        Assert.assertTrue(visitsMetric.comparingVisitsFiles_Successful(), "Files are not equal");
    }

    @Test (description = "Validating that expected columns are included on Final file")
    public void columnsAreOnFinal() throws SQLException {

        Assert.assertTrue(visitsMetric.expectedColumnsOnVisitsFinalTable(), "Not all expected columns are in the Final file");
    }

}
