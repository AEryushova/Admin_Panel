package admin.utils.decoratorsTest.setting;

import admin.utils.dbUtils.DataBaseQuery;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AddBugReportDecorator implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllBugReports();
        DataBaseQuery.addBugReport();
    }
}
