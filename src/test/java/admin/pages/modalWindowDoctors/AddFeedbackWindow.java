package admin.pages.modalWindowDoctors;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import admin.pages.calendar.Calendar;


import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class AddFeedbackWindow {


    private final SelenideElement windowAddedFeedback = $x("//div[@class='SIqL']");
    private final SelenideElement fieldFio = $x("//input[@name='fio']");

    private final SelenideElement calendarButtonToday = $x("//div[@class='field__c8da container__ce0e']");

    private final SelenideElement fieldTextFeedback = $x("//textarea[@placeholder='Введите текст отзыва']");

    private final SelenideElement publishButton = $x("//button[text()='Опубликовать']");

    private final SelenideElement closeWindowButton = $x("//input[@name='fio']//parent::div//parent::div/parent::*/div[@class='UnAf Ee5G']");

    public void addFeedbackWindow() {
        windowAddedFeedback.shouldBe(Condition.visible, Duration.ofSeconds(5));
        fieldFio.shouldBe(Condition.visible, Duration.ofSeconds(5));
        calendarButtonToday.shouldBe(Condition.visible, Duration.ofSeconds(5));
        fieldTextFeedback.shouldBe(Condition.visible, Duration.ofSeconds(5));
        publishButton.shouldBe(Condition.disabled, Duration.ofSeconds(5));
    }

    public void fillFieldFio(String fio) {
        fieldFio.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(fio);
    }

    public void fillFieldTextFeedback(String textFeedback) {
        fieldTextFeedback.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(textFeedback);
    }

    public Calendar openCalendarSelectDate() {
        calendarButtonToday.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return new Calendar();
    }

    public void clickPublishButton() {
        publishButton.shouldBe(Condition.visible, Duration.ofSeconds(10));
        publishButton.click();
        windowAddedFeedback.shouldBe(Condition.hidden, Duration.ofSeconds(10));
    }

    public String getValuesButtonToday() {
        calendarButtonToday.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return calendarButtonToday.getText();
    }

    public void closeWindowAddFeedback() {
        closeWindowButton.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public boolean isWindowAppear() {
        return windowAddedFeedback.exists();
    }


}
