package admin.utils.preparationDataTests.administration;

import admin.data.DataConfig;
import admin.utils.preparationDataTests.requestAPI.PreparationDataService;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AdminAddDeleteDecorator implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        PreparationDataService.createAdmin(DataConfig.UserData.getLoginAdminTest(), DataConfig.UserData.getPasswordAdminTest());
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        PreparationDataService.deleteAdmin(DataConfig.UserData.getLoginAdminTest());
    }

}
