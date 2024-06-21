package admin.utils.preparationDataTests.services;

import admin.config.DataConfig;
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
      UUID categoryId=DataBaseQuery.selectServicesInfo(DataConfig.DataTest.getCATEGORY_RULES()).getId();
      setCategoryId(categoryId);
      PreparationDataServicesTest.deleteRuleCategory(categoryId);
      PreparationDataServicesTest.addRuleCategory(categoryId, DataConfig.DataTest.getRULE_TITLE(),DataConfig.DataTest.getRULE_DESCRIPTION());
    }
}
