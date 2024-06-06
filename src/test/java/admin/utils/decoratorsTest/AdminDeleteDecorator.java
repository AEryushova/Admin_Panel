package admin.utils.decoratorsTest;

import admin.data.DataInfo;
import admin.utils.testUtils.DataPreparationService;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AdminDeleteDecorator implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DataPreparationService.deleteAdmin(DataInfo.UserData.getLoginAdminTest());
    }
}