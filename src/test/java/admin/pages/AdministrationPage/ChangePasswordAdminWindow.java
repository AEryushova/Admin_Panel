package admin.pages.AdministrationPage;

import admin.data.DataConfig;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class ChangePasswordAdminWindow {

    private final SelenideElement WINDOW = $x("//input[@name='login' and @value='"+ DataConfig.DataTest.getLoginAdminTest() + "']/parent::div/parent::div/parent::div[@class='ya9N CXVi']");
    private final SelenideElement HEADER_WINDOW = $x("//input[@name='login' and @value='"+ DataConfig.DataTest.getLoginAdminTest() + "']");
    private final SelenideElement NEW_PASSWORD_FIELD = $x("//input[@name=\"newPassword\"]");
    private final SelenideElement CONFIRM_PASSWORD_FIELD = $x("//input[@name=\"confirmPassword\"]");
    private final SelenideElement SAVE_BUTTON = $x("//button[text()='Сохранить']");
    private final SelenideElement CLOSE_WINDOW_BUTTON = $x("//input[@name='login' and @value='"+ DataConfig.DataTest.getLoginAdminTest() +"']/parent::div/parent::div/parent::div/div[@class='q2XL']/div");
    private final SelenideElement ERROR_FIELD_PASSWORD = $x("//input[@name='newPassword']/following-sibling::div");
    private final SelenideElement ERROR_FIELD_CONFIRM_PASSWORD = $x("//input[@name='confirmPassword']/following-sibling::div");


    public void changePasswordAdminWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        HEADER_WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        NEW_PASSWORD_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CONFIRM_PASSWORD_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SAVE_BUTTON.shouldBe(Condition.visible,Duration.ofSeconds(5)).shouldBe(Condition.disabled);
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void fillFieldNewPassword(String newPassword) {
        NEW_PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(newPassword);
    }

    public void fillFieldConfirmPassword(String confirmPassword) {
        CONFIRM_PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(confirmPassword);
    }

    public void clickSaveNewPasswordButton() {
        SAVE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public boolean isEnabledSaveButton(){
        return SAVE_BUTTON.isEnabled();
    }

    public void clickFieldNewPassword() {
        NEW_PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void clickFieldConfirmPassword() {
        CONFIRM_PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public String getValuePasswordField() {
        NEW_PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return NEW_PASSWORD_FIELD.getValue();
    }

    public String getValueConfirmPasswordField() {
        CONFIRM_PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return CONFIRM_PASSWORD_FIELD.getValue();
    }

    public String getErrorFieldPassword() {
        ERROR_FIELD_PASSWORD.shouldBe(Condition.visible);
        return ERROR_FIELD_PASSWORD.getText();
    }

    public String getErrorFieldConfirmPassword() {
        ERROR_FIELD_CONFIRM_PASSWORD.shouldBe(Condition.visible);
        return ERROR_FIELD_CONFIRM_PASSWORD.getText();
    }

    public boolean isErrorPasswordAppear() {
        return ERROR_FIELD_PASSWORD.exists();
    }

    public void closeWindowChangePasswordAdmin() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW.shouldBe(Condition.hidden, Duration.ofSeconds(5));
    }

    public boolean isWindowAppear() {
        return WINDOW.exists();
    }
}
