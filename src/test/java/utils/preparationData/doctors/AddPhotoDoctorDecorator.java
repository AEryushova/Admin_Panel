package utils.preparationData.doctors;


import utils.APIUtils.PreparationDataSettingTest;
import utils.dbUtils.DataBaseQuery;
import utils.testsUtils.TestHelper;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static data.TestData.DataTest.*;

public class AddPhotoDoctorDecorator implements BeforeEachCallback {

        @Override
        public void beforeEach(ExtensionContext context) throws Exception {
            PreparationDataSettingTest.uploadPhotoToStorage(PHOTO);
            String photoUri= TestHelper.urlPhotoBuilder();
            DataBaseQuery.setPhotoDoctor(DOCTOR, DOCTOR_SPECIALIZATION, photoUri);
        }


}
