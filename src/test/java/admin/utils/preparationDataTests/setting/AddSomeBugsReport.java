package admin.utils.preparationDataTests.setting;

import admin.utils.dbUtils.DataBaseQuery;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AddSomeBugsReport implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllBugReports();
        DataBaseQuery.addBugReport();
        DataBaseQuery.addBugReport();
        DataBaseQuery.addBugReport();
        DataBaseQuery.addBugReport();
        DataBaseQuery.addBugReport();
        DataBaseQuery.addBugReport();
        DataBaseQuery.addBugReport();
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllBugReports();
    }




}
