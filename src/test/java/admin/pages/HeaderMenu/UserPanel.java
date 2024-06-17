package admin.pages.HeaderMenu;

import admin.pages.AuthorizationPage.AuthorizationPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class UserPanel {
    private final SelenideElement WINDOW =$x("//div[@class='iJul uLXf']");
    private final SelenideElement ROLE_STATUS = $x("//div[@class='role_qp4S']/span");
    private final SelenideElement LOGIN=$x("//span[@data-locator='admin-login']/span");
    private final SelenideElement EXIT_BUTTON = $x("//span[@data-locator='logout-btn']");
    private final SelenideElement CHANGE_PASSWORD_BUTTON=$x("//span[@data-locator='change-password-btn']");

    public void userPanelSuperAdmin() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ROLE_STATUS.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EXIT_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void userPanelAdmin() {
        ROLE_STATUS.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EXIT_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CHANGE_PASSWORD_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }


    public String checkProfileInfoUser() {
        ROLE_STATUS.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return ROLE_STATUS.getText();
    }

    public String checkLogin() {
        LOGIN.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return LOGIN.getText();
    }

    public ChangeMinePasswordWindow changePassword(){
        CHANGE_PASSWORD_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.enabled)
                .click();
        return new ChangeMinePasswordWindow();
    }

    public AuthorizationPage exitAdminPanel() {
        EXIT_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.enabled)
                .click();
        return new AuthorizationPage();
    }

    public boolean isWindowAppear() {
        return WINDOW.exists();
    }

}
