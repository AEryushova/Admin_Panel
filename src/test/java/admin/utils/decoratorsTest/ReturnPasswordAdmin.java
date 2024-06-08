package admin.utils.decoratorsTest;

import admin.data.DataInfo;
import admin.utils.dbUtils.DataBaseUtils;
import admin.utils.testUtils.BrowserManager;
import admin.utils.testUtils.DataPreparationService;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ReturnPasswordAdmin implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DataPreparationService.authAdmin(DataInfo.UserData.getLoginAdmin(),DataInfo.UserData.getNewPasswordAdmin());
        DataPreparationService.changePasswordAdmin(DataInfo.UserData.getNewPasswordAdmin(),DataInfo.UserData.getPasswordAdmin());
    }
}
