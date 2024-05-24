package admin.pages.modalWindowDoctors;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import admin.pages.calendar.Calendar;


import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class AddFeedbackWindow {


   private final SelenideElement windowAddedFeedback=$x("//div[@class='SIqL']");
    private final SelenideElement fieldFio =$x("//input[@name='fio']");

    private final SelenideElement calendarButtonToday = $x("//div[@class='field__c8da container__ce0e']");

    private final SelenideElement fieldTextFeedback=$x("//textarea[@placeholder='Введите текст отзыва']");

    private final SelenideElement publishButton=$x("//button[text()='Опубликовать']");

    private final SelenideElement closeWindowButton = $x("//input[@name='fio']//parent::div//parent::div/parent::*/div[@class='UnAf Ee5G']");

    public void addFeedbackWindow() {
        windowAddedFeedback.shouldBe(Condition.visible, Duration.ofSeconds(10));
        fieldFio.shouldBe(Condition.visible, Duration.ofSeconds(10));
        calendarButtonToday.shouldBe(Condition.visible, Duration.ofSeconds(10));
        fieldTextFeedback.shouldBe(Condition.visible, Duration.ofSeconds(10));
        publishButton.shouldBe(Condition.disabled, Duration.ofSeconds(10));
    }

    public void fillingFieldFio(String fio) {
        fieldFio.setValue(fio);
    }

    public void fillingFieldTextFeedback(String textFeedback) {
        fieldTextFeedback.setValue(textFeedback);
    }

    public Calendar openCalendarSelectDate() {
        calendarButtonToday.click();
        return new Calendar();
    }

    public void clickPublishButton() {
        publishButton.shouldBe(Condition.visible, Duration.ofSeconds(10));
        publishButton.click();
        windowAddedFeedback.shouldBe(Condition.hidden, Duration.ofSeconds(10));
    }

    public String getValuesButtonToday() {
        return calendarButtonToday.getText();
    }

    public void closeWindowAddFeedback() {
        closeWindowButton.click();
        windowAddedFeedback.shouldBe(Condition.hidden, Duration.ofSeconds(10));

    }





}
