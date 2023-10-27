package pulse;

import base.Wrappers;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.SQLException;

public class LeadsPulseTests {

    Wrappers wrappersPage;

    @Test(description = "Comparing Snowflake information against Pulse one, for Leads Metric")
    public void LeadsPulseValidationTest() throws SQLException, IOException {

        wrappersPage = new Wrappers();
        wrappersPage.convertJsonIntoCsv("PULSE/LEADS/LeadsPulse.json", "PULSE/LEADS/LeadsPulse.csv");

        System.out.println("************** LEADS **************");

        System.out.println("Percentage of accuracy: "+wrappersPage.countMatchingIds("PULSE/LEADS/LeadsSF.csv", "PULSE/LEADS/LeadsPulse.csv")+"%");
        System.out.println("IDs in SF but not in Pulse: "+wrappersPage.getIdsInFileANotInFileB("PULSE/LEADS/LeadsSF.csv", "PULSE/LEADS/LeadsPulse.csv"));
        System.out.println("IDs in Pulse but not in SF: "+wrappersPage.getIdsInFileBNotInFileA("PULSE/LEADS/LeadsSF.csv", "PULSE/LEADS/LeadsPulse.csv"));
    }

    @Test(description = "Comparing Snowflake information against Pulse one, for Jobs Metric")
    public void JobsPulseValidationTest() throws SQLException, IOException {

        wrappersPage = new Wrappers();
        wrappersPage.convertJsonIntoCsv("PULSE/JOBS/JobsPulse.json", "PULSE/JOBS/JobsPulse.csv");

        System.out.println("************** JOBS Metrics **************");

        System.out.println("Percentage of accuracy: "+wrappersPage.countMatchingIds("PULSE/JOBS/JobsSF.csv", "PULSE/JOBS/JobsPulse.csv")+"%");
        System.out.println("IDs in SF but not in Pulse: "+wrappersPage.getIdsInFileANotInFileB("PULSE/JOBS/JobsSF.csv", "PULSE/JOBS/JobsPulse.csv"));
        System.out.println("IDs in Pulse but not in SF: "+wrappersPage.getIdsInFileBNotInFileA("PULSE/JOBS/JobsSF.csv", "PULSE/JOBS/JobsPulse.csv"));
    }

    @Test(description = "Comparing Snowflake information against Pulse one, for AM DBAs Metric")
    public void AMDBAsPulseValidationTest() throws SQLException, IOException {

        wrappersPage = new Wrappers();
        wrappersPage.convertJsonIntoCsv("PULSE/AMDBAs/AMDBAsPulse.json", "PULSE/AMDBAs/AMDBAsPulse.csv");

        System.out.println("************** AM DBAs Metrics **************");

        System.out.println("Percentage of accuracy: "+wrappersPage.countMatchingIds("PULSE/AMDBAs/AMDBAsSF.csv", "PULSE/AMDBAs/AMDBAsPulse.csv")+"%");
        System.out.println("IDs in SF but not in Pulse: "+wrappersPage.getIdsInFileANotInFileB("PULSE/AMDBAs/AMDBAsSF.csv", "PULSE/AMDBAs/AMDBAsPulse.csv"));
        System.out.println("IDs in Pulse but not in SF: "+wrappersPage.getIdsInFileBNotInFileA("PULSE/AMDBAs/AMDBAsSF.csv", "PULSE/AMDBAs/AMDBAsPulse.csv"));
    }

    @Test(description = "Comparing Snowflake information against Pulse one, for Recruiting DBAs Metric")
    public void RecruitingDBAsPulseValidationTest() throws SQLException, IOException {

        wrappersPage = new Wrappers();
        wrappersPage.convertJsonIntoCsv("PULSE/RECRUITINGDBAs/RecruitingDBAsPulse.json", "PULSE/RECRUITINGDBAs/RecruitingDBAsPulse.csv");

        System.out.println("************** RECRUITING DBAs Metrics **************");

        System.out.println("Percentage of accuracy: "+wrappersPage.countMatchingIds("PULSE/RECRUITINGDBAs/RecruitingDBAsSF.csv", "PULSE/RECRUITINGDBAs/RecruitingDBAsPulse.csv")+"%");
        System.out.println("IDs in SF but not in Pulse: "+wrappersPage.getIdsInFileANotInFileB("PULSE/RECRUITINGDBAs/RecruitingDBAsSF.csv", "PULSE/RECRUITINGDBAs/RecruitingDBAsPulse.csv"));
        System.out.println("IDs in Pulse but not in SF: "+wrappersPage.getIdsInFileBNotInFileA("PULSE/RECRUITINGDBAs/RecruitingDBAsSF.csv", "PULSE/RECRUITINGDBAs/RecruitingDBAsPulse.csv"));
    }

