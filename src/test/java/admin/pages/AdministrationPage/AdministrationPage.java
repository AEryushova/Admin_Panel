package admin.pages.AdministrationPage;

import admin.pages.BasePage.BasePage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;


import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class AdministrationPage extends BasePage {

    private final SelenideElement TAB_NAME = $x("//span[text()='Админы ']");
    private final SelenideElement UPDATE_OFFER = $x("//span[text()='Обновить оферту' ]//parent::div//parent::button");
    private final SelenideElement UPDATE_PROCESSING_POLICY = $x("//span[text()='Обновить политику обработки' ]//parent::div//parent::button");
    private final SelenideElement UPDATE_ORDER = $x("//span[text()='Обновить приказ' ]//parent::div//parent::button");
    private final SelenideElement UPDATE_PRICE = $x("//span[text()='Обновить прайс' ]//parent::div//parent::button");
    private final SelenideElement ADD_ADMIN = $x("//span[text()='Добавить админа']//parent::div//parent::button");


    public void adminPage() {
        TAB_NAME.shouldBe(Condition.visible, Duration.ofSeconds(5));
        UPDATE_OFFER.shouldBe(Condition.visible, Duration.ofSeconds(5));
        UPDATE_PROCESSING_POLICY.shouldBe(Condition.visible, Duration.ofSeconds(5));
        UPDATE_ORDER.shouldBe(Condition.visible, Duration.ofSeconds(5));
        UPDATE_PRICE.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADD_ADMIN.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public NewAdminWindow openWindowAddedNewAdmin() {
        ADD_ADMIN.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new NewAdminWindow();
    }

    public ChangePasswordAdminWindow openWindowChangedPasswordAdmin(String login) {
        searchCardAdmin(login).shouldBe(Condition.visible);
        searchChangedPasswordAdmin(login).shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new ChangePasswordAdminWindow();
    }

    public DeleteAdminWindow openWindowDeleteAdmin(String login) {
        searchCardAdmin(login).shouldBe(Condition.visible);
        searchDeleteAdmin(login).shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new DeleteAdminWindow();
    }

    public void adminCard(String login){
        searchCardAdmin(login).shouldBe(Condition.visible);
        searchChangedPasswordAdmin(login).shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        searchDeleteAdmin(login).shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
    }

    private SelenideElement searchCardAdmin(String login){
        return $x("//input[@name='login' and @value='" + login + "']/parent::div/parent::div/parent::div");
    }

    private SelenideElement searchChangedPasswordAdmin(String login){
        return $x("//div[.//input[contains(@value, '" + login + "')]]/following-sibling::div/button[contains(text(), 'Сменить пароль')]");
    }

    private SelenideElement searchDeleteAdmin(String login){
        return $x("//div[.//input[contains(@value, '" + login + "')]]/following-sibling::div/button[contains(text(), 'Удалить')]");
    }

    public boolean isVisibleAdminCard(String login) {
        return searchCardAdmin(login).isDisplayed();
    }

    public UpdateLegalDocWindow updateOffer() {
        UPDATE_OFFER.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new UpdateLegalDocWindow();
    }

    public UpdateLegalDocWindow updateProcessingPolicy() {
        UPDATE_PROCESSING_POLICY.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new UpdateLegalDocWindow();
    }

    public UpdateOrderWindow updateOrder() {
        UPDATE_ORDER.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new UpdateOrderWindow();
    }

    public UpdatePriceWindow updatePrice() {
        UPDATE_PRICE.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new UpdatePriceWindow();
    }


}