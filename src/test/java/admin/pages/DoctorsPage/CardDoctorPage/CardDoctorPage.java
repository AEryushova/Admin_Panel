package admin.pages.DoctorsPage.CardDoctorPage;

import admin.pages.BasePage.BasePage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;


import static com.codeborne.selenide.Selenide.$x;

public class CardDoctorPage extends BasePage {

    private final SelenideElement RETURN_BUTTON = $x("//span[text()='Вернуться назад']");
    private final SelenideElement DOCTOR_PHOTO = $x("//div[@class='zzfM']/img");
    private final SelenideElement EDIT_PHOTO_BUTTON = $x("//div[@class='ctFG']/div");
    private final SelenideElement DELETE_PHOTO_BUTTON = $x("//div[@class='ctFG']/div/following-sibling::div");
    private final SelenideElement ADD_SECTION = $x("//button[text()='Добавить раздел']");
    private final SelenideElement SECTION = $x("//div[@class='aksW']");
    private final SelenideElement DESCRIPTION = $x("//div[@class='IrCo']");
    private final SelenideElement ADD_FEEDBACK = $x("//button[text()='Добавить отзыв']");
    private final SelenideElement NO_SELECTED_PUBLISHED_BUTTON = $x("//span[text()='Опубликованные']/preceding-sibling::div");
    private final SelenideElement SELECTED_PUBLISHED_BUTTON = $x("//span[text()='Опубликованные']/preceding-sibling::div/div[@class='WzjF']");
    private final SelenideElement NO_SELECTED_UNPUBLISHED_BUTTON = $x("//span[text()='Неопубликованные']/preceding-sibling::div");
    private final SelenideElement SELECTED_UNPUBLISHED_BUTTON = $x("//span[text()='Неопубликованные']/preceding-sibling::div/div[@class='WzjF']");
    private final SelenideElement FEEDBACK = $x("//div[@class='qJe_']");
    private final SelenideElement SORTING_FEEDBACK_NEW = $x("//span[text()='Новые']//parent::div//parent::button");
    private final SelenideElement SORTING_FEEDBACK_OLD = $x("//span[text()='Старые ']//parent::div//parent::button");
    private final SelenideElement EMPTY_LIST_SECTION = $x("//span[text()='Описание в карточке отсутствует!']");
    private final SelenideElement EMPTY_LIST_DESCRIPTION = $x("//div[text()='Пустой список']");
    private final SelenideElement NAVIGATE_MENU = $x("//div[@class='_odc']");

    @Step("Верифицировать карточку врача")
    public void cardDoctorPage() {
        RETURN_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DOCTOR_PHOTO.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EDIT_PHOTO_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_PHOTO_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADD_SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADD_FEEDBACK.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Нажать кнопку изменения фотографии")
    public EditPhotoDoctorWindow openWindowEditPhoto() {
        EDIT_PHOTO_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new EditPhotoDoctorWindow();
    }

    @Step("Нажать кнопку удаления фотографии")
    public void deletePhoto() {
        DELETE_PHOTO_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Получить ссылку на файл")
    public String getSrcPhoto() {
        return DOCTOR_PHOTO.getAttribute("src");
    }

    @Step("Нажать кнопку добавления информации о враче")
    public AddInfoDoctorWindow openWindowAddSection() {
        ADD_SECTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new AddInfoDoctorWindow();
    }

    @Step("Проверить отображение раздела")
    public boolean isExistSection() {
        return SECTION.isDisplayed();
    }

    @Step("Проверить отображение описания к разделу")
    public boolean isExistDescription() {
        return DESCRIPTION.isDisplayed();
    }

    @Step("Получить блок раздела")
    public Section getSection() {
        SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.exist, Duration.ofSeconds(5));
        return new Section();
    }

    @Step("Получить блок описания к разделу")
    public Description getDescription() {
        DESCRIPTION.shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.exist, Duration.ofSeconds(5));
        return new Description();
    }

    @Step("Проверить отображение информации о пустом списке разделов")
    public boolean isExistsEmptyListSection() {
        return EMPTY_LIST_SECTION.isDisplayed();
    }

    @Step("Проверить отображение информации о пустом списке описаний к разделу")
    public boolean isExistsEmptyListDescription() {
        return EMPTY_LIST_DESCRIPTION.isDisplayed();
    }

    @Step("Нажать радиобаттон неопубликованных отзывов")
    public void switchUnpublishedFeedback() {
        NO_SELECTED_UNPUBLISHED_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Проверить выбранный радиобаттон опубликованных отзывов")
    public void selectedPublishedFeedback() {
        SELECTED_PUBLISHED_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
    }

    @Step("Проверить выбранный радиобаттон неопубликованных отзывов")
    public void selectedUnpublishedFeedback() {
        SELECTED_UNPUBLISHED_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
    }

    @Step("Проверить невыбранный радиобаттон опубликованных отзывов")
    public void noSelectedPublishedFeedback() {
        NO_SELECTED_PUBLISHED_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
    }

    @Step("Проверить невыбранный радиобаттон неопубликованных отзывов")
    public void noSelectedUnpublishedFeedback() {
        NO_SELECTED_UNPUBLISHED_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
    }

    @Step("Проверить отображение выбранного радиобаттона опубликованных отзывов")
    public boolean isSelectPublishedFeedback() {
        return SELECTED_PUBLISHED_BUTTON.isDisplayed();
    }

    @Step("Проверить отображение выбранного радиобаттона неопубликованных отзывов")
    public boolean isSelectUnPublishedFeedback() {
        return SELECTED_UNPUBLISHED_BUTTON.isDisplayed();
    }

    @Step("Нажать кнопку добавления отзыва")
    public AddFeedbackWindow openWindowAddFeedback() {
        ADD_FEEDBACK.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new AddFeedbackWindow();
    }

    @Step("Получить отзыв")
    public Feedback getFeedback() {
        FEEDBACK.shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.exist, Duration.ofSeconds(5));
        return new Feedback();
    }

    @Step("Проверить отображение отзыва")
    public boolean isExistFeedback() {
        return FEEDBACK.isDisplayed();
    }

    @Step("Нажать кнопку возврата к списку докторов")
    public void comebackDoctorsPage() {
        RETURN_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Проверить отображение кнопки сортировки отзывов от новых к старым")
    public boolean isSortingNewAppear() {
        return SORTING_FEEDBACK_NEW.isDisplayed();
    }

    @Step("Нажать кнопку сортировки отзывов от старых к новым")
    public void sortingFeedbackNew() {
        SORTING_FEEDBACK_NEW.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Проверить отображение кнопки сортировки отзывов от старых к новым")
    public boolean isSortingOldAppear() {
        return SORTING_FEEDBACK_OLD.isDisplayed();
    }

    @Step("Нажать кнопку сортировки отзывов от новых к старым")
    public void sortingFeedbackOld() {
        SORTING_FEEDBACK_OLD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Навести курсор мыши на навигационное меню")
    public NavigateMenu openNavigateMenu() {
        NAVIGATE_MENU.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .hover();
        return new NavigateMenu();
    }


}

