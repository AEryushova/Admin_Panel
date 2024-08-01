package utils.preparationDataTests.administration;

import utils.APIUtils.PreparationDataAdminTest;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static data.TestData.DataTest.login;
import static utils.otherUtils.DataGenerator.generateLogin;
import static utils.otherUtils.DataGenerator.generatePassword;

public class AdminAddDeleteDecorator implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        PreparationDataAdminTest.createAdmin(generateLogin(),generatePassword());
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        PreparationDataAdminTest.deleteAdmin(login);
    }

}
