package admin.pages.FaqPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class Question {

    private final SelenideElement EDIT_BUTTON = $x("//div[@class='UnAf hwSa eQX6']");
    private final ElementsCollection QUESTION_TEXT = $$x("//div[@class='zxOH vkQg']/textarea");
    private final ElementsCollection ANSWER_TEXT = $$x("//div[@class='zxOH yCzg']/textarea");
    private final SelenideElement QUESTION;

    public Question(SelenideElement question) {
        this.QUESTION = question;
    }

    public void question() {
        EDIT_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SelenideElement question = QUESTION_TEXT.get(0);
        question.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SelenideElement answer = ANSWER_TEXT.get(0);
        answer.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public ChangeQuestionWindow openWindowChangeQuestion() {
        EDIT_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
        .click();
        return new ChangeQuestionWindow();
    }

    public String getQuestion(int index) {
        SelenideElement question = QUESTION_TEXT.get(index);
        return question.getText();
    }

    public String getAnswer(int index) {
        SelenideElement answer = ANSWER_TEXT.get(index);
        return answer.getText();
    }
}



