package admin.utils.preparationDataTests.faq;



import admin.utils.dbUtils.DataBaseQuery;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static admin.data.TestData.DataTest.*;

public class AddSomeFaq implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllFaq();
        DataBaseQuery.addFaqSome(0, QUESTION, ANSWER);
        DataBaseQuery.addFaqSome(1, CHANGE_QUESTION, CHANGE_ANSWER);
        DataBaseQuery.addFaqSome(2, CHANGE_ANSWER, QUESTION);
        DataBaseQuery.addFaqSome(3, QUESTION, QUESTION);
        DataBaseQuery.addFaqSome(4, ANSWER, ANSWER);
        DataBaseQuery.addFaqSome(5, ANSWER, CHANGE_QUESTION);

    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllFaq();
    }
}
