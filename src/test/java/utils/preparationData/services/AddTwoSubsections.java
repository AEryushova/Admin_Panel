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
        PreparationDataServicesTest.addCategory(generateCategoryName());
        UUID categoryId= DataBaseQuery.selectServicesCategories(categoryName).getId();
        setCategoryId(categoryId);
        PreparationDataServicesTest.addSection(generateSectionName(),categoryId);
        UUID sectionId= DataBaseQuery.selectServicesCategories(sectionName).getId();
        setSectionId(sectionId);
        PreparationDataServicesTest.addSection(generateSubSectionName(),sectionId);
        UUID subsectionIdFirst= DataBaseQuery.selectServicesCategories(subSectionName).getId();
        setSubsectionIdFirst(subsectionIdFirst);
        PreparationDataServicesTest.addSection(generateText(),sectionId);
        UUID subsectionIdSecond= DataBaseQuery.selectServicesCategories(text).getId();
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
