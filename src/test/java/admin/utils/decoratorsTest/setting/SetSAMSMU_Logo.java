package admin.utils.decoratorsTest.setting;

import admin.pages.SettingPage.SettingPage;
import admin.pages.SettingPage.EditLogoWindow;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class SetSAMSMU_Logo implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        SettingPage settingPage = new SettingPage();
        EditLogoWindow editLogoWindow=settingPage.openWindowEditLogo();
        editLogoWindow.uploadLogo("src/test/resources/logo.png");
    }
}
