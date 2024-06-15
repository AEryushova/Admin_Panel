package admin.pages.FaqPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class Question {
    private final SelenideElement EDIT_BUTTON = $x("//div[@class='UnAf hwSa eQX6']");
    private final ElementsCollection QUESTION_TEXTS = $$x("//div[@class='zxOH vkQg']/textarea");
    private final SelenideElement QUESTION_TEXT = $x("//div[@class='zxOH vkQg']/textarea");
    private final SelenideElement ANSWER_TEXT = $x("//div[@class='zxOH yCzg']/textarea");


    public void question() {
        EDIT_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        QUESTION_TEXT.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ANSWER_TEXT.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public ChangeQuestionWindow openWindowChangeQuestion() {
        EDIT_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new ChangeQuestionWindow();
    }

    public String getQuestion() {
        QUESTION_TEXT.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return QUESTION_TEXT.getText();
    }

    public String getAnswer() {
        ANSWER_TEXT.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return ANSWER_TEXT.getText();
    }

    public String getQuestionByIndex(int index) {
        SelenideElement questionText = QUESTION_TEXTS.get(index);
        questionText.shouldBe(Condition.visible);
        return questionText.getText();
    }

}
