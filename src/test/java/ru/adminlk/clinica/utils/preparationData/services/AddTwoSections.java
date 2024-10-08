package ru.adminlk.clinica.utils.preparationData.services;

import ru.adminlk.clinica.utils.APIUtils.PreparationDataServicesTest;
import ru.adminlk.clinica.utils.dbUtils.dbUtils.DataBaseQuery;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.UUID;

import static ru.adminlk.clinica.data.GeneratedTestData.*;
import static ru.adminlk.clinica.utils.testsUtils.DataGenerator.*;

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
        PreparationDataServicesTest.addCategory(generateCategoryName());
        UUID categoryId= DataBaseQuery.selectServicesCategories(categoryName).getId();
        setCategoryId(categoryId);
        PreparationDataServicesTest.addSection(generateSectionName(),categoryId);
        UUID sectionIdFirst= DataBaseQuery.selectServicesCategories(sectionName).getId();
        setSectionIdFirst(sectionIdFirst);
        PreparationDataServicesTest.addSection(generateSubSectionName(),categoryId);
        UUID sectionIdSecond= DataBaseQuery.selectServicesCategories(subSectionName).getId();
        setSectionIdSecond(sectionIdSecond);
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        PreparationDataServicesTest.deleteCategory(sectionIdFirst);
        PreparationDataServicesTest.deleteCategory(sectionIdSecond);
        PreparationDataServicesTest.deleteCategory(categoryId);
    }
}
