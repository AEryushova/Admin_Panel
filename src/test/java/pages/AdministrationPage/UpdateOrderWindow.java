package pages.AdministrationPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import pages.Calendar.Calendar;
import io.qameta.allure.Step;


import java.io.File;
import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class UpdateOrderWindow {

    private final SelenideElement WINDOW = $x("//span[text()='Обновить приказ']//parent::div//parent::div//parent::div[@class='eV2Y']");
    private final SelenideElement HEADER_WINDOW = $x("//div[@id='popap_window']/div/div/div/div/div/span[text()='Обновить приказ']");
    private final SelenideElement FILE_INPUT_ELEMENT = $("input[type='file']");
    private final SelenideElement UPLOAD_BUTTON = $x("//span[text()='Загрузить']");
    private final SelenideElement CLOSE_WINDOW_BUTTON = $x("//span[text()='Обновить приказ']//parent::div//parent::div/parent::*/div[@class='UnAf Ee5G']");
    private final SelenideElement TODAY_BUTTON = $x("//div[@class='zMyf']");

    @Step("Верифицировать окно добавления приказа")
    public void verifyUpdateOrderWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        HEADER_WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TODAY_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        UPLOAD_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Открыть календарь")
    public Calendar openCalendarUpdateOrder() {
        TODAY_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new Calendar();
    }

    @Step("Загрузить файл '{0}'")
    public void uploadOrder(String pathFilesOffer) {
        FILE_INPUT_ELEMENT.shouldBe(Condition.exist)
        .uploadFile(new File(pathFilesOffer));
    }

    @Step("Получить текущую дату с календаря")
    public String getValuesButtonToday() {
        TODAY_BUTTON.shouldBe(Condition.visible);
        return TODAY_BUTTON.getText();
    }

    @Step("Закрыть окно добавления приказа")
    public void closeWindowUpdateOrder() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW.shouldNotBe(Condition.visible, Duration.ofSeconds(10));
    }

    @Step("Проверить отображение окна добавления приказа")
    public boolean isWindowAppear() {
        return WINDOW.isDisplayed();
    }

}
