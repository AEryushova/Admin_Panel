package admin.utils.preparationDataTests.headerMenu;

import admin.data.DataConfig;
import admin.utils.preparationDataTests.requestAPI.PreparationDataService;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ReturnPasswordAdmin implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        PreparationDataService.authAdmin(DataConfig.UserData.getLoginSuperAdmin(), DataConfig.UserData.getPasswordSuperAdmin());
        String resp=PreparationDataService.changePasswordAdmin(DataConfig.UserData.getLoginAdmin(), DataConfig.UserData.getPasswordAdmin());


    }
}
