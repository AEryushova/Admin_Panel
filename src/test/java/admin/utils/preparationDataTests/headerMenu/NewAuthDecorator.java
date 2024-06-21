package admin.utils.preparationDataTests.headerMenu;


import admin.data.DataConfig;
import admin.utils.testUtils.BrowserManager;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class NewAuthDecorator implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        Selenide.closeWebDriver();
        BrowserManager.openBrowser(DataConfig.UserData.getLOGIN_ADMIN(), DataConfig.UserData.getPASSWORD_ADMIN());
    }


}
