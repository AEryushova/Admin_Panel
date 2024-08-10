package pages.FaqPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class EditQuestionWindow {
    public final SelenideElement WINDOW = $x("//div[@class='dc4P zw3J']");
    private final SelenideElement QUESTION_FIELD = $x("//div[@class='zxOH vkQg']/textarea");
    private final SelenideElement ANSWER_FIELD = $x("//div[@class='zxOH yCzg']/textarea");
    private final SelenideElement SAVE_BUTTON = $x("//button[text()='Сохранить']");
    private final SelenideElement DELETE_BUTTON = $x("//button[text()='Удалить']");
    private final SelenideElement CLOSE_WINDOW_BUTTON = $x("//div[@class='UnAf hwSa Er9P']");

    @Step("Верифицировать окно изменения вопроса")
    public void verifyChangeQuestionWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        QUESTION_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ANSWER_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SAVE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Ввести в поле вопроса '{0}'")
    public void fillQuestionField(String question) {
        QUESTION_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(question);
    }

    @Step("Ввести в поле ответа '{0}'")
    public void fillAnswerField(String answer) {
        ANSWER_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(answer);
    }

    @Step("Очистить поле вопроса")
    public void clearQuestionField(){
        QUESTION_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .sendKeys(Keys.CONTROL, "a");
        QUESTION_FIELD.sendKeys(Keys.BACK_SPACE);
    }

    @Step("Очистить поле ответа")
    public void clearAnswerField(){
        ANSWER_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .sendKeys(Keys.CONTROL, "a");
        ANSWER_FIELD.sendKeys(Keys.BACK_SPACE);
    }

    @Step("Нажать кнопку сохранения")
    public void clickButtonSaveChangesQuestion() {
        SAVE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Проверить доступность для нажатия кнопки сохранения вопроса")
    public boolean isEnabledSaveButton(){
        return SAVE_BUTTON.isEnabled();
    }

    @Step("Нажать кнопку удаления")
    public void clickButtonDeleteQuestion() {
        DELETE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Закрыть окно изменения вопроса")
    public void closeWindowEditQuestion() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW.shouldNotBe(Condition.visible, Duration.ofSeconds(15));
    }


    @Step("Проверить отображение окна изменения вопроса")
    public boolean isWindowAppear() {
        return WINDOW.isDisplayed();
    }


}
