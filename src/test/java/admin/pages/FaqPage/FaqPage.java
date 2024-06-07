package admin.pages.FaqPage;

import admin.pages.BasePage.BasePage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class FaqPage extends BasePage {

    private final SelenideElement TAB_NAME = $x("//a[text()='FAQ']");
    private final SelenideElement SEARCH_FAQ = $x("//input[@placeholder='Поиск по FAQ']");
    private final SelenideElement ADD_QUESTION = $x("//span[text()='Добавить вопрос']//parent::div//parent::button");
    private final ElementsCollection QUESTION_CARD =$$x("//div[@class='dc4P']");

    public void faqPage() {
        TAB_NAME.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SEARCH_FAQ.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADD_QUESTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public AddQuestionWindow openWindowAddQuestion() {
        ADD_QUESTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
       .click();
        return new AddQuestionWindow();
    }

public void sequenceChangeActive(SelenideElement questionSource, SelenideElement questionTarget) {
    Actions actions = actions();
    actions.clickAndHold(questionSource)
            .moveToElement(questionTarget)
            .release()
            .perform();
}

    public boolean isExistQuestions(int index) {
        return QUESTION_CARD.get(index).exists();
    }

    public Question questionCard(int index) {
        SelenideElement questionCard = QUESTION_CARD.get(index);
        questionCard.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return new Question(questionCard);
    }

    public void sequenceChangeQuestions(int sourceIndex, int targetIndex) {
        SelenideElement sourceQuestion = QUESTION_CARD.get(sourceIndex);
        SelenideElement targetQuestion = QUESTION_CARD.get(targetIndex);
        sequenceChangeActive(sourceQuestion, targetQuestion);
    }
}
