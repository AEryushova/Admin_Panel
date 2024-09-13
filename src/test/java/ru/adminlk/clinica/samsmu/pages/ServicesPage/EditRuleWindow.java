package ru.adminlk.clinica.samsmu.pages.ServicesPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class EditRuleWindow {

    public final SelenideElement
            WINDOW = $x("//button[text()='Изменить']//parent::div//parent::div//parent::div//parent::div//parent::div[@class='TW3C']"),
            TITLE_FIELD = $x("//input[@placeholder='Укажите заголовок правила']"),
            DESCRIPTION_FIELD = $x("//textarea[@placeholder='Укажите описание правила']"),
            EDIT_BUTTON = $x("//button[text()='Изменить']"),
            DELETE_BUTTON = $x("//button[text()='Удалить']"),
            CLOSE_WINDOW_BUTTON = $x("//div[@class='TW3C']/preceding-sibling::div[@class='UnAf Ee5G']");

    @Step("Верифицировать окно изменения правила")
    public EditRuleWindow verifyEditRuleWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TITLE_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DESCRIPTION_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EDIT_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return this;
    }

    @Step("Ввести в поле заголовка '{0}'")
    public EditRuleWindow fillFieldTitle(String header) {
        TITLE_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(header);
        return this;
    }

    @Step("Ввести в поле описания '{0}'")
    public EditRuleWindow fillFieldDescription(String description) {
        DESCRIPTION_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(description);
        return this;
    }

    @Step("Нажать кнопку изменения")
    public EditRuleWindow clickButtonChangeRules() {
        EDIT_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return this;
    }

    @Step("Нажать кнопку удаления")
    public void clickButtonDeleteRules() {
        DELETE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Очистить поле заголовка")
    public EditRuleWindow clearTitleField() {
        TITLE_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .sendKeys(Keys.CONTROL, "a");
        TITLE_FIELD.sendKeys(Keys.BACK_SPACE);
        return this;
    }

    @Step("Очистить поле описания")
    public EditRuleWindow clearDescriptionField() {
        DESCRIPTION_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .sendKeys(Keys.CONTROL, "a");
        DESCRIPTION_FIELD.sendKeys(Keys.BACK_SPACE);
        return this;
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
        WINDOW.shouldNotBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Step("Проверить отображение окна изменения правила")
    public boolean isWindowAppear() {
        return WINDOW.isDisplayed();
    }

}
