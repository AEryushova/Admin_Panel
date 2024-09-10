package utils.preparationData.doctors;


import utils.dbUtils.DataBaseQuery;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.UUID;

import static data.TestData.DataTest.*;
import static utils.testsUtils.DataGenerator.generateWord;
import static utils.testsUtils.TestHelper.*;

public class AddSection implements BeforeEachCallback{

    @Setter
    @Getter
    public static UUID doctorId;

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        UUID doctorId = DataBaseQuery.selectInfoDoctor(DOCTOR, DOCTOR_SPECIALIZATION).getEmployee_id();
        setDoctorId(doctorId);
        DataBaseQuery.clearSection(doctorId);
        DataBaseQuery.addSection(doctorId, generateWord(),0, generateDate("current"), generateDate("current"),generateUuid());

    }
}
