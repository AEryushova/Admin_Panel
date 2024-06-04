package admin.utils.testUtils;

import admin.data.DataInfo;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AdminAddDecorator implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        SuperAdminDataPreparationService.createAdmin(DataInfo.UserData.getLoginAdminTest(), DataInfo.UserData.getPasswordAdminTest());
    }


}
