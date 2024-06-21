package admin.utils.preparationDataTests.headerMenu;

import admin.config.DataConfig;
import admin.utils.APIUtils.PreparationDataHeaderTest;
import admin.utils.testUtils.BrowserManager;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ReturnPasswordAdmin implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        PreparationDataHeaderTest.authAdmin(DataConfig.UserData.getLOGIN_SUPER_ADMIN(), DataConfig.UserData.getPASSWORD_SUPER_ADMIN());
        PreparationDataHeaderTest.changePasswordAdmin(DataConfig.UserData.getLOGIN_ADMIN(), DataConfig.UserData.getPASSWORD_ADMIN());
        Selenide.closeWebDriver();
        BrowserManager.openBrowser(DataConfig.UserData.getLOGIN_ADMIN(), DataConfig.UserData.getPASSWORD_ADMIN());

    }
}
