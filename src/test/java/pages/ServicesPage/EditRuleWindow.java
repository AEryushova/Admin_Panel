package pages.ServicesPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class EditRuleWindow {

    public final SelenideElement WINDOW = $x("//button[text()='Изменить']//parent::div//parent::div//parent::div//parent::div//parent::div[@class='TW3C']");
    public final SelenideElement TITLE_FIELD = $x("//input[@placeholder='Укажите заголовок правила']");
    public final SelenideElement DESCRIPTION_FIELD = $x("//textarea[@placeholder='Укажите описание правила']");
    public final SelenideElement EDIT_BUTTON = $x("//button[text()='Изменить']");
    private final SelenideElement DELETE_BUTTON = $x("//button[text()='Удалить']");
    private final SelenideElement CLOSE_WINDOW_BUTTON = $x("//div[@class='TW3C']/preceding-sibling::div[@class='UnAf Ee5G']");

    @Step("Верифицировать окно изменения правила")
    public void verifyEditRuleWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TITLE_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DESCRIPTION_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EDIT_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Ввести в поле заголовка '{0}'")
    public void fillFieldTitle(String header) {
        TITLE_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(header);
    }

    @Step("Ввести в поле описания '{0}'")
    public void fillFieldDescription(String description) {
        DESCRIPTION_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(description);
    }

    @Step("Нажать кнопку изменения")
    public void clickButtonChangeRules() {
        EDIT_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать кнопку удаления")
    public void clickButtonDeleteRules() {
        DELETE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Очистить поле заголовка")
    public void clearTitleField(){
        TITLE_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .sendKeys(Keys.CONTROL, "a");
        TITLE_FIELD.sendKeys(Keys.BACK_SPACE);
    }

    @Step("Очистить поле описания")
    public void clearDescriptionField(){
        DESCRIPTION_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .sendKeys(Keys.CONTROL, "a");
        DESCRIPTION_FIELD.sendKeys(Keys.BACK_SPACE);
    }

    @Step("Получить значение поля заголовка")
    public String getTitleRule() {
        TITLE_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return TITLE_FIELD.getValue();
    }

    @Step("Получить значение поля описания")
    public String getDescriptionRule() {
        DESCRIPTION_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return DESCRIPTION_FIELD.getValue();
    }

    @Step("Закрыть окно изменения правила")
    public void closeWindowEditRule() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW.shouldNotBe(Condition.visible, Duration.ofSeconds(10));
    }

    @Step("Проверить отображение окна изменения правила")
    public boolean isWindowAppear() {
        return WINDOW.isDisplayed();
    }

}
