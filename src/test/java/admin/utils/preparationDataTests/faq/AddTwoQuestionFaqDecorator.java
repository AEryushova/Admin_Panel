package admin.utils.preparationDataTests.faq;


import admin.data.TestData;
import admin.utils.dbUtils.DataBaseQuery;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AddTwoQuestionFaqDecorator implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllFaq();
        DataBaseQuery.addFaq(0, TestData.DataTest.getQUESTION(), TestData.DataTest.getANSWER());
        DataBaseQuery.addFaq(1, TestData.DataTest.getCHANGE_QUESTION(), TestData.DataTest.getCHANGE_ANSWER());
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllFaq();
    }
}
