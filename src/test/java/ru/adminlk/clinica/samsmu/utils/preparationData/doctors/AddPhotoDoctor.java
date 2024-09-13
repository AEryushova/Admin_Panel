package ru.adminlk.clinica.samsmu.utils.preparationData.doctors;


import ru.adminlk.clinica.samsmu.utils.APIUtils.PreparationDataSettingTest;
import ru.adminlk.clinica.samsmu.utils.dbUtils.DataBaseQuery;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static ru.adminlk.clinica.samsmu.appData.AppData.URI_PERSONAL_AREA;
import static ru.adminlk.clinica.samsmu.data.TestData.DataTest.*;

public class AddPhotoDoctor implements BeforeEachCallback {

        @Override
        public void beforeEach(ExtensionContext context) throws Exception {
            PreparationDataSettingTest.uploadPhotoToStorage(PHOTO);
            String photoUri= URI_PERSONAL_AREA + PreparationDataSettingTest.getLocation();
            DataBaseQuery.setPhotoDoctor(DOCTOR, DOCTOR_SPECIALIZATION, photoUri);
        }


}
