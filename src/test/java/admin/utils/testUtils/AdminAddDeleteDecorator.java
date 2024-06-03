package admin.utils.testUtils;

import admin.data.DataInfo;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AdminAddDeleteDecorator implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        TestSetupAPI.authRequest(DataInfo.UserData.getLoginSuperAdmin(), DataInfo.UserData.getPasswordSuperAdmin());
        TestSetupAPI.createAdmin(DataInfo.UserData.getLoginAdminTest(), DataInfo.UserData.getPasswordAdminTest());
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        TestSetupAPI.deleteAdmin(DataInfo.UserData.getLoginAdminTest());
    }

}
