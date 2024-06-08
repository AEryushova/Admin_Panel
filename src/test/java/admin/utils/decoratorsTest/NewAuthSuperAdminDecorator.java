package admin.utils.decoratorsTest;

import admin.data.DataInfo;
import admin.utils.dbUtils.DataBaseUtils;
import admin.utils.testUtils.BrowserManager;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class NewAuthSuperAdminDecorator implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        BrowserManager.authGetCookie(DataInfo.UserData.getLoginSuperAdmin(), DataInfo.UserData.getPasswordSuperAdmin());
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
    }
}
