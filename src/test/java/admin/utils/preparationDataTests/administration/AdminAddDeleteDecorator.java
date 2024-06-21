package admin.utils.preparationDataTests.administration;

import admin.data.TestData;
import admin.utils.APIUtils.PreparationDataAdminTest;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AdminAddDeleteDecorator implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        PreparationDataAdminTest.createAdmin(TestData.DataTest.getLOGIN_ADMIN_TEST(), TestData.DataTest.getPASSWORD_ADMIN_TEST());
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        PreparationDataAdminTest.deleteAdmin(TestData.DataTest.getLOGIN_ADMIN_TEST());
    }

}
