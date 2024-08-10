package pages.DoctorsPage.CardDoctorPage;

import com.codeborne.selenide.*;
import pages.BasePage.BasePage;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

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

    @Step("Верифицировать раздел фото")
    public void verifyDoctorCardPage() {
        RETURN_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DOCTOR_PHOTO.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EDIT_PHOTO_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_PHOTO_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADD_SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADD_FEEDBACK.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }


    @Step("Нажать кнопку изменения фотографии")
    public EditPhotoDoctorWindow clickButtonEditPhoto() {
        EDIT_PHOTO_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new EditPhotoDoctorWindow();
    }

    @Step("Нажать кнопку удаления фотографии")
    public void clickButtonDeletePhoto() {
        DELETE_PHOTO_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Получить ссылку на файл")
    public String getSrcPhoto() {
        DOCTOR_PHOTO.shouldBe(Condition.exist);
        return DOCTOR_PHOTO.getAttribute("src");
    }

    @Step("Получить фотографию")
    public SelenideElement getPhoto() {
        return DOCTOR_PHOTO;
    }

    @Step("Нажать кнопку добавления информации о враче")
    public AddInfoDoctorWindow clickButtonAddSection() {
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
        SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return new Section();
    }

    @Step("Получить блок описания к разделу")
    public Description getDescription() {
        DESCRIPTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return new Description();
    }

    @Step("Проверить отображение информации о пустом списке разделов")
    public boolean isExistsEmptyListSection() {
        return EMPTY_LIST_SECTION.isDisplayed();
    }

    @Step("Получить информацию о пустом списке разделов")
    public SelenideElement getEmptyListSection() {
        return EMPTY_LIST_SECTION;
    }

    @Step("Проверить отображение информации о пустом списке описаний к разделу")
    public boolean isExistsEmptyListDescription() {
        return EMPTY_LIST_DESCRIPTION.isDisplayed();
    }

    @Step("Получить информацию о пустом списке описаний к разделу")
    public SelenideElement getEmptyListDescription() {
        return EMPTY_LIST_DESCRIPTION;
    }

    @Step("Нажать радиобаттон неопубликованных отзывов")
    public void clickButtonUnpublishedFeedback() {
        NO_SELECTED_UNPUBLISHED_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Проверить выбранный радиобаттон опубликованных отзывов")
    public void checkSelectPublishedFeedback() {
        SELECTED_PUBLISHED_BUTTON.shouldBe(Condition.visible);
    }

    @Step("Проверить выбранный радиобаттон неопубликованных отзывов")
    public void checkSelectUnpublishedFeedback() {
        SELECTED_UNPUBLISHED_BUTTON.shouldBe(Condition.visible);
    }

    @Step("Проверить невыбранный радиобаттон опубликованных отзывов")
    public void checkNoSelectPublishedFeedback() {
        NO_SELECTED_PUBLISHED_BUTTON.shouldBe(Condition.visible);
    }

    @Step("Проверить невыбранный радиобаттон неопубликованных отзывов")
    public void checkNoSelectUnpublishedFeedback() {
        NO_SELECTED_UNPUBLISHED_BUTTON.shouldBe(Condition.visible);
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
    public AddFeedbackWindow clickButtonAddFeedback() {
        ADD_FEEDBACK.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new AddFeedbackWindow();
    }

    @Step("Получить отзыв")
    public Feedback getFeedback() {
        FEEDBACK.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return new Feedback();
    }

    @Step("Проверить отображение отзыва")
    public boolean isExistFeedback() {
        return FEEDBACK.isDisplayed();
    }

    @Step("Нажать кнопку возврата к списку докторов")
    public void clickButtonComebackDoctorsPage() {
        RETURN_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Проверить отображение кнопки сортировки отзывов от новых к старым")
    public boolean isSortingNewAppear() {
        return SORTING_FEEDBACK_NEW.isDisplayed();
    }

    @Step("Нажать кнопку сортировки отзывов от старых к новым")
    public void clickSortingFeedbackNew() {
        SORTING_FEEDBACK_NEW.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Проверить отображение кнопки сортировки отзывов от старых к новым")
    public boolean isSortingOldAppear() {
        return SORTING_FEEDBACK_OLD.isDisplayed();
    }

    @Step("Нажать кнопку сортировки отзывов от новых к старым")
    public void clickSortingFeedbackOld() {
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

    @SuppressWarnings("unused")
    @Step("Получить секцию по имени")
    public SelenideElement getSectionPageByName(String nameSection){
        return $x("//div[@class='S1TV']/span[text()='" + nameSection + "']");
    }
}

