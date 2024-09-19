package ru.adminlk.clinica.samsmu.utils.preparationData.doctors;


import ru.adminlk.clinica.samsmu.utils.dbUtils.DataBaseQuery;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.UUID;

import static ru.adminlk.clinica.samsmu.data.FinalTestData.TestData.*;
import static ru.adminlk.clinica.samsmu.utils.testsUtils.DataGenerator.*;
import static ru.adminlk.clinica.samsmu.utils.testsUtils.TestHelper.*;

public class AddDescription implements BeforeEachCallback {

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
        DataBaseQuery.addDescription(sectionId, generateText(),0, generateDate("current"), generateDate("current"),generateUuid());
    }
}
