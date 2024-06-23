package admin.utils.preparationDataTests.administration;

import admin.utils.APIUtils.PreparationDataAdminTest;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static admin.data.TestData.DataTest.LOGIN_ADMIN_TEST;
import static admin.data.TestData.DataTest.PASSWORD_ADMIN_TEST;

public class AdminAddDecorator implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        PreparationDataAdminTest.createAdmin(LOGIN_ADMIN_TEST, PASSWORD_ADMIN_TEST);
    }


}
