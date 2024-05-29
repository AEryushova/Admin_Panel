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
        loginField.shouldBe(Condition.visible, Duration.ofSeconds(10));
        passwordField.shouldBe(Condition.visible, Duration.ofSeconds(10));
        toComeInButton.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    public DoctorsPage authorizationAdminPanel(String login, String password) {
        loginField.setValue(login);
        passwordField.setValue(password);
        toComeInButton.click();
        loader.shouldBe(Condition.visible, Duration.ofSeconds(10));
        return new DoctorsPage();
    }

    public void fillingLoginField(String login) {
        loginField.setValue(login);
    }

    public void fillingPasswordField(String password) {
        passwordField.setValue(password);
    }

    public void subAuthorizationButton() {
        toComeInButton.click();
    }

    public void clearPasswordField() {
        passwordField.clear();
    }

    public void clickLoginField() {
        loginField.click();
    }

    public void clickPasswordField() {
        passwordField.click();
    }

    public void hoverLoginField() {
        loginField.hover();
    }

    public void hoverPasswordField() {
        passwordField.hover();
    }

    public void clearLoginClickButton() {
        clearButtonLogin.click();
    }

    public String getValueLoginField() {
        return loginField.getValue();
    }

    public void showPasswordClickButton() {
        showPasswordButton.click();
        passwordField.shouldBe(attribute("type", "text"));
    }

    public String getNotification() {
        notification.shouldBe(Condition.visible);
        return notification.getText();
    }
    public void closeNotification() {
        closeNotification.click();
        notification.shouldBe(Condition.hidden);
    }

    public String getErrorFieldLogin() {
        errorFieldLogin.shouldBe(Condition.visible);
        return errorFieldLogin.getText();
    }

    public void hiddenErrorFieldLogin() {
        errorFieldLogin.shouldNotBe(Condition.visible);
    }

    public String getErrorFieldPassword() {
        errorFieldPassword.shouldBe(Condition.visible);
        return errorFieldPassword.getText();
    }

    public void hiddenErrorFieldPassword() {
        errorFieldPassword.shouldNotBe(Condition.visible);
    }

}