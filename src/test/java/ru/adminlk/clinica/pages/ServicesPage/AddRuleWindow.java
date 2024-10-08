package ru.adminlk.clinica.pages.ServicesPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class AddRuleWindow {
    public final SelenideElement
            WINDOW = $x("//span[text()='вернуться назад']/parent::div/parent::div/parent::div/parent::div"),
            RETURN_BUTTON = $x("//span[text()='вернуться назад']"),
            TITLE_FIELD = $x("//input[@placeholder='Укажите заголовок правила']"),
            DESCRIPTION_FIELD = $x("//textarea[@placeholder='Укажите описание правила']"),
            SAVE_BUTTON = $x("//button[text()='Сохранить']"),
            CLOSE_WINDOW_BUTTON = $x("//span[text()='вернуться назад']/parent::div/parent::div/parent::div/parent::div/preceding-sibling::div[@class='UnAf Ee5G']");

    @Step("Верифицировать окно добавления нового правила")
    public AddRuleWindow verifyAddRuleWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        RETURN_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TITLE_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DESCRIPTION_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SAVE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return this;
    }

    @Step("Ввести в поле заголовка '{0}'")
    public AddRuleWindow fillFieldTitle(String header) {
        TITLE_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(header);
        return this;
    }

    @Step("Ввести в поле описания '{0}'")
    public AddRuleWindow fillFieldDescription(String description) {
        DESCRIPTION_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(description);
        return this;
    }

    @Step("Нажать кнопку сохранения")
    public AddRuleWindow clickSaveButton() {
        SAVE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return this;
    }

    @Step("Получить значение поля заголовка")
    public String getValueTitleField() {
        TITLE_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return TITLE_FIELD.getValue();
    }

    @Step("Получить значение поля описания")
    public String getValueDescriptionField() {
        DESCRIPTION_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return DESCRIPTION_FIELD.getValue();
    }

    @Step("Закрыть окно добавления нового правила")
    public void closeWindowAddRule() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW.shouldNotBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Step("Нажать кнопку возвращения назад")
    public void clickButtonReturnRulesList() {
        RETURN_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Проверить отображение окна добавления нового правила")
    public boolean isWindowAppear() {
        return WINDOW.isDisplayed();
    }

}
