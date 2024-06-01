package admin.utils.testUtils;

import admin.data.DataTest;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AdminTestDecorator implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        TestSetupAPI.authRequest(DataTest.getLoginSuperAdmin(), DataTest.getPasswordSuperAdmin());
        TestSetupAPI.createAdmin(DataTest.getLoginAdminTest(), DataTest.getPasswordAdminTest());
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        TestSetupAPI.deleteAdmin(DataTest.getLoginAdminTest());
    }

}
