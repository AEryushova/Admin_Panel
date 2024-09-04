package utils.preparationData.headerMenu;


import test.BaseTest;
import utils.APIUtils.PreparationDataHeaderTest;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static data.TestData.UserData.*;

public class ReturnPasswordAdmin implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        PreparationDataHeaderTest.authAdmin(LOGIN_SUPER_ADMIN, PASSWORD_SUPER_ADMIN);
        PreparationDataHeaderTest.changePasswordAdmin(LOGIN_ADMIN, PASSWORD_ADMIN);
        BaseTest.authAdminPanel(LOGIN_ADMIN, PASSWORD_ADMIN);
    }
}
