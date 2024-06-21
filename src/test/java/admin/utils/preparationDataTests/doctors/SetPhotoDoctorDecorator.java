package admin.utils.preparationDataTests.doctors;

import admin.data.TestData;
import admin.utils.APIUtils.PreparationDataSettingTest;
import admin.utils.dbUtils.DataBaseQuery;
import admin.utils.testUtils.DataHelper;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class SetPhotoDoctorDecorator implements BeforeEachCallback {

        @Override
        public void beforeEach(ExtensionContext context) throws Exception {
            PreparationDataSettingTest.uploadPhoto(TestData.DataTest.getPHOTO());
            String photoUri=DataHelper.urlPhotoBuilder();
            DataBaseQuery.setPhotoDoctor(photoUri, TestData.DataTest.getDOCTOR(), TestData.DataTest.getDOCTOR_SPECIALIZATION());
        }


}
