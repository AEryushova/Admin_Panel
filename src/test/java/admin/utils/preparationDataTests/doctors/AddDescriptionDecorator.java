package admin.utils.preparationDataTests.doctors;

import admin.data.DataConfig;
import admin.utils.dbUtils.DataBaseQuery;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.UUID;

public class AddDescriptionDecorator implements BeforeEachCallback {

    @Setter
    @Getter
    public static UUID doctorId;
    @Setter
    @Getter
    public static UUID sectionId;

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        UUID doctorId = DataBaseQuery.selectInfoDoctor(DataConfig.DataTest.getDOCTOR(),DataConfig.DataTest.getDOCTOR_SPECIALIZATION()).getEmployee_id();
        setDoctorId(doctorId);
        DataBaseQuery.clearSection(doctorId);
        DataBaseQuery.addSection(doctorId,DataConfig.DataTest.getSECTION(),0);
        UUID sectionId = DataBaseQuery.selectSection(doctorId).getEmployee_details_id();
        setSectionId(sectionId);
        DataBaseQuery.addDescription(sectionId, DataConfig.DataTest.getDESCRIPTION(),0);
    }
}
