package admin.utils.decoratorsTest.faq;

import admin.data.DataInfo;
import admin.utils.dbUtils.DataBaseQuery;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AddFaqDecorator implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllFaq();
        DataBaseQuery.addFaq(0, DataInfo.DataTest.getQuestion(), DataInfo.DataTest.getAnswer());
    }
}
