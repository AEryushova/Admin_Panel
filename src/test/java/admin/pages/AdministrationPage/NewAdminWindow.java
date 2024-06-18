package admin.pages.AdministrationPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;


import java.time.Duration;


import static com.codeborne.selenide.Selenide.$x;


public class NewAdminWindow {
    private final SelenideElement WINDOW = $x("//span[text()='Добавить Администратора']//parent::div//parent::div//parent::div[@class='eV2Y']");
    private final SelenideElement HEADER_WINDOW = $x("//span[text()='Добавить Администратора']");
    private final SelenideElement LOGIN_FIELD = $x("//div[@id='popap_window']//input[@name=\"login\"]");
    private final SelenideElement PASSWORD_FIELD = $x("//div[@id='popap_window']//input[@name=\"password\"]");
    private final SelenideElement CONFIRM_PASSWORD_FIELD = $x("//div[@id='popap_window']//input[@name=\"confirmPassword\"]");
    private final SelenideElement ADD_BUTTON = $x("//div[@id='popap_window']//button[text()='Добавить']");
    private final SelenideElement CLOSE_WINDOW_BUTTON = $x("//span[text()='Добавить Администратора']//parent::div//parent::div/parent::*/div[@class='UnAf Ee5G']");
    private final SelenideElement CLEAR_FIELD_LOGIN_BUTTON = $x("//input[@name='login']//preceding-sibling::div[@class='m4oD']");
    private final SelenideElement CLEAR_FIELD_PASSWORD_BUTTON = $x("//input[@name='password']//preceding-sibling::div[@class='m4oD']");
    private final SelenideElement CLEAR_FIELD_CONFIRM_PASSWORD_BUTTON = $x("//input[@name='confirmPassword']//preceding-sibling::div[@class='m4oD']");
    private final SelenideElement ERROR_FIELD_LOGIN = $x("//input[@name='login']/following-sibling::div");
    private final SelenideElement ERROR_FIELD_PASSWORD = $x("//input[@name='password']/following-sibling::div");
    private final SelenideElement ERROR_FIELD_CONFIRM_PASSWORD = $x("//input[@name='confirmPassword']/following-sibling::div");


    public void newAdminWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        HEADER_WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        LOGIN_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        PASSWORD_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CONFIRM_PASSWORD_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADD_BUTTON.shouldBe(Condition.visible,Duration.ofSeconds(5)).shouldBe(Condition.disabled);
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void fillFieldNewAdminLogin(String login) {
        LOGIN_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(login);
    }

    public void fillFieldNewAdminPassword(String password) {
        PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(password);
    }

    public void fillFieldNewAdminConfirmPassword(String confirmPassword) {
        CONFIRM_PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(confirmPassword);
    }

    public void clickAddButton() {
        ADD_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public boolean isEnabledAddButton(){
        return ADD_BUTTON.isEnabled();
    }

    public void clickFieldNewAdminLogin() {
        LOGIN_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void clickFieldNewAdminPassword() {
        PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void clickFieldNewAdminConfirmPassword() {
        CONFIRM_PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void clearButtonLoginField() {
        CLEAR_FIELD_LOGIN_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void clickClearButtonPasswordField() {
        CLEAR_FIELD_PASSWORD_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void clickClearButtonConfirmPasswordField() {
        CLEAR_FIELD_CONFIRM_PASSWORD_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();

    }

    public String getValueLoginField() {
        LOGIN_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return LOGIN_FIELD.getValue();
    }

    public String getValuePasswordField() {
        PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return PASSWORD_FIELD.getValue();
    }

    public String getValueConfirmPasswordField() {
        CONFIRM_PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return CONFIRM_PASSWORD_FIELD.getValue();
    }

    public String getErrorFieldLogin() {
        ERROR_FIELD_LOGIN.shouldBe(Condition.visible);
        return ERROR_FIELD_LOGIN.getText();
    }

    public boolean isErrorLoginAppear() {
        return ERROR_FIELD_LOGIN.exists();
    }

    public String getErrorFieldPassword() {
        ERROR_FIELD_PASSWORD.shouldBe(Condition.visible);
        return ERROR_FIELD_PASSWORD.getText();
    }

    public boolean isErrorPasswordAppear() {
        return ERROR_FIELD_PASSWORD.exists();
    }

    public String getErrorFieldConfirmPassword() {
        ERROR_FIELD_CONFIRM_PASSWORD.shouldBe(Condition.visible);
        return ERROR_FIELD_CONFIRM_PASSWORD.getText();
    }

    public void closeWindowAddedAdmin() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW.shouldBe(Condition.hidden, Duration.ofSeconds(5));
    }

    public boolean isWindowAppear() {
        return WINDOW.exists();
    }

}
