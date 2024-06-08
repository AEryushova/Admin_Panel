package admin.utils.decoratorsTest;

import admin.data.DataInfo;
import admin.utils.dbUtils.DataBaseUtils;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AddFaqDecorator implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DataBaseUtils.clearAllFaq();
        DataBaseUtils.addFaq(0, DataInfo.DataTest.getQuestion(), DataInfo.DataTest.getAnswer());
    }
}
