package admin.utils.decoratorsTest.setting;

import admin.data.DataInfo;
import admin.utils.dbUtils.DataBaseQuery;
import admin.utils.testUtils.DataPreparationService;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AddDeleteBugReportDecorator implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllBugReports();
        DataPreparationService.authPatient();
        DataPreparationService.addBugReportPatient(DataInfo.DataTest.getMessageBugReport(),DataInfo.DataTest.getEmailPatient(),DataInfo.DataTest.getNamePatient());
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllBugReports();
    }



}
