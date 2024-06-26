package admin.pages.FaqPage;

import admin.pages.BasePage.BasePage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class FaqPage extends BasePage {

    private final SelenideElement TAB_NAME = $x("//a[text()='FAQ']");
    private final SelenideElement SEARCH_FAQ = $x("//input[@placeholder='Поиск по FAQ']");
    private final SelenideElement ADD_QUESTION = $x("//span[text()='Добавить вопрос']//parent::div//parent::button");
    private final ElementsCollection QUESTION_CARDS = $$x("//div[@class='dc4P']");
    private final SelenideElement QUESTION_CARD = $x("//div[@class='dc4P']");
    private final SelenideElement EMPTY_LIST_FAQ = $x("//span[text()='Список Пуст']");
    private final ElementsCollection QUESTIONS_FIELDS = $$x("//div[@class='zxOH vkQg']/textarea");
    private final ElementsCollection ANSWER_FIELDS = $$x("//div[@class='zxOH yCzg']/textarea");
    private final SelenideElement COUNT_FAQ = $x("//div[@class='wYqZ']/span[2]");

    @Step("Верифицировать страницу FAQ")
    public void faqPage() {
        TAB_NAME.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SEARCH_FAQ.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADD_QUESTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Нажать кнопку добавления нового вопроса")
    public AddQuestionWindow openWindowAddQuestion() {
        ADD_QUESTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new AddQuestionWindow();
    }

    @Step("Проверить отображение вопроса")
    public boolean isExistQuestions() {
        return QUESTION_CARD.isDisplayed();
    }

    @Step("Проверить отображение вопроса по индексу '{0}'")
    public boolean isExistQuestionsByIndex(int index) {
        SelenideElement question = QUESTION_CARDS.get(index);
        return question.isDisplayed();
    }

    @Step("Получить вопрос")
    public Question getQuestion() {
        QUESTION_CARD.shouldBe(Condition.visible,Duration.ofSeconds(5))
                .shouldBe(Condition.exist,Duration.ofSeconds(5));
        return new Question();
    }

    @Step("Поменять местами вопрос с индексом '{0}' и вопрос с индексом '{1}'")
    public void sequenceChangeQuestions(int sourceIndex, int targetIndex) {
        SelenideElement sourceQuestion = QUESTION_CARDS.get(sourceIndex);
        SelenideElement targetQuestion = QUESTION_CARDS.get(targetIndex);
        Actions actions = actions();
        actions.clickAndHold(sourceQuestion)
                .moveToElement(targetQuestion)
                .release()
                .perform();
    }

    @Step("Ввести в поле поиска '{0}'")
    public void searchFaq(String textSearch) {
        SEARCH_FAQ.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(textSearch);
    }

    @Step("Получить заголовки вопросов")
    public ElementsCollection getQuestionsFields() {
        return QUESTIONS_FIELDS;
    }

    @Step("Получить ответы вопросов")
    public ElementsCollection getAnswerFields() {
        return ANSWER_FIELDS;
    }

    @Step("Очистить поле поиска")
    public void clearSearchField() {
        SEARCH_FAQ.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .sendKeys(Keys.CONTROL, "a");
        SEARCH_FAQ.sendKeys(Keys.BACK_SPACE);
    }

    @Step("Получить значение поля поиска")
    public String getValueSearchField() {
        SEARCH_FAQ.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return SEARCH_FAQ.getValue();
    }

    @Step("Получить количество вопросов")
    public int getCountFaq() {
        COUNT_FAQ.shouldBe(Condition.visible);
        return Integer.parseInt(COUNT_FAQ.getText().split(" ")[0]);
    }

    @Step("Проверить отображение информации о пустом списке вопросов")
    public boolean isExistsEmptyList() {
        return EMPTY_LIST_FAQ.isDisplayed();
    }
}
