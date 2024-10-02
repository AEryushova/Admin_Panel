package ru.adminlk.clinica.utils.preparationData.faq;

import ru.adminlk.clinica.utils.dbUtils.dbUtils.DataBaseQuery;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static ru.adminlk.clinica.utils.testsUtils.DataGenerator.*;
import static ru.adminlk.clinica.utils.testsUtils.TestHelper.*;

public class AddFaq implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllFaq();
        DataBaseQuery.addFaq(0, generateQuestion(), generateText(), generateDate("current"), generateDate("current"),generateUuid(),generateUuid());
    }
}
