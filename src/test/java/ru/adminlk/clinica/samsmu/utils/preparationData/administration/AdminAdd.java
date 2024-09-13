package ru.adminlk.clinica.samsmu.utils.preparationData.administration;

import ru.adminlk.clinica.samsmu.utils.APIUtils.PreparationDataAdminTest;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static ru.adminlk.clinica.samsmu.utils.testsUtils.DataGenerator.generateLogin;
import static ru.adminlk.clinica.samsmu.utils.testsUtils.DataGenerator.generatePassword;

public class AdminAdd implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        PreparationDataAdminTest.createAdmin(generateLogin(), generatePassword());
    }
}
