package pages.AdministrationPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class ChangePasswordAdminWindow {

    private final SelenideElement
            WINDOW = $x("//div[@class='ya9N CXVi']"),
            NEW_PASSWORD_FIELD = $x("//input[@name=\"newPassword\"]"),
            CONFIRM_PASSWORD_FIELD = $x("//input[@name=\"confirmPassword\"]"),
            SAVE_BUTTON = $x("//button[text()='Сохранить']"),
            CLOSE_WINDOW_BUTTON = $x("//div[@class='Yimd']"),
            CLEAR_FIELD_NEW_PASSWORD_BUTTON = $x("//input[@name='newPassword']//preceding-sibling::div[@class='m4oD']"),
            CLEAR_FIELD_CONFIRM_PASSWORD_BUTTON = $x("//input[@name='confirmPassword']//preceding-sibling::div[@class='m4oD']"),
            ERROR_FIELD_PASSWORD = $x("//input[@name='newPassword']/following-sibling::div"),
            ERROR_FIELD_CONFIRM_PASSWORD = $x("//input[@name='confirmPassword']/following-sibling::div");

    @Step("Верифицировать окно замены пароля админу")
    public ChangePasswordAdminWindow verifyChangePasswordAdminWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        NEW_PASSWORD_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CONFIRM_PASSWORD_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SAVE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5)).shouldBe(Condition.disabled);
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return this;
    }

    @Step("Ввести в поле нового пароля '{0}'")
    public ChangePasswordAdminWindow fillFieldNewPassword(String newPassword) {
        NEW_PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(newPassword);
        return this;
    }

    @Step("Ввести в поле подтверждения пароля '{0}'")
    public ChangePasswordAdminWindow fillFieldConfirmPassword(String confirmPassword) {
        CONFIRM_PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(confirmPassword);
        return this;
    }

    @Step("Нажать кнопку сохранения пароля")
    public void clickButtonSaveNewPassword() {
        SAVE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .scrollIntoView(true)
                .click();
    }

    @Step("Проверить доступность для нажатия кнопки сохранения пароля")
    public boolean isEnabledSaveButton() {
        return SAVE_BUTTON.isEnabled();
    }

    @Step("Проверить отображение админа '{0}' в заголовке окна")
    public boolean isHeaderLoginAppear(String login) {
        SelenideElement HEADER_LOGIN = $x("//input[@name='login' and @value='" + login + "']");
        return HEADER_LOGIN.isDisplayed();
    }

    @Step("Нажать на поле нового пароля")
    public ChangePasswordAdminWindow clickFieldNewPassword() {
        NEW_PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return this;
    }

    @Step("Нажать на поле подтверждения пароля")
    public ChangePasswordAdminWindow clickFieldConfirmPassword() {
        CONFIRM_PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return this;
    }

    @Step("Нажать на кнопку очищения поля нового пароля")
    public ChangePasswordAdminWindow clickClearButtonNewPasswordField() {
        CLEAR_FIELD_NEW_PASSWORD_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        NEW_PASSWORD_FIELD.shouldHave((Condition.empty), Duration.ofSeconds(15));
        return this;
    }

    @Step("Нажать на кнопку очищения поля подтверждения пароля")
    public ChangePasswordAdminWindow clickClearButtonConfirmPasswordField() {
        CLEAR_FIELD_CONFIRM_PASSWORD_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        CONFIRM_PASSWORD_FIELD.shouldHave((Condition.empty), Duration.ofSeconds(15));
        return this;
    }

    @Step("Получить значение поля нового пароля")
    public String getValuePasswordField() {
        NEW_PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return NEW_PASSWORD_FIELD.getValue();
    }

    @Step("Получить значение поля подтверждения пароля")
    public String getValueConfirmPasswordField() {
        CONFIRM_PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return CONFIRM_PASSWORD_FIELD.getValue();
    }

    @Step("Получить текст ошибки поля нового пароля")
    public String getErrorFieldPassword() {
        ERROR_FIELD_PASSWORD.shouldBe(Condition.visible);
        return ERROR_FIELD_PASSWORD.getText();
    }

    @Step("Получить текст ошибки поля подтверждения пароля")
    public String getErrorFieldConfirmPassword() {
        ERROR_FIELD_CONFIRM_PASSWORD.shouldBe(Condition.visible);
        return ERROR_FIELD_CONFIRM_PASSWORD.getText();
    }

    @Step("Проверить отображение ошибки поля нового пароля")
    public boolean isErrorPasswordAppear() {
        return ERROR_FIELD_PASSWORD.isDisplayed();
    }

    @Step("Закрыть окно смены пароля админу")
    public void closeWindowChangePasswordAdmin() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW.shouldNotBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Step("Проверить отображение окна смены пароля админу")
    public boolean isWindowAppear() {
        return WINDOW.isDisplayed();
    }
}
