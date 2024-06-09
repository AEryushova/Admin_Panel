package admin.utils.decoratorsTest.doctors;

import admin.data.DataInfo;
import admin.utils.dbUtils.DataBaseQuery;
import admin.utils.testUtils.DataHelper;
import admin.utils.testUtils.DataPreparationService;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class SetPhotoDoctorDecorator implements BeforeEachCallback {

        @Override
        public void beforeEach(ExtensionContext context) throws Exception {
            DataPreparationService.uploadPhoto(DataInfo.DataTest.getPhoto());
            String photoUri=DataHelper.urlPhotoBuilder();
            DataBaseQuery.setPhotoDoctor(photoUri);
        }


}