    @Test(description = "Comparing Snowflake information against Pulse one, for Clients Visits Metric")
    public void ClientVisitsPulseValidationTest() throws SQLException, IOException {

        wrappersPage = new Wrappers();
        wrappersPage.convertJsonIntoCsv("PULSE/CLIENTVISITS/ClientVisitsPulse.json", "PULSE/CLIENTVISITS/ClientVisitsPulse.csv");

        System.out.println("************** CLIENT VISITS Metrics **************");

        System.out.println("Percentage of accuracy: "+wrappersPage.countMatchingIds("PULSE/CLIENTVISITS/ClientVisitsSF.csv", "PULSE/CLIENTVISITS/ClientVisitsPulse.csv")+"%");
        System.out.println("IDs in SF but not in Pulse: "+wrappersPage.getIdsInFileANotInFileB("PULSE/CLIENTVISITS/ClientVisitsSF.csv", "PULSE/CLIENTVISITS/ClientVisitsPulse.csv"));
        System.out.println("IDs in Pulse but not in SF: "+wrappersPage.getIdsInFileBNotInFileA("PULSE/CLIENTVISITS/ClientVisitsSF.csv", "PULSE/CLIENTVISITS/ClientVisitsPulse.csv"));
    }

    @Test(description = "Comparing Snowflake information against Pulse one, for Sendouts Metric")
    public void SendoutsPulseValidationTest() throws SQLException, IOException {

        wrappersPage = new Wrappers();
        wrappersPage.convertJsonIntoCsv("PULSE/SENDOUTS/SendoutsPulseApr.json", "PULSE/SENDOUTS/SendoutsPulseApr.csv");

        System.out.println("************** SENDOUTS Metrics **************");

        System.out.println("Percentage of accuracy: "+wrappersPage.countMatchingIds("PULSE/SENDOUTS/SendoutsSFApr.csv", "PULSE/SENDOUTS/SendoutsPulseApr.csv")+"%");
        System.out.println("IDs in SF but not in Pulse: "+wrappersPage.getIdsInFileANotInFileB("PULSE/SENDOUTS/SendoutsSFApr.csv", "PULSE/SENDOUTS/SendoutsPulseApr.csv"));
        System.out.println("IDs in Pulse but not in SF: "+wrappersPage.getIdsInFileBNotInFileA("PULSE/SENDOUTS/SendoutsSFApr.csv", "PULSE/SENDOUTS/SendoutsPulseApr.csv"));
    }

    @Test(description = "Comparing Snowflake information against Pulse one, for Client Submissions Metric")
    public void ClientSubmissionsPulseValidationTest() throws SQLException, IOException {

        wrappersPage = new Wrappers();
        wrappersPage.convertJsonIntoCsv("PULSE/CLIENTSUBMISSIONS/ClientSubmissionsPulse.json", "PULSE/CLIENTSUBMISSIONS/ClientSubmissionsPulse.csv");

        System.out.println("************** CLIENT SUBMISSIONS Metrics **************");

        System.out.println("Percentage of accuracy: "+wrappersPage.countMatchingIds("PULSE/CLIENTSUBMISSIONS/ClientSubmissionsSF.csv", "PULSE/CLIENTSUBMISSIONS/ClientSubmissionsPulse.csv")+"%");
        System.out.println("IDs in SF but not in Pulse: "+wrappersPage.getIdsInFileANotInFileB("PULSE/CLIENTSUBMISSIONS/ClientSubmissionsSF.csv", "PULSE/CLIENTSUBMISSIONS/ClientSubmissionsPulse.csv"));
        System.out.println("IDs in Pulse but not in SF: "+wrappersPage.getIdsInFileBNotInFileA("PULSE/CLIENTSUBMISSIONS/ClientSubmissionsSF.csv", "PULSE/CLIENTSUBMISSIONS/ClientSubmissionsPulse.csv"));
    }

