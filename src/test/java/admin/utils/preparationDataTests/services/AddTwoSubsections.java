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

public class AddTwoSubsections implements BeforeEachCallback, AfterEachCallback {

    @Setter
    @Getter
    public static UUID categoryId;
    @Setter
    @Getter
    public static UUID sectionId;
    @Setter
    @Getter
    public static UUID subsectionIdFirst;
    @Setter
    @Getter
    public static UUID subsectionIdSecond;


    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        PreparationDataServicesTest.addCategory(NAME_CATEGORY);
        UUID categoryId= DataBaseQuery.selectServicesInfo(NAME_CATEGORY).getId();
        setCategoryId(categoryId);
        PreparationDataServicesTest.addSection(NAME_SECTION,categoryId);
        UUID sectionId= DataBaseQuery.selectServicesInfo(NAME_SECTION).getId();
        setSectionId(sectionId);
        PreparationDataServicesTest.addSection(NAME_SUBSECTION,sectionId);
        UUID subsectionIdFirst= DataBaseQuery.selectServicesInfo(NAME_SUBSECTION).getId();
        setSubsectionIdFirst(subsectionIdFirst);
        PreparationDataServicesTest.addSection(NEW_NAME_SUBSECTION,sectionId);
        UUID subsectionIdSecond= DataBaseQuery.selectServicesInfo(NEW_NAME_SUBSECTION).getId();
        setSubsectionIdSecond(subsectionIdSecond);

    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        PreparationDataServicesTest.deleteCategory(subsectionIdFirst);
        PreparationDataServicesTest.deleteCategory(subsectionIdSecond);
        PreparationDataServicesTest.deleteCategory(sectionId);
        PreparationDataServicesTest.deleteCategory(categoryId);
    }
}
