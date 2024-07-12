package admin.pages.Calendar;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import admin.utils.otherUtils.DataHelper;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class Calendar {

    private final SelenideElement HEADER_CURRENT_MONTH = $x("//div[@class='react-datepicker__current-month']");
    private final SelenideElement SWITCH_PREVIOUS_MONTH_BUTTON = $x("//button[@aria-label='Previous Month']");
    private final SelenideElement SWITCH_NEXT_MONTH_BUTTON = $x("//button[@aria-label='Next Month']");
    private final SelenideElement DATE_ACTIVATION;

    public Calendar() {
        this.DATE_ACTIVATION = $x("//div[@role='option' and text()='" + DataHelper.generateFutureDayCurrentMonth() + "']");
    }

    @Step("Верифицировать окно календаря")
    public void verifyCalendar() {
        HEADER_CURRENT_MONTH.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SWITCH_PREVIOUS_MONTH_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SWITCH_NEXT_MONTH_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DATE_ACTIVATION.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Нажать на выбранную в календаре дату")
    public void clickDateActivation() {
        DATE_ACTIVATION.shouldBe(Condition.visible)
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
        HEADER_CURRENT_MONTH.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return HEADER_CURRENT_MONTH.getText();
    }

}
