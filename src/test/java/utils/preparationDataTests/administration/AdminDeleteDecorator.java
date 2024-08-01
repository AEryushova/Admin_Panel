package utils.preparationDataTests.administration;


import utils.APIUtils.PreparationDataAdminTest;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static data.TestData.DataTest.login;


public class AdminDeleteDecorator implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        PreparationDataAdminTest.deleteAdmin(login);
    }
}