package admin.pages.calendar;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import admin.utils.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class Calendar {

    private final SelenideElement headerCurrentMonth = $x("//div[@class='pickerHeaderDate__a75e']");
    private final SelenideElement switchLeftMonthButton = $x("(//div[@class='pickerHeaderButton__b3f8'])[1]");
    private final SelenideElement switchRightMonthButton = $x("(//div[@class='pickerHeaderButton__b3f8'])[2]");
    private final SelenideElement dateActivation;

    public Calendar() {
        this.dateActivation = $x("//div[@class='pickerDateUnit__f5d7' and text()='" + DataHelper.generateFutureDayCurrentMonth() + "']");
    }

    private final SelenideElement todayButton = $x("//div[text()='Сегодня']");


    public void calendar() {
        headerCurrentMonth.shouldBe(Condition.visible, Duration.ofSeconds(10));
        switchLeftMonthButton.shouldBe(Condition.visible, Duration.ofSeconds(10));
        switchRightMonthButton.shouldBe(Condition.visible, Duration.ofSeconds(10));
        dateActivation.shouldBe(Condition.visible, Duration.ofSeconds(10));
        todayButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void selectDateActivation() {
        dateActivation.click();
    }

    public void switchFutureMonth() {
        switchRightMonthButton.click();
    }

    public void switchPreviousMonth() {
        switchLeftMonthButton.click();
    }

    public void selectDateActivationToday() {
        todayButton.click();
    }

    public String getCurrentMonthCalendar() {
        return headerCurrentMonth.getText();
    }


}
