package admin.pages.modalWindowDoctors;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class Feedback {
    private final SelenideElement dateFeedback = $x("//div[@class='YxjE']/span");

    private final SelenideElement authorFeedback = $x("//div[@class='zuRY']/span");
    private final SelenideElement textFeedback = $x("//div[@class='zxOH']/textarea");
    private final SelenideElement editFeedback = $x("//div[@class='UnAf gvNC']");
    private final SelenideElement publicationFeedback = $x("//button[text()='Опубликовать']");
    private final SelenideElement withdrawalPublication = $x("//button[text()='Снять публикацию']");
    private final SelenideElement deleteFeedback = $x("//button[text()='Удалить']");

    public void feedbackUnpublished() {
        dateFeedback.shouldBe(Condition.visible, Duration.ofSeconds(5));
        authorFeedback.shouldBe(Condition.visible, Duration.ofSeconds(5));
        textFeedback.shouldBe(Condition.visible, Duration.ofSeconds(5));
        editFeedback.shouldBe(Condition.visible, Duration.ofSeconds(5));
        publicationFeedback.shouldBe(Condition.visible, Duration.ofSeconds(5));
        deleteFeedback.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void feedbackPublished() {
        dateFeedback.shouldBe(Condition.visible, Duration.ofSeconds(10));
        authorFeedback.shouldBe(Condition.visible, Duration.ofSeconds(10));
        textFeedback.shouldBe(Condition.visible, Duration.ofSeconds(10));
        editFeedback.shouldBe(Condition.visible, Duration.ofSeconds(10));
        withdrawalPublication.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    public String getDateFeedback() {
        dateFeedback.exists();
        return dateFeedback.getText();
    }

    public String getAuthorFeedback() {
        authorFeedback.exists();
        return authorFeedback.getText();
    }

    public String getTextFeedback() {
        textFeedback.exists();
        return textFeedback.getText();
    }

    public ChangeFeedbackWindow editFeedback() {
        editFeedback.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new ChangeFeedbackWindow();
    }

    public void publicationFeedback() {
        publicationFeedback.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void withdrawalPublication() {
        withdrawalPublication.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void deleteFeedback() {
        deleteFeedback.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }
}
