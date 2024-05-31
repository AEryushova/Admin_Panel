package admin.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class BasePage {

    private final SelenideElement notification = $x("//div[@role='alert']/div//following-sibling::div");
    private final SelenideElement closeNotification = $x("//button[@aria-label='close']");
    private final SelenideElement footerAdministrationPage = $x("//span[text()='@ Самарский государственный медицинский университет']");
    private final SelenideElement returnToStartButton = $x("//div[@class='_x1E']");

    public String getNotification() {
        notification.shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.exist);
        return notification.getText();
    }


    public void closeNotification() {
        closeNotification.shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.enabled)
                .click();
        notification.shouldBe(Condition.hidden);
    }

    public void scrollPageToBottom() {
        footerAdministrationPage.exists();
        footerAdministrationPage.scrollTo();
    }

    public void returnToStartPage() {
        returnToStartButton.shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.enabled)
        .click();
    }

    public boolean isReturnButtonAppear() {
       return returnToStartButton.exists();
    }

    public boolean notificationDisappears() {
        return notification.exists();
    }
}
