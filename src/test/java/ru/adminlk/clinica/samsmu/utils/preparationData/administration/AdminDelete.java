package ru.adminlk.clinica.samsmu.utils.preparationData.administration;


import ru.adminlk.clinica.samsmu.utils.APIUtils.PreparationDataAdminTest;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static ru.adminlk.clinica.samsmu.data.GeneratedTestData.login;


public class AdminDelete implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        PreparationDataAdminTest.deleteAdmin(login);
    }
}