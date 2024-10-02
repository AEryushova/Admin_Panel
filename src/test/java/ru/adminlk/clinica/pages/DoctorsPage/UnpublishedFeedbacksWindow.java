package ru.adminlk.clinica.pages.DoctorsPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ru.adminlk.clinica.pages.Calendar.Calendar;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.$x;

public class UnpublishedFeedbacksWindow {

    private final SelenideElement
            WINDOW = $x("//span[text()='Неопубликованные отзывы']//parent::div//parent::div//parent::div[@class='eV2Y ZVVb']"),
            HEADER_WINDOW = $x("//div[@id='popap_window']/div/div/div/div/div/span[text()='Неопубликованные отзывы']"),
            CLOSE_WINDOW_BUTTON = $x("//span[text()='Неопубликованные отзывы']//parent::div//parent::div/parent::*/div[@class='UnAf Ee5G']"),
            CLEAR_FIELD_NEW_PASSWORD_BUTTON = $x("//button[text()='Очистить']"),
            CALENDAR_BUTTON=$x("//div[@class='RMs0']"),
            FEEDBACK = $x("//div[@class='qoxW']"),
            EMPTY_LIST_FEEDBACK=$x("//span[text()='Нет отзывов']");


    @Step("Верифицировать окно неопубликованных отзывов")
    public UnpublishedFeedbacksWindow verifyUnpublishedFeedbacksWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        HEADER_WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CALENDAR_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CLEAR_FIELD_NEW_PASSWORD_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return this;
    }

    @Step("Открыть календарь")
    public Calendar openCalendarSelectPeriod() {
        CALENDAR_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new Calendar();
    }

    @Step("Получить период с календаря")
    public String getValuesButtonPeriod() {
        CALENDAR_BUTTON.shouldBe(Condition.visible);
        return CALENDAR_BUTTON.getText();
    }

    @Step("Очистить фильтр по дате")
    public UnpublishedFeedbacksWindow clickClearFilter() {
        CLEAR_FIELD_NEW_PASSWORD_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return this;
    }

    @Step("Получить отзыв")
    public UnpublishedFeedback getUnpublishedFeedback() {
        FEEDBACK.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return new UnpublishedFeedback();
    }

    @Step("Проверить отображение отзыва")
    public boolean isExistUnpublishedFeedback() {
        return FEEDBACK.isDisplayed();
    }

    @Step("Получить информацию о пустом списке неопубликованных отзывов")
    public SelenideElement getEmptyListFeedback() {
        return EMPTY_LIST_FEEDBACK;
    }

    @Step("Проверить отображение информации о пустом списке неопубликованных отзывов")
    public boolean isExistsEmptyListFeedback() {
        return EMPTY_LIST_FEEDBACK.isDisplayed();
    }

    @Step("Проверить является присутствует ли в периоде дата")
    public boolean isDateInThisPeriod(String date, String period){
        DateTimeFormatter periodFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter feedbackFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", new Locale("ru"));
        String[] periodDates = period.split(" - ");
        LocalDate startDate = LocalDate.parse(periodDates[0], periodFormatter);
        LocalDate endDate = LocalDate.parse(periodDates[1], periodFormatter);
        LocalDate feedbackDate = LocalDate.parse(date, feedbackFormatter);
        return !feedbackDate.isBefore(startDate) && !feedbackDate.isAfter(endDate);
    }

    @Step("Закрыть окно неопубликованных отзывов")
    public void closeWindowUnpublishedFeedbacksWindow() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW.shouldNotBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Step("Проверить отображение окна неопубликованных отзывов")
    public boolean isWindowAppear() {
        return WINDOW.isDisplayed();
    }


}
