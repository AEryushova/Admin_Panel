package utils.preparationDataTests.doctors;

import utils.APIUtils.PreparationDataSettingTest;
import utils.dbUtils.DataBaseQuery;
import utils.otherUtils.TestHelper;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static data.TestData.DataTest.*;

public class AddDeletePhotoDoctorDecorator implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        PreparationDataSettingTest.uploadPhotoToStorage(PHOTO);
        String photoUri= TestHelper.urlPhotoBuilder();
        DataBaseQuery.setPhotoDoctor(photoUri, DOCTOR, DOCTOR_SPECIALIZATION);
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DataBaseQuery.setDefaultPhotoDoctor(DOCTOR, DOCTOR_SPECIALIZATION, DEFAULT_PHOTO);
        PreparationDataSettingTest.deleteImageInStorage();
    }
}
