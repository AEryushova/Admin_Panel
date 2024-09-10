package utils.preparationData.doctors;


import utils.dbUtils.DataBaseQuery;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.UUID;

import static data.TestData.DataTest.*;
import static utils.testsUtils.DataGenerator.generateWord;
import static utils.testsUtils.TestHelper.*;

public class DeleteDescription implements BeforeEachCallback, AfterEachCallback {

    @Setter
    @Getter
    public static UUID doctorId;
    @Setter
    @Getter
    public static UUID sectionId;

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        UUID doctorId = DataBaseQuery.selectInfoDoctor(DOCTOR, DOCTOR_SPECIALIZATION).getEmployee_id();
        setDoctorId(doctorId);
        DataBaseQuery.clearSection(doctorId);
        DataBaseQuery.addSection(doctorId, generateWord(),0, generateDate("current"), generateDate("current"),generateUuid());
        UUID sectionId = DataBaseQuery.selectSection(doctorId).getEmployee_details_id();
        setSectionId(sectionId);
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearDescription(sectionId);
        DataBaseQuery.clearSection(doctorId);
    }
}
