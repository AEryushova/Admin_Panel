package pages.HeaderMenu;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

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

    @Step("Верифицировать окно замены своего пароля")
    public void verifyChangeMinePasswordWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        OLD_PASSWORD_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        NEW_PASSWORD_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CHANGE_PASSWORD_BUTTON.shouldBe(Condition.visible,Duration.ofSeconds(5)).shouldBe(Condition.disabled);
        CANCEL_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Ввести в поле старого пароля '{0}'")
    public void fillFieldOldPassword(String login) {
        OLD_PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(login);
    }

    @Step("Ввести в поле нового пароля '{0}'")
    public void fillFieldNewPassword(String password) {
        NEW_PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(password);
    }

    @Step("Нажать кнопку изменения")
    public void clickButtonChangePassword() {
        CHANGE_PASSWORD_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать кнопку отмены")
    public void clickCancelButtonChangePassword() {
        CANCEL_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW.shouldNotBe(Condition.visible, Duration.ofSeconds(10));
    }

    @Step("Проверить доступность для нажатия кнопки изменения пароля")
    public boolean isEnabledChangeButton(){
        return CHANGE_PASSWORD_BUTTON.isEnabled();
    }

    @Step("Нажать на поле старого пароля")
    public void clickFieldOldPassword() {
        OLD_PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать на поле нового пароля")
    public void clickFieldNewPassword() {
        NEW_PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать на кнопку очищения поля старого пароля")
    public void clickClearButtonOldPasswordField() {
        CLEAR_FIELD_OLD_PASSWORD .shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        OLD_PASSWORD_FIELD.shouldHave((Condition.empty),Duration.ofSeconds(10));
    }

    @Step("Нажать на кнопку очищения поля нового пароля")
    public void clickClearButtonNewPasswordField() {
        CLEAR_FIELD_NEW_PASSWORD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        NEW_PASSWORD_FIELD.shouldHave((Condition.empty),Duration.ofSeconds(10));
    }

    @Step("Получить значение поля старого пароля")
    public String getValueOldPasswordField() {
        OLD_PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return OLD_PASSWORD_FIELD.getValue();
    }

    @Step("Получить значение поля нового пароля")
    public String getValueNewPasswordField() {
        NEW_PASSWORD_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return NEW_PASSWORD_FIELD.getValue();
    }


    @Step("Получить текст ошибки поля старого пароля")
    public String getErrorFieldOldPassword() {
        ERROR_FIELD_OLD_PASSWORD.shouldBe(Condition.visible);
        return ERROR_FIELD_OLD_PASSWORD.getText();
    }

    @Step("Проверить отображение ошибки поля старого пароля")
    public boolean isErrorOldPasswordAppear() {
        return ERROR_FIELD_OLD_PASSWORD.isDisplayed();
    }

    @Step("Получить текст ошибки поля нового пароля")
    public String getErrorFieldNewPassword() {
        ERROR_FIELD_NEW_PASSWORD.shouldBe(Condition.visible);
        return ERROR_FIELD_NEW_PASSWORD.getText();
    }

    @Step("Проверить отображение ошибки поля нового пароля")
    public boolean isErrorNewPasswordAppear() {
        return ERROR_FIELD_NEW_PASSWORD.isDisplayed();
    }

    @Step("Проверить отображение окна замены своего пароля")
    public boolean isWindowAppear() {
        return WINDOW.isDisplayed();
    }
}
