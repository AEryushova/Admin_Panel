package admin.pages.modalWindowFAQ;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class AddQuestionWindow {
    public final SelenideElement windowAddQuestion = $x("//span[text()='Новый Вопрос']//parent::div//parent::div//parent::div[@class='eV2Y']");
    private final SelenideElement headerWindow = $x("//span[text()='Новый Вопрос']");
    private final SelenideElement questionField = $x("//textarea[@placeholder='Укажите вопрос']");
    private final SelenideElement answerField=$x("//textarea[@placeholder='Укажите ответ']");
    private final SelenideElement addButton=$x("//button[text()='Добавить']");
    private final SelenideElement closeWindowButton = $x("//span[text()='Новый Вопрос']//parent::div//parent::div/parent::*/div[@class='UnAf Ee5G']");



    public void addQuestionWindow() {
        windowAddQuestion.shouldBe(Condition.visible, Duration.ofSeconds(5));
        headerWindow.shouldBe(Condition.visible, Duration.ofSeconds(5));
        questionField.shouldBe(Condition.visible, Duration.ofSeconds(5));
        answerField.shouldBe(Condition.visible, Duration.ofSeconds(5));
        addButton.shouldBe(Condition.disabled);
        closeWindowButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

public void fillingQuestionField(String question){
        questionField.setValue(question);
}
public void fillAnswerField(String answer){
        answerField.setValue(answer);
}
public void addQuestion(){
    addButton.shouldBe(Condition.enabled);
    addButton.click();
    windowAddQuestion.shouldBe(Condition.hidden, Duration.ofSeconds(5));
}

    public void clearFieldQuestion() {
        questionField.clear();
    }

    public void clearFieldAnswer() {
        answerField.clear();
    }

    public String getValueQuestionField() {
        return questionField.getValue();
    }

    public String getValueAnswerField() {
        return answerField.getValue();
    }

    public void closeWindowAddQuestion() {
        closeWindowButton.click();
        windowAddQuestion.shouldBe(Condition.hidden, Duration.ofSeconds(5));
    }

}
