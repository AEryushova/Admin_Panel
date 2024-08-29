package pages.ServicesPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class AddSectionWindow {

    public final SelenideElement
            WINDOW = $x("//span[text()='Добавить Раздел']//parent::div//parent::div//parent::div[@class='eV2Y']"),
            HEADER_WINDOW = $x("//span[text()='Добавить Раздел']"),
            NAME_FIELD = $x("//input[@name='name']"),
            ADD_BUTTON = $x("//button[text()='Добавить']"),
            CANCEL_BUTTON = $x("//button[text()='Отменить']"),
            CLEAR_FIELD_NAME_BUTTON = $x("//input[@name='name']//preceding-sibling::div[@class='m4oD']"),
            CLOSE_WINDOW_BUTTON = $x("//span[text()='Добавить Раздел']//parent::div//parent::div/parent::*/div[@class='UnAf Ee5G']"),
            ERROR_FIELD_NAME = $x("//input[@name='name']/following-sibling::div");

    @Step("Верифицировать окно добавления нового раздела")
    public AddSectionWindow verifyAddSectionWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        HEADER_WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        NAME_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CANCEL_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADD_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5)).shouldBe(Condition.disabled);
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return this;
    }

    @Step("Ввести в поле названия '{0}'")
    public AddSectionWindow  fillNameSectionField(String name) {
        NAME_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(name);
        return this;
    }

    @Step("Нажать кнопку добавления")
    public void clickButtonAddSection() {
        ADD_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать кнопку отмены")
    public void clickCancelButtonAddSection() {
        CANCEL_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW.shouldNot(Condition.appear, Duration.ofSeconds(10));
    }


    @Step("Получить значение поля названия")
    public String getValueNameField() {
        NAME_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return NAME_FIELD.getValue();
    }


    @Step("Нажать на поле названия")
    public void clickFieldName() {
        NAME_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать на кнопку очищения поля названия")
    public void clearButtonNameField() {
        CLEAR_FIELD_NAME_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        NAME_FIELD.shouldBe((Condition.empty), Duration.ofSeconds(15));
    }

    @Step("Получить текст ошибки поля названия")
    public String getErrorFieldName() {
        ERROR_FIELD_NAME.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return ERROR_FIELD_NAME.getText();
    }

    @Step("Проверить доступность для нажатия кнопки добавления раздела")
    public boolean isEnabledAddButton() {
        return ADD_BUTTON.isEnabled();
    }

    @Step("Закрыть окно добавления нового раздела")
    public void closeWindowAddSection() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW.shouldNotBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Step("Получить окно добавления нового раздела")
    public SelenideElement getAddSectionWindow() {
        return WINDOW;
    }

    @Step("Проверить отображение окна добавления нового раздела")
    public boolean isWindowAppear() {
        return WINDOW.isDisplayed();
    }
}
