package admin.pages.AdministrationPage;

import admin.pages.BasePage.BasePage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;


import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class AdministrationPage extends BasePage {

    private final SelenideElement TAB_NAME = $x("//span[text()='Админы ']");
    private final SelenideElement UPDATE_OFFER = $x("//span[text()='Обновить оферту' ]//parent::div//parent::button");
    private final SelenideElement UPDATE_PROCESSING_POLICY = $x("//span[text()='Обновить политику обработки' ]//parent::div//parent::button");
    private final SelenideElement UPDATE_ORDER = $x("//span[text()='Обновить приказ' ]//parent::div//parent::button");
    private final SelenideElement UPDATE_PRICE = $x("//span[text()='Обновить прайс' ]//parent::div//parent::button");
    private final SelenideElement ADD_ADMIN = $x("//span[text()='Добавить админа']//parent::div//parent::button");

    @Step("Верифицировать страницу Администрирования")
    public void adminPage() {
        TAB_NAME.shouldBe(Condition.visible, Duration.ofSeconds(5));
        UPDATE_OFFER.shouldBe(Condition.visible, Duration.ofSeconds(5));
        UPDATE_PROCESSING_POLICY.shouldBe(Condition.visible, Duration.ofSeconds(5));
        UPDATE_ORDER.shouldBe(Condition.visible, Duration.ofSeconds(5));
        UPDATE_PRICE.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADD_ADMIN.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Нажать кнопку добавления нового админа")
    public NewAdminWindow clickButtonAddedNewAdmin() {
        ADD_ADMIN.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new NewAdminWindow();
    }

    @Step("Нажать кнопку замены пароля админу '{0}'")
    public ChangePasswordAdminWindow clickButtonChangedPasswordAdmin(String login) {
        searchCardAdmin(login).shouldBe(Condition.visible);
        searchChangedPasswordAdmin(login).shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new ChangePasswordAdminWindow();
    }

    @Step("Нажать кнопку удаления админа '{0}'")
    public DeleteAdminWindow clickButtonDeleteAdmin(String login) {
        searchCardAdmin(login).shouldBe(Condition.visible);
        searchDeleteAdmin(login).shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new DeleteAdminWindow();
    }

    @Step("Верифицировать карточку админа '{0}'")
    public void adminCard(String login){
        searchCardAdmin(login).shouldBe(Condition.visible);
        searchChangedPasswordAdmin(login).shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        searchDeleteAdmin(login).shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
    }

    @Step("Найти карточку админа '{0}'")
    private SelenideElement searchCardAdmin(String login){
        return $x("//input[@name='login' and @value='" + login + "']/parent::div/parent::div/parent::div");
    }

    @Step("Найти кнопку смены пароля админу '{0}'")
    private SelenideElement searchChangedPasswordAdmin(String login){
        return $x("//div[.//input[contains(@value, '" + login + "')]]/following-sibling::div/button[contains(text(), 'Сменить пароль')]");
    }

    @Step("Найти кнопку удаления админа '{0}'")
    private SelenideElement searchDeleteAdmin(String login){
        return $x("//div[.//input[contains(@value, '" + login + "')]]/following-sibling::div/button[contains(text(), 'Удалить')]");
    }

    @Step("Проверить отображение карточки админа '{0}'")
    public boolean isVisibleAdminCard(String login) {
        return searchCardAdmin(login).isDisplayed();
    }

    @Step("Нажать кнопку обновления оферты")
    public UpdateLegalDocWindow updateOffer() {
        UPDATE_OFFER.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new UpdateLegalDocWindow();
    }

    @Step("Нажать кнопку обновления политики обработки")
    public UpdateLegalDocWindow updateProcessingPolicy() {
        UPDATE_PROCESSING_POLICY.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new UpdateLegalDocWindow();
    }

    @Step("Нажать кнопку обновления приказа")
    public UpdateOrderWindow updateOrder() {
        UPDATE_ORDER.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new UpdateOrderWindow();
    }

    @Step("Нажать кнопку обновления прайса")
    public UpdatePriceWindow updatePrice() {
        UPDATE_PRICE.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new UpdatePriceWindow();
    }


}