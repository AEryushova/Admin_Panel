package admin.pages.modalWindowFAQ;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class ChangeQuestionWindow {
    public final SelenideElement windowChangeQuestion = $x("//div[@class='dc4P zw3J']");
    private final SelenideElement questionField = $x("//div[@class='zxOH vkQg']/textarea");
    private final SelenideElement answerField=$x("//div[@class='zxOH yCzg']/textarea");
    private final SelenideElement saveButton=$x("//button[text()='Сохранить']");
    private final SelenideElement deleteButton=$x("//button[text()='Удалить']");
    private final SelenideElement closeWindowButton = $x("//div[@class='UnAf hwSa Er9P']");

    public void changeQuestionWindow() {
        windowChangeQuestion.shouldBe(Condition.visible, Duration.ofSeconds(5));
        questionField.shouldBe(Condition.visible, Duration.ofSeconds(5));
        answerField.shouldBe(Condition.visible, Duration.ofSeconds(5));
        saveButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        deleteButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        closeWindowButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void fillingQuestionField(String question){
        questionField.setValue(question);
    }
    public void clearQuestionField(){
        answerField.clear();
    }

    public void fillingAnswerField(String answer){
        answerField.setValue(answer);
    }
    public void clearAnswerField() {
        questionField.clear();
    }

    public void saveChangesQuestion(){
        saveButton.click();
        windowChangeQuestion.shouldBe(Condition.hidden, Duration.ofSeconds(5));
    }

    public void deleteQuestion(){
        deleteButton.click();
    }

    public void closeWindowEditQuestion(){
        closeWindowButton.click();
    }


}
