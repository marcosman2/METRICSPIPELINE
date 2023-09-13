import base.Wrappers;
import operations.LeadMetric;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;

public class LeadTestCases extends Wrappers {

    LeadMetric leadMetric;

    public LeadTestCases() throws SQLException {
        leadMetric = new LeadMetric();
    }

    @Test (description = "Validating that information of both Source and Final files are equal")
    public void sourceAndFinalFilesMatch() throws SQLException {

        leadMetric.extractSourceFile();
        leadMetric.extractFinalFile();
        closeConnections();
        Assert.assertTrue(leadMetric.comparingLeadsFiles_Successful(), "Files are not equal");
    }

    @Test (description = "Validating that expected columns are included on Final file")
    public void columnsAreOnFinal() throws SQLException {

        Assert.assertTrue(leadMetric.expectedColumnsOnLeadsFinalTable(), "Not all expected columns are in the Final file");
    }

}
