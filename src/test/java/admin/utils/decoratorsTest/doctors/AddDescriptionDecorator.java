package admin.utils.decoratorsTest.doctors;

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
        UUID doctorId = DataBaseQuery.selectInfoDoctor().getEmployee_id();
        setDoctorId(doctorId);
        DataBaseQuery.clearSection(doctorId);
        DataBaseQuery.addSection(doctorId);
        UUID sectionId = DataBaseQuery.selectSection(doctorId).getEmployee_details_id();
        setSectionId(sectionId);
        DataBaseQuery.addDescription(sectionId);
    }
}
