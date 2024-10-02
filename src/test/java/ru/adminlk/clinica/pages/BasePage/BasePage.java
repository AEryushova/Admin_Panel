package ru.adminlk.clinica.pages.BasePage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class BasePage {

    private final SelenideElement
            NOTIFICATION = $x("//div[@role='alert']/div//following-sibling::div"),
            CLOSE_NOTIFICATION = $x("//button[@aria-label='close']"),
            RETURN_TO_HEADER_BUTTON = $x("//div[@class='_x1E']");


    @Step("Получить текст нотификации")
    public String getTextNotification() {
        NOTIFICATION.shouldBe(visible, Duration.ofSeconds(8));
        return NOTIFICATION.getText();
    }

    @Step("Нажать на кнопку закрытия нотификации")
    public void closeNotification() {
        CLOSE_NOTIFICATION.shouldBe(visible, Duration.ofSeconds(8))
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Получить нотификацию")
    public SelenideElement getNotification() {
        return NOTIFICATION;
    }

    @Step("Проверить отображение нотификации")
    public boolean isNotificationAppear() {
        return NOTIFICATION.isDisplayed();

    }

    @Step("Получить кнопку возврата к хереру страницы")
    public SelenideElement getButtonReturnToHeader() {
        return RETURN_TO_HEADER_BUTTON;
    }

    @Step("Нажать на кнопку возврата к хедеру страницы")
    public void clickButtonReturnToHeader() {
        RETURN_TO_HEADER_BUTTON.shouldBe(visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Проверить отображение кнопки возврата к хэдеру страницы")
    public boolean isVisibleButtonReturnToHeader() {
        return RETURN_TO_HEADER_BUTTON.isDisplayed();
    }


    @Step("Проскроллить страницу вниз на '{0}' пикселей")
    public void scrollPageDown(String countPixel) {
        Selenide.executeJavaScript("window.scrollBy(0, " + countPixel + ")");
        Selenide.sleep(3000);
    }

    @Step("Проскроллить страницу вверх на '{0}' пикселей")
    public void scrollPageUp(String countPixel) {
        Selenide.executeJavaScript("window.scrollBy(0, -arguments[0]);", countPixel);
        Selenide.sleep(3000);
    }

    @Step("Проскроллить страницу до элемента '{0}'")
    public void scrollToCard(SelenideElement element) {
        element.scrollIntoView("{behavior: 'auto', block: 'center'}");
        Selenide.sleep(3000);
    }

}
