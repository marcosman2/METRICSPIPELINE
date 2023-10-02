import base.Wrappers;
import operations.FinishesMetric;
import operations.StartsMetric;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;

public class FinishesTestCases extends Wrappers {

    FinishesMetric finishesMetric;

    public FinishesTestCases() throws SQLException {
        finishesMetric = new FinishesMetric();
    }

    @Test (description = "Validating that information of both Source and Final files are equal")
    public void sourceAndFinalFilesMatch() throws SQLException {

        finishesMetric.extractSourceFile();
        finishesMetric.extractFinalFile();
        closeConnections();
        Assert.assertTrue(finishesMetric.comparingFinishesFiles_Successful(), "Files are not equal");
    }

    @Test (description = "Validating that expected columns are included on Final file")
    public void columnsAreOnFinal() throws SQLException {

        Assert.assertTrue(finishesMetric.expectedColumnsOnFinishesFinalTable(), "Not all expected columns are in the Final file");
    }

}
