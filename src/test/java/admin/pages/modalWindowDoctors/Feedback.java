package admin.pages.modalWindowDoctors;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class Feedback {
    private final SelenideElement dateFeedback = $x("//div[@class='YxjE']/span");

    private final SelenideElement AUTHOR = $x("//div[@class='zuRY']/span");
    private final SelenideElement TEXT = $x("//div[@class='zxOH']/textarea");
    private final SelenideElement EDIT_BUTTON = $x("//div[@class='UnAf gvNC']");
    private final SelenideElement PUBLICATION = $x("//button[text()='Опубликовать']");
    private final SelenideElement WITHDRAWAL_PUBLICATION = $x("//button[text()='Снять публикацию']");
    private final SelenideElement DELETE_BUTTON = $x("//button[text()='Удалить']");

    public void feedbackUnpublished() {
        dateFeedback.shouldBe(Condition.visible, Duration.ofSeconds(5));
        AUTHOR.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TEXT.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EDIT_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        PUBLICATION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void feedbackPublished() {
        dateFeedback.shouldBe(Condition.visible, Duration.ofSeconds(10));
        AUTHOR.shouldBe(Condition.visible, Duration.ofSeconds(10));
        TEXT.shouldBe(Condition.visible, Duration.ofSeconds(10));
        EDIT_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(10));
        WITHDRAWAL_PUBLICATION.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    public String getDateFeedback() {
        dateFeedback.exists();
        return dateFeedback.getText();
    }

    public String getAuthorFeedback() {
        AUTHOR.exists();
        return AUTHOR.getText();
    }

    public String getTextFeedback() {
        TEXT.exists();
        return TEXT.getText();
    }

    public ChangeFeedbackWindow editFeedback() {
        EDIT_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new ChangeFeedbackWindow();
    }

    public void publicationFeedback() {
        PUBLICATION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void withdrawalPublication() {
        WITHDRAWAL_PUBLICATION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void deleteFeedback() {
        DELETE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }
}
