package pages.ServicesPage;

import pages.BasePage.BasePage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class ServicesPage extends BasePage {

    private final SelenideElement TAB_NAME = $x("//a[text()='Услуги']");
    private final SelenideElement SEARCH_SERVICES = $x("//input[@placeholder='Поиск услуги']");
    private final ElementsCollection CONTAINER_CATEGORIES = $$x("//div[@class='qH7D']/span");
    private final SelenideElement OTHER_SERVICES = $x("//span[text()='Иные услуги']//parent::div//parent::div[@class='ZAC4']");
    private final SelenideElement TELEMEDICINE = $x("//span[text()='Телемедицина']//parent::div//parent::div[@class='ZAC4']");
    private final SelenideElement DOCTORS = $x("//span[text()='Врачи']//parent::div//parent::div[@class='ZAC4']");
    private final SelenideElement LABORATORY = $x("//span[text()='Лаборатория']//parent::div//parent::div[@class='ZAC4']");
    private final SelenideElement DIAGNOSTICS = $x("//span[text()='Диагностика']//parent::div//parent::div[@class='ZAC4']");
    private final SelenideElement DENTISTRY = $x("//span[text()='Стоматология']//parent::div//parent::div[@class='ZAC4']");

    @Step("Верифицировать страницу Услуги")
    public void verifyServicesPage() {
        TAB_NAME.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SEARCH_SERVICES.shouldBe(Condition.visible, Duration.ofSeconds(5));
        OTHER_SERVICES.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TELEMEDICINE.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DOCTORS.shouldBe(Condition.visible, Duration.ofSeconds(5));
        LABORATORY.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DIAGNOSTICS.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DENTISTRY.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Нажать кнопку открытия правил подготовки к категории '{0}'")
    public RulesPreparingWindow clickButtonOpenRulesPreparingCategory(String categoryName){
        SelenideElement RULES_PREPARING = searchCategory(categoryName).$x("div[@class='Ie41']");
        RULES_PREPARING.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new RulesPreparingWindow();
    }

    @Step("Нажать кнопку раскрытия категории")
    public CategoryCard clickButtonOpenCategory(String categoryName) {
        SelenideElement EXPAND_CATEGORY = searchCategory(categoryName).$x("div[@class='gm_s']");
        EXPAND_CATEGORY.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new CategoryCard();
    }

    @Step("Нажать кнопку закрытия категории")
    public void clickButtonCloseCategory(String categoryName) {
        SelenideElement CLOSE_CATEGORY = searchCategory(categoryName).$x("div[@class='gm_s']");
        CLOSE_CATEGORY.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Найти категорию с названием '{0}'")
    public SelenideElement searchCategory(String categoryName){
        SelenideElement CATEGORY=$x("//span[text()='" + categoryName + "']//parent::div//parent::div[@class='ZAC4']");
        CATEGORY.shouldBe(Condition.visible);
        return CATEGORY;
    }

    @Step("Получить индекс категории по названию '{0}'")
    public int getCategoryIndexByName(String categoryName) {
        List<SelenideElement> categoryElements = CONTAINER_CATEGORIES;
        for (int i = 0; i < categoryElements.size(); i++) {
            if (categoryElements.get(i).getText().equals(categoryName)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Category not found: " + categoryName);
    }

    @Step("Получить все категории")
    public ElementsCollection getCategories() {
        return CONTAINER_CATEGORIES;
    }

    public SelenideElement getCategoryByName(String title) {
        return CONTAINER_CATEGORIES.findBy(Condition.text(title));
    }

    @Step("Поменять местами категорию с названием '{0}' и категорию с названием '{1}'")
    public void changeSequenceDisplayCategories(String sourceName, String targetName) {
        SelenideElement categorySource = searchCategory(sourceName);
        SelenideElement categoryTarget = searchCategory(targetName);
        Actions actions = actions();
        actions.clickAndHold(categorySource)
                .moveToElement(categoryTarget)
                .release()
                .perform();
    }
}
