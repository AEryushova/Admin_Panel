package pages.CardDoctorPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class EditFeedbackWindow {

    private final SelenideElement
            WINDOW = $x("//div[@class='qJe_ OR_i']"),
            TEXT_FIELD = $x("//div[@class='zxOH']/textarea"),
            SAVE_BUTTON = $x("//button[text()='Сохранить']"),
            CLOSE_WINDOW_BUTTON = $x("//div[@class='UnAf gvNC']");

    @Step("Верифицировать окно изменения отзыва")
    public EditFeedbackWindow verifyChangeFeedbackWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TEXT_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SAVE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return this;
    }

    @Step("Ввести в поле текста отзыва '{0}'")
    public EditFeedbackWindow fillFieldText(String text) {
        TEXT_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(text);
        return this;
    }

    @Step("Нажать на кнопку сохранения")
    public void clickButtonSaveChanges() {
        SAVE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Получить значение поля текста отзыва")
    public String getValueTextFeedbackField() {
        TEXT_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return TEXT_FIELD.getValue();
    }

    @Step("Очистить поле текста отзыва")
    public void clearTextField() {
        TEXT_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .sendKeys(Keys.CONTROL, "a");
        TEXT_FIELD.sendKeys(Keys.BACK_SPACE);
    }

    @Step("Проверить доступность для нажатия кнопки сохранения")
    public boolean isEnabledSaveButton() {
        return SAVE_BUTTON.isEnabled();
    }

    @Step("Закрыть окно изменения отзыва")
    public void closeWindowChangeFeedback() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW.shouldNotBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Step("Проверить отображение окна изменения отзыва")
    public boolean isWindowAppear() {
        return WINDOW.isDisplayed();
    }


}
