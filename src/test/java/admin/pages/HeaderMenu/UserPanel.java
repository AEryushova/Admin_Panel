package admin.pages.HeaderMenu;

import admin.pages.AuthorizationPage.AuthorizationPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class UserPanel {
    private final SelenideElement WINDOW =$x("//div[@class='iJul uLXf']");
    private final SelenideElement ROLE_STATUS = $x("//div[@data-locator='admin-role']/span");
    private final SelenideElement LOGIN=$x("//div[@data-locator='admin-login']/span");
    private final SelenideElement EXIT_BUTTON = $x("//span[@data-locator='logout-btn']");
    private final SelenideElement CHANGE_PASSWORD_BUTTON=$x("//span[@data-locator='change-password-btn']");

    @Step("Верифицировать юзер-панели для Суперадмина")
    public void verifyUserPanelSuperAdmin() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ROLE_STATUS.shouldBe(Condition.visible, Duration.ofSeconds(5));
        LOGIN.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EXIT_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Верифицировать юзер-панели для Админа")
    public void verifyUserPanelAdmin() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ROLE_STATUS.shouldBe(Condition.visible, Duration.ofSeconds(5));
        LOGIN.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CHANGE_PASSWORD_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EXIT_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Получить роль юзера")
    public String getProfileInfoUser() {
        ROLE_STATUS.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return ROLE_STATUS.getText();
    }

    @Step("Получить логин юзера")
    public String getLogin() {
        LOGIN.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return LOGIN.getText();
    }

    @Step("Нажать на кнопку изменения своего пароля")
    public ChangeMinePasswordWindow clickButtonChangePassword(){
        CHANGE_PASSWORD_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.enabled)
                .click();
        return new ChangeMinePasswordWindow();
    }

    @Step("Нажать на кнопку выхода из админ-панели")
    public AuthorizationPage clickButtonExitAdminPanel() {
        EXIT_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.enabled)
                .click();
        return new AuthorizationPage();
    }

    @Step("Проверить отображение юзер-панели")
    public boolean isWindowAppear() {
        return WINDOW.isDisplayed();
    }

}
