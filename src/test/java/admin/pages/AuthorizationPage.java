package admin.pages;

import admin.data.DataTest;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import admin.data.DataInfo;

import java.time.Duration;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.*;

public class AuthorizationPage {
    private final SelenideElement loginField = $x("//label[text()='Логин']/preceding-sibling::div/div/input");
    private final SelenideElement passwordField = $x("//label[text()='Пароль']/preceding-sibling::div/div/input");
    private final SelenideElement clearButtonLogin = $x("//label[text()='Логин']/preceding::div[@class='P1GK']");
    private final SelenideElement showPasswordButton = $x("//label[text()='Пароль']/preceding::div[@class='P1GK']");
    private final SelenideElement loader = $x("//div[@class='Loader__dcc3']");
    private final SelenideElement toComeInButton = $x("//button[text()=\"Войти\"]");
    private final SelenideElement notification = $x("//div[@role='alert']/div//following-sibling::div");
    private final SelenideElement closeNotification = $x("//button[@aria-label='close']");
    private final SelenideElement errorFieldLogin = $x("//input[@type='text']//following-sibling::div");
    private final SelenideElement errorFieldPassword = $x("//input[@type='password']//following-sibling::div");


    public void authorizationPage() {
        loginField.shouldBe(Condition.visible, Duration.ofSeconds(5));
        passwordField.shouldBe(Condition.visible, Duration.ofSeconds(5));
        toComeInButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public DoctorsPage authorizationAdminPanel(String login, String password) {
        loginField.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(login);
        passwordField.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(password);
        toComeInButton.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        loader.shouldBe(Condition.visible, Duration.ofSeconds(10));
        return new DoctorsPage();
    }

    public void fillingLoginField(String login) {
        loginField.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(login);
    }

    public void fillingPasswordField(String password) {
        passwordField.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(password);
    }

    public void subAuthorizationButton() {
        toComeInButton.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void clearPasswordField() {
        passwordField.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .clear();
    }

    public void clickLoginField() {
        loginField.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void clickPasswordField() {
        passwordField.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void hoverLoginField() {
        loginField.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .hover();
    }

    public void hoverPasswordField() {
        passwordField.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .hover();
    }

    public void clearLoginClickButton() {
        clearButtonLogin.shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.enabled)
                .click();
    }

    public String getValueLoginField() {
        loginField.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return loginField.getValue();
    }

    public void showPasswordClickButton() {
        showPasswordButton.shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.enabled)
                .click();
        passwordField.shouldBe(attribute("type", "text"));
    }

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

    public String getErrorFieldLogin() {
        errorFieldLogin.shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.exist);
        return errorFieldLogin.getText();
    }

    public void hiddenErrorFieldLogin() {
        errorFieldLogin.shouldBe(Condition.hidden);
    }

    public String getErrorFieldPassword() {
        errorFieldPassword.shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.exist);
        return errorFieldPassword.getText();
    }

    public void hiddenErrorFieldPassword() {
        errorFieldPassword.shouldBe(Condition.hidden);
    }

    public void notificationDisappears() {
        notification.shouldBe(Condition.hidden, Duration.ofSeconds(7));
    }

}