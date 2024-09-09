package utils.preparationData.doctors;


import utils.dbUtils.DataBaseQuery;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.UUID;

import static data.TestData.DataTest.*;
import static utils.testsUtils.DataGenerator.generateNamePatient;
import static utils.testsUtils.DataGenerator.generateText;
import static utils.testsUtils.TestHelper.*;

public class AddPublishedDeleteFeedback implements BeforeEachCallback, AfterEachCallback {

    @Setter
    @Getter
    public static UUID doctorId;
    @Setter
    @Getter
    public static UUID feedbackId;

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        UUID doctorId = DataBaseQuery.selectInfoDoctor(DOCTOR, DOCTOR_SPECIALIZATION).getEmployee_id();
        setDoctorId(doctorId);
        DataBaseQuery.clearAllFeedback();
        DataBaseQuery.addFeedback(doctorId, generateNamePatient(), generateText(), true, generateDate("now"), generateDate("now"),generateUuid());
        UUID feedbackId = DataBaseQuery.selectFeedback().getId();
        setFeedbackId(feedbackId);
        DataBaseQuery.publishedFeedback(feedbackId);
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllFeedback();
    }
}
