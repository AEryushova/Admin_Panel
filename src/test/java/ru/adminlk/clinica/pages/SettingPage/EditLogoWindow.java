package ru.adminlk.clinica.pages.SettingPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.io.File;
import java.time.Duration;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;


public class EditLogoWindow {
    private final SelenideElement
            WINDOW = $x("//span[text()='Установка логотипа приложения']//parent::div//parent::div//parent::div[@class='eV2Y']"),
            LOGO_APP = $x("//div[@class='UnAf aZDp HdF2']/img"),
            HEADER_WINDOW = $x("//span[text()='Установка логотипа приложения']"),
            FILE_INPUT_ELEMENT = $("input[type='file']"),
            CLOSE_WINDOW_BUTTON = $x("//span[text()='Установка логотипа приложения']//parent::div//parent::div/parent::*/div[@class='UnAf Ee5G']");

    @Step("Верифицировать окно изменения логотипа")
    public EditLogoWindow verifyEditLogoWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        HEADER_WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        LOGO_APP.shouldHave(attribute("src")).shouldNotBe(attribute("src", ""));
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return this;
    }

    @Step("Загрузить файл '{0}'")
    public void uploadLogo(String pathFilesOffer) {
        FILE_INPUT_ELEMENT.shouldBe(Condition.exist)
                .uploadFile(new File(pathFilesOffer));
    }

    @Step("Закрыть окно изменения логотипа")
    public void closeWindowEditLogo() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW.shouldNotBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Step("Проверить отображение окна изменения логотипа")
    public boolean isWindowAppear() {
        return WINDOW.isDisplayed();
    }


}
