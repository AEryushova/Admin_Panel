package utils.preparationDataTests.doctors;


import utils.APIUtils.PreparationDataSettingTest;
import utils.dbUtils.DataBaseQuery;
import utils.otherUtils.TestHelper;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static data.TestData.DataTest.*;

public class AddPhotoDoctorDecorator implements BeforeEachCallback {

        @Override
        public void beforeEach(ExtensionContext context) throws Exception {
            PreparationDataSettingTest.uploadPhotoToStorage(PHOTO);
            String photoUri= TestHelper.urlPhotoBuilder();
            DataBaseQuery.setPhotoDoctor(photoUri, DOCTOR, DOCTOR_SPECIALIZATION);
        }


}
