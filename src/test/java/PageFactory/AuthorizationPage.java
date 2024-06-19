package PageFactory;

import admin.pages.BasePage.BasePage;
import admin.pages.DoctorsPage.DoctorsPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.util.Objects;

public class AuthorizationPage extends BasePage {

    @FindBy(xpath = "//input[@name='login']")
    private SelenideElement loginField;

    @FindBy(xpath = "//input[@name='password']")
    private SelenideElement passwordField;

    @FindBy(xpath = "//input[@name='login']//preceding-sibling::div[@class='m4oD']")
    private SelenideElement clearLoginButton;

    @FindBy(xpath = "//input[@name='password']//preceding-sibling::div[@class='m4oD']")
    private SelenideElement showPasswordButton;

    @FindBy(xpath = "//div[@class='ocE_']")
    private SelenideElement loader;

    @FindBy(xpath = "//button[text()='Войти']")
    private SelenideElement comeInButton;

    @FindBy(xpath = "//input[@type='text']//following-sibling::div")
    private SelenideElement errorFieldLogin;

    @FindBy(xpath = "//input[@type='password']//following-sibling::div")
    private SelenideElement errorFieldPassword;

    @FindBy(xpath = "//div[@class='RDMc']/img")
    private SelenideElement logo;


    public void authPage() {
        loginField.shouldBe(Condition.visible, Duration.ofSeconds(5));
        passwordField.shouldBe(Condition.visible, Duration.ofSeconds(5));
        comeInButton.shouldBe(Condition.visible, Duration.ofSeconds(5)).shouldBe(Condition.disabled);
        clearLoginButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        showPasswordButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        logo.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public DoctorsPage authorization(String login, String password) {
        loginField.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(login);
        passwordField.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(password);
        comeInButton.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        loader.shouldBe(Condition.visible, Duration.ofSeconds(5));
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

    public void clickToComeIn() {
        comeInButton.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
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

    public void clearLoginClickButton() {
        clearLoginButton.shouldBe(Condition.visible)
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

    public void showPassword() {
        passwordField.shouldBe(Condition.visible)
                .shouldHave(Condition.attribute("type", "password"));
        showPasswordButton.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void hidePassword() {
        passwordField.shouldBe(Condition.visible)
                .shouldHave(Condition.attribute("type", "text"));
        showPasswordButton.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }
}


