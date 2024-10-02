package ru.adminlk.clinica.utils.preparationData.doctors;


import ru.adminlk.clinica.utils.APIUtils.PreparationDataSettingTest;
import ru.adminlk.clinica.utils.dbUtils.dbUtils.DataBaseQuery;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static ru.adminlk.clinica.data.AppData.URL_PERSONAL_AREA;
import static ru.adminlk.clinica.data.FinalTestData.TestData.*;

public class AddPhotoDoctor implements BeforeEachCallback {

        @Override
        public void beforeEach(ExtensionContext context) throws Exception {
            PreparationDataSettingTest.uploadPhotoToStorage(PHOTO);
            String photoUri= URL_PERSONAL_AREA + PreparationDataSettingTest.getLocation();
            DataBaseQuery.setPhotoDoctor(DOCTOR, DOCTOR_SPECIALIZATION, photoUri);
        }


}
