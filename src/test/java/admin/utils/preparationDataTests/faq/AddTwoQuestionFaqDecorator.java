package admin.utils.preparationDataTests.faq;


import admin.config.DataConfig;
import admin.utils.dbUtils.DataBaseQuery;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AddTwoQuestionFaqDecorator implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllFaq();
        DataBaseQuery.addFaq(0, DataConfig.DataTest.getQUESTION(),DataConfig.DataTest.getANSWER());
        DataBaseQuery.addFaq(1,DataConfig.DataTest.getCHANGE_QUESTION(),DataConfig.DataTest.getCHANGE_ANSWER());
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllFaq();
    }
}
