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
        DataBaseQuery.addFaqSome(0, DataConfig.DataTest.getQUESTION(), DataConfig.DataTest.getANSWER());
        DataBaseQuery.addFaqSome(1, DataConfig.DataTest.getCHANGE_QUESTION(), DataConfig.DataTest.getCHANGE_ANSWER());
        DataBaseQuery.addFaqSome(2,DataConfig.DataTest.getCHANGE_ANSWER(),DataConfig.DataTest.getQUESTION());
        DataBaseQuery.addFaqSome(3, DataConfig.DataTest.getQUESTION(), DataConfig.DataTest.getQUESTION());
        DataBaseQuery.addFaqSome(4,DataConfig.DataTest.getANSWER(),DataConfig.DataTest.getANSWER());
        DataBaseQuery.addFaqSome(5,DataConfig.DataTest.getANSWER(), DataConfig.DataTest.getCHANGE_QUESTION());

    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllFaq();
    }
}
