package admin.pages.DoctorsPage.CardDoctorPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class Feedback {

    private final SelenideElement FEEDBACK = $x("//div[@class='qJe_']");
    private final SelenideElement DATE_FEEDBACK = $x("//div[@class='YxjE']/span");
    private final ElementsCollection DATES_FEEDBACKS = $$x("//div[@class='YxjE']/span");
    private final SelenideElement AUTHOR = $x("//div[@class='zuRY']/span");
    private final SelenideElement TEXT = $x("//div[@class='zxOH']/textarea");
    private final SelenideElement EDIT_BUTTON = $x("//div[@class='UnAf gvNC']");
    private final SelenideElement PUBLICATION = $x("//button[text()='Опубликовать']");
    private final SelenideElement WITHDRAWAL_PUBLICATION = $x("//button[text()='Снять публикацию']");
    private final SelenideElement DELETE_BUTTON = $x("//button[text()='Удалить']");

    @Step("Верифицировать неопубликованный отзыв")
    public void verifyFeedbackUnpublished() {
        FEEDBACK.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DATE_FEEDBACK.shouldBe(Condition.visible, Duration.ofSeconds(5));
        AUTHOR.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TEXT.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EDIT_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        PUBLICATION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Верифицировать опубликованный отзыв")
    public void verifyFeedbackPublished() {
        FEEDBACK.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DATE_FEEDBACK.shouldBe(Condition.visible, Duration.ofSeconds(5));
        AUTHOR.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TEXT.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EDIT_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        WITHDRAWAL_PUBLICATION.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Получить дату отзыва")
    public String getDateFeedback() {
        DATE_FEEDBACK.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
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
        AUTHOR.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return AUTHOR.getText();
    }

    @Step("Получить текст отзыва")
    public String getTextFeedback() {
        TEXT.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
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
    public void clickButtonPublicationFeedback() {
        PUBLICATION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать на кнопку снятия с публикации")
    public void clickButtonWithdrawalPublication() {
        WITHDRAWAL_PUBLICATION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать на кнопку удаления")
    public void clickButtonDeleteFeedback() {
        DELETE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }
}
