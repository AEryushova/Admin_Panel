package admin.utils.decoratorsTest;

import admin.utils.dbUtils.DataBaseUtils;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AddDeleteFaqDecorator implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DataBaseUtils.clearAllFaq();
        DataBaseUtils.addFaq(0);
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DataBaseUtils.clearAllFaq();
    }
}
