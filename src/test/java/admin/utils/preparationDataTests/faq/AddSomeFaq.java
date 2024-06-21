package admin.utils.preparationDataTests.faq;


import admin.data.TestData;
import admin.utils.dbUtils.DataBaseQuery;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AddSomeFaq implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllFaq();
        DataBaseQuery.addFaqSome(0, TestData.DataTest.getQUESTION(), TestData.DataTest.getANSWER());
        DataBaseQuery.addFaqSome(1, TestData.DataTest.getCHANGE_QUESTION(), TestData.DataTest.getCHANGE_ANSWER());
        DataBaseQuery.addFaqSome(2, TestData.DataTest.getCHANGE_ANSWER(), TestData.DataTest.getQUESTION());
        DataBaseQuery.addFaqSome(3, TestData.DataTest.getQUESTION(), TestData.DataTest.getQUESTION());
        DataBaseQuery.addFaqSome(4, TestData.DataTest.getANSWER(), TestData.DataTest.getANSWER());
        DataBaseQuery.addFaqSome(5, TestData.DataTest.getANSWER(), TestData.DataTest.getCHANGE_QUESTION());

    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllFaq();
    }
}
