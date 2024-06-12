package admin.pages.FaqPage;

import admin.pages.BasePage.BasePage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class FaqPage extends BasePage {

    private final SelenideElement TAB_NAME = $x("//a[text()='FAQ']");
    private final SelenideElement SEARCH_FAQ = $x("//input[@placeholder='Поиск по FAQ']");
    private final SelenideElement CLEAR_SEARCH_FIELD_BUTTON = $x("//div[@class='InputAfter inputAfter__d6f9']");
    private final SelenideElement ADD_QUESTION = $x("//span[text()='Добавить вопрос']//parent::div//parent::button");
    private final ElementsCollection QUESTION_CARDS = $$x("//div[@class='dc4P']");
    private final SelenideElement QUESTION_CARD = $x("//div[@class='dc4P']");
    private final SelenideElement EMPTY_LIST_FAQ = $x("//span[text()='Список Пуст']");
    private final ElementsCollection QUESTIONS_FIELDS=$$x("//div[@class='zxOH vkQg']/textarea");
    private final ElementsCollection ANSWER_FIELDS = $$x("//div[@class='zxOH yCzg']/textarea");


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

    public boolean isExistQuestions() {
        return QUESTION_CARD.exists();
    }


    public Question getQuestion() {
        return new Question();
    }


    public void sequenceChangeQuestions(int sourceIndex, int targetIndex) {
        SelenideElement sourceQuestion = QUESTION_CARDS.get(sourceIndex);
        SelenideElement targetQuestion = QUESTION_CARDS.get(targetIndex);
        sequenceChangeActive(sourceQuestion, targetQuestion);
    }

    public void searchFaq(String textSearch) {
        SEARCH_FAQ.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(textSearch);
    }

    public ElementsCollection getQuestionsFields() {
        return QUESTIONS_FIELDS;
    }

    public ElementsCollection getAnswerFields() {
        return ANSWER_FIELDS;
    }

    public void clearSearchFieldThroughButton() {
        CLEAR_SEARCH_FIELD_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void clearSearchField() {
        SEARCH_FAQ.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .sendKeys(Keys.CONTROL, "a");
        SEARCH_FAQ.sendKeys(Keys.BACK_SPACE);
    }

    public String getValueSearchField() {
        SEARCH_FAQ.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return SEARCH_FAQ.getValue();
    }

    public boolean isExistsEmptyList() {
        return EMPTY_LIST_FAQ.exists();
    }
}
