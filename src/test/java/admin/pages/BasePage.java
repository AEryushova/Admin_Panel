package admin.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class BasePage {

    private final SelenideElement NOTIFICATION = $x("//div[@role='alert']/div//following-sibling::div");
    private final SelenideElement CLOSE_NOTIFICATION = $x("//button[@aria-label='close']");
    private final SelenideElement FOOTER_PAGE = $x("//span[text()='@ Самарский государственный медицинский университет']");
    private final SelenideElement RETURN_TO_START_BUTTON = $x("//div[@class='_x1E']");

    public String getNotification() {
        NOTIFICATION.shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.exist);
        return NOTIFICATION.getText();
    }

    public boolean isNotificationVisible() {
        return NOTIFICATION.is(Condition.visible);
    }


    public void closeNotification() {
        CLOSE_NOTIFICATION.shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.enabled)
                .click();
        NOTIFICATION.shouldBe(Condition.hidden);
    }

    public void scrollPageToBottom() {
        FOOTER_PAGE.scrollTo();
    }

    public void returnToStartPage() {
        RETURN_TO_START_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.enabled)
        .click();
    }

    public boolean isReturnButtonAppear() {
       return RETURN_TO_START_BUTTON.exists();
    }

    public boolean notificationAppear() {
        return NOTIFICATION.exists();
    }
}
