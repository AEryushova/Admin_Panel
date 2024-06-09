package admin.pages.FaqPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class ChangeQuestionWindow {
    public final SelenideElement WINDOW = $x("//div[@class='dc4P zw3J']");
    private final SelenideElement QUESTION_FIELD = $x("//div[@class='zxOH vkQg']/textarea");
    private final SelenideElement ANSWER_FIELD = $x("//div[@class='zxOH yCzg']/textarea");
    private final SelenideElement SAVE_BUTTON = $x("//button[text()='Сохранить']");
    private final SelenideElement DELETE_BUTTON = $x("//button[text()='Удалить']");
    private final SelenideElement CLOSE_WINDOW_BUTTON = $x("//div[@class='UnAf hwSa Er9P']");

    public void changeQuestionWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        QUESTION_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ANSWER_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SAVE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void fillQuestionField(String question) {
        QUESTION_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(question);
    }

    public void fillAnswerField(String answer) {
        ANSWER_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(answer);
    }

    public void saveChangesQuestion() {
        SAVE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void deleteQuestion() {
        DELETE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }


    public void closeWindowEditQuestion() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public boolean isWindowAppear() {
        return WINDOW.exists();
    }


}
