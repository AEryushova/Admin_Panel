package admin.utils.preparationDataTests.setting;

import admin.data.DataConfig;
import admin.utils.dbUtils.DataBaseQuery;
import admin.utils.preparationDataTests.requestAPI.PreparationDataService;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AddDeleteBugReportDecorator implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllBugReports();
        PreparationDataService.authPatient();
        PreparationDataService.addBugReportPatient(DataConfig.DataTest.getMessageBugReport(), DataConfig.DataTest.getEmailPatient(), DataConfig.DataTest.getNamePatient());
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllBugReports();
    }



}
