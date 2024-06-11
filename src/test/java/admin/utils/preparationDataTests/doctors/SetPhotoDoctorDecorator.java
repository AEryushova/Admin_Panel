package admin.utils.preparationDataTests.doctors;

import admin.data.DataConfig;
import admin.utils.dbUtils.DataBaseQuery;
import admin.utils.testUtils.DataHelper;
import admin.utils.preparationDataTests.requestAPI.PreparationDataService;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class SetPhotoDoctorDecorator implements BeforeEachCallback {

        @Override
        public void beforeEach(ExtensionContext context) throws Exception {
            PreparationDataService.uploadPhoto(DataConfig.DataTest.getPhoto());
            String photoUri=DataHelper.urlPhotoBuilder();
            DataBaseQuery.setPhotoDoctor(photoUri);
        }


}
