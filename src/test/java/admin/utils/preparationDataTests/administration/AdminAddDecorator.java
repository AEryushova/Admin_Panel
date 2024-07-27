package admin.utils.preparationDataTests.administration;

import admin.utils.APIUtils.PreparationDataAdminTest;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static admin.utils.otherUtils.DataGenerator.generateLogin;
import static admin.utils.otherUtils.DataGenerator.generatePassword;

public class AdminAddDecorator implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        PreparationDataAdminTest.createAdmin(generateLogin(), generatePassword());
    }


}
