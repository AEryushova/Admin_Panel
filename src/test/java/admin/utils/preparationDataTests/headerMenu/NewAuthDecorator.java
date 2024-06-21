package admin.utils.preparationDataTests.headerMenu;


import admin.data.TestData;
import admin.utils.testUtils.BrowserManager;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class NewAuthDecorator implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        Selenide.closeWebDriver();
        BrowserManager.openAdminPanel(TestData.UserData.LOGIN_ADMIN, TestData.UserData.PASSWORD_ADMIN);
    }


}
