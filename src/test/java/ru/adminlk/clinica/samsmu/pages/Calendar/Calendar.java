package ru.adminlk.clinica.samsmu.pages.Calendar;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class Calendar {

    private final SelenideElement
            CALENDAR = $x("//div[@class='react-datepicker-popper']"),
            HEADER_CURRENT_MONTH = $x("//div[@class='react-datepicker__current-month']"),
            SWITCH_PREVIOUS_MONTH_BUTTON = $x("//button[@aria-label='Previous Month']"),
            SWITCH_NEXT_MONTH_BUTTON = $x("//button[@aria-label='Next Month']");
    private final ElementsCollection DATES_ACTIVATIONS = $$x("//div[@role='option']");


    @Step("Верифицировать окно календаря")
    public Calendar verifyCalendar() {
        CALENDAR.shouldBe(Condition.visible, Duration.ofSeconds(5));
        HEADER_CURRENT_MONTH.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SWITCH_PREVIOUS_MONTH_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SWITCH_NEXT_MONTH_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return this;
    }


    @Step("Нажать на дату '{0}' в календаре")
    public void clickDateActivation(String date) {
        SelenideElement dateActivation = getDate(date);
        dateActivation.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать на кнопку переключения на следующий месяц")
    public Calendar switchFutureMonth() {
        SWITCH_NEXT_MONTH_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return this;
    }

    @Step("Нажать на кнопку переключения на предыдущий месяц")
    public Calendar switchPreviousMonth() {
        SWITCH_PREVIOUS_MONTH_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return this;
    }

    @Step("Получить название текущего месяца в календаре")
    public String getNameCurrentMonth() {
        HEADER_CURRENT_MONTH.shouldBe(Condition.visible);
        return HEADER_CURRENT_MONTH.getText();
    }

    @Step("Проверить отображение окна календаря")
    public boolean isCalendarAppear() {
        return CALENDAR.isDisplayed();
    }

    @Step("Получить дату '{0}' из календаря")
    private SelenideElement getDate(String date) {
        for (SelenideElement element : DATES_ACTIVATIONS) {
            String ariaLabel = element.getAttribute("aria-label");
            if (ariaLabel != null && ariaLabel.equals(date)) {
                return element;
            }
        }
        throw new IllegalArgumentException("Date not found: " + date);
    }
}
