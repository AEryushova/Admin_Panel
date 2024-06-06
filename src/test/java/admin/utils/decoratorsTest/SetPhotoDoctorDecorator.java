package admin.utils.decoratorsTest;

import admin.utils.dbUtils.DataBaseUtils;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class SetPhotoDoctorDecorator implements BeforeEachCallback {

        @Override
        public void beforeEach(ExtensionContext context) throws Exception {
            DataBaseUtils.setPhotoDoctor();
        }
}
