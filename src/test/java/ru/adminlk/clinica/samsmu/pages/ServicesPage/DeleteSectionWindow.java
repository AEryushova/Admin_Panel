package ru.adminlk.clinica.samsmu.pages.ServicesPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$x;
import static ru.adminlk.clinica.samsmu.data.TestData.DataTest.NAME_OTHER_SERVICE_CATEGORY;

public class DeleteSectionWindow {

    public final SelenideElement
            WINDOW = $x("//div[@class='sCwo']"),
            HEADER_WINDOW = $x("//span[text()='Удалить Раздел']"),
            TEXT_WINDOW = $x("//div[@class='Fujp']/span"),
            DELETE_BUTTON = $x("//button[text()='Удалить']"),
            CANCEL_BUTTON = $x("//button[text()='Отменить']"),
            CLOSE_WINDOW_BUTTON = $x("//span[text()='Удалить Раздел']//parent::div//parent::div/parent::*/div[@class='UnAf Ee5G']");

    @Step("Верифицировать окно удаления раздела")
    public DeleteSectionWindow verifyDeleteSectionWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        HEADER_WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TEXT_WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CANCEL_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return this;
    }

    @Step("Нажать кнопку удаления")
    public void clickButtonDeleteSection() {
        DELETE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать кнопку отмены")
    public void clickCancelButtonDeleteSection() {
        CANCEL_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Верифицировать название раздела")
    public boolean verifyNameSection(String nameSection) {
        return TEXT_WINDOW.has(text("Раздел \"" + nameSection + "\" будет безвозвратно удален и все имеющиеся услуги внутри него будут перенесены в категорию \"" + NAME_OTHER_SERVICE_CATEGORY + "\". \n" +
                "Заранее убедитесь, что нет услуг которые должны быть отображены клиенту."));
    }

    @Step("Закрыть окно удаления раздела")
    public void closeWindowDeleteSection() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW.shouldNotBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Step("Получить окно удаления раздела")
    public SelenideElement getDeleteSectionWindow() {
        return WINDOW;
    }

    @Step("Проверить отображение окна удаления раздела")
    public boolean isWindowAppear() {
        return WINDOW.isDisplayed();
    }


}
