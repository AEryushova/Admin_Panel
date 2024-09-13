package ru.adminlk.clinica.samsmu.utils.preparationData.doctors;


import ru.adminlk.clinica.samsmu.utils.dbUtils.DataBaseQuery;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.UUID;

import static ru.adminlk.clinica.samsmu.data.TestData.DataTest.*;
import static ru.adminlk.clinica.samsmu.utils.testsUtils.DataGenerator.*;
import static ru.adminlk.clinica.samsmu.utils.testsUtils.TestHelper.*;

public class AddTwoFeedback implements BeforeEachCallback, AfterEachCallback {

    @Setter
    @Getter
    public static UUID doctorId;


    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        UUID doctorId = DataBaseQuery.selectInfoDoctor(DOCTOR, DOCTOR_SPECIALIZATION).getEmployee_id();
        setDoctorId(doctorId);
        DataBaseQuery.clearAllFeedback();
        DataBaseQuery.addFeedback(doctorId, generateNamePatient(), generateText(), false, generateDate("current"), generateDate("current"),generateUuid());
        DataBaseQuery.addFeedback(doctorId, generateNamePatient(), generateText(), false, generateDate("yesterday"), generateDate("yesterday"),generateUuid());
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DataBaseQuery.clearAllFeedback();
    }
}
