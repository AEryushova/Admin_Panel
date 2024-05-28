package admin.pages;

import admin.data.DataInfo;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import admin.pages.modalWindowAdministration.*;


import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class AdministrationPage {

    private final SelenideElement tabNameAdministration = $x("//span[text()='Админы ' ]");
    private final SelenideElement updateOffer = $x("//span[text()='Обновить оферту' ]//parent::div//parent::button");
    private final SelenideElement updateProcessingPolicy = $x("//span[text()='Обновить политику обработки' ]//parent::div//parent::button");
    private final SelenideElement updateOrder = $x("//span[text()='Обновить приказ' ]//parent::div//parent::button");
    private final SelenideElement updatePrice = $x("//span[text()='Обновить прайс' ]//parent::div//parent::button");
    private final SelenideElement addedAdminButton = $x("//span[text()='Добавить админа']//parent::div//parent::button");
    private final SelenideElement cardAdmin = $x("//input[@name='login' and @value='ADMIN_TESTUI_2']");
    private final SelenideElement changePasswordButtonAdmin = $x("//div[.//input[contains(@value, 'ADMIN_TESTUI_2')]]/following-sibling::div/button[contains(text(), 'Сменить пароль')]");
    private final SelenideElement deleteButtonAdmin = $x("//div[.//input[contains(@value, 'ADMIN_TESTUI_2')]]/following-sibling::div/button[contains(text(), 'Удалить')]");
    private final SelenideElement deleteWindowAdmin = $x("//span[contains(text(), 'хотите удалить администратора')]//parent::div//parent::div//parent::div[@class='eV2Y']");
    private final SelenideElement yesButtonDelete = $x("//button[text()='Да']");
    private final SelenideElement noButtonDelete = $x("//button[text()='Нет']");
    private final SelenideElement notification = $x("//div[@role='alert']/div//following-sibling::div");
    private final SelenideElement closeNotification = $x("//button[@aria-label='close']");
    private final SelenideElement footerAdministrationPage = $x("//span[text()='@ Самарский государственный медицинский университет']");
    private final SelenideElement returnToStartButton = $x("//div[@class='_x1E']");


    public void administrationPage() {
        tabNameAdministration.shouldBe(Condition.visible, Duration.ofSeconds(10));
        updateOffer.shouldBe(Condition.visible, Duration.ofSeconds(10));
        updateProcessingPolicy.shouldBe(Condition.visible, Duration.ofSeconds(10));
        updateOrder.shouldBe(Condition.visible, Duration.ofSeconds(10));
        updatePrice.shouldBe(Condition.visible, Duration.ofSeconds(10));
        addedAdminButton.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    public NewAdminWindow openWindowAddedNewAdmin() {
        addedAdminButton.click();
        return new NewAdminWindow();
    }

    public ChangePasswordAdminWindow openWindowChangedPasswordAdmin() {
        cardAdmin.shouldBe(Condition.visible, Duration.ofSeconds(5));
        changePasswordButtonAdmin.click();
        return new ChangePasswordAdminWindow();
    }

    public UpdateLegalDocWindow updateOffer() {
        updateOffer.click();
        return new UpdateLegalDocWindow();
    }

    public UpdateLegalDocWindow updateProcessingPolicy() {
        updateProcessingPolicy.click();
        return new UpdateLegalDocWindow();
    }

    public UpdateOrderWindow updateOrder() {
        updateOrder.click();
        return new UpdateOrderWindow();
    }

    public UpdatePriceWindow updatePrice() {
        updatePrice.click();
        return new UpdatePriceWindow();
    }


    public void getAdminCard() {
        cardAdmin.shouldBe(Condition.visible, Duration.ofSeconds(5));
        changePasswordButtonAdmin.shouldBe(Condition.visible, Duration.ofSeconds(5));
        deleteButtonAdmin.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void getNotExistAdminCard() {
        cardAdmin.shouldBe(Condition.hidden, Duration.ofSeconds(5));
        changePasswordButtonAdmin.shouldBe(Condition.hidden, Duration.ofSeconds(5));
        deleteButtonAdmin.shouldBe(Condition.hidden, Duration.ofSeconds(5));
    }

    public void deleteAdmin() {
        cardAdmin.shouldBe(Condition.visible, Duration.ofSeconds(5));
        deleteButtonAdmin.click();
        deleteWindowAdmin.shouldBe(Condition.visible, Duration.ofSeconds(5));
        yesButtonDelete.click();
        cardAdmin.shouldBe(Condition.hidden);
        changePasswordButtonAdmin.shouldBe(Condition.hidden);
        deleteButtonAdmin.shouldBe(Condition.hidden);
    }

    public void openWindowDeleteAdmin() {
        deleteButtonAdmin.click();
    }

    public void cancelDeleteAdmin() {
        deleteWindowAdmin.shouldBe(Condition.visible,Duration.ofSeconds(5));
        noButtonDelete.click();
        cardAdmin.shouldBe(Condition.visible);
        changePasswordButtonAdmin.shouldBe(Condition.visible);
        deleteButtonAdmin.shouldBe(Condition.visible);
    }


    public String getNotification() {
        notification.shouldBe(Condition.visible);
        return notification.getText();
    }

    public void closeNotification() {
        closeNotification.click();
        notification.shouldBe(Condition.hidden);
    }

    public void scrollPageToBottom() {
        footerAdministrationPage.scrollTo();
    }

    public void returnToStartPage() {
        returnToStartButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        returnToStartButton.click();
    }

    public void returnButtonDisappears(){
        returnToStartButton.shouldBe(Condition.hidden, Duration.ofSeconds(5));
    }


}