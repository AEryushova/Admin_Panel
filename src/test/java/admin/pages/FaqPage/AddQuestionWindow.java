package admin.pages.FaqPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class AddQuestionWindow {
    public final SelenideElement WINDOW = $x("//span[text()='Новый Вопрос']//parent::div//parent::div//parent::div[@class='eV2Y']");
    private final SelenideElement HEADER_WINDOW = $x("//span[text()='Новый Вопрос']");
    private final SelenideElement QUESTION_FIELD = $x("//textarea[@placeholder='Укажите вопрос']");
    private final SelenideElement ANSWER_FIELD = $x("//textarea[@placeholder='Укажите ответ']");
    private final SelenideElement ADD_BUTTON = $x("//button[text()='Добавить']");
    private final SelenideElement CLOSE_WINDOW_BUTTON = $x("//span[text()='Новый Вопрос']/parent::div/parent::div/preceding-sibling::div");

    @Step("Верифицировать окно добавления нового вопроса")
    public void verifyAddQuestionWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        HEADER_WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        QUESTION_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ANSWER_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADD_BUTTON.shouldBe(Condition.visible,Duration.ofSeconds(5)).shouldBe(Condition.disabled);
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Ввести в поле вопроса '{0}'")
    public void fillQuestionField(String question) {
        QUESTION_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(question);
    }

    @Step("Ввести в поле ответа '{0}'")
    public void fillAnswerField(String answer) {
        ANSWER_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(answer);
    }

    @Step("Нажать кнопку добавления")
    public void clickButtonAddQuestion() {
        ADD_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Проверить доступность для нажатия кнопки добавления вопроса")
    public boolean isEnabledAddButton(){
        return ADD_BUTTON.isEnabled();
    }

    @Step("Получить значение поля вопроса")
    public String getValueQuestionField() {
        QUESTION_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return QUESTION_FIELD.getValue();
    }

    @Step("Получить значение поля ответа")
    public String getValueAnswerField() {
        ANSWER_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return ANSWER_FIELD.getValue();
    }

    @Step("Закрыть окно добавления нового вопроса")
    public void closeWindowAddQuestion() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW.shouldNot(Condition.appear, Duration.ofSeconds(10));
    }

    @Step("Проверить отображение окна добавления нового вопроса")
    public boolean isWindowAppear() {
        return WINDOW.isDisplayed();
    }
}
