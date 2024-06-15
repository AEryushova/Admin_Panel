package admin.utils.preparationDataTests.doctors;

import admin.data.DataConfig;
import admin.utils.APIUtils.PreparationDataSettingTest;
import admin.utils.dbUtils.DataBaseQuery;
import admin.utils.testUtils.DataHelper;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class SetPhotoDoctorDecorator implements BeforeEachCallback {

        @Override
        public void beforeEach(ExtensionContext context) throws Exception {
            PreparationDataSettingTest.uploadPhoto(DataConfig.DataTest.getPhoto());
            String photoUri=DataHelper.urlPhotoBuilder();
            DataBaseQuery.setPhotoDoctor(photoUri);
        }


}
