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
        DataBaseQuery.addFaq(0, TestData.DataTest.QUESTION, TestData.DataTest.ANSWER);
        DataBaseQuery.addFaq(1, TestData.DataTest.CHANGE_QUESTION, TestData.DataTest.CHANGE_ANSWER);
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllFaq();
    }
}
