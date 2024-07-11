package admin.pages.BasePage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class BasePage {

    private final SelenideElement NOTIFICATION = $x("//div[@role='alert']/div//following-sibling::div");
    private final SelenideElement CLOSE_NOTIFICATION = $x("//button[@aria-label='close']");
    private final SelenideElement FOOTER_PAGE = $x("//span[text()='@ Самарский государственный медицинский университет']");
    private final SelenideElement RETURN_TO_START_BUTTON = $x("//div[@class='_x1E']");


    @Step("Получить текст нотификации")
    public String getNotification() {
        NOTIFICATION.shouldBe(visible, Duration.ofSeconds(5))
                .shouldBe(Condition.exist);
        return NOTIFICATION.getText();
    }

    @Step("Нажать на кнопку закрытия нотификации")
    public void closeNotification() {
        CLOSE_NOTIFICATION.shouldBe(visible, Duration.ofSeconds(5))
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Проверить отображение нотификации")
    public boolean isNotificationAppear() {
        return NOTIFICATION.isDisplayed();
    }
}
