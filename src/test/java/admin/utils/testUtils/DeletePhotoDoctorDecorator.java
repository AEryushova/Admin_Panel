package admin.utils.testUtils;

import admin.data.DataInfo;
import admin.utils.dbUtils.DataBaseUtils;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class DeletePhotoDoctorDecorator implements BeforeEachCallback {

        @Override
        public void beforeEach(ExtensionContext context) throws Exception {
                DataBaseUtils.setDefaultPhotoDoctor(DataInfo.DataTest.getDoctorId());
        }
}

