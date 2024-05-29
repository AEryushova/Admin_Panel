package user.pages.modalWindowReportBug;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class ReportBug {

    private final SelenideElement windowReportBug = $x("//div[@class='jNNrqi4Q']");
    private final SelenideElement headerWindow = $x("//div[@class='jNNrqi4Q']//span[text()='Сообщить об ошибке']");
    private final SelenideElement textFieldReportBug = $x("//textarea[@id='bugReport']");
    private final SelenideElement emailFieldReportBug = $x("//input[@name='email']");
    private final SelenideElement sendButtonReportBug = $x("//button[@data-locator='send-bugreport-btn']");
    private final SelenideElement clearFieldEmailButton = $x("//div[@class='ovPfYv5l']");
    private final SelenideElement closeWindowButton = $x("//div[@data-locator='close-popup-btn']");
    private final SelenideElement errorEmailField = $x("//input[@name='email']/following-sibling::div");


    public void reportBug() {
        windowReportBug.shouldBe(Condition.visible, Duration.ofSeconds(5));
        headerWindow.shouldBe(Condition.visible, Duration.ofSeconds(5));
        textFieldReportBug .shouldBe(Condition.visible, Duration.ofSeconds(5));
        emailFieldReportBug.shouldBe(Condition.visible, Duration.ofSeconds(5));
        sendButtonReportBug.shouldBe(Condition.disabled);
        closeWindowButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void fillingFieldTextReportBug(String text) {
        textFieldReportBug.setValue(text);
    }

    public void fillingFieldEmailReportBug(String email) {
        emailFieldReportBug.setValue(email);
    }

    public void clickSendButton() {
        sendButtonReportBug.shouldBe(Condition.enabled);
        sendButtonReportBug.click();
        windowReportBug.shouldBe(Condition.hidden, Duration.ofSeconds(5));
    }

    public void clearButtonEmailField() {
        clearFieldEmailButton.shouldBe(Condition.visible);
        clearFieldEmailButton.click();
    }

    public void closeWindowReportBug() {
        closeWindowButton.click();
        windowReportBug.shouldBe(Condition.hidden, Duration.ofSeconds(5));
    }

    public String getErrorFieldEmail() {
        errorEmailField.shouldBe(Condition.visible);
        return errorEmailField.getText();
    }

}
