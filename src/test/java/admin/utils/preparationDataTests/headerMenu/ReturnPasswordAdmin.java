package admin.utils.preparationDataTests.headerMenu;

import admin.data.TestData;
import admin.utils.APIUtils.PreparationDataHeaderTest;
import admin.utils.testUtils.BrowserManager;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ReturnPasswordAdmin implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        PreparationDataHeaderTest.authAdmin(TestData.UserData.LOGIN_SUPER_ADMIN, TestData.UserData.PASSWORD_SUPER_ADMIN);
        PreparationDataHeaderTest.changePasswordAdmin(TestData.UserData.LOGIN_ADMIN, TestData.UserData.PASSWORD_ADMIN);
        Selenide.closeWebDriver();
        BrowserManager.openAdminPanel(TestData.UserData.LOGIN_ADMIN, TestData.UserData.PASSWORD_ADMIN);

    }
}
