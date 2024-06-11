package admin.utils.preparationDataTests.doctors;

import admin.utils.dbUtils.DataBaseQuery;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class DeletePhotoDoctorDecorator implements BeforeEachCallback , AfterEachCallback {

        @Override
        public void beforeEach(ExtensionContext context) throws Exception {
                DataBaseQuery.setDefaultPhotoDoctor();
        }

        @Override
        public void afterEach(ExtensionContext context) throws Exception {
                DataBaseQuery.setDefaultPhotoDoctor();
        }
}

