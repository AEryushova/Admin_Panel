package admin.pages.modalWindowAdministration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import admin.pages.calendar.Calendar;


import java.io.File;
import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class UpdateOrderWindow {

    private final SelenideElement windowUpdateOrder = $x("//span[text()='Обновить приказ']//parent::div//parent::div//parent::div[@class='eV2Y']");
    private final SelenideElement headerWindow = $x("//div[@id='popap_window']/div/div/div/div/div/span[text()='Обновить приказ']");
    private final SelenideElement fileInputElement = $("input[type='file']");
    private final SelenideElement uploadOrderButton = $x("//span[text()='Загрузить']");
    private final SelenideElement closeWindowButton = $x("//span[text()='Обновить приказ']//parent::div//parent::div/parent::*/div[@class='UnAf Ee5G']");
    private final SelenideElement calendarButtonToday = $x("//div[@class='field__c8da container__ce0e']");


    public void updateOrderWindow() {
        windowUpdateOrder.shouldBe(Condition.visible, Duration.ofSeconds(5));
        headerWindow.shouldBe(Condition.visible, Duration.ofSeconds(5));
        calendarButtonToday.shouldBe(Condition.visible, Duration.ofSeconds(5));
        uploadOrderButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        closeWindowButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public Calendar openCalendarUpdateOrder() {
        calendarButtonToday.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new Calendar();
    }

    public void uploadOrder(String pathFilesOffer) {
        fileInputElement.uploadFile(new File(pathFilesOffer));
    }

    public String getValuesButtonToday() {
        calendarButtonToday.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return calendarButtonToday.getText();
    }

    public void closeWindowUpdateOrder() {
        closeWindowButton.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public boolean isWindowAppear() {
        return windowUpdateOrder.exists();
    }

}
