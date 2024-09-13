package ru.adminlk.clinica.samsmu.utils.preparationData.setting;

import ru.adminlk.clinica.samsmu.utils.APIUtils.PreparationDataSettingTest;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static ru.adminlk.clinica.samsmu.data.TestData.DataTest.LOGO;

public class SetSAMSMU_Logo implements AfterEachCallback {


    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        PreparationDataSettingTest.uploadLogo(LOGO);
    }
}
