package admin.utils.preparationDataTests.services;

import admin.utils.APIUtils.PreparationDataServicesTest;
import admin.utils.dbUtils.DataBaseQuery;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.UUID;

import static admin.data.TestData.DataTest.CATEGORY_RULES;

public class DeleteRuleCategoryDecorator implements BeforeEachCallback, AfterEachCallback {

    @Setter
    @Getter
    public static UUID categoryId;


    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        UUID categoryId=DataBaseQuery.selectServicesCategories(CATEGORY_RULES).getId();
        setCategoryId(categoryId);
        PreparationDataServicesTest.deleteRuleCategory(categoryId);
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        PreparationDataServicesTest.deleteRuleCategory(categoryId);
    }

}
