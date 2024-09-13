package ru.adminlk.clinica.samsmu.pages.AdministrationPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$x;

public class DeleteAdminWindow {

    private final SelenideElement
            WINDOW = $x("//span[contains(text(), 'хотите удалить администратора')]//parent::div//parent::div//parent::div[@class='eV2Y']"),
            HEADER_WINDOW = $x("//span[contains(text(), 'хотите удалить администратора')]"),
            YES_BUTTON = $x("//button[text()='Да']"),
            NO_BUTTON = $x("//button[text()='Нет']");

    @Step("Верифицировать окно удаления админа")
    public void verifyDeleteAdminWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        YES_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        NO_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Нажать кнопку удаления админа")
    public void clickButtonDeleteAdmin() {
        YES_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать кнопку отмены удаления админа")
    public void clickCancelButtonDeleteAdmin() {
        NO_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW.shouldNotBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Step("Проверить отображение админа '{0}' в заголовке окна")
    public boolean verifyLoginAdmin(String login) {
        return HEADER_WINDOW.has(text("Вы действительно хотите удалить администратора " + login + " ?"));
    }

    @Step("Проверить отображение окна удаления админа")
    public boolean isWindowAppear() {
        return WINDOW.isDisplayed();
    }
}



