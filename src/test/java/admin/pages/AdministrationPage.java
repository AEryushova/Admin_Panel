package admin.pages;

import admin.data.DataInfo;
import admin.data.DataTest;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import admin.pages.modalWindowAdministration.*;


import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class AdministrationPage extends BasePage{

    private final SelenideElement tabNameAdministration = $x("//span[text()='Админы ' ]");
    private final SelenideElement updateOffer = $x("//span[text()='Обновить оферту' ]//parent::div//parent::button");
    private final SelenideElement updateProcessingPolicy = $x("//span[text()='Обновить политику обработки' ]//parent::div//parent::button");
    private final SelenideElement updateOrder = $x("//span[text()='Обновить приказ' ]//parent::div//parent::button");
    private final SelenideElement updatePrice = $x("//span[text()='Обновить прайс' ]//parent::div//parent::button");
    private final SelenideElement addedAdminButton = $x("//span[text()='Добавить админа']//parent::div//parent::button");
    private final SelenideElement cardAdmin = $x("//input[@name='login' and @value='" + DataTest.getLoginAdminTest() + "']");
    private final SelenideElement changePasswordButtonAdmin = $x("//div[.//input[contains(@value, '" + DataTest.getLoginAdminTest() + "')]]/following-sibling::div/button[contains(text(), 'Сменить пароль')]");
    private final SelenideElement deleteButtonAdmin = $x("//div[.//input[contains(@value, '" + DataTest.getLoginAdminTest() + "')]]/following-sibling::div/button[contains(text(), 'Удалить')]");


    public void adminPage() {
        tabNameAdministration.shouldBe(Condition.visible, Duration.ofSeconds(5));
        updateOffer.shouldBe(Condition.visible, Duration.ofSeconds(5));
        updateProcessingPolicy.shouldBe(Condition.visible, Duration.ofSeconds(5));
        updateOrder.shouldBe(Condition.visible, Duration.ofSeconds(5));
        updatePrice.shouldBe(Condition.visible, Duration.ofSeconds(5));
        addedAdminButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public NewAdminWindow openWindowAddedNewAdmin() {
        addedAdminButton.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new NewAdminWindow();
    }

    public ChangePasswordAdminWindow openWindowChangedPasswordAdmin() {
        cardAdmin.shouldBe(Condition.visible);
        changePasswordButtonAdmin.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new ChangePasswordAdminWindow();
    }

    public UpdateLegalDocWindow updateOffer() {
        updateOffer.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new UpdateLegalDocWindow();
    }

    public UpdateLegalDocWindow updateProcessingPolicy() {
        updateProcessingPolicy.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new UpdateLegalDocWindow();
    }

    public UpdateOrderWindow updateOrder() {
        updateOrder.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new UpdateOrderWindow();
    }

    public UpdatePriceWindow updatePrice() {
        updatePrice.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new UpdatePriceWindow();
    }

    public boolean isExistAdminCard() {
        return cardAdmin.exists();
    }

    public DeleteAdminWindow openWindowDeleteAdmin() {
        cardAdmin.shouldBe(Condition.visible);
        deleteButtonAdmin.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new DeleteAdminWindow();
    }

}