package admin.utils.preparationDataTests.administration;

import admin.data.DataConfig;
import admin.utils.APIUtils.PreparationDataAdminTest;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AdminAddDecorator implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        PreparationDataAdminTest.createAdmin(DataConfig.DataTest.getLoginAdminTest(), DataConfig.DataTest.getPasswordAdminTest());
    }


}
