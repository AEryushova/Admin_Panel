package ru.adminlk.clinica.utils.preparationData.services;

import ru.adminlk.clinica.utils.APIUtils.PreparationDataServicesTest;
import ru.adminlk.clinica.utils.dbUtils.dbUtils.DataBaseQuery;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.UUID;

import static ru.adminlk.clinica.data.FinalTestData.TestData.NAME_OTHER_SERVICE_CATEGORY;
import static ru.adminlk.clinica.utils.testsUtils.DataGenerator.*;
import static ru.adminlk.clinica.data.GeneratedTestData.*;

public class AddTwoServices implements BeforeEachCallback, AfterEachCallback {

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
    public static String serviceCodeFirst;
    @Getter
    @Setter
    public static String serviceCodeSecond;
    @Setter
    @Getter
    public static String parentServiceId;


    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        PreparationDataServicesTest.addCategory(generateCategoryName());
        UUID categoryId = DataBaseQuery.selectServicesCategories(categoryName).getId();
        setCategoryId(categoryId);
        PreparationDataServicesTest.addSection(generateSectionName(), categoryId);
        UUID sectionId = DataBaseQuery.selectServicesCategories(sectionName).getId();
        setSectionId(sectionId);
        PreparationDataServicesTest.addSection(generateSubSectionName(), sectionId);
        UUID subsectionId = DataBaseQuery.selectServicesCategories(subSectionName).getId();
        setSubsectionId(subsectionId);
        String serviceCodeFirst = PreparationDataServicesTest.getRandomService2(NAME_OTHER_SERVICE_CATEGORY);
        setServiceCodeFirst(serviceCodeFirst);
        String serviceCodeSecond = PreparationDataServicesTest.getRandomService2(NAME_OTHER_SERVICE_CATEGORY);
        setServiceCodeSecond(serviceCodeSecond);
        String parentServiceId = PreparationDataServicesTest.getCategoryIdByName(NAME_OTHER_SERVICE_CATEGORY);
        setParentServiceId(parentServiceId);
        PreparationDataServicesTest.transferServices(serviceCodeFirst, parentServiceId, subsectionId.toString());
        PreparationDataServicesTest.transferServices(serviceCodeSecond, parentServiceId, subsectionId.toString());
    }
    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        PreparationDataServicesTest.transferServices(serviceCodeFirst,subsectionId.toString(),parentServiceId);
        PreparationDataServicesTest.transferServices(serviceCodeSecond,subsectionId.toString(),parentServiceId);
        PreparationDataServicesTest.deleteCategory(subsectionId);
        PreparationDataServicesTest.deleteCategory(sectionId);
        PreparationDataServicesTest.deleteCategory(categoryId);
    }
}
