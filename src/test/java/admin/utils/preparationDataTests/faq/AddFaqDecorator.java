package admin.utils.preparationDataTests.faq;

import admin.config.DataConfig;
import admin.utils.dbUtils.DataBaseQuery;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AddFaqDecorator implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllFaq();
        DataBaseQuery.addFaq(0,DataConfig.DataTest.getQUESTION(),DataConfig.DataTest.getANSWER());
    }
}
