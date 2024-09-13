package ru.adminlk.clinica.samsmu.utils.preparationData.faq;


import ru.adminlk.clinica.samsmu.utils.dbUtils.DataBaseQuery;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static ru.adminlk.clinica.samsmu.utils.testsUtils.DataGenerator.*;
import static ru.adminlk.clinica.samsmu.utils.testsUtils.TestHelper.*;

public class AddTwoQuestionFaq implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllFaq();
        DataBaseQuery.addFaq(0, generateQuestion(), generateText(), generateDate("current"), generateDate("current"),generateUuid(),generateUuid());
        DataBaseQuery.addFaq(1, generateQuestion(), generateText(), generateDate("current"), generateDate("current"),generateUuid(),generateUuid());
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllFaq();
    }
}
