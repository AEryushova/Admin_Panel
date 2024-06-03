package admin.utils.testUtils;

import admin.data.DataInfo;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AdminDeleteDecorator implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        TestSetupAPI.authRequest(DataInfo.UserData.getLoginSuperAdmin(), DataInfo.UserData.getPasswordSuperAdmin());
        TestSetupAPI.deleteAdmin(DataInfo.UserData.getLoginAdminTest());
    }
}