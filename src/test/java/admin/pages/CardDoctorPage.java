package admin.pages;

import admin.pages.modalWindowDoctors.*;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.$x;

public class CardDoctorPage {

    private final SelenideElement comebackButton=$x("//span[text()='Вернуться назад']");
    private final SelenideElement doctorPhoto=$x("//div[@class='zzfM']/img");
    private final SelenideElement editPhoto=$x("//div[@class='ctFG']/div[1]");
    private final SelenideElement deletePhoto=$x("//div[@class='ctFG']/div[2]");
    private final SelenideElement addSection=$x("//button[text()='Добавить раздел']");
    private final SelenideElement addFeedback=$x("//button[text()='Добавить отзыв']");
    private final SelenideElement publishedCheckbox=$x("//span[text()='Опубликованные']/preceding-sibling::div/div[@class='WzjF']");
    private final SelenideElement unpublishedCheckbox=$x("//span[text()='Неопубликованные']/preceding-sibling::div/div[@class='WzjF']");
    private final SelenideElement switchPublishedButton=$x("//span[text()='Опубликованные']//parent::div");
    private final SelenideElement switchUnpublishedButton=$x("//span[text()='Опубликованные']//parent::div");
    private final SelenideElement sortingFeedback=$x("//span[text()='Новые']//parent::div//parent::button");
    private final SelenideElement returnToStartButton = $x("//div[@class='_x1E']");
    private final SelenideElement footerDoctorsPage = $x("//span[text()='@ Самарский государственный медицинский университет']");
    private final SelenideElement notification = $x("//div[@role='alert']/div//following-sibling::div");
    private final SelenideElement closeNotification = $x("//button[@aria-label='close']");

    public void cardDoctorPage(){
        comebackButton.shouldBe(Condition.visible, Duration.ofSeconds(10));
        doctorPhoto.shouldBe(Condition.visible, Duration.ofSeconds(10));
        editPhoto.shouldBe(Condition.visible, Duration.ofSeconds(10));
        deletePhoto.shouldBe(Condition.visible, Duration.ofSeconds(10));
        addSection.shouldBe(Condition.visible, Duration.ofSeconds(10));
        addFeedback.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    public void unpublishedCheckbox(){
        sortingFeedback.shouldBe(Condition.visible, Duration.ofSeconds(10));
        unpublishedCheckbox.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    public void publishedCheckbox(){
        sortingFeedback.shouldBe(Condition.hidden, Duration.ofSeconds(10));
        publishedCheckbox.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    public EditPhotoDoctorWindow openWindowEditPhoto(){
        editPhoto.click();
        return new EditPhotoDoctorWindow();
    }

    public void deletePhoto(){
        deletePhoto.click();
    }

    public String getSrcPhoto(){
        return doctorPhoto.getAttribute("src");
    }

    public AddIntelligenceWindow openWindowAddSection(){
        addSection.click();
        return new AddIntelligenceWindow();
    }

    public Section getSection(){
        return new Section();
    }

    public Description getDescription(){
        return new Description();
    }

    public AddFeedbackWindow openWindowAddFeedback() {
        addFeedback.click();
        return new AddFeedbackWindow();
    }

    public Feedback getFeedback() {
        return new Feedback();
    }

    public void switchPublishedFeedback(){
        switchPublishedButton.click();
    }

    public void switchUnpublishedFeedback(){
        switchUnpublishedButton.click();
    }


    public String getNotification() {
        notification.shouldBe(Condition.visible);
        return notification.getText();
    }

    public void closeNotification() {
        closeNotification.click();
        notification.shouldBe(Condition.hidden);
    }

    public DoctorsPage comebackDoctorsPage(){
        comebackButton.click();
        return new DoctorsPage();
    }

    public void scrollPageToBottom() {
        footerDoctorsPage.scrollTo();
    }

    public void returnToStartPage() {
        returnToStartButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        returnToStartButton.click();
    }

    public void returnButtonDisappears(){
        returnToStartButton.shouldBe(Condition.hidden, Duration.ofSeconds(5));
    }






}

