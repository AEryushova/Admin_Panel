package admin.utils.testUtils;

import admin.data.DataTest;
import admin.utils.dbUtils.DataBaseUtils;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class DeletePhotoDoctorDecorator implements BeforeEachCallback {

        @Override
        public void beforeEach(ExtensionContext context) throws Exception {
                DataBaseUtils.setDefaultPhotoDoctor(DataTest.getDoctorId());
        }
}

