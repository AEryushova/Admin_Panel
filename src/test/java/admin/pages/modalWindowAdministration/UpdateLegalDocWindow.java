package admin.pages.modalWindowAdministration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.io.File;
import java.time.Duration;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class UpdateLegalDocWindow {

    private final SelenideElement windowUpdateLegalDoc = $x("//span[text()='Изменить ссылку документа']//parent::div//parent::div//parent::div[@class='eV2Y']");
    private final SelenideElement headerWindow = $x("//span[text()='Изменить ссылку документа']");
    private final SelenideElement docImage = $("iframe");
    private final SelenideElement fileInputElement = $("input[type='file']");
    private final SelenideElement uploadDocButton = $x("//span[text()='Загрузить документ']");
    private final SelenideElement closeWindowButton = $x("//span[text()='Изменить ссылку документа']//parent::div//parent::div/parent::*/div[@class='UnAf Ee5G']");

    public void uploadDocWindow() {
        windowUpdateLegalDoc.shouldBe(Condition.visible, Duration.ofSeconds(5));
        headerWindow.shouldBe(Condition.visible, Duration.ofSeconds(5));
        docImage.shouldHave(attribute("src")).shouldNotBe(attribute("src", ""));
        uploadDocButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        closeWindowButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void uploadValidDoc(String pathFilesOffer) {
        fileInputElement.uploadFile(new File(pathFilesOffer));
        windowUpdateLegalDoc.shouldBe(Condition.hidden, Duration.ofSeconds(5));
    }

    public void uploadInvalidDoc(String pathFilesOffer) {
        fileInputElement.uploadFile(new File(pathFilesOffer));
        windowUpdateLegalDoc.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public String getSrcDoc() {
        return docImage.getAttribute("src");
    }

    public void closeWindowUpdateLegalDoc() {
        closeWindowButton.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public boolean isWindowAppear() {
        return windowUpdateLegalDoc.exists();
    }

}
