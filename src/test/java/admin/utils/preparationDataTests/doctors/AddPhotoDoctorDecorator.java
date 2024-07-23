package admin.utils.preparationDataTests.doctors;


import admin.utils.APIUtils.PreparationDataSettingTest;
import admin.utils.dbUtils.DataBaseQuery;
import admin.utils.otherUtils.TestHelper;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static admin.data.TestData.DataTest.*;

public class AddPhotoDoctorDecorator implements BeforeEachCallback {

        @Override
        public void beforeEach(ExtensionContext context) throws Exception {
            PreparationDataSettingTest.uploadPhoto(PHOTO);
            String photoUri= TestHelper.urlPhotoBuilder();
            DataBaseQuery.setPhotoDoctor(photoUri, DOCTOR, DOCTOR_SPECIALIZATION);
        }


}
