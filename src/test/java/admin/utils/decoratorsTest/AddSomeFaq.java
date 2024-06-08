package admin.utils.decoratorsTest;

import admin.data.DataInfo;
import admin.utils.dbUtils.DataBaseUtils;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AddSomeFaq implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DataBaseUtils.clearAllFaq();
        DataBaseUtils.addFaq(0, DataInfo.DataTest.getQuestion(), DataInfo.DataTest.getAnswer());
        DataBaseUtils.addFaq(1,DataInfo.DataTest.getChangeQuestion(), DataInfo.DataTest.getChangeAnswer());
        DataBaseUtils.addFaq(0, DataInfo.DataTest.getQuestion(), DataInfo.DataTest.getAnswer());
        DataBaseUtils.addFaq(1,DataInfo.DataTest.getChangeQuestion(), DataInfo.DataTest.getChangeAnswer());
        DataBaseUtils.addFaq(0, DataInfo.DataTest.getQuestion(), DataInfo.DataTest.getAnswer());
        DataBaseUtils.addFaq(1,DataInfo.DataTest.getChangeQuestion(), DataInfo.DataTest.getChangeAnswer());

    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DataBaseUtils.clearAllFaq();
    }
}
