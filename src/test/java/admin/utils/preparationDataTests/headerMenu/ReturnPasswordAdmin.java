package admin.utils.preparationDataTests.headerMenu;


import admin.utils.APIUtils.PreparationDataHeaderTest;
import admin.utils.testUtils.BrowserManager;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static admin.data.TestData.UserData.*;

public class ReturnPasswordAdmin implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        PreparationDataHeaderTest.authAdmin(LOGIN_SUPER_ADMIN, PASSWORD_SUPER_ADMIN);
        PreparationDataHeaderTest.changePasswordAdmin(LOGIN_ADMIN, PASSWORD_ADMIN);
        Selenide.closeWebDriver();
        BrowserManager.openAdminPanel(LOGIN_ADMIN, PASSWORD_ADMIN);

    }
}
