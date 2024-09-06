package utils.preparationData.services;


import utils.APIUtils.PreparationDataServicesTest;
import utils.dbUtils.DataBaseQuery;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.UUID;

import static data.TestData.DataTest.*;
import static utils.testsUtils.DataGenerator.*;

public class AddDeleteSubsection implements BeforeEachCallback, AfterEachCallback {

    @Setter
    @Getter
    public static UUID categoryId;
    @Getter
    @Setter
    public static UUID sectionId;
    @Getter
    @Setter
    public static UUID subsectionId;

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
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        PreparationDataServicesTest.deleteCategory(subsectionId);
        PreparationDataServicesTest.deleteCategory(sectionId);
        PreparationDataServicesTest.deleteCategory(categoryId);
    }
}
