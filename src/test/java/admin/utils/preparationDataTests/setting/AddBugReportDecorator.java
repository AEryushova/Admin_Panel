package admin.utils.preparationDataTests.setting;

import admin.data.TestData;
import admin.utils.dbUtils.DataBaseQuery;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AddBugReportDecorator implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllBugReports();
        DataBaseQuery.addBugReport(TestData.DataTest.getMESSAGE_BUG_REPORT(), TestData.DataTest.getEMAIL_PATIENT(), TestData.DataTest.getNAME_PATIENT());
    }
}
