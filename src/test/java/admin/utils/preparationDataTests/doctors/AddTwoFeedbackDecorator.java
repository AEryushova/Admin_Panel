package admin.utils.preparationDataTests.doctors;

import admin.data.TestData;
import admin.utils.dbUtils.DataBaseQuery;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.UUID;

public class AddTwoFeedbackDecorator implements BeforeEachCallback, AfterEachCallback {

    @Setter
    @Getter
    public static UUID doctorId;


    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        UUID doctorId = DataBaseQuery.selectInfoDoctor(TestData.DataTest.getDOCTOR(), TestData.DataTest.getDOCTOR_SPECIALIZATION()).getEmployee_id();
        setDoctorId(doctorId);
        DataBaseQuery.clearAllFeedback();
        DataBaseQuery.addFeedback(doctorId, TestData.DataTest.getNAME_PATIENT(), TestData.DataTest.getFEEDBACK(),false);
        DataBaseQuery.addYesterdayFeedback(doctorId, TestData.DataTest.getNAME_PATIENT(), TestData.DataTest.getFEEDBACK(),false);
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllFeedback();
    }
}
