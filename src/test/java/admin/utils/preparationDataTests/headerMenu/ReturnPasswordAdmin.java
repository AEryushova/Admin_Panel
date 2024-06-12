package admin.utils.preparationDataTests.headerMenu;

import admin.data.DataConfig;
import admin.utils.preparationDataTests.requestAPI.PreparationDataService;
import admin.utils.testUtils.BrowserManager;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ReturnPasswordAdmin implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        PreparationDataService.authAdmin(DataConfig.UserData.getLoginSuperAdmin(), DataConfig.UserData.getPasswordSuperAdmin());
        PreparationDataService.changePasswordAdmin(DataConfig.UserData.getLoginAdmin(), DataConfig.UserData.getPasswordAdmin());
        Selenide.closeWebDriver();
        BrowserManager.authGetCookie(DataConfig.UserData.getLoginAdmin(), DataConfig.UserData.getPasswordAdmin());

    }
}
