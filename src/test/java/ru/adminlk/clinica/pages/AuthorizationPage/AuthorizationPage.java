package ru.adminlk.clinica.pages.AuthorizationPage;

import ru.adminlk.clinica.pages.BasePage.BasePage;
import ru.adminlk.clinica.pages.DoctorsPage.DoctorsPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.*;

public class AuthorizationPage extends BasePage {
    private final SelenideElement
            LOGIN_FIELD = $x("//input[@name='login']"),
            PASSWORD_FIELD = $x("//input[@name='password']"),
            CLEAR_LOGIN_BUTTON = $x("//input[@name='login']//preceding-sibling::div[@class='m4oD']"),
            SHOW_PASSWORD_BUTTON = $x("//input[@name='password']//preceding-sibling::div[@class='m4oD']"),
            LOADER = $x("//div[@class='ocE_']"),
            TO_COME_IN_BUTTON = $x("//button[text()='Войти']"),
            ERROR_FIELD_LOGIN = $x("//input[@type='text']//following-sibling::div"),
            ERROR_FIELD_PASSWORD = $x("//input[@type='password']//following-sibling::div"),
            LOGO = $x("//div[@class='RDMc']/img");

    @Step("Верифицировать страницу Авторизации")
    public void verifyAuthPage() {
        LOGIN_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        PASSWORD_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TO_COME_IN_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5)).shouldBe(Condition.disabled);
        CLEAR_LOGIN_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SHOW_PASSWORD_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        LOGO.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Авторизоваться с логином '{0}' и паролем '{1}'")
    public DoctorsPage authorization(String login, String password) {
        LOGIN_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(login);
        PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(password);
        TO_COME_IN_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        LOADER.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return new DoctorsPage();
    }

    @Step("Ввести в поле логина '{0}'")
    public AuthorizationPage fillLoginField(String login) {
        LOGIN_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(login);
        return this;
    }

    @Step("Ввести в поле пароля '{0}'")
    public AuthorizationPage fillPasswordField(String password) {
        PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(password);
        return this;
    }

    @Step("Нажать кнопку входа")
    public void clickToComeIn() {
        TO_COME_IN_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать на поле логина")
    public AuthorizationPage clickLoginField() {
        LOGIN_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return this;
    }

    @Step("Нажать на поле пароля")
    public void clickPasswordField() {
        PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать на кнопку очищения поля логина")
    public void clickClearButtonLoginField() {
        CLEAR_LOGIN_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        LOGIN_FIELD.shouldBe(Condition.value(""), Duration.ofSeconds(10));
    }

    @Step("Получить значение поля логина")
    public String getValueLoginField() {
        LOGIN_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return LOGIN_FIELD.getValue();
    }

    @Step("Проверить видимость значения введеного пароля")
    public boolean isHidePassword() {
        PASSWORD_FIELD.shouldBe(Condition.visible);
        return Objects.equals(PASSWORD_FIELD.getAttribute("type"), "password");
    }

    @Step("Нажать на кнопку отображения значения пароля")
    public void showPassword() {
        PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldHave(Condition.attribute("type", "password"));
        SHOW_PASSWORD_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать на кнопку скрытия значения пароля")
    public void hidePassword() {
        PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldHave(Condition.attribute("type", "text"));
        SHOW_PASSWORD_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Проверить доступность для нажатия кнопки входа")
    public boolean isEnabledComeInButton() {
        return TO_COME_IN_BUTTON.isEnabled();
    }

    @Step("Получить текст ошибки поля логина")
    public String getErrorFieldLogin() {
        ERROR_FIELD_LOGIN.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return ERROR_FIELD_LOGIN.getText();
    }

    @Step("Получить текст ошибки поля пароля")
    public String getErrorFieldPassword() {
        ERROR_FIELD_PASSWORD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return ERROR_FIELD_PASSWORD.getText();
    }

    @Step("Проверить отображение ошибки поля логина")
    public boolean isErrorLoginAppear() {
        return ERROR_FIELD_LOGIN.isDisplayed();
    }

    @Step("Проверить отображение ошибки поля пароля")
    public boolean isErrorPasswordAppear() {
        return ERROR_FIELD_PASSWORD.isDisplayed();
    }

}