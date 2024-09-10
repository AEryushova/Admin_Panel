package utils.preparationData.doctors;

import org.junit.jupiter.api.extension.AfterEachCallback;
import utils.dbUtils.DataBaseQuery;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.UUID;

import static data.TestData.DataTest.*;
import static utils.testsUtils.DataGenerator.generateNamePatient;
import static utils.testsUtils.DataGenerator.generateText;
import static utils.testsUtils.TestHelper.*;

public class AddUnpublishedFeedback implements BeforeEachCallback, AfterEachCallback {

    @Setter
    @Getter
    public static UUID doctorId;


    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        UUID doctorId = DataBaseQuery.selectInfoDoctor(DOCTOR, DOCTOR_SPECIALIZATION).getEmployee_id();
        setDoctorId(doctorId);
        DataBaseQuery.clearAllFeedback();
        DataBaseQuery.addFeedback(doctorId, generateNamePatient(), generateText(), false, generateDate("current"), generateDate("current"),generateUuid());
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllFeedback();
    }
}
