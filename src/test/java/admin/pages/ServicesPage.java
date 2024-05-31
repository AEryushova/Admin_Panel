package admin.pages;

import admin.pages.modalWindowServices.CategoryWindow;
import admin.pages.modalWindowServices.RulesPreparingWindow;
import admin.utils.DataHelper;
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

    private final SelenideElement tabNameService = $x("//a[text()='Услуги']");
    private final SelenideElement searchFAQ= $x("//input[@placeholder='Поиск услуги']");
    private final ElementsCollection containerCategory= $$x("//div[@class='qH7D']/span");
    private final SelenideElement otherServices= $x("//span[text()='Иные услуги']//parent::div");
    private final SelenideElement telemedicine= $x("//span[text()='Телемедицина']//parent::div");
    private final SelenideElement doctors= $x("//span[text()='Врачи']//parent::div");
    private final SelenideElement laboratory= $x("//span[text()='Лаборатория']//parent::div");
    private final SelenideElement diagnostics= $x("//span[text()='Диагностика']//parent::div");
    private final SelenideElement dentistry= $x("//span[text()='Стоматология']//parent::div");


    private final SelenideElement expandCategoryLaboratory= $x("//span[text()='Лаборатория']//parent::div/following-sibling::div[@class='gm_s']");
    private final SelenideElement rulesPreparingLaboratory= $x("//span[text()='Лаборатория']//parent::div/following-sibling::div[@class='Ie41']");

    public void servicesPage() {
        tabNameService.shouldBe(Condition.visible, Duration.ofSeconds(5));
        searchFAQ.shouldBe(Condition.visible, Duration.ofSeconds(5));
        otherServices.shouldBe(Condition.visible, Duration.ofSeconds(5));
        telemedicine.shouldBe(Condition.visible, Duration.ofSeconds(5));
        doctors.shouldBe(Condition.visible, Duration.ofSeconds(5));
        laboratory.shouldBe(Condition.visible, Duration.ofSeconds(5));
        diagnostics.shouldBe(Condition.visible, Duration.ofSeconds(5));
        dentistry.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public CategoryWindow openCategory(){
        expandCategoryLaboratory.click();
        return new CategoryWindow();
    }

    public RulesPreparingWindow openRulesPreparing(){
        rulesPreparingLaboratory.click();
        return new RulesPreparingWindow();
    }


    public int getCategoryIndexByName(String categoryName) {
        List<SelenideElement> categoryElements = containerCategory;
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
        elementMap.put("Иные услуги", otherServices);
        elementMap.put("Телемедицина", telemedicine);
        elementMap.put("Врачи", doctors);
        elementMap.put("Лаборатория", laboratory);
        elementMap.put("Диагностика", diagnostics);
        elementMap.put("Стоматология", dentistry);
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
