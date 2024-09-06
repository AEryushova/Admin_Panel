package utils.preparationData.faq;

import utils.dbUtils.DataBaseQuery;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static utils.testsUtils.DataGenerator.generateQuestion;
import static utils.testsUtils.DataGenerator.generateText;
import static utils.testsUtils.TestHelper.generateUuid;
import static utils.testsUtils.TestHelper.getDateTime;

public class AddFaq implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllFaq();
        DataBaseQuery.addFaq(0, generateQuestion(), generateText(),getDateTime(),getDateTime(),generateUuid(),generateUuid());
    }
}
