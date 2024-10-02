package ru.adminlk.clinica.utils.preparationData.doctors;


import ru.adminlk.clinica.utils.dbUtils.dbUtils.DataBaseQuery;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static ru.adminlk.clinica.data.FinalTestData.TestData.*;

public class DeletePhotoDoctor implements BeforeEachCallback , AfterEachCallback {

        @Override
        public void beforeEach(ExtensionContext context) throws Exception {
                DataBaseQuery.setPhotoDoctor(DOCTOR, DOCTOR_SPECIALIZATION, DEFAULT_PHOTO);
        }

        @Override
        public void afterEach(ExtensionContext context) throws Exception {
                DataBaseQuery.setPhotoDoctor(DOCTOR, DOCTOR_SPECIALIZATION, DEFAULT_PHOTO);

        }
}

