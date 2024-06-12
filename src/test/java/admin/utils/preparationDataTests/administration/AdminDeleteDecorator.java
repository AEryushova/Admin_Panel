package admin.utils.preparationDataTests.administration;

import admin.data.DataConfig;
import admin.utils.preparationDataTests.requestAPI.PreparationDataService;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AdminDeleteDecorator implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        PreparationDataService.deleteAdmin(DataConfig.DataTest.getLoginAdminTest());
    }
}