    @Test(description = "Comparing Snowflake information against Pulse one, for Job Submissions Metric")
    public void JobSubmissionsPulseValidationTest() throws SQLException, IOException {

        wrappersPage = new Wrappers();
        wrappersPage.convertJsonIntoCsv("PULSE/JOBSUBMISSIONS/JobSubmissionsPulse.json", "PULSE/JOBSUBMISSIONS/JobSubmissionsPulse.csv");

        System.out.println("************** JOB SUBMISSIONS Metrics **************");

        System.out.println("Percentage of accuracy: "+wrappersPage.countMatchingIds("PULSE/JOBSUBMISSIONS/JobSubmissionsSF.csv", "PULSE/JOBSUBMISSIONS/JobSubmissionsPulse.csv")+"%");
        System.out.println("IDs in SF but not in Pulse: "+wrappersPage.getIdsInFileANotInFileB("PULSE/JOBSUBMISSIONS/JobSubmissionsSF.csv", "PULSE/JOBSUBMISSIONS/JobSubmissionsPulse.csv"));
        System.out.println("IDs in Pulse but not in SF: "+wrappersPage.getIdsInFileBNotInFileA("PULSE/JOBSUBMISSIONS/JobSubmissionsSF.csv", "PULSE/JOBSUBMISSIONS/JobSubmissionsPulse.csv"));
    }

    @Test(description = "Comparing Snowflake information against Pulse one, for Deals Metric")
    public void DealsPulseValidationTest() throws SQLException, IOException {

        wrappersPage = new Wrappers();
        wrappersPage.convertJsonIntoCsv("PULSE/DEALS/DealsPulse.json", "PULSE/DEALS/DealsPulse.csv");

        System.out.println("************** DEALS Metrics **************");

        System.out.println("Percentage of accuracy: "+wrappersPage.countMatchingIds("PULSE/DEALS/DealsSF.csv", "PULSE/DEALS/DealsPulse.csv")+"%");
        System.out.println("IDs in SF but not in Pulse: "+wrappersPage.getIdsInFileANotInFileB("PULSE/DEALS/DealsSF.csv", "PULSE/DEALS/DealsPulse.csv"));
        System.out.println("IDs in Pulse but not in SF: "+wrappersPage.getIdsInFileBNotInFileA("PULSE/DEALS/DealsSF.csv", "PULSE/DEALS/DealsPulse.csv"));
    }

    @Test(description = "Comparing Snowflake information against Pulse one, for Scheduled Calls Metric")
    public void SScheduledCallsPulseValidationTest() throws SQLException, IOException {

        wrappersPage = new Wrappers();
        wrappersPage.convertJsonIntoCsv("PULSE/SCHEDULEDCALLS/ScheduledCallsPulse.json", "PULSE/SCHEDULEDCALLS/ScheduledCallsPulse.csv");

        System.out.println("************** SCHEDULED CALLS Metrics **************");

        System.out.println("Percentage of accuracy: "+wrappersPage.countMatchingIds("PULSE/SCHEDULEDCALLS/ScheduledCallsSF.csv", "PULSE/SCHEDULEDCALLS/ScheduledCallsPulse.csv")+"%");
        System.out.println("IDs in SF but not in Pulse: "+wrappersPage.getIdsInFileANotInFileB("PULSE/SCHEDULEDCALLS/ScheduledCallsSF.csv", "PULSE/SCHEDULEDCALLS/ScheduledCallsPulse.csv"));
        System.out.println("IDs in Pulse but not in SF: "+wrappersPage.getIdsInFileBNotInFileA("PULSE/SCHEDULEDCALLS/ScheduledCallsSF.csv", "PULSE/SCHEDULEDCALLS/ScheduledCallsPulse.csv"));
    }
}
