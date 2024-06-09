package admin.utils.decoratorsTest.administration;

import admin.data.DataInfo;
import admin.utils.testUtils.DataPreparationService;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AdminAddDecorator implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DataPreparationService.createAdmin(DataInfo.UserData.getLoginAdminTest(), DataInfo.UserData.getPasswordAdminTest());
    }


}
