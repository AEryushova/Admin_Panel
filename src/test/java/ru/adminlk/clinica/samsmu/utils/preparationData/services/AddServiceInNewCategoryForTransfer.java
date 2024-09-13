package ru.adminlk.clinica.samsmu.utils.preparationData.services;

import ru.adminlk.clinica.samsmu.utils.APIUtils.PreparationDataServicesTest;
import ru.adminlk.clinica.samsmu.utils.dbUtils.DataBaseQuery;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.UUID;

import static ru.adminlk.clinica.samsmu.data.TestData.DataTest.*;
import static ru.adminlk.clinica.samsmu.utils.testsUtils.DataGenerator.*;

public class AddServiceInNewCategoryForTransfer implements BeforeEachCallback, AfterEachCallback {
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
        PreparationDataServicesTest.deleteCategory(subsectionId);
        PreparationDataServicesTest.deleteCategory(sectionId);
        PreparationDataServicesTest.deleteCategory(categoryId);
    }
}
