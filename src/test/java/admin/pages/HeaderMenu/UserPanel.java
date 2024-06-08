package admin.pages.HeaderMenu;

import admin.pages.AuthorizationPage.AuthorizationPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class UserPanel {
    private final SelenideElement WINDOW =$x("//div[@class='hopi']");
    private final SelenideElement ROLE_STATUS = $x("//div[@data-locator='container']/div/div/div/span[1]");
    private final SelenideElement EXIT_BUTTON = $x("//span[text()='Выход']");
    private final SelenideElement CHANGE_PASSWORD_BUTTON=$x("//span[text()='Сменить пароль']");

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
