package admin.utils.preparationDataTests.faq;

import admin.utils.dbUtils.DataBaseQuery;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static admin.utils.otherUtils.DataGenerator.generateQuestion;
import static admin.utils.otherUtils.DataGenerator.generateText;

public class AddFaqDecorator implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllFaq();
        DataBaseQuery.addFaq(0, generateQuestion(), generateText());
    }
}
