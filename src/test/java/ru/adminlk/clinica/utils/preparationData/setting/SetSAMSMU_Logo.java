package ru.adminlk.clinica.utils.preparationData.setting;

import ru.adminlk.clinica.utils.APIUtils.PreparationDataSettingTest;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static ru.adminlk.clinica.data.FinalTestData.TestData.LOGO;

public class SetSAMSMU_Logo implements AfterEachCallback {


    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        PreparationDataSettingTest.uploadLogo(LOGO);
    }
}
