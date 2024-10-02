package ru.adminlk.clinica.pages.AdministrationPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;


import java.time.Duration;


import static com.codeborne.selenide.Selenide.$x;


public class NewAdminWindow {
    private final SelenideElement
            WINDOW = $x("//span[text()='Добавить Администратора']//parent::div//parent::div//parent::div[@class='eV2Y']"),
            HEADER_WINDOW = $x("//span[text()='Добавить Администратора']"),
            LOGIN_FIELD = $x("//div[@id='popap_window']//input[@name='login']"),
            PASSWORD_FIELD = $x("//div[@id='popap_window']//input[@name='password']"),
            CONFIRM_PASSWORD_FIELD = $x("//div[@id='popap_window']//input[@name='confirmPassword']"),
            ADD_BUTTON = $x("//div[@id='popap_window']//button[text()='Добавить']"),
            CLOSE_WINDOW_BUTTON = $x("//span[text()='Добавить Администратора']//parent::div//parent::div/parent::*/div[@class='UnAf Ee5G']"),
            CLEAR_FIELD_LOGIN_BUTTON = $x("//input[@name='login']//preceding-sibling::div[@class='m4oD']"),
            CLEAR_FIELD_PASSWORD_BUTTON = $x("//input[@name='password']//preceding-sibling::div[@class='m4oD']"),
            CLEAR_FIELD_CONFIRM_PASSWORD_BUTTON = $x("//input[@name='confirmPassword']//preceding-sibling::div[@class='m4oD']"),
            ERROR_FIELD_LOGIN = $x("//input[@name='login']/following-sibling::div"),
            ERROR_FIELD_PASSWORD = $x("//input[@name='password']/following-sibling::div"),
            ERROR_FIELD_CONFIRM_PASSWORD = $x("//input[@name='confirmPassword']/following-sibling::div");

    @Step("Верифицировать окно добавления нового админа")
    public NewAdminWindow verifyNewAdminWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        HEADER_WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        LOGIN_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        PASSWORD_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CONFIRM_PASSWORD_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADD_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5)).shouldBe(Condition.disabled);
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return this;
    }

    @Step("Ввести в поле логина '{0}'")
    public NewAdminWindow fillFieldNewAdminLogin(String login) {
        LOGIN_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(login);
        return this;
    }

    @Step("Ввести в поле пароля '{0}'")
    public NewAdminWindow fillFieldNewAdminPassword(String password) {
        PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(password);
        return this;
    }

    @Step("Ввести в поле подтверждения пароля '{0}'")
    public NewAdminWindow fillFieldNewAdminConfirmPassword(String confirmPassword) {
        CONFIRM_PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(confirmPassword);
        return this;
    }

    @Step("Нажать кнопку добавления админа")
    public void clickAddButton() {
        ADD_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Проверить доступность для нажатия кнопки добавления админа")
    public boolean isEnabledAddButton() {
        return ADD_BUTTON.isEnabled();
    }


    @Step("Нажать на поле логина")
    public NewAdminWindow clickFieldNewAdminLogin() {
        LOGIN_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return this;
    }

    @Step("Нажать на поле пароля")
    public NewAdminWindow clickFieldNewAdminPassword() {
        PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return this;
    }

    @Step("Нажать на поле подтверждения пароля")
    public NewAdminWindow clickFieldNewAdminConfirmPassword() {
        CONFIRM_PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return this;
    }

    @Step("Нажать на кнопку очищения поля логина")
    public NewAdminWindow clickClearButtonLoginField() {
        CLEAR_FIELD_LOGIN_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        LOGIN_FIELD.shouldHave((Condition.empty), Duration.ofSeconds(15));
        return this;
    }

    @Step("Нажать на кнопку очищения поля пароля")
    public NewAdminWindow clickClearButtonPasswordField() {
        CLEAR_FIELD_PASSWORD_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        PASSWORD_FIELD.shouldHave((Condition.empty), Duration.ofSeconds(15));
        return this;
    }

    @Step("Нажать на кнопку очищения поля подтверждения пароля")
    public void clickClearButtonConfirmPasswordField() {
        CLEAR_FIELD_CONFIRM_PASSWORD_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        CONFIRM_PASSWORD_FIELD.shouldHave((Condition.empty), Duration.ofSeconds(15));
    }

    @Step("Получить значение поля логина")
    public String getValueLoginField() {
        LOGIN_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return LOGIN_FIELD.getValue();
    }

    @Step("Получить значение поля пароля")
    public String getValuePasswordField() {
        PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return PASSWORD_FIELD.getValue();
    }

    @Step("Получить значение поля подтверждения пароля")
    public String getValueConfirmPasswordField() {
        CONFIRM_PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return CONFIRM_PASSWORD_FIELD.getValue();
    }

    @Step("Получить текст ошибки поля логина")
    public String getErrorFieldLogin() {
        ERROR_FIELD_LOGIN.shouldBe(Condition.visible);
        return ERROR_FIELD_LOGIN.getText();
    }

    @Step("Проверить отображение ошибки поля логина")
    public boolean isErrorLoginAppear() {
        return ERROR_FIELD_LOGIN.isDisplayed();
    }

    @Step("Получить текст ошибки поля пароля")
    public String getErrorFieldPassword() {
        ERROR_FIELD_PASSWORD.shouldBe(Condition.visible);
        return ERROR_FIELD_PASSWORD.getText();
    }

    @Step("Проверить отображение ошибки поля пароля")
    public boolean isErrorPasswordAppear() {
        return ERROR_FIELD_PASSWORD.isDisplayed();
    }

    @Step("Получить текст ошибки поля подтверждения пароля")
    public String getErrorFieldConfirmPassword() {
        ERROR_FIELD_CONFIRM_PASSWORD.shouldBe(Condition.visible);
        return ERROR_FIELD_CONFIRM_PASSWORD.getText();
    }

    @Step("Закрыть окно добавления нового админа")
    public void closeWindowAddedAdmin() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW.shouldNotBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Step("Проверить отображение окна добавления нового админа")
    public boolean isWindowAppear() {
        return WINDOW.isDisplayed();
    }

}
