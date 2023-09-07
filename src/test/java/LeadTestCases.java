import base.Wrappers;
import operations.LeadMetric;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;

public class LeadTestCases extends Wrappers {

    LeadMetric leadMetric;

    public LeadTestCases() throws SQLException {
    }

    @Test (description = "Validating that information of both Source and Final files are equal")
    public void sourceAndFinalFilesMatch() throws SQLException {

        leadMetric = new LeadMetric();
        leadMetric.extractSourceFile();
        leadMetric.extractFinalFile();
        closeConnections();
        Assert.assertTrue(leadMetric.comparingLeadsFiles_Successful(), "Files are not equal");
    }

    @Test (description = "Validating that comparison fails when there are missing rows on Final file")
    public void missingRowsOnFinalFile() throws SQLException {

        leadMetric = new LeadMetric();
        Assert.assertFalse(leadMetric.comparingLeadsFiles_MissingRows(), "Comparison fails");
    }

    @Test (description = "Validating that comparison fails when there is a data error on Final file")
    public void dataErrorOnFinalFile() throws SQLException {

        leadMetric = new LeadMetric();
        Assert.assertFalse(leadMetric.comparingLeadsFiles_DataError(), "Comparison fails");
    }

}
