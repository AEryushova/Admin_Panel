package admin.pages.modalWindowAdministration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class DeleteAdminWindow {

    private final SelenideElement windowDeleteAdmin = $x("//span[contains(text(), 'хотите удалить администратора')]//parent::div//parent::div//parent::div[@class='eV2Y']");
    private final SelenideElement yesButtonDelete = $x("//button[text()='Да']");
    private final SelenideElement noButtonDelete = $x("//button[text()='Нет']");

    public void deleteAdminWindow() {
        windowDeleteAdmin.shouldBe(Condition.visible, Duration.ofSeconds(5));
        yesButtonDelete.shouldBe(Condition.visible, Duration.ofSeconds(5));
        noButtonDelete.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void deleteAdmin() {
        yesButtonDelete.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void cancelDeleteAdmin() {
        noButtonDelete.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }
}



