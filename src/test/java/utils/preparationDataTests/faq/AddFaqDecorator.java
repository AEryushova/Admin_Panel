package utils.preparationDataTests.faq;

import utils.dbUtils.DataBaseQuery;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static utils.otherUtils.DataGenerator.generateQuestion;
import static utils.otherUtils.DataGenerator.generateText;

public class AddFaqDecorator implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllFaq();
        DataBaseQuery.addFaq(0, generateQuestion(), generateText());
    }
}
