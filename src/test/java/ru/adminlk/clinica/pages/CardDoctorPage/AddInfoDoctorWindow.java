package ru.adminlk.clinica.pages.CardDoctorPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class AddInfoDoctorWindow {

    private final SelenideElement
            TEXT_FIELD = $x("//input[@placeholder='Укажите название пункта']"),
            SAVE_BUTTON = $x("//button[text()='Сохранить']"),
            CANCEL_BUTTON = $x("//button[text()='Отмена']"),
            WINDOW_SECTION = $x("//input[@placeholder='Укажите название пункта']//parent::div//parent::div[@class='ijoD']"),
            WINDOW_DESCRIPTION = $x("//input[@placeholder='Укажите название пункта']//parent::div//parent::div[@class='S8Lv']");


    @Step("Верифицировать окно добавления раздела врачу")
    public AddInfoDoctorWindow verifyAddSectionDoctorWindow() {
        WINDOW_SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TEXT_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SAVE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CANCEL_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return this;
    }

    @Step("Верифицировать окно добавления описания врачу")
    public AddInfoDoctorWindow verifyAddDescriptionDoctorWindow() {
        WINDOW_DESCRIPTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TEXT_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SAVE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CANCEL_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return this;
    }

    @Step("Ввести в поле названия '{0}'")
    public AddInfoDoctorWindow fillFieldText(String title) {
        TEXT_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(title);
        return this;
    }

    @Step("Получить значение поля названия")
    public String getValueField() {
        TEXT_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return TEXT_FIELD.getValue();
    }

    @Step("Нажать на кнопку сохранения")
    public void clickButtonSaveValue() {
        SAVE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать на кнопку отмены добавления раздела")
    public void clickCancelButtonAddSectionDoctor() {
        CANCEL_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW_SECTION.shouldNotBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Step("Нажать на кнопку отмены добавления описания в раздел")
    public void clickCancelButtonAddDescriptionDoctor() {
        CANCEL_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW_DESCRIPTION.shouldNotBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Step("Проверить отображение окна добавления раздела врачу")
    public boolean isWindowAppearSection() {
        return WINDOW_SECTION.isDisplayed();
    }

    @Step("Проверить отображение окна добавления описания врачу")
    public boolean isWindowAppearDescription() {
        return WINDOW_DESCRIPTION.isDisplayed();
    }


}
