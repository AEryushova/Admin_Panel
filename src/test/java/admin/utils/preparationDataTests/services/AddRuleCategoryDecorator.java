package admin.utils.preparationDataTests.services;


import admin.utils.APIUtils.PreparationDataServicesTest;
import admin.utils.dbUtils.DataBaseQuery;
import lombok.Getter;

import lombok.Setter;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.UUID;

import static admin.data.TestData.DataTest.*;

public class AddRuleCategoryDecorator implements BeforeEachCallback {

    @Setter
    @Getter
    public static UUID categoryId;

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
      UUID categoryId=DataBaseQuery.selectServicesInfo(CATEGORY_RULES).getId();
      setCategoryId(categoryId);
      PreparationDataServicesTest.deleteRuleCategory(categoryId);
      PreparationDataServicesTest.addRuleCategory(categoryId, RULE_TITLE, RULE_DESCRIPTION);
    }
}
