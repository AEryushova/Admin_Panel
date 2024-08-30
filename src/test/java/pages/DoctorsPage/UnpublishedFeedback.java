package pages.DoctorsPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import pages.CardDoctorPage.CardDoctorPage;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class UnpublishedFeedback {

    private final SelenideElement
            FEEDBACK = $x("//div[@class='qoxW']"),
            AUTHOR = $x("//span[@class='Yk1f']"),
            DOCTOR=$x("//span[@class='xrzV']"),
            DATE_FEEDBACK = $x("//span[@class='Xbdq']"),
            TEXT = $x("//div[@class='zxOH']/textarea"),
            OPEN_CARD_DOCTOR = $x("//button[text()='Перейти']"),
            PUBLICATION_BUTTON = $x("//button[text()='Опубликовать']"),
            DELETE_BUTTON = $x("//button[text()='Удалить']");

    @Step("Верифицировать неопубликованный отзыв")
    public UnpublishedFeedback verifyUnpublishedFeedback() {
        FEEDBACK.shouldBe(Condition.visible, Duration.ofSeconds(5));
        AUTHOR.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DOCTOR.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DATE_FEEDBACK.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TEXT.shouldBe(Condition.visible, Duration.ofSeconds(5));
        OPEN_CARD_DOCTOR.shouldBe(Condition.visible, Duration.ofSeconds(5));
        PUBLICATION_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return this;
    }

    @Step("Получить ФИО автора отзыва")
    public String getNameAuthorFeedback() {
        AUTHOR.shouldBe(Condition.visible);
        return AUTHOR.getText();
    }

    @Step("Получить ФИО врача")
    public String getNameDoctorFeedback() {
        DOCTOR.shouldBe(Condition.visible);
        return DOCTOR.getText();
    }

    @Step("Получить дату отзыва")
    public String getDateFeedback() {
        DATE_FEEDBACK.shouldBe(Condition.visible);
        return DATE_FEEDBACK.getText();
    }

    @Step("Получить текст отзыва")
    public String getTextFeedback() {
        TEXT.shouldBe(Condition.visible);
        return TEXT.getText();
    }

    @Step("Ввести в поле текста отзыва '{0}'")
    public UnpublishedFeedback fillFieldText(String text) {
        TEXT.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(text);
        return this;
    }

    @Step("Нажать на кнопку перехода на карточку врача")
    public CardDoctorPage clickButtonOpenCardDoctor() {
        OPEN_CARD_DOCTOR.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new CardDoctorPage();
    }

    @Step("Нажать на кнопку публикации")
    public void clickButtonPublicationFeedback() {
        PUBLICATION_BUTTON.shouldBe(Condition.visible)
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
