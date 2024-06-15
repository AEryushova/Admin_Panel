package admin.utils.preparationDataTests.setting;

import admin.data.DataConfig;
import admin.utils.APIUtils.PreparationDataSettingTest;
import admin.utils.dbUtils.DataBaseQuery;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AddDeleteBugReportDecorator implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllBugReports();
        PreparationDataSettingTest.authPatient();
        PreparationDataSettingTest.addBugReportPatient(DataConfig.DataTest.getMessageBugReport(), DataConfig.DataTest.getEmailPatient(), DataConfig.DataTest.getNamePatient());
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllBugReports();
    }



}
