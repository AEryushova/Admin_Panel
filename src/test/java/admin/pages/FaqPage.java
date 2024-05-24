package admin.pages;

import admin.pages.modalWindowFAQ.AddQuestionWindow;
import admin.pages.modalWindowFAQ.ChangeQuestionWindow;
import admin.pages.modalWindowFAQ.Question;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class FaqPage {

    private final SelenideElement tabNameFAQ = $x("//a[text()='FAQ']");
    private final SelenideElement searchFAQ= $x("//input[@placeholder='Поиск по FAQ']");
    private final SelenideElement addQuestion = $x("//span[text()='Добавить вопрос']//parent::div//parent::button");
    private final ElementsCollection questionCards=$$x("//div[@class='dc4P']");
    private final SelenideElement notification=$x("//div[@role='alert']/div//following-sibling::div");
    private final SelenideElement closeNotification = $x("//button[@aria-label='close']");
    private final SelenideElement footerFaqPage = $x("//span[text()='@ Самарский государственный медицинский университет']");
    private final SelenideElement returnToStartButton = $x("//div[@class='_x1E']");

    public void faqPage() {
        tabNameFAQ.shouldBe(Condition.visible, Duration.ofSeconds(5));
        searchFAQ.shouldBe(Condition.visible, Duration.ofSeconds(5));
        addQuestion.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public AddQuestionWindow openWindowAddQuestion() {
        addQuestion.click();
        return new AddQuestionWindow();
    }

    public Question questionCard(int index) {
        SelenideElement questionCard = questionCards.get(index);
        questionCard.shouldBe(Condition.visible, Duration.ofSeconds(10));
        return new Question(questionCard);
    }

public void sequenceChangeQuestions(int sourceIndex, int targetIndex) {
    SelenideElement sourceQuestion = questionCards.get(sourceIndex);
    SelenideElement targetQuestion = questionCards.get(targetIndex);
    sequenceChangeActive(sourceQuestion, targetQuestion);
}

public void sequenceChangeActive(SelenideElement questionSource, SelenideElement questionTarget) {
    Actions actions = actions();
    actions.clickAndHold(questionSource)
            .moveToElement(questionTarget)
            .release()
            .perform();
}

    public String getNotification() {
        notification.shouldBe(Condition.visible);
        return notification.getText();
    }

    public void closeNotification() {
        closeNotification.click();
        notification.shouldBe(Condition.hidden);
    }

    public void scrollPageToBottom() {
        footerFaqPage.scrollTo();
    }

    public void returnToStartPage() {
        returnToStartButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        returnToStartButton.click();
    }

    public void returnButtonDisappears(){
        returnToStartButton.shouldBe(Condition.hidden, Duration.ofSeconds(5));
    }

}
