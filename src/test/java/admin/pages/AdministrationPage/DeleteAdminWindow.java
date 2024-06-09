package admin.pages.AdministrationPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$x;

public class DeleteAdminWindow {

    private final SelenideElement WINDOW = $x("//span[contains(text(), 'хотите удалить администратора')]//parent::div//parent::div//parent::div[@class='eV2Y']");
    private final SelenideElement HEADER_WINDOW = $x("//span[contains(text(), 'хотите удалить администратора')]");
    private final SelenideElement YES_BUTTON = $x("//button[text()='Да']");
    private final SelenideElement NO_BUTTON = $x("//button[text()='Нет']");

    public void deleteAdminWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        YES_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        NO_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void deleteAdmin() {
        YES_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void cancelDeleteAdmin() {
        NO_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public boolean verifyLoginAdmin(String login){
        return HEADER_WINDOW.has(text("Вы действительно хотите удалить администратора " + login + " ?"));
    }
}



