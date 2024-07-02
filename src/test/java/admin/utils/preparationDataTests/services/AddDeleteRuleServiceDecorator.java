package admin.utils.preparationDataTests.services;

import admin.utils.APIUtils.PreparationDataServicesTest;
import admin.utils.dbUtils.DataBaseQuery;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.UUID;

import static admin.data.TestData.DataTest.*;
import static admin.data.TestData.DataTest.NAME_OTHER_SERVICE_CATEGORY;

public class AddDeleteRuleServiceDecorator implements BeforeEachCallback, AfterEachCallback {

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
        PreparationDataServicesTest.addCategory(NAME_CATEGORY);
        UUID categoryId= DataBaseQuery.selectServicesCategories(NAME_CATEGORY).getId();
        setCategoryId(categoryId);
        PreparationDataServicesTest.addSection(NAME_SECTION,categoryId);
        UUID sectionId= DataBaseQuery.selectServicesCategories(NAME_SECTION).getId();
        setSectionId(sectionId);
        PreparationDataServicesTest.addSection(NAME_SUBSECTION,sectionId);
        UUID subsectionId= DataBaseQuery.selectServicesCategories(NAME_SUBSECTION).getId();
        setSubsectionId(subsectionId);
        String serviceCode=PreparationDataServicesTest.getRandomService(NAME_OTHER_SERVICE_CATEGORY);
        setServiceCode(serviceCode);
        String parentServiceId=PreparationDataServicesTest.getCategoryIdByName(NAME_OTHER_SERVICE_CATEGORY);
        setParentServiceId(parentServiceId);
        PreparationDataServicesTest.transferServices(serviceCode,parentServiceId,subsectionId.toString());
        PreparationDataServicesTest.deleteRuleService(serviceCode);
        PreparationDataServicesTest.addRuleService(serviceCode,RULE_TITLE,RULE_DESCRIPTION);
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
