package admin.pages.modalWindowFAQ;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class Question {

    private final SelenideElement changeQuestion = $x("//div[@class='UnAf hwSa eQX6']");
    private final ElementsCollection questionTexts = $$x("//div[@class='zxOH vkQg']/textarea");
    private final ElementsCollection answerTexts = $$x("//div[@class='zxOH yCzg']/textarea");
    private final SelenideElement question;

    public Question(SelenideElement question) {
        this.question = question;
    }

    public void question() {
        changeQuestion.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SelenideElement question = questionTexts.get(0);
        question.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SelenideElement answer = answerTexts.get(0);
        answer.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public ChangeQuestionWindow openWindowChangeQuestion() {
        changeQuestion.click();
        return new ChangeQuestionWindow();
    }

    public String getQuestion(int index) {
        SelenideElement question = questionTexts.get(index);
        return question.getText();
    }

    public String getAnswer(int index) {
        SelenideElement answer =answerTexts.get(index);
        return answer.getText();
    }
}



