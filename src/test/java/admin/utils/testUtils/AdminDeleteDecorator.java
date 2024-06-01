package admin.utils.testUtils;

import admin.data.DataTest;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AdminDeleteDecorator implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        TestSetupAPI.deleteAdmin(DataTest.getLoginAdminTest());
    }
}