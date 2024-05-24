package admin.pages.modalWindowAdministration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class ChangePasswordAdminWindow {

    private final SelenideElement windowChangePasswordAdmin = $x("//input[@name='login' and @value='ADMIN_TESTUI_2']/parent::div/parent::div/parent::div[@class='ya9N CXVi']");
    private final SelenideElement headerWindowLogin = $x("//input[@name='login' and @value='ADMIN_TESTUI_2']");
    private final SelenideElement newPasswordField = $x("//input[@name=\"newPassword\"]");
    private final SelenideElement confirmPasswordField = $x("//input[@name=\"confirmPassword\"]");
    private final SelenideElement buttonSaveNewPasswordAdmin = $x("//button[text()='Сохранить']");
    private final SelenideElement closeWindowButton = $x("//input[@name='login' and @value='ADMIN_TESTUI_2']/parent::div/parent::div/parent::div/div[@class='q2XL']/div");
    private final SelenideElement errorFieldPassword = $x("//input[@name='newPassword']/following-sibling::div");
    private final SelenideElement errorFieldConfirmPassword = $x("//input[@name='confirmPassword']/following-sibling::div");


    public void changePasswordAdminWindow() {
        windowChangePasswordAdmin.shouldBe(Condition.visible, Duration.ofSeconds(5));
        headerWindowLogin.shouldBe(Condition.visible, Duration.ofSeconds(5));
        newPasswordField.shouldBe(Condition.visible, Duration.ofSeconds(5));
        confirmPasswordField.shouldBe(Condition.visible, Duration.ofSeconds(5));
        buttonSaveNewPasswordAdmin.shouldBe(Condition.disabled);
        closeWindowButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void fillingFieldNewPassword(String newPassword) {
        newPasswordField.setValue(newPassword);
    }

    public void fillingFieldConfirmPassword(String confirmPassword) {
        confirmPasswordField.setValue(confirmPassword);
    }

    public void clickSaveNewPasswordButton() {
        buttonSaveNewPasswordAdmin.shouldBe(Condition.enabled);
        buttonSaveNewPasswordAdmin.click();
        windowChangePasswordAdmin.shouldBe(Condition.hidden, Duration.ofSeconds(5));
    }
    public void clickFieldNewPassword() {
        newPasswordField.click();
    }

    public void clickFieldConfirmPassword() {
        confirmPasswordField.click();
    }

    public String getValuePasswordField() {
        return newPasswordField.getValue();
    }

    public String getValueConfirmPasswordField() {
        return confirmPasswordField.getValue();
    }

    public void closeWindowChangePasswordAdmin() {
        closeWindowButton.click();
        windowChangePasswordAdmin.shouldBe(Condition.hidden, Duration.ofSeconds(5));
    }

    public String getErrorFieldPassword() {
        errorFieldPassword.shouldBe(Condition.visible);
        return errorFieldPassword.getText();
    }

    public void hiddenErrorFieldPassword() {
        errorFieldPassword.shouldBe(Condition.hidden);
    }

    public String getErrorFieldConfirmPassword() {
        errorFieldConfirmPassword.shouldBe(Condition.visible);
        return errorFieldConfirmPassword.getText();
    }

}
