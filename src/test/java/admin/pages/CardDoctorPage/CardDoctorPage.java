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
    private final SelenideElement DELETE_PHOTO_BUTTON = $x("//div[@class='ctFG']/div/following-sibling::div");
    private final SelenideElement ADD_SECTION = $x("//button[text()='Добавить раздел']");
    private final SelenideElement SECTION =$x("//div[@class='aksW']");
    private final SelenideElement DESCRIPTION =$x("//div[@class='IrCo']");
    private final SelenideElement ADD_FEEDBACK = $x("//button[text()='Добавить отзыв']");
    private final SelenideElement NO_SELECTED_PUBLISHED_BUTTON=$x("//span[text()='Опубликованные']/preceding-sibling::div");
    private final SelenideElement SELECTED_PUBLISHED_BUTTON=$x("//span[text()='Опубликованные']/preceding-sibling::div/div[@class='WzjF']");
    private final SelenideElement NO_SELECTED_UNPUBLISHED_BUTTON=$x("//span[text()='Неопубликованные']/preceding-sibling::div");
    private final SelenideElement SELECTED_UNPUBLISHED_BUTTON=$x("//span[text()='Неопубликованные']/preceding-sibling::div/div[@class='WzjF']");
    private final SelenideElement FEEDBACK=$x("//div[@class='qJe_']");
    private final SelenideElement SORTING_FEEDBACK = $x("//span[text()='Новые']//parent::div//parent::button");
    private final SelenideElement EMPTY_LIST_SECTION =$x("//span[text()='Описание в карточке отсутствует!']");
    private final SelenideElement EMPTY_LIST_DESCRIPTION =$x("//div[text()='Пустой список']");



    private final SelenideElement PUBLISHED_CHECKBOX = $x("//span[text()='Опубликованные']/preceding-sibling::div/div[@class='WzjF']");
    private final SelenideElement UNPUBLISHED_CHECKBOX = $x("//span[text()='Неопубликованные']/preceding-sibling::div/div[@class='WzjF']");
    private final SelenideElement SWITCH_PUBLISHED_BUTTON = $x("//span[text()='Опубликованные']//parent::div");
    private final SelenideElement SWITCH_UNPUBLISHED_BUTTON = $x("//span[text()='Опубликованные']//parent::div");


    public void cardDoctorPage() {
        COMEBACK_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DOCTOR_PHOTO.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EDIT_PHOTO_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_PHOTO_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADD_SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADD_FEEDBACK.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public EditPhotoDoctorWindow openWindowEditPhoto() {
        EDIT_PHOTO_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new EditPhotoDoctorWindow();
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

    public boolean isExistSection() {
        return SECTION.exists();
    }

    public boolean isExistDescription() {
        return DESCRIPTION.exists();
    }
    public Section getSection() {
        SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return new Section();
    }

    public Description getDescription() {
        DESCRIPTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return new Description();
    }

    public boolean isExistsEmptyListSection(){
        return EMPTY_LIST_SECTION.exists();
    }

    public boolean isExistsEmptyListDescription(){
        return EMPTY_LIST_DESCRIPTION.exists();
    }

    public void switchPublishedFeedback() {
        NO_SELECTED_PUBLISHED_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();

    }

    public void switchUnpublishedFeedback() {
        NO_SELECTED_UNPUBLISHED_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void selectedPublishedFeedback(){
        SELECTED_PUBLISHED_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
    }

    public void selectedUnpublishedFeedback(){
        SELECTED_UNPUBLISHED_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
    }

    public void noSelectedPublishedFeedback(){
        NO_SELECTED_PUBLISHED_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
    }

    public void noSelectedUnpublishedFeedback(){
        NO_SELECTED_UNPUBLISHED_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
    }

    public boolean isSelectPublishedFeedback(){
        return SELECTED_PUBLISHED_BUTTON.exists();
    }


    public boolean isSelectUnPublishedFeedback(){
        return SELECTED_UNPUBLISHED_BUTTON.exists();
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

    public boolean isExistFeedback() {
        return FEEDBACK.exists();
    }

    public DoctorsPage comebackDoctorsPage() {
        COMEBACK_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new DoctorsPage();
    }

    public boolean isSortingAppear() {
        return SORTING_FEEDBACK.exists();
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


}

