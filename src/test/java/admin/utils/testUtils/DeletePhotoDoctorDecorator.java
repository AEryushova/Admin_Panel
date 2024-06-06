package admin.utils.testUtils;

import admin.data.DataInfo;
import admin.utils.dbUtils.DataBaseUtils;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class DeletePhotoDoctorDecorator implements BeforeEachCallback , AfterEachCallback {

        @Override
        public void beforeEach(ExtensionContext context) throws Exception {
                DataBaseUtils.setDefaultPhotoDoctor2(DataInfo.DataTest.getDoctorName(),DataInfo.DataTest.getDoctorSpecialization());
        }

        @Override
        public void afterEach(ExtensionContext context) throws Exception {
                DataBaseUtils.setDefaultPhotoDoctor2(DataInfo.DataTest.getDoctorName(),DataInfo.DataTest.getDoctorSpecialization());
        }
}

