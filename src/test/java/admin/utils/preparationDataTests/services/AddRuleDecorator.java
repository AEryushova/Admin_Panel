package admin.utils.preparationDataTests.services;

import admin.data.TestData;
import admin.utils.APIUtils.PreparationDataServicesTest;
import admin.utils.dbUtils.DataBaseQuery;
import lombok.Getter;

import lombok.Setter;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.UUID;

public class AddRuleDecorator implements BeforeEachCallback {

    @Setter
    @Getter
    public static UUID categoryId;

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
      UUID categoryId=DataBaseQuery.selectServicesInfo(TestData.DataTest.getCATEGORY_RULES()).getId();
      setCategoryId(categoryId);
      PreparationDataServicesTest.deleteRuleCategory(categoryId);
      PreparationDataServicesTest.addRuleCategory(categoryId, TestData.DataTest.getRULE_TITLE(), TestData.DataTest.getRULE_DESCRIPTION());
    }
}
