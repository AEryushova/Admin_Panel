package admin.utils.preparationDataTests.administration;

import admin.utils.APIUtils.PreparationDataAdminTest;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static admin.data.TestData.DataTest.LOGIN_ADMIN_TEST;
import static admin.utils.otherUtils.DataGenerator.generateLogin;
import static admin.utils.otherUtils.DataGenerator.generatePassword;

public class AdminAddDeleteDecorator implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        PreparationDataAdminTest.createAdmin(generateLogin(),generatePassword());
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        PreparationDataAdminTest.deleteAdmin(LOGIN_ADMIN_TEST);
    }

}
