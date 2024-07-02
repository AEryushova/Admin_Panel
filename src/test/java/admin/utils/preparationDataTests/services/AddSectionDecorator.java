package admin.utils.preparationDataTests.services;

import admin.utils.APIUtils.PreparationDataServicesTest;
import admin.utils.dbUtils.DataBaseQuery;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.UUID;

import static admin.data.TestData.DataTest.NAME_CATEGORY;
import static admin.data.TestData.DataTest.NAME_SECTION;

public class AddSectionDecorator implements BeforeEachCallback, AfterEachCallback {

    @Setter
    @Getter
    public static UUID categoryId;


    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        PreparationDataServicesTest.addCategory(NAME_CATEGORY);
        UUID categoryId= DataBaseQuery.selectServicesCategories(NAME_CATEGORY).getId();
        setCategoryId(categoryId);
        PreparationDataServicesTest.addSection(NAME_SECTION,categoryId);
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        PreparationDataServicesTest.deleteCategory(categoryId);
    }
}
