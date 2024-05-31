package admin.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;
import java.util.Objects;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.*;

public class AuthorizationPage extends BasePage {
    private final SelenideElement loginField = $x("//label[text()='Логин']/preceding-sibling::div/div/input");
    private final SelenideElement passwordField = $x("//label[text()='Пароль']/preceding-sibling::div/div/input");
    private final SelenideElement clearButtonLogin = $x("//label[text()='Логин']/preceding::div[@class='P1GK']");
    private final SelenideElement showPasswordButton = $x("//label[text()='Пароль']/preceding::div[@class='P1GK']");
    private final SelenideElement loader = $x("//div[@class='Loader__dcc3']");
    private final SelenideElement toComeInButton = $x("//button[text()=\"Войти\"]");
    private final SelenideElement errorFieldLogin = $x("//input[@type='text']//following-sibling::div");
    private final SelenideElement errorFieldPassword = $x("//input[@type='password']//following-sibling::div");


    public void authPage() {
        loginField.shouldBe(Condition.visible, Duration.ofSeconds(5));
        passwordField.shouldBe(Condition.visible, Duration.ofSeconds(5));
        toComeInButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public DoctorsPage authorization(String login, String password) {
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

    public void fillLoginField(String login) {
        loginField.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(login);
    }

    public void fillPasswordField(String password) {
        passwordField.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(password);
    }

    public void subAuthButton() {
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
        clearButtonLogin.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public String getValueLoginField() {
        loginField.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return loginField.getValue();
    }

    public boolean isHidePassword() {
        passwordField.shouldBe(Condition.visible);
        return Objects.equals(passwordField.getAttribute("type"), "password");
    }

    public boolean showPassword() {
        passwordField.shouldBe(Condition.visible)
        .shouldHave(Condition.attribute("type", "password"));
        showPasswordButton.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return Objects.equals(passwordField.getAttribute("type"), "text");
    }

    public boolean hidePassword() {
        passwordField.shouldBe(Condition.visible)
        .shouldHave(Condition.attribute("type", "text"));
        showPasswordButton.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return Objects.equals(passwordField.getAttribute("type"), "password");
    }

    public String getErrorFieldLogin() {
        errorFieldLogin.shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.exist);
        return errorFieldLogin.getText();
    }

    public String getErrorFieldPassword() {
        errorFieldPassword.shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.exist);
        return errorFieldPassword.getText();
    }
    public boolean isErrorLoginAppear() {
        return errorFieldLogin.exists();
    }

    public boolean isErrorPasswordAppear() {
        return errorFieldPassword.exists();
    }

}