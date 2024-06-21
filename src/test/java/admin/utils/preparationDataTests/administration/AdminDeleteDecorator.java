package admin.utils.preparationDataTests.administration;

import admin.config.DataConfig;
import admin.utils.APIUtils.PreparationDataAdminTest;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AdminDeleteDecorator implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        PreparationDataAdminTest.deleteAdmin(DataConfig.DataTest.getLOGIN_ADMIN_TEST());
    }
}