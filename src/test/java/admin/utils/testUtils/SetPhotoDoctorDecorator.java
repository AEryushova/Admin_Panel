package admin.utils.testUtils;

import admin.data.DataTest;
import admin.utils.dbUtils.DataBaseUtils;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class SetPhotoDoctorDecorator implements BeforeEachCallback {

        @Override
        public void beforeEach(ExtensionContext context) throws Exception {
            DataBaseUtils.setPhotoDoctor(DataTest.getPhoto());
        }
}
