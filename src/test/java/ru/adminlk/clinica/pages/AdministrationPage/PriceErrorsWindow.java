package ru.adminlk.clinica.pages.AdministrationPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class PriceErrorsWindow {

    private final SelenideElement
            WINDOW = $x("//div[text()='Ошибки в прайсе']//parent::div//parent::div//parent::div[@class='eV2Y']"),
            ADJUSTMENT_RULES_TAB = $x("//div[text()='Правила корректирования']"),
            ERROR_PRICE_TAB = $x("//div[text()='Ошибки в прайсе']"),
            ERROR_INFO = $x("//div[@class='FeiP']/span"),
            ADJUSTMENT_RULES_INFO = $x("//span[contains(text(), 'Код услуги предполагает следующий формат')]"),
            CLOSE_WINDOW_BUTTON = $x("//div[text()='Ошибки в прайсе']/parent::div/parent::div/preceding-sibling::div[@class='UnAf Ee5G']");


    @Step("Верифицировать окно ошибок прайса")
    public PriceErrorsWindow verifyPriceErrorsWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADJUSTMENT_RULES_TAB.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ERROR_PRICE_TAB.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return this;
    }

    @Step("Нажать на вкладку правил корректирования")
    public PriceErrorsWindow clickAdjustmentRulesTab() {
        ADJUSTMENT_RULES_TAB.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return this;
    }

    @Step("Нажать на вкладку ошибок в прайсе")
    public void clickErrorPriceTab() {
        ERROR_PRICE_TAB.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Получить текст ошибки во вкладке ошибок прайса")
    public String getErrorInfo() {
        ERROR_INFO.shouldBe(Condition.visible);
        return ERROR_INFO.getText();
    }

    @Step("Проверить отображение информации об ошибках во вкладке ошибок прайса")
    public boolean isErrorInfoAppear() {
        return ERROR_INFO.isDisplayed();
    }

    @Step("Проверить отображение информации во вкладке правил корректирования")
    public boolean isAdjustmentRulesAppear() {
        return ADJUSTMENT_RULES_INFO.isDisplayed();
    }

    @Step("Закрыть окно ошибок прайса")
    public void closeWindowPriceErrors() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW.shouldNotBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Step("Проверить отображение окна ошибок прайса")
    public boolean isWindowAppear() {
        return WINDOW.isDisplayed();
    }

}
