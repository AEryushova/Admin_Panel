package ru.adminlk.clinica.utils.preparationData.setting;

import ru.adminlk.clinica.utils.dbUtils.dbUtils.DataBaseQuery;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static ru.adminlk.clinica.utils.testsUtils.DataGenerator.*;
import static ru.adminlk.clinica.utils.testsUtils.TestHelper.*;

public class AddDeleteBugReport implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllBugReports();
        DataBaseQuery.addBugReport(generateText(), generateEmail(),generateNamePatient(), generateDate("current"),generateUuid(), generatePhone(),generateAgentId());
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllBugReports();
    }

}
