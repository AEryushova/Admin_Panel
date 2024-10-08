package ru.adminlk.clinica.pages.AdministrationPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.io.File;
import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class UpdateLegalDocWindow {

    private final SelenideElement
            WINDOW = $x("//span[text()='Изменить ссылку документа']//parent::div//parent::div//parent::div[@class='eV2Y']"),
            HEADER_WINDOW = $x("//span[text()='Изменить ссылку документа']"),
            DOC_IMAGE = $("iframe"),
            FILE_INPUT_ELEMENT = $("input[type='file']"),
            UPLOAD_BUTTON = $x("//span[text()='Загрузить документ']"),
            CLOSE_WINDOW_BUTTON = $x("//span[text()='Изменить ссылку документа']//parent::div//parent::div/parent::*/div[@class='UnAf Ee5G']");


    @Step("Верифицировать окно добавления оферты и политики обработки")
    public UpdateLegalDocWindow verifyUploadDocWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        HEADER_WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DOC_IMAGE.shouldHave(Condition.attribute("src")).shouldNotBe(Condition.attribute("src", ""));
        UPLOAD_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return this;
    }

    @Step("Загрузить файл '{0}'")
    public void uploadDoc(String pathFilesOffer) {
        FILE_INPUT_ELEMENT.shouldBe(Condition.exist)
                .uploadFile(new File(pathFilesOffer));
    }

    @Step("Получить ссылку на файл")
    public String getSrcDoc() {
        DOC_IMAGE.shouldBe(Condition.exist);
        return DOC_IMAGE.getAttribute("src");
    }

    @Step("Закрыть окно добавления оферты и политики обработки")
    public void closeWindowUpdateLegalDoc() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW.shouldNotBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Step("Проверить отображение окна добавления оферты и политики обработки")
    public boolean isWindowAppear() {
        return WINDOW.isDisplayed();
    }

}
