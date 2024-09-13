package ru.adminlk.clinica.samsmu.utils.preparationData.faq;


import ru.adminlk.clinica.samsmu.utils.dbUtils.DataBaseQuery;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import static ru.adminlk.clinica.samsmu.utils.testsUtils.TestHelper.*;
import static ru.adminlk.clinica.samsmu.utils.testsUtils.DataGenerator.*;


public class AddDeleteFaq implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllFaq();
        DataBaseQuery.addFaq(0, generateQuestion(), generateText(), generateDate("current"), generateDate("current"),generateUuid(),generateUuid());
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllFaq();
    }
}
