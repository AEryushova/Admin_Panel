package utils.preparationData.faq;



import utils.dbUtils.DataBaseQuery;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static utils.testsUtils.TestHelper.*;


public class AddSomeFaq implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllFaq();
        DataBaseQuery.addFaq(0, "Как вернуть деньги?", "Никак", generateDate("current"), generateDate("current"),generateUuid(),generateUuid());
        DataBaseQuery.addFaq(1, "Как записаться к врачу?", "Записаться к врачу можно через вкладку 'Врачи'", generateDate("current"), generateDate("current"),generateUuid(),generateUuid());
        DataBaseQuery.addFaq(2, "Записаться к врачу можно через вкладку 'Врачи'", "Как вернуть деньги?", generateDate("current"), generateDate("current"),generateUuid(),generateUuid());
        DataBaseQuery.addFaq(3, "Как вернуть деньги?", "Как вернуть деньги?", generateDate("current"), generateDate("current"),generateUuid(),generateUuid());
        DataBaseQuery.addFaq(4, "Никак", "Никак", generateDate("current"), generateDate("current"),generateUuid(),generateUuid());
        DataBaseQuery.addFaq(5, "Никак", "Как записаться к врачу?", generateDate("current"), generateDate("current"),generateUuid(),generateUuid());

    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllFaq();
    }
}
