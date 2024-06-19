package admin.utils.preparationDataTests.setting;

import admin.data.DataConfig;
import admin.utils.dbUtils.DataBaseQuery;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AddBugReportDecorator implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllBugReports();
        DataBaseQuery.addBugReport(DataConfig.DataTest.getMESSAGE_BUG_REPORT(),DataConfig.DataTest.getEMAIL_PATIENT(),DataConfig.DataTest.getNAME_PATIENT());
    }
}
