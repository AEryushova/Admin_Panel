package admin.pages.CardDoctorPage;

import admin.pages.BasePage.BasePage;
import admin.pages.DoctorsPage.DoctorsPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.$x;

public class CardDoctorPage extends BasePage {

    private final SelenideElement COMEBACK_BUTTON = $x("//span[text()='Вернуться назад']");
    private final SelenideElement DOCTOR_PHOTO = $x("//div[@class='zzfM']/img");
    private final SelenideElement EDIT_PHOTO_BUTTON = $x("//div[@class='ctFG']/div");
    private final SelenideElement DELETE_PHOTO_BUTTON = $x("//div[@class='ctFG']/div");
    private final SelenideElement ADD_SECTION = $x("//button[text()='Добавить раздел']");
    private final SelenideElement ADD_FEEDBACK = $x("//button[text()='Добавить отзыв']");
    private final SelenideElement PUBLISHED_CHECKBOX = $x("//span[text()='Опубликованные']/preceding-sibling::div/div[@class='WzjF']");
    private final SelenideElement UNPUBLISHED_CHECKBOX = $x("//span[text()='Неопубликованные']/preceding-sibling::div/div[@class='WzjF']");
    private final SelenideElement SWITCH_PUBLISHED_BUTTON = $x("//span[text()='Опубликованные']//parent::div");
    private final SelenideElement SWITCH_UNPUBLISHED_BUTTON = $x("//span[text()='Опубликованные']//parent::div");
    private final SelenideElement SORTING_FEEDBACK = $x("//span[text()='Новые']//parent::div//parent::button");


    public void cardDoctorPage() {
        COMEBACK_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DOCTOR_PHOTO.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EDIT_PHOTO_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_PHOTO_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADD_SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADD_FEEDBACK.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void toggleUnpublishedCheckbox() {
        UNPUBLISHED_CHECKBOX.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        if (!UNPUBLISHED_CHECKBOX.isSelected()) {
            UNPUBLISHED_CHECKBOX.click();
        }
    }

    public void togglePublishedCheckbox() {
        PUBLISHED_CHECKBOX.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        if (!PUBLISHED_CHECKBOX.isSelected()) {
            PUBLISHED_CHECKBOX.click();
        }
    }

    public EditPhotoDoctorWindow openWindowEditPhoto() {
        EDIT_PHOTO_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new EditPhotoDoctorWindow();
    }

    public boolean isSortingAppear() {
        return SORTING_FEEDBACK.exists();
    }

    public void deletePhoto() {
        DELETE_PHOTO_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public String getSrcPhoto() {
        DOCTOR_PHOTO.exists();
        return DOCTOR_PHOTO.getAttribute("src");
    }

    public AddIntelligenceWindow openWindowAddSection() {
        ADD_SECTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new AddIntelligenceWindow();
    }

    public Section getSection() {
        return new Section();
    }

    public Description getDescription() {
        return new Description();
    }

    public AddFeedbackWindow openWindowAddFeedback() {
        ADD_FEEDBACK.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new AddFeedbackWindow();
    }

    public Feedback getFeedback() {
        return new Feedback();
    }

    public void switchPublishedFeedback() {
        SWITCH_PUBLISHED_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
        .click();
    }

    public void switchUnpublishedFeedback() {
        SWITCH_UNPUBLISHED_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
        .click();
    }


    public DoctorsPage comebackDoctorsPage() {
        COMEBACK_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new DoctorsPage();
    }


}

