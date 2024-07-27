package admin.utils.preparationDataTests.setting;

import admin.utils.APIUtils.PreparationDataSettingTest;
import admin.utils.dbUtils.DataBaseQuery;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static admin.data.TestData.DataTest.*;
import static admin.utils.otherUtils.DataGenerator.generateEmail;

public class AddDeleteBugReportDecorator implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllBugReports();
        PreparationDataSettingTest.authPatient();
        PreparationDataSettingTest.addBugReportPatient(MESSAGE_BUG_REPORT,generateEmail(),NAME_PATIENT);
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllBugReports();
    }



}
