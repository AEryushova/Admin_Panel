package admin.utils.decoratorsTest.headerMenu;

import admin.data.DataInfo;
import admin.utils.testUtils.BrowserManager;
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
