package utils.preparationData.administration;

import utils.APIUtils.PreparationDataAdminTest;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static utils.testsUtils.DataGenerator.generateLogin;
import static utils.testsUtils.DataGenerator.generatePassword;

public class AdminAdd implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        PreparationDataAdminTest.createAdmin(generateLogin(), generatePassword());
    }
}
