package admin.utils.preparationDataTests.setting;


import admin.utils.dbUtils.DataBaseQuery;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static admin.data.TestData.DataTest.*;

public class AddSomeBugsReport implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllBugReports();
        DataBaseQuery.addBugReport(MESSAGE_BUG_REPORT, EMAIL_PATIENT, NAME_PATIENT);
        DataBaseQuery.addBugReport(MESSAGE_BUG_REPORT, EMAIL_PATIENT, NAME_PATIENT);
        DataBaseQuery.addBugReport(MESSAGE_BUG_REPORT, EMAIL_PATIENT, NAME_PATIENT);
        DataBaseQuery.addBugReport(MESSAGE_BUG_REPORT, EMAIL_PATIENT, NAME_PATIENT);
        DataBaseQuery.addBugReport(MESSAGE_BUG_REPORT, EMAIL_PATIENT, NAME_PATIENT);
        DataBaseQuery.addBugReport(MESSAGE_BUG_REPORT, EMAIL_PATIENT, NAME_PATIENT);
        DataBaseQuery.addBugReport(MESSAGE_BUG_REPORT, EMAIL_PATIENT, NAME_PATIENT);
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllBugReports();
    }




}
