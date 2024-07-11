package admin.pages.AuthorizationPage;

import admin.pages.BasePage.BasePage;
import admin.pages.DoctorsPage.DoctorsPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.*;

public class AuthorizationPage extends BasePage {
    private final SelenideElement LOGIN_FIELD = $x("//input[@name='login']");
    private final SelenideElement PASSWORD_FIELD = $x("//input[@name='password']");
    private final SelenideElement CLEAR_LOGIN_BUTTON = $x("//input[@name='login']//preceding-sibling::div[@class='m4oD']");
    private final SelenideElement SHOW_PASSWORD_BUTTON = $x("//input[@name='password']//preceding-sibling::div[@class='m4oD']");
    private final SelenideElement LOADER = $x("//div[@class='ocE_']");
    private final SelenideElement TO_COME_IN_BUTTON = $x("//button[text()='Войти']");
    private final SelenideElement ERROR_FIELD_LOGIN = $x("//input[@type='text']//following-sibling::div");
    private final SelenideElement ERROR_FIELD_PASSWORD = $x("//input[@type='password']//following-sibling::div");
    private final SelenideElement LOGO=$x("//div[@class='RDMc']/img");

    @Step("Верифицировать страницу Авторизации")
    public void verifyAuthPage() {
        LOGIN_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        PASSWORD_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TO_COME_IN_BUTTON.shouldBe(Condition.visible,Duration.ofSeconds(5)).shouldBe(Condition.disabled);
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
    public void fillLoginField(String login) {
        LOGIN_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(login);
    }

    @Step("Ввести в поле пароля '{0}'")
    public void fillPasswordField(String password) {
        PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(password);
    }

    @Step("Нажать кнопку входа")
    public void clickToComeIn() {
        TO_COME_IN_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать на поле логина")
    public void clickLoginField() {
        LOGIN_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
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
    }

    @Step("Получить значение поля логина")
    public String getValueLoginField() {
        LOGIN_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
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
    public boolean isEnabledComeInButton(){
        return TO_COME_IN_BUTTON.isEnabled();
    }

    @Step("Получить текст ошибки поля логина")
    public String getErrorFieldLogin() {
        ERROR_FIELD_LOGIN.shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.exist);
        return ERROR_FIELD_LOGIN.getText();
    }

    @Step("Получить текст ошибки поля пароля")
    public String getErrorFieldPassword() {
        ERROR_FIELD_PASSWORD.shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.exist);
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