package ru.adminlk.clinica.utils.preparationData.services;

import ru.adminlk.clinica.utils.APIUtils.PreparationDataServicesTest;
import ru.adminlk.clinica.utils.dbUtils.dbUtils.DataBaseQuery;
import lombok.*;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.UUID;

import static ru.adminlk.clinica.data.FinalTestData.TestData.*;
import static ru.adminlk.clinica.utils.testsUtils.DataGenerator.*;
import static ru.adminlk.clinica.data.GeneratedTestData.*;

public class AddServiceInNewCategory implements BeforeEachCallback, AfterEachCallback {

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
        String serviceCode=PreparationDataServicesTest.getRandomService2(NAME_OTHER_SERVICE_CATEGORY);
        setServiceCode(serviceCode);
        String parentServiceId=PreparationDataServicesTest.getCategoryIdByName(NAME_OTHER_SERVICE_CATEGORY);
        setParentServiceId(parentServiceId);
        PreparationDataServicesTest.transferServices(serviceCode,parentServiceId,subsectionId.toString());
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        PreparationDataServicesTest.deleteRuleService(serviceCode);
        PreparationDataServicesTest.transferServices(serviceCode,subsectionId.toString(),parentServiceId);
        PreparationDataServicesTest.deleteCategory(subsectionId);
        PreparationDataServicesTest.deleteCategory(sectionId);
        PreparationDataServicesTest.deleteCategory(categoryId);
    }
}
