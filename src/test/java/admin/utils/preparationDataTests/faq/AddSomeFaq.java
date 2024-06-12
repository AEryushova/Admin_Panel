package admin.utils.preparationDataTests.faq;


import admin.data.DataConfig;
import admin.utils.dbUtils.DataBaseQuery;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AddSomeFaq implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllFaq();
        DataBaseQuery.addFaqSome(0, DataConfig.DataTest.getQuestion(), DataConfig.DataTest.getAnswer());
        DataBaseQuery.addFaqSome(1, DataConfig.DataTest.getChangeQuestion(), DataConfig.DataTest.getChangeAnswer());
        DataBaseQuery.addFaqSome(2,DataConfig.DataTest.getChangeAnswer(),DataConfig.DataTest.getQuestion());
        DataBaseQuery.addFaqSome(3, DataConfig.DataTest.getQuestion(), DataConfig.DataTest.getQuestion());
        DataBaseQuery.addFaqSome(4,DataConfig.DataTest.getAnswer(),DataConfig.DataTest.getAnswer());
        DataBaseQuery.addFaqSome(5,DataConfig.DataTest.getAnswer(), DataConfig.DataTest.getChangeQuestion());

    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllFaq();
    }
}
