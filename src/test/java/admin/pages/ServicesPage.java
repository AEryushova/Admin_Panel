package admin.pages;

import admin.pages.modalWindowServices.CategoryWindow;
import admin.pages.modalWindowServices.RulesPreparingWindow;
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
    private final SelenideElement OTHER_SERVICES = $x("//span[text()='Иные услуги']//parent::div");
    private final SelenideElement TELEMEDICINE = $x("//span[text()='Телемедицина']//parent::div");
    private final SelenideElement DOCTORS = $x("//span[text()='Врачи']//parent::div");
    private final SelenideElement LABORATORY = $x("//span[text()='Лаборатория']//parent::div");
    private final SelenideElement DIAGNOSTICS = $x("//span[text()='Диагностика']//parent::div");
    private final SelenideElement DENTISTRY = $x("//span[text()='Стоматология']//parent::div");


    private final SelenideElement expandCategoryLaboratory= $x("//span[text()='Лаборатория']//parent::div/following-sibling::div[@class='gm_s']");
    private final SelenideElement rulesPreparingLaboratory= $x("//span[text()='Лаборатория']//parent::div/following-sibling::div[@class='Ie41']");

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

    public CategoryWindow openCategory(){
        expandCategoryLaboratory.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
        .click();
        return new CategoryWindow();
    }

    public RulesPreparingWindow openRulesPreparing(){
        rulesPreparingLaboratory.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
        .click();
        return new RulesPreparingWindow();
    }


    public int getCategoryIndexByName(String categoryName) {
        List<SelenideElement> categoryElements = CONTAINER_CATEGORY;
        for (int i = 0; i < categoryElements.size(); i++) {
            if (categoryElements.get(i).getText().equals(categoryName)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Category not found: " + categoryName);
    }

    public void sequenceChangeCategory(String sourceName, String targetName) {
        SelenideElement sourceCategory = getElementByName(sourceName);
        SelenideElement targetCategory = getElementByName(targetName);
        sequenceChangeActive(sourceCategory, targetCategory);
    }

    private SelenideElement getElementByName(String categoryName){
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
