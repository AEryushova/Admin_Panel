package admin.utils.preparationDataTests.administration;


import admin.utils.APIUtils.PreparationDataAdminTest;
import admin.utils.otherUtils.DataGenerator;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;


public class AdminDeleteDecorator implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        PreparationDataAdminTest.deleteAdmin(DataGenerator.getLogin());
    }
}