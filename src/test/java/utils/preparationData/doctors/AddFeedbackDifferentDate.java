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
import static utils.testsUtils.DataGenerator.generateNamePatient;
import static utils.testsUtils.DataGenerator.generateText;
import static utils.testsUtils.TestHelper.*;
import static utils.testsUtils.TestHelper.getDateTime;

public class AddFeedbackDifferentDate implements BeforeEachCallback, AfterEachCallback {

    @Setter
    @Getter
    public static UUID doctorId;

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        UUID doctorId = DataBaseQuery.selectInfoDoctor(DOCTOR, DOCTOR_SPECIALIZATION).getEmployee_id();
        setDoctorId(doctorId);
        DataBaseQuery.clearAllFeedback();
        DataBaseQuery.addFeedback(doctorId, generateNamePatient(), generateText(), false, getDateTime(), getDateTime(),generateUuid());
        DataBaseQuery.addFeedback(doctorId, generateNamePatient(), generateText(), false, getPreviousMonthDateTime(), getPreviousMonthDateTime(),generateUuid());
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllFeedback();
    }
}
