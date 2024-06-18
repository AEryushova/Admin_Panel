package admin.pages.HeaderMenu;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class ChangeMinePasswordWindow {

    private final SelenideElement WINDOW = $x("//input[@name='oldPassword']//parent::div//parent::div//parent::div//parent::div[@class='eV2Y']");
    private final SelenideElement OLD_PASSWORD_FIELD = $x("//input[@name='oldPassword']");
    private final SelenideElement NEW_PASSWORD_FIELD = $x("//input[@name='newPassword']");
    private final SelenideElement CHANGE_PASSWORD_BUTTON=$x("//button[text()='Сменить']");
    private final SelenideElement CANCEL_BUTTON=$x("//button[text()='Отменить']");
    private final SelenideElement CLEAR_FIELD_OLD_PASSWORD = $x("//input[@name='oldPassword']//preceding-sibling::div[@class='m4oD']");
    private final SelenideElement CLEAR_FIELD_NEW_PASSWORD = $x("//input[@name='newPassword']//preceding-sibling::div[@class='m4oD']");
    private final SelenideElement ERROR_FIELD_OLD_PASSWORD = $x("//input[@name='oldPassword']/following-sibling::div");
    private final SelenideElement ERROR_FIELD_NEW_PASSWORD = $x("//input[@name='newPassword']/following-sibling::div");


    public void changeMinePasswordWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        OLD_PASSWORD_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        NEW_PASSWORD_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CHANGE_PASSWORD_BUTTON.shouldBe(Condition.visible,Duration.ofSeconds(5)).shouldBe(Condition.disabled);
        CANCEL_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void fillFieldOldPassword(String login) {
        OLD_PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(login);
    }

    public void fillFieldNewPassword(String password) {
        NEW_PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(password);
    }

    public void clickChangeButton() {
        CHANGE_PASSWORD_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void clickCancelButton() {
        CANCEL_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW.shouldBe(Condition.hidden, Duration.ofSeconds(5));
    }


    public boolean isEnabledChangeButton(){
        return CHANGE_PASSWORD_BUTTON.isEnabled();
    }

    public void clickFieldOldPassword() {
        OLD_PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void clickFieldNewPassword() {
        NEW_PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void clickClearButtonOldPasswordField() {
        CLEAR_FIELD_OLD_PASSWORD .shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void clickClearButtonNewPasswordField() {
        CLEAR_FIELD_NEW_PASSWORD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public String getValueOldPasswordField() {
        OLD_PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return OLD_PASSWORD_FIELD.getValue();
    }

    public String getValueNewPasswordField() {
        NEW_PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return NEW_PASSWORD_FIELD.getValue();
    }

    public String getErrorFieldOldPassword() {
        ERROR_FIELD_OLD_PASSWORD.shouldBe(Condition.visible);
        return ERROR_FIELD_OLD_PASSWORD.getText();
    }

    public boolean isErrorOldPasswordAppear() {
        return ERROR_FIELD_OLD_PASSWORD.exists();
    }

    public String getErrorFieldNewPassword() {
        ERROR_FIELD_NEW_PASSWORD.shouldBe(Condition.visible);
        return ERROR_FIELD_NEW_PASSWORD.getText();
    }

    public boolean isErrorNewPasswordAppear() {
        return ERROR_FIELD_NEW_PASSWORD.exists();
    }

    public boolean isWindowAppear() {
        return WINDOW.exists();
    }
}
