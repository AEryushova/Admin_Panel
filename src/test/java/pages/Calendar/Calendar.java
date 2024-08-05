package pages.Calendar;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class Calendar {

    private final SelenideElement HEADER_CURRENT_MONTH = $x("//div[@class='react-datepicker__current-month']");
    private final SelenideElement SWITCH_PREVIOUS_MONTH_BUTTON = $x("//button[@aria-label='Previous Month']");
    private final SelenideElement SWITCH_NEXT_MONTH_BUTTON = $x("//button[@aria-label='Next Month']");
    private final ElementsCollection DATES_ACTIVATIONS = $$x("//div[@role='option']");


    @Step("Верифицировать окно календаря")
    public void verifyCalendar() {
        HEADER_CURRENT_MONTH.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SWITCH_PREVIOUS_MONTH_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SWITCH_NEXT_MONTH_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }


    @Step("Нажать на дату '{0}' в календаре")
    public void clickDateActivation(String date) {
        SelenideElement date_activation=getDate(date);
        date_activation.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать на кнопку переключения на следующий месяц")
    public void switchFutureMonth() {
        SWITCH_NEXT_MONTH_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать на кнопку переключения на предыдущий месяц")
    public void switchPreviousMonth() {
        SWITCH_PREVIOUS_MONTH_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Получить название текущего месяца в календаре")
    public String getNameCurrentMonth() {
        HEADER_CURRENT_MONTH.shouldBe(Condition.visible);
        return HEADER_CURRENT_MONTH.getText();
    }

    @Step("Получить дату '{0}' из календаря")
    private SelenideElement getDate(String date) {
        for (SelenideElement element : DATES_ACTIVATIONS) {
            if (element.getAttribute("aria-label").equals(date)) {
                return element;
            }
        }
        throw new IllegalArgumentException("Date not found: " + date);
    }
}
