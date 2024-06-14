package admin.pages.ServicesPage;

import admin.data.DataConfig;
import admin.pages.BasePage.BasePage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.*;

public class ServicesPage extends BasePage {

    private final SelenideElement TAB_NAME = $x("//a[text()='Услуги']");
    private final SelenideElement SEARCH_SERVICES = $x("//input[@placeholder='Поиск услуги']");
    private final ElementsCollection CONTAINER_CATEGORY = $$x("//div[@class='qH7D']/span");
    private final SelenideElement OTHER_SERVICES = $x("//span[text()='Иные услуги']//parent::div//parent::div[@class='ZAC4']");
    private final SelenideElement TELEMEDICINE = $x("//span[text()='Телемедицина']//parent::div//parent::div[@class='ZAC4']");
    private final SelenideElement DOCTORS = $x("//span[text()='Врачи']//parent::div//parent::div[@class='ZAC4']");
    private final SelenideElement LABORATORY = $x("//span[text()='Лаборатория']//parent::div//parent::div[@class='ZAC4']");
    private final SelenideElement DIAGNOSTICS = $x("//span[text()='Диагностика']//parent::div//parent::div[@class='ZAC4']");
    private final SelenideElement DENTISTRY = $x("//span[text()='Стоматология']//parent::div//parent::div[@class='ZAC4']");
    private final SelenideElement CATEGORY = $x("//span[text()='" + DataConfig.DataTest.getCategoryName() + "']//parent::div//parent::div[@class='ZAC4']");


    private final SelenideElement expandCategoryLaboratory = $x("//span[text()='Диагностика']//parent::div/following-sibling::div[@class='gm_s']");


    public void servicesPage() {
        TAB_NAME.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SEARCH_SERVICES.shouldBe(Condition.visible, Duration.ofSeconds(5));
        OTHER_SERVICES.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TELEMEDICINE.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DOCTORS.shouldBe(Condition.visible, Duration.ofSeconds(5));
        LABORATORY.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DIAGNOSTICS.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DENTISTRY.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }


    public RulesPreparingWindow openRulesPreparingCategory() {
        CATEGORY.shouldBe(Condition.visible);
        SelenideElement rulesPreparing = CATEGORY.$x("div[@class='Ie41']");
        rulesPreparing.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new RulesPreparingWindow();
    }



    public CategoryWindow openCategory() {
        expandCategoryLaboratory.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new CategoryWindow();
    }


    public int getCategoryIndexByName(String categoryName) {
        CATEGORY.shouldBe(Condition.visible);
        List<SelenideElement> categoryElements = CONTAINER_CATEGORY;
        for (int i = 0; i < categoryElements.size(); i++) {
            if (categoryElements.get(i).getText().equals(categoryName)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Category not found: " + categoryName);
    }

    public void sequenceChangeCategory(String sourceName, String targetName) {
        SelenideElement sourceCategory = getCategoryByName(sourceName);
        SelenideElement targetCategory = getCategoryByName(targetName);
        sequenceChangeActive(sourceCategory, targetCategory);
    }

    public SelenideElement getCategoryByName(String categoryName) {
        Map<String, SelenideElement> elementMap = new HashMap<>();
        elementMap.put("Иные услуги", OTHER_SERVICES);
        elementMap.put("Телемедицина", TELEMEDICINE);
        elementMap.put("Врачи", DOCTORS);
        elementMap.put("Лаборатория", LABORATORY);
        elementMap.put("Диагностика", DIAGNOSTICS);
        elementMap.put("Стоматология", DENTISTRY);
        return elementMap.get(categoryName);
    }

    private void sequenceChangeActive(SelenideElement categorySource, SelenideElement categoryTarget) {
        Actions actions = actions();
        actions.clickAndHold(categorySource)
                .moveToElement(categoryTarget)
                .release()
                .perform();
    }
}
