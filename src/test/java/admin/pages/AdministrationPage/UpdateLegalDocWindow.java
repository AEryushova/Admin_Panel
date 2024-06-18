package admin.pages.AdministrationPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.io.File;
import java.time.Duration;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class UpdateLegalDocWindow {

    private final SelenideElement WINDOW = $x("//span[text()='Изменить ссылку документа']//parent::div//parent::div//parent::div[@class='eV2Y']");
    private final SelenideElement HEADER_WINDOW = $x("//span[text()='Изменить ссылку документа']");
    private final SelenideElement DOC_IMAGE = $("iframe");
    private final SelenideElement FILE_INPUT_ELEMENT = $("input[type='file']");
    private final SelenideElement UPLOAD_BUTTON = $x("//span[text()='Загрузить документ']");
    private final SelenideElement CLOSE_WINDOW_BUTTON = $x("//span[text()='Изменить ссылку документа']//parent::div//parent::div/parent::*/div[@class='UnAf Ee5G']");

    public void uploadDocWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        HEADER_WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DOC_IMAGE.shouldHave(attribute("src")).shouldNotBe(attribute("src", ""));
        UPLOAD_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void uploadValidDoc(String pathFilesOffer) {
        FILE_INPUT_ELEMENT.uploadFile(new File(pathFilesOffer));
    }

    public void uploadInvalidDoc(String pathFilesOffer) {
        FILE_INPUT_ELEMENT.uploadFile(new File(pathFilesOffer));
    }

    public String getSrcDoc() {
        return DOC_IMAGE.getAttribute("src");
    }

    public void closeWindowUpdateLegalDoc() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW.shouldBe(Condition.hidden, Duration.ofSeconds(5));
    }

    public boolean isWindowAppear() {
        return WINDOW.exists();
    }

}
