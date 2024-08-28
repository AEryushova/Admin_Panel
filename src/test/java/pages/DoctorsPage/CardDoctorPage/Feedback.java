package pages.DoctorsPage.CardDoctorPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class Feedback {

    private final SelenideElement
            FEEDBACK = $x("//div[@class='qJe_']"),
            DATE_FEEDBACK = $x("//div[@class='YxjE']/span"),
            AUTHOR = $x("//div[@class='zuRY']/span"),
            TEXT = $x("//div[@class='zxOH']/textarea"),
            EDIT_BUTTON = $x("//div[@class='UnAf gvNC']"),
            PUBLICATION = $x("//button[text()='Опубликовать']"),
            WITHDRAWAL_PUBLICATION = $x("//button[text()='Снять публикацию']"),
            DELETE_BUTTON = $x("//button[text()='Удалить']");
    private final ElementsCollection DATES_FEEDBACKS = $$x("//div[@class='YxjE']/span");


    @Step("Верифицировать неопубликованный отзыв")
    public Feedback verifyFeedbackUnpublished() {
        FEEDBACK.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DATE_FEEDBACK.shouldBe(Condition.visible, Duration.ofSeconds(5));
        AUTHOR.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TEXT.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EDIT_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        PUBLICATION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return this;
    }

    @Step("Верифицировать опубликованный отзыв")
    public Feedback verifyFeedbackPublished() {
        FEEDBACK.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DATE_FEEDBACK.shouldBe(Condition.visible, Duration.ofSeconds(5));
        AUTHOR.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TEXT.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EDIT_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        WITHDRAWAL_PUBLICATION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return this;
    }

    @Step("Получить дату отзыва")
    public String getDateFeedback() {
        DATE_FEEDBACK.shouldBe(Condition.visible);
        return DATE_FEEDBACK.getText();
    }

    @Step("Получить дату отзыва по индексу '{0}'")
    public String getDateFeedbackByIndex(int index) {
        SelenideElement date = DATES_FEEDBACKS.get(index);
        date.shouldBe(Condition.visible);
        return date.getText();
    }

    @Step("Получить автора отзыва")
    public String getAuthorFeedback() {
        AUTHOR.shouldBe(Condition.visible);
        return AUTHOR.getText();
    }

    @Step("Получить текст отзыва")
    public String getTextFeedback() {
        TEXT.shouldBe(Condition.visible);
        return TEXT.getText();
    }

    @Step("Нажать на кнопку изменения отзыва")
    public EditFeedbackWindow clickButtonEditFeedback() {
        EDIT_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new EditFeedbackWindow();
    }

    @Step("Нажать на кнопку публикации")
    public Feedback clickButtonPublicationFeedback() {
        PUBLICATION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return this;
    }

    @Step("Нажать на кнопку снятия с публикации")
    public Feedback clickButtonWithdrawalPublication() {
        WITHDRAWAL_PUBLICATION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return this;
    }

    @Step("Нажать на кнопку удаления")
    public void clickButtonDeleteFeedback() {
        DELETE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }
}
