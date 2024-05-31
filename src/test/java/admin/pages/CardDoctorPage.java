package admin.pages;

import admin.pages.modalWindowDoctors.*;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.$x;

public class CardDoctorPage extends BasePage {

    private final SelenideElement comebackButton = $x("//span[text()='Вернуться назад']");
    private final SelenideElement doctorPhoto = $x("//div[@class='zzfM']/img");
    private final SelenideElement editPhoto = $x("//div[@class='ctFG']/div[1]");
    private final SelenideElement deletePhoto = $x("//div[@class='ctFG']/div[2]");
    private final SelenideElement addSection = $x("//button[text()='Добавить раздел']");
    private final SelenideElement addFeedback = $x("//button[text()='Добавить отзыв']");
    private final SelenideElement publishedCheckbox = $x("//span[text()='Опубликованные']/preceding-sibling::div/div[@class='WzjF']");
    private final SelenideElement unpublishedCheckbox = $x("//span[text()='Неопубликованные']/preceding-sibling::div/div[@class='WzjF']");
    private final SelenideElement switchPublishedButton = $x("//span[text()='Опубликованные']//parent::div");
    private final SelenideElement switchUnpublishedButton = $x("//span[text()='Опубликованные']//parent::div");
    private final SelenideElement sortingFeedback = $x("//span[text()='Новые']//parent::div//parent::button");


    public void cardDoctorPage() {
        comebackButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        doctorPhoto.shouldBe(Condition.visible, Duration.ofSeconds(5));
        editPhoto.shouldBe(Condition.visible, Duration.ofSeconds(5));
        deletePhoto.shouldBe(Condition.visible, Duration.ofSeconds(5));
        addSection.shouldBe(Condition.visible, Duration.ofSeconds(5));
        addFeedback.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void toggleUnpublishedCheckbox() {
        unpublishedCheckbox.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        if (!unpublishedCheckbox.isSelected()) {
            unpublishedCheckbox.click();
        }
    }

    public void togglePublishedCheckbox() {
        publishedCheckbox.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        if (!publishedCheckbox.isSelected()) {
            publishedCheckbox.click();
        }
    }

    public EditPhotoDoctorWindow openWindowEditPhoto() {
        editPhoto.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new EditPhotoDoctorWindow();
    }

    public boolean isSortingAppear() {
        return sortingFeedback.exists();
    }

    public void deletePhoto() {
        deletePhoto.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public String getSrcPhoto() {
        doctorPhoto.exists();
        return doctorPhoto.getAttribute("src");
    }

    public AddIntelligenceWindow openWindowAddSection() {
        addSection.shouldBe(Condition.visible)
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
        addFeedback.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new AddFeedbackWindow();
    }

    public Feedback getFeedback() {
        return new Feedback();
    }

    public void switchPublishedFeedback() {
        switchPublishedButton.click();
    }

    public void switchUnpublishedFeedback() {
        switchUnpublishedButton.click();
    }


    public DoctorsPage comebackDoctorsPage() {
        comebackButton.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new DoctorsPage();
    }


}

