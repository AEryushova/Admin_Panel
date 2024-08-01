package utils.preparationDataTests.faq;



import utils.dbUtils.DataBaseQuery;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;


public class AddSomeFaq implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllFaq();
        DataBaseQuery.addFaqSome(0, "Как вернуть деньги?", "Никак");
        DataBaseQuery.addFaqSome(1, "Как записаться к врачу?", "Записаться к врачу можно через вкладку 'Врачи'");
        DataBaseQuery.addFaqSome(2, "Записаться к врачу можно через вкладку 'Врачи'", "Как вернуть деньги?");
        DataBaseQuery.addFaqSome(3, "Как вернуть деньги?", "Как вернуть деньги?");
        DataBaseQuery.addFaqSome(4, "Никак", "Никак");
        DataBaseQuery.addFaqSome(5, "Никак", "Как записаться к врачу?");

    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllFaq();
    }
}
