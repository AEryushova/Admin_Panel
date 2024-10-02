package ru.adminlk.clinica.pages.FaqPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class Question {
    private final SelenideElement
            EDIT_BUTTON = $x("//div[@class='UnAf hwSa eQX6']"),
            QUESTION_TEXT = $x("//div[@class='zxOH vkQg']/textarea"),
            ANSWER_TEXT = $x("//div[@class='zxOH yCzg']/textarea");
    private final ElementsCollection QUESTIONS_TEXTS = $$x("//div[@class='zxOH vkQg']/textarea"),
            ANSWERS_TEXTS = $$x("//div[@class='zxOH yCzg']/textarea");

    @Step("Верифицировать вопрос")
    public void verifyQuestion() {
        EDIT_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        QUESTION_TEXT.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Нажать кнопку изменения вопроса")
    public EditQuestionWindow clickButtonChangeQuestion() {
        EDIT_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new EditQuestionWindow();
    }

    @Step("Получить текст заголовка вопроса")
    public String getTextQuestion() {
        QUESTION_TEXT.shouldBe(Condition.visible);
        return QUESTION_TEXT.getText();
    }

    @Step("Получить текст ответа вопроса")
    public String getTextAnswer() {
        ANSWER_TEXT.shouldBe(Condition.visible);
        return ANSWER_TEXT.getText();
    }

    @Step("Получить текст заголовка по индексу '{0}'")
    public String getQuestionTextByIndex(int index) {
        SelenideElement questionText = QUESTIONS_TEXTS.get(index);
        questionText.shouldBe(Condition.visible);
        return questionText.getText();
    }

    @Step("Получить текст ответа по индексу '{0}'")
    public String getAnswerTextByIndex(int index) {
        SelenideElement questionText = ANSWERS_TEXTS.get(index);
        questionText.shouldBe(Condition.visible);
        return questionText.getText();
    }

}
