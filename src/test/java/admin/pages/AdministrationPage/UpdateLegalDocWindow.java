package admin.pages.AdministrationPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.io.File;
import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class UpdateLegalDocWindow {

    private final SelenideElement WINDOW = $x("//span[text()='Изменить ссылку документа']//parent::div//parent::div//parent::div[@class='eV2Y']");
    private final SelenideElement HEADER_WINDOW = $x("//span[text()='Изменить ссылку документа']");
    private final SelenideElement DOC_IMAGE = $("iframe");
    private final SelenideElement FILE_INPUT_ELEMENT = $("input[type='file']");
    private final SelenideElement UPLOAD_BUTTON = $x("//span[text()='Загрузить документ']");
    private final SelenideElement CLOSE_WINDOW_BUTTON = $x("//span[text()='Изменить ссылку документа']//parent::div//parent::div/parent::*/div[@class='UnAf Ee5G']");


    @Step("Верифицировать окно добавления оферты и политики обработки")
    public void verifyUploadDocWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        HEADER_WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DOC_IMAGE.shouldHave(Condition.attribute("src")).shouldNotBe(Condition.attribute("src", ""));
        UPLOAD_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Загрузить файл '{0}'")
    public void uploadValidDoc(String pathFilesOffer) {
        FILE_INPUT_ELEMENT.uploadFile(new File(pathFilesOffer));
    }

    @Step("Загрузить файл '{0}'")
    public void uploadInvalidDoc(String pathFilesOffer) {
        FILE_INPUT_ELEMENT.uploadFile(new File(pathFilesOffer));
    }

    @Step("Получить ссылку на файл")
    public String getSrcDoc() {
        return DOC_IMAGE.getAttribute("src");
    }

    @Step("Закрыть окно добавления оферты и политики обработки")
    public void closeWindowUpdateLegalDoc() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW.shouldNot(Condition.appear, Duration.ofSeconds(10));
    }

    @Step("Проверить отображение окна добавления оферты и политики обработки")
    public boolean isWindowAppear() {
        return WINDOW.isDisplayed();
    }

}
