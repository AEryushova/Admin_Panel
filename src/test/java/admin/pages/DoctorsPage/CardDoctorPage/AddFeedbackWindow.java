package admin.pages.DoctorsPage.CardDoctorPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import admin.pages.Calendar.Calendar;


import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class AddFeedbackWindow {


    private final SelenideElement WINDOW = $x("//div[@class='SIqL']");
    private final SelenideElement FIO_FIELD = $x("//input[@name='fio']");
    private final SelenideElement TODAY_BUTTON = $x("//div[@class='zMyf']");
    private final SelenideElement TEXT_FEEDBACK_FIELD = $x("//textarea[@placeholder='Введите текст отзыва']");
    private final SelenideElement PUBLISH_BUTTON = $x("//button[text()='Опубликовать']");
    private final SelenideElement CLOSE_WINDOW_BUTTON = $x("//input[@name='fio']//parent::div//parent::div/parent::*/div[@class='UnAf Ee5G']");

    public void addFeedbackWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        FIO_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TODAY_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TEXT_FEEDBACK_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        PUBLISH_BUTTON.shouldBe(Condition.visible,Duration.ofSeconds(5)).shouldBe(Condition.disabled);
    }

    public void fillFieldFio(String fio) {
        FIO_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(fio);
    }

    public void fillFieldTextFeedback(String textFeedback) {
        TEXT_FEEDBACK_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(textFeedback);
    }

    public Calendar openCalendarSelectDate() {
        TODAY_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new Calendar();
    }

    public void publishFeedbackButton() {
        PUBLISH_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public String getValuesButtonToday() {
        TODAY_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return TODAY_BUTTON.getText();
    }

    public String getValueFioField() {
        FIO_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return FIO_FIELD.getValue();
    }

    public String getValueTextFeedbackField() {
        TEXT_FEEDBACK_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return TEXT_FEEDBACK_FIELD.getValue();
    }

    public boolean isEnabledPublishButton(){
        return PUBLISH_BUTTON.isEnabled();
    }

    public void closeWindowAddFeedback() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public boolean isWindowAppear() {
        return WINDOW.exists();
    }


}
