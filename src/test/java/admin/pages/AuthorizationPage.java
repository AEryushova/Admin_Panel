package admin.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;
import java.util.Objects;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.*;

public class AuthorizationPage extends BasePage {
    private final SelenideElement LOGIN_FIELD = $x("//label[text()='Логин']/preceding-sibling::div/div/input");
    private final SelenideElement PASSWORD_FIELD = $x("//label[text()='Пароль']/preceding-sibling::div/div/input");
    private final SelenideElement CLEAR_LOGIN_BUTTON = $x("//label[text()='Логин']/preceding::div[@class='P1GK']");
    private final SelenideElement SHOW_PASSWORD_BUTTON = $x("//label[text()='Пароль']/preceding::div[@class='P1GK']");
    private final SelenideElement LOADER = $x("//div[@class='Loader__dcc3']");
    private final SelenideElement TO_COME_IN_BUTTON = $x("//button[text()='Войти']");
    private final SelenideElement ERROR_FIELD_LOGIN = $x("//input[@type='text']//following-sibling::div");
    private final SelenideElement ERROR_FIELD_PASSWORD = $x("//input[@type='password']//following-sibling::div");


    public void authPage() {
        LOGIN_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        PASSWORD_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TO_COME_IN_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

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

    public void fillLoginField(String login) {
        LOGIN_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(login);
    }

    public void fillPasswordField(String password) {
        PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(password);
    }

    public void pressToComeIn() {
        TO_COME_IN_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void clearPasswordField() {
        PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .clear();
    }

    public void clickLoginField() {
        LOGIN_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void clickPasswordField() {
        PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void hoverLoginField() {
        LOGIN_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .hover();
    }

    public void hoverPasswordField() {
        PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .hover();
    }

    public void clearLoginClickButton() {
        CLEAR_LOGIN_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public String getValueLoginField() {
        LOGIN_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return LOGIN_FIELD.getValue();
    }

    public boolean isHidePassword() {
        PASSWORD_FIELD.shouldBe(Condition.visible);
        return Objects.equals(PASSWORD_FIELD.getAttribute("type"), "password");
    }

    public void showPassword() {
        PASSWORD_FIELD.shouldBe(Condition.visible)
        .shouldHave(Condition.attribute("type", "password"));
        SHOW_PASSWORD_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void hidePassword() {
        PASSWORD_FIELD.shouldBe(Condition.visible)
        .shouldHave(Condition.attribute("type", "text"));
        SHOW_PASSWORD_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public String getErrorFieldLogin() {
        ERROR_FIELD_LOGIN.shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.exist);
        return ERROR_FIELD_LOGIN.getText();
    }

    public String getErrorFieldPassword() {
        ERROR_FIELD_PASSWORD.shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.exist);
        return ERROR_FIELD_PASSWORD.getText();
    }

    public boolean isErrorLoginAppear() {
        return ERROR_FIELD_LOGIN.exists();
    }

    public boolean isErrorPasswordAppear() {
        return ERROR_FIELD_PASSWORD.exists();
    }

}