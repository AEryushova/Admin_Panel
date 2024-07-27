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
import static admin.data.TestData.DataTest.NAME_OTHER_SERVICE_CATEGORY;
import static admin.utils.otherUtils.DataGenerator.*;

public class AddServiceInNewCategoryForDeleteSection implements BeforeEachCallback, AfterEachCallback {

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
    @Setter
    @Getter
    public static String parentServiceId;

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        PreparationDataServicesTest.addCategory(generateCategoryName());
        UUID categoryId= DataBaseQuery.selectServicesCategories(categoryName).getId();
        setCategoryId(categoryId);
        PreparationDataServicesTest.addSection(generateSectionName(),categoryId);
        UUID sectionId= DataBaseQuery.selectServicesCategories(sectionName).getId();
        setSectionId(sectionId);
        PreparationDataServicesTest.addSection(generateSubSectionName(),sectionId);
        UUID subsectionId= DataBaseQuery.selectServicesCategories(subSectionName).getId();
        setSubsectionId(subsectionId);
        String serviceCode=PreparationDataServicesTest.getRandomService(NAME_OTHER_SERVICE_CATEGORY);
        setServiceCode(serviceCode);
        String parentServiceId=PreparationDataServicesTest.getCategoryIdByName(NAME_OTHER_SERVICE_CATEGORY);
        setParentServiceId(parentServiceId);
        PreparationDataServicesTest.transferServices(serviceCode,parentServiceId,subsectionId.toString());
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        PreparationDataServicesTest.deleteCategory(sectionId);
        PreparationDataServicesTest.deleteCategory(categoryId);
    }
}
