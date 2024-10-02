package ru.adminlk.clinica.utils.preparationData.headerMenu;


import ru.adminlk.clinica.test.BaseTest;
import ru.adminlk.clinica.utils.APIUtils.PreparationDataHeaderTest;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static ru.adminlk.clinica.data.FinalTestData.UserData.*;

public class ReturnPasswordAdmin implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        PreparationDataHeaderTest.authAdmin(LOGIN_SUPER_ADMIN, PASSWORD_SUPER_ADMIN);
        PreparationDataHeaderTest.changePasswordAdmin(LOGIN_ADMIN, PASSWORD_ADMIN);
        BaseTest.getAuthToken(LOGIN_ADMIN, PASSWORD_ADMIN);
    }
}
