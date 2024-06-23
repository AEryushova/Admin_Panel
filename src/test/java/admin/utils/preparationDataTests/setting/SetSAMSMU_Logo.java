package admin.utils.preparationDataTests.setting;

import admin.utils.APIUtils.PreparationDataSettingTest;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static admin.data.TestData.DataTest.LOGO;

public class SetSAMSMU_Logo implements AfterEachCallback {


    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        PreparationDataSettingTest.uploadLogo(LOGO);
    }
}
