package admin.pages.modalWindowAdministration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;


import java.time.Duration;


import static com.codeborne.selenide.Selenide.$x;


public class NewAdminWindow {
    private final SelenideElement windowAddedAdmin = $x("//span[text()='Добавить Администратора']//parent::div//parent::div//parent::div[@class='eV2Y']");
    private final SelenideElement headerWindow = $x("//span[text()='Добавить Администратора']");
    private final SelenideElement loginField = $x("//div[@id='popap_window']//input[@name=\"login\"]");
    private final SelenideElement passwordField = $x("//div[@id='popap_window']//input[@name=\"password\"]");
    private final SelenideElement confirmationPasswordField = $x("//div[@id='popap_window']//input[@name=\"confirmPassword\"]");
    private final SelenideElement addButton = $x("//div[@id='popap_window']//button[text()='Добавить']");
    private final SelenideElement closeWindowButton = $x("//span[text()='Добавить Администратора']//parent::div//parent::div/parent::*/div[@class='UnAf Ee5G']");
    private final SelenideElement clearFieldLoginButton = $x("//div[input[@name='login']]/div[@class='c51Z']");
    private final SelenideElement clearFieldPasswordButton = $x("//div[input[@name='password']]/div[@class='c51Z']");
    private final SelenideElement clearFieldConfirmPasswordButton = $x("//div[input[@name='confirmPassword']]/div[@class='c51Z']");
    private final SelenideElement errorFieldLogin = $x("//input[@name='login']/following-sibling::div");
    private final SelenideElement errorFieldPassword = $x("//input[@name='password']/following-sibling::div");
    private final SelenideElement errorFieldConfirmPassword = $x("//input[@name='confirmPassword']/following-sibling::div");


    public void newAdminWindow() {
        windowAddedAdmin.shouldBe(Condition.visible, Duration.ofSeconds(5));
        headerWindow.shouldBe(Condition.visible, Duration.ofSeconds(5));
        loginField.shouldBe(Condition.visible, Duration.ofSeconds(5));
        passwordField.shouldBe(Condition.visible, Duration.ofSeconds(5));
        confirmationPasswordField.shouldBe(Condition.visible, Duration.ofSeconds(5));
        addButton.shouldBe(Condition.disabled);
        closeWindowButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void fillFieldNewAdminLogin(String login) {
        loginField.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(login);
    }

    public void fillFieldNewAdminPassword(String password) {
        passwordField.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(password);
    }

    public void fillFieldNewAdminConfirmPassword(String confirmPassword) {
        confirmationPasswordField.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(confirmPassword);
    }

    public void clickAddButton() {
        addButton.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        windowAddedAdmin.shouldBe(Condition.hidden, Duration.ofSeconds(5));
    }

    public void clickFieldNewAdminLogin() {
        loginField.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void clickFieldNewAdminPassword() {
        passwordField.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void clickFieldNewAdminConfirmPassword() {
        confirmationPasswordField.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void clearButtonLoginField() {
        clearFieldLoginButton.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void clearButtonPasswordField() {
        clearFieldPasswordButton.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void clearButtonConfirmPasswordField() {
        clearFieldConfirmPasswordButton.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();

    }

    public String getValueLoginField() {
        loginField.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return loginField.getValue();
    }

    public String getValuePasswordField() {
        passwordField.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return passwordField.getValue();
    }

    public String getValueConfirmPasswordField() {
        confirmationPasswordField.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return confirmationPasswordField.getValue();
    }

    public void closeWindowAddedAdmin() {
        closeWindowButton.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public String getErrorFieldLogin() {
        errorFieldLogin.shouldBe(Condition.visible);
        return errorFieldLogin.getText();
    }

    public boolean isErrorLoginAppear() {
        return errorFieldLogin.exists();
    }

    public String getErrorFieldPassword() {
        errorFieldPassword.shouldBe(Condition.visible);
        return errorFieldPassword.getText();
    }

    public boolean isErrorPasswordAppear() {
        return errorFieldPassword.exists();
    }

    public String getErrorFieldConfirmPassword() {
        errorFieldConfirmPassword.shouldBe(Condition.visible);
        return errorFieldConfirmPassword.getText();
    }

    public boolean isWindowAppear() {
        return windowAddedAdmin.exists();
    }

}
