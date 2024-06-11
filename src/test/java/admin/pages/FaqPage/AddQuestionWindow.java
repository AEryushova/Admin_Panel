package admin.pages.FaqPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class AddQuestionWindow {
    public final SelenideElement WINDOW = $x("//span[text()='Новый Вопрос']//parent::div//parent::div//parent::div[@class='eV2Y']");
    private final SelenideElement HEADER_WINDOW = $x("//span[text()='Новый Вопрос']");
    private final SelenideElement QUESTION_FIELD = $x("//textarea[@placeholder='Укажите вопрос']");
    private final SelenideElement ANSWER_FIELD = $x("//textarea[@placeholder='Укажите ответ']");
    private final SelenideElement ADD_BUTTON = $x("//button[text()='Добавить']");
    private final SelenideElement CLOSE_WINDOW_BUTTON = $x("//span[text()='Новый Вопрос']//parent::div//parent::div/parent::*/div[@class='UnAf Ee5G']");


    public void addQuestionWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        HEADER_WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        QUESTION_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ANSWER_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADD_BUTTON.shouldBe(Condition.disabled);
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

    public void addQuestion() {
        ADD_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public boolean isEnabledAddButton(){
        return ADD_BUTTON.isEnabled();
    }

    public String getValueQuestionField() {
        QUESTION_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return QUESTION_FIELD.getValue();
    }

    public String getValueAnswerField() {
        ANSWER_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return ANSWER_FIELD.getValue();
    }

    public void closeWindowAddQuestion() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public boolean isWindowAppear() {
        return WINDOW.exists();
    }
}
