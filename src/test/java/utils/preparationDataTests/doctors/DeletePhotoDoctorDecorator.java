package utils.preparationDataTests.doctors;


import utils.dbUtils.DataBaseQuery;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static data.TestData.DataTest.*;

public class DeletePhotoDoctorDecorator implements BeforeEachCallback , AfterEachCallback {

        @Override
        public void beforeEach(ExtensionContext context) throws Exception {
                DataBaseQuery.setDefaultPhotoDoctor(DOCTOR, DOCTOR_SPECIALIZATION, DEFAULT_PHOTO);
        }

        @Override
        public void afterEach(ExtensionContext context) throws Exception {
                DataBaseQuery.setDefaultPhotoDoctor(DOCTOR, DOCTOR_SPECIALIZATION, DEFAULT_PHOTO);

        }
}

