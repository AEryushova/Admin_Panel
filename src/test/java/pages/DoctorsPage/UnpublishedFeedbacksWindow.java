package pages.DoctorsPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import pages.Calendar.Calendar;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class UnpublishedFeedbacksWindow {

    private final SelenideElement
            WINDOW = $x("//span[text()='Неопубликованные отзывы']//parent::div//parent::div//parent::div[@class='eV2Y ZVVb']"),
            HEADER_WINDOW = $x("//div[@id='popap_window']/div/div/div/div/div/span[text()='Неопубликованные отзывы']"),
            CLOSE_WINDOW_BUTTON = $x("//span[text()='Неопубликованные отзывы']//parent::div//parent::div/parent::*/div[@class='UnAf Ee5G']"),
            CLEAR_FIELD_NEW_PASSWORD_BUTTON = $x("//button[text()='Очистить']"),
            CALENDAR_BUTTON=$x("//div[@class='RMs0']"),
            FEEDBACK = $x("//div[@class='qoxW']");


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
    public void clickClearFilter() {
        CLEAR_FIELD_NEW_PASSWORD_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
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

    @Step("Закрыть окно неопубликованных отзывов")
    public void closeWindowUnpublishedFeedbacksWindow() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW.shouldNotBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Step("Проверить отображение окна неопубликованных отзывов")
    public boolean isWindowAppears() {
        return WINDOW.isDisplayed();
    }


}
