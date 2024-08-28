package pages.AdministrationPage;

import pages.BasePage.BasePage;
import com.codeborne.selenide.*;
import io.qameta.allure.Step;


import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class AdministrationPage extends BasePage {

    private final SelenideElement
            TAB_NAME = $x("//span[text()='Админы ']"),
            UPDATE_OFFER = $x("//span[text()='Обновить оферту' ]//parent::div//parent::button"),
            UPDATE_PROCESSING_POLICY = $x("//span[text()='Обновить политику обработки' ]//parent::div//parent::button"),
            UPDATE_ORDER = $x("//span[text()='Обновить приказ' ]//parent::div//parent::button"),
            UPDATE_PRICE = $x("//span[text()='Обновить прайс' ]//parent::div//parent::button"),
            ADD_ADMIN = $x("//span[text()='Добавить админа']//parent::div//parent::button");

    @Step("Верифицировать страницу Администрирования")
    public void verifyAdminPage() {
        TAB_NAME.shouldBe(Condition.visible, Duration.ofSeconds(10));
        UPDATE_OFFER.shouldBe(Condition.visible, Duration.ofSeconds(10));
        UPDATE_PROCESSING_POLICY.shouldBe(Condition.visible, Duration.ofSeconds(10));
        UPDATE_ORDER.shouldBe(Condition.visible, Duration.ofSeconds(10));
        UPDATE_PRICE.shouldBe(Condition.visible, Duration.ofSeconds(10));
        ADD_ADMIN.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    @Step("Нажать кнопку добавления нового админа")
    public NewAdminWindow clickButtonAddNewAdmin() {
        ADD_ADMIN.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new NewAdminWindow();
    }

    @Step("Нажать кнопку замены пароля админу '{0}'")
    public ChangePasswordAdminWindow clickButtonChangePassword(String login) {
        getCardAdmin(login).shouldBe(Condition.visible, Duration.ofSeconds(5));
        getButtonChangedPassword(login).shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.enabled)
                .click();
        return new ChangePasswordAdminWindow();
    }

    @Step("Нажать кнопку удаления админа '{0}'")
    public DeleteAdminWindow clickButtonDeleteAdmin(String login) {
        getCardAdmin(login).shouldBe(Condition.visible, Duration.ofSeconds(5));
        getButtonDeleteAdmin(login).shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new DeleteAdminWindow();
    }

    @Step("Верифицировать карточку админа '{0}'")
    public void verifyAdminCard(String login) {
        getCardAdmin(login).shouldBe(Condition.visible, Duration.ofSeconds(5));
        getButtonChangedPassword(login).shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.enabled);
        getButtonDeleteAdmin(login).shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.enabled);
    }

    @Step("Получить карточку админа '{0}'")
    public SelenideElement getCardAdmin(String login) {
        return $x("//input[@name='login' and @value='" + login + "']/parent::div/parent::div/parent::div");
    }

    @Step("Получить кнопку смены пароля админу '{0}'")
    private SelenideElement getButtonChangedPassword(String login) {
        return $x("//div[.//input[contains(@value, '" + login + "')]]/following-sibling::div/button[contains(text(), 'Сменить пароль')]");
    }

    @Step("Получить кнопку удаления админа '{0}'")
    private SelenideElement getButtonDeleteAdmin(String login) {
        return $x("//div[.//input[contains(@value, '" + login + "')]]/following-sibling::div/button[contains(text(), 'Удалить')]");
    }

    @Step("Проскроллить страницу до карточки админа '{0}'")
    public AdministrationPage scrollToCardAdmin(String login) {
        SelenideElement cardAdmin = getCardAdmin(login);
        cardAdmin.scrollIntoView("{behavior: 'auto', block: 'center'}");
        Selenide.sleep(3000);
        return this;
    }

    @Step("Проверить отображение карточки админа '{0}'")
    public boolean isVisibleAdminCard(String login) {
        return getCardAdmin(login).isDisplayed();
    }

    @Step("Проверить существование карточки админа '{0}'")
    public boolean isExistAdminCard(String login) {
        try {
            SelenideElement cardAdmin = getCardAdmin(login);
            return cardAdmin.exists();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Нажать кнопку обновления оферты")
    public UpdateLegalDocWindow clickButtonUpdateOffer() {
        UPDATE_OFFER.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new UpdateLegalDocWindow();
    }

    @Step("Нажать кнопку обновления политики обработки")
    public UpdateLegalDocWindow clickButtonUpdateProcessingPolicy() {
        UPDATE_PROCESSING_POLICY.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new UpdateLegalDocWindow();
    }

    @Step("Нажать кнопку обновления приказа")
    public UpdateOrderWindow clickButtonUpdateOrder() {
        UPDATE_ORDER.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new UpdateOrderWindow();
    }

    @Step("Нажать кнопку обновления прайса")
    public UpdatePriceWindow clickButtonUpdatePrice() {
        UPDATE_PRICE.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new UpdatePriceWindow();
    }


}