package admin.utils.preparationDataTests.services;

import admin.utils.APIUtils.PreparationDataServicesTest;
import admin.utils.dbUtils.DataBaseQuery;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.UUID;

import static admin.data.TestData.DataTest.*;

public class AddTwoSections implements BeforeEachCallback, AfterEachCallback {

    @Setter
    @Getter
    public static UUID categoryId;
    @Setter
    @Getter
    public static UUID sectionIdFirst;
    @Setter
    @Getter
    public static UUID sectionIdSecond;


    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        PreparationDataServicesTest.addCategory(NAME_CATEGORY);
        UUID categoryId= DataBaseQuery.selectServicesInfo(NAME_CATEGORY).getId();
        setCategoryId(categoryId);
        PreparationDataServicesTest.addSection(NAME_SECTION,categoryId);
        UUID sectionIdFirst= DataBaseQuery.selectServicesInfo(NAME_SECTION).getId();
        setSectionIdFirst(sectionIdFirst);
        PreparationDataServicesTest.addSection(NEW_NAME_SECTION,categoryId);
        UUID sectionIdSecond= DataBaseQuery.selectServicesInfo(NEW_NAME_SECTION).getId();
        setSectionIdFirst(sectionIdSecond);
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        PreparationDataServicesTest.deleteCategory(sectionIdFirst);
        PreparationDataServicesTest.deleteCategory(sectionIdSecond);
        PreparationDataServicesTest.deleteCategory(categoryId);
    }
}
