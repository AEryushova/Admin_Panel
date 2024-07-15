package admin.pages.FaqPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class Question {
    private final SelenideElement EDIT_BUTTON = $x("//div[@class='UnAf hwSa eQX6']");
    private final ElementsCollection QUESTIONS_TEXTS = $$x("//div[@class='zxOH vkQg']/textarea");
    private final SelenideElement QUESTION_TEXT = $x("//div[@class='zxOH vkQg']/textarea");
    private final SelenideElement ANSWER_TEXT = $x("//div[@class='zxOH yCzg']/textarea");
    private final ElementsCollection ANSWERS_TEXTS=$$x("//div[@class='zxOH yCzg']/textarea");

    @Step("Верифицировать вопрос")
    public void verifyQuestion() {
        EDIT_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        QUESTION_TEXT.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ANSWER_TEXT.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Нажать кнопку изменения вопроса")
    public EditQuestionWindow clickButtonChangeQuestion() {
        EDIT_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new EditQuestionWindow();
    }

    @Step("Получить текст заголовка вопроса")
    public String getQuestion() {
        QUESTION_TEXT.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return QUESTION_TEXT.getText();
    }

    @Step("Получить текст ответа вопроса")
    public String getAnswer() {
        ANSWER_TEXT.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return ANSWER_TEXT.getText();
    }

    @Step("Получить текст заголовка по индексу '{0}'")
    public String getQuestionByIndex(int index) {
        SelenideElement questionText = QUESTIONS_TEXTS.get(index);
        questionText.shouldBe(Condition.visible);
        return questionText.getText();
    }

    @Step("Получить текст ответа по индексу '{0}'")
    public String getAnswerByIndex(int index) {
        SelenideElement questionText = ANSWERS_TEXTS.get(index);
        questionText.shouldBe(Condition.visible);
        return questionText.getText();
    }

}
