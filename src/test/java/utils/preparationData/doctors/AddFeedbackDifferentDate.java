package utils.preparationData.doctors;

import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import utils.dbUtils.DataBaseQuery;

import java.util.UUID;

import static data.TestData.DataTest.DOCTOR;
import static data.TestData.DataTest.DOCTOR_SPECIALIZATION;
import static utils.testsUtils.DataGenerator.*;
import static utils.testsUtils.TestHelper.*;

public class AddFeedbackDifferentDate implements BeforeEachCallback, AfterEachCallback {

    @Setter
    @Getter
    public static UUID doctorId;

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        UUID doctorId = DataBaseQuery.selectInfoDoctor(DOCTOR, DOCTOR_SPECIALIZATION).getEmployee_id();
        setDoctorId(doctorId);
        DataBaseQuery.clearAllFeedback();
        DataBaseQuery.addFeedback(doctorId, generateNamePatient(), generateText(), false, generateDate("current"), generateDate("current"),generateUuid());
        DataBaseQuery.addFeedback(doctorId, generateNamePatient(), generateText(), false, generateDate("previous"), generateDate("previous"),generateUuid());
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllFeedback();
    }
}
