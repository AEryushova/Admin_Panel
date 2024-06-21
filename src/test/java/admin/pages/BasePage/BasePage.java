package admin.pages.BasePage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class BasePage {
    static {
        Configuration.timeout = 5000;
    }

    private final SelenideElement NOTIFICATION = $x("//div[@role='alert']/div//following-sibling::div");
    private final SelenideElement CLOSE_NOTIFICATION = $x("//button[@aria-label='close']");
    private final SelenideElement FOOTER_PAGE = $x("//span[text()='@ Самарский государственный медицинский университет']");
    private final SelenideElement RETURN_TO_START_BUTTON = $x("//div[@class='_x1E']");

    public String getNotification() {
        NOTIFICATION.shouldBe(visible, Duration.ofSeconds(5))
                .shouldBe(Condition.exist);
        return NOTIFICATION.getText();
    }

    public boolean isNotificationVisible() {
        return NOTIFICATION.is(visible);
    }


    public void closeNotification() {
        CLOSE_NOTIFICATION.shouldBe(visible, Duration.ofSeconds(5))
                .shouldBe(Condition.enabled)
                .click();
        NOTIFICATION.shouldBe(Condition.hidden);
    }

    public boolean notificationAppear() {
        return NOTIFICATION.isDisplayed();
    }
}
