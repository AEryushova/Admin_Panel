package utils.preparationData.setting;

import utils.dbUtils.DataBaseQuery;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static utils.testsUtils.DataGenerator.*;
import static utils.testsUtils.TestHelper.*;

public class AddBugReport implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllBugReports();
        DataBaseQuery.addBugReport(generateText(), generateEmail(),generateNamePatient(), generateDate("current"),generateUuid());
    }
}
