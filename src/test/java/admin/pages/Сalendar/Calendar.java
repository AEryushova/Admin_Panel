package admin.pages.Сalendar;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import admin.utils.testUtils.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class Calendar {

    private final SelenideElement HEADER_CURRENT_MONTH = $x("//div[@class='pickerHeaderDate__a75e']");
    private final SelenideElement SWITCH_LEFT_MONTH_BUTTON = $x("(//div[@class='pickerHeaderButton__b3f8'])[1]");
    private final SelenideElement SWITCH_RIGHT_MONTH_BUTTON = $x("(//div[@class='pickerHeaderButton__b3f8'])[2]");
    private final SelenideElement DATE_ACTIVATION;
    private final SelenideElement TODAY_BUTTON = $x("//div[text()='Сегодня']");

    public Calendar() {
        this.DATE_ACTIVATION = $x("//div[@class='pickerDateUnit__f5d7' and text()='" + DataHelper.generateFutureDayCurrentMonth() + "']");
    }

    public void calendar() {
        HEADER_CURRENT_MONTH.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SWITCH_LEFT_MONTH_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SWITCH_RIGHT_MONTH_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DATE_ACTIVATION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TODAY_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void selectDateActivation() {
        DATE_ACTIVATION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void switchFutureMonth() {
        SWITCH_RIGHT_MONTH_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void switchPreviousMonth() {
        SWITCH_LEFT_MONTH_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void selectDateActivationToday() {
        TODAY_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public String getCurrentMonthCalendar() {
        HEADER_CURRENT_MONTH.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return HEADER_CURRENT_MONTH.getText();
    }

}
