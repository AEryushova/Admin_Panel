package utils.preparationDataTests.setting;

import utils.APIUtils.PreparationDataSettingTest;
import utils.dbUtils.DataBaseQuery;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static utils.otherUtils.DataGenerator.*;

public class AddDeleteBugReportDecorator implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllBugReports();
        PreparationDataSettingTest.authPatient();
        PreparationDataSettingTest.addBugReportPatient(generateText(), generateEmail(),generateNamePatient());
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllBugReports();
    }

}
