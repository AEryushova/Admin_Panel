package admin.utils.preparationDataTests.services;

import admin.data.TestData;
import admin.utils.APIUtils.PreparationDataServicesTest;
import admin.utils.dbUtils.DataBaseQuery;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.UUID;

public class AddDeleteSectionDecorator implements BeforeEachCallback, AfterEachCallback {

    @Setter
    @Getter
    public static UUID categoryId;
    @Setter
    public static UUID sectionId;


    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        PreparationDataServicesTest.addCategory(TestData.DataTest.getNAME_CATEGORY());
        UUID categoryId= DataBaseQuery.selectServicesInfo(TestData.DataTest.getNAME_CATEGORY()).getId();
        setCategoryId(categoryId);
        PreparationDataServicesTest.addSection(TestData.DataTest.getNAME_SECTION(),categoryId);
        UUID sectionId= DataBaseQuery.selectServicesInfo(TestData.DataTest.getNAME_SECTION()).getId();
        setSectionId(sectionId);
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        PreparationDataServicesTest.deleteCategory(sectionId);
        PreparationDataServicesTest.deleteCategory(categoryId);
    }
}
