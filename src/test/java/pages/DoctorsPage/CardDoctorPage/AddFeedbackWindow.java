package pages.DoctorsPage.CardDoctorPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import pages.Calendar.Calendar;
import io.qameta.allure.Step;


import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class AddFeedbackWindow {


    private final SelenideElement
            WINDOW = $x("//div[@class='SIqL']"),
            FIO_FIELD = $x("//input[@name='fio']"),
            TODAY_BUTTON = $x("//div[@class='zMyf']"),
            TEXT_FEEDBACK_FIELD = $x("//textarea[@placeholder='Введите текст отзыва']"),
            CLEAR_FIELD_FIO_BUTTON = $x("//input[@name='fio']//preceding-sibling::div[@class='m4oD']"),
            PUBLISH_BUTTON = $x("//button[text()='Опубликовать']"),
            CLOSE_WINDOW_BUTTON = $x("//input[@name='fio']/parent::div/parent::div/parent::div/preceding-sibling::div");

    @Step("Верифицировать окно добавления отзыва")
    public AddFeedbackWindow verifyAddFeedbackWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        FIO_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TODAY_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TEXT_FEEDBACK_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        PUBLISH_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5)).shouldBe(Condition.disabled);
        CLEAR_FIELD_FIO_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return this;
    }

    @Step("Ввести в поле ФИО '{0}'")
    public AddFeedbackWindow fillFieldFio(String fio) {
        FIO_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(fio);
        return this;
    }

    @Step("Ввести в поле текста отзыва '{0}'")
    public AddFeedbackWindow fillFieldTextFeedback(String textFeedback) {
        TEXT_FEEDBACK_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(textFeedback);
        return this;
    }

    @Step("Открыть календарь")
    public Calendar openCalendarAddFeedback() {
        TODAY_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new Calendar();
    }

    @Step("Нажать на кнопку публикации")
    public void clickButtonPublishFeedbackButton() {
        PUBLISH_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Получить текущую дату с календаря")
    public String getCurrentDateButton() {
        TODAY_BUTTON.shouldBe(Condition.visible);
        return TODAY_BUTTON.getText();
    }

    @Step("Получить значение поля ФИО")
    public String getValueFioField() {
        FIO_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return FIO_FIELD.getValue();
    }


    @Step("Получить значение поля текста отзыва")
    public String getValueTextFeedbackField() {
        TEXT_FEEDBACK_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return TEXT_FEEDBACK_FIELD.getValue();
    }

    @Step("Нажать на кнопку очищения поля ФИО")
    public void clickClearButtonFioField() {
        CLEAR_FIELD_FIO_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        FIO_FIELD.shouldBe((Condition.empty), Duration.ofSeconds(15));
    }

    @Step("Проверить доступность для нажатия кнопки публикации")
    public boolean isEnabledPublishButton() {
        return PUBLISH_BUTTON.isEnabled();
    }

    @Step("Закрыть окно добавления отызва")
    public void closeWindowAddFeedback() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW.shouldNotBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Step("Проверить отображение окна добавления отызва")
    public boolean isWindowAppear() {
        return WINDOW.isDisplayed();
    }


}
