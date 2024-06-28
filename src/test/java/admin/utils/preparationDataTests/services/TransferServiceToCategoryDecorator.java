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
import static admin.data.TestData.DataTest.NAME_SUBSECTION;

public class TransferServiceToCategoryDecorator implements BeforeEachCallback, AfterEachCallback {
    @Setter
    @Getter
    public static UUID categoryId;
    @Getter
    @Setter
    public static UUID sectionId;
    @Getter
    @Setter
    public static UUID subsectionId;
    @Getter
    @Setter
    public static String serviceCode;

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        PreparationDataServicesTest.addCategory(NAME_CATEGORY);
        UUID categoryId= DataBaseQuery.selectServicesInfo(NAME_CATEGORY).getId();
        setCategoryId(categoryId);
        PreparationDataServicesTest.addSection(NAME_SECTION,categoryId);
        UUID sectionId= DataBaseQuery.selectServicesInfo(NAME_SECTION).getId();
        setSectionId(sectionId);
        PreparationDataServicesTest.addSection(NAME_SUBSECTION,sectionId);
        UUID subsectionId= DataBaseQuery.selectServicesInfo(NAME_SUBSECTION).getId();
        setSubsectionId(subsectionId);
        String serviceCode=PreparationDataServicesTest.getRandomOtherService(OTHER_CATEGORY_ID);
        setServiceCode(serviceCode);
        PreparationDataServicesTest.transferServices(serviceCode,OTHER_CATEGORY_ID,subsectionId.toString());
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        PreparationDataServicesTest.transferServices(serviceCode,subsectionId.toString(),OTHER_CATEGORY_ID);
        PreparationDataServicesTest.deleteCategory(subsectionId);
        PreparationDataServicesTest.deleteCategory(sectionId);
        PreparationDataServicesTest.deleteCategory(categoryId);
    }
}
