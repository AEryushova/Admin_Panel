package ru.adminlk.clinica.pages.DoctorsPage;

import ru.adminlk.clinica.pages.BasePage.BasePage;
import ru.adminlk.clinica.pages.CardDoctorPage.CardDoctorPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class DoctorsPage extends BasePage {

    private final SelenideElement
            TAB_NAME = $x("//div[@class='wYqZ']/span[text()='Врачи']"),
            SEARCH_DOCTOR = $x("//input[@placeholder='Поиск по врачам']"),
            CARD_DOCTOR = $x("//div[@class='eF30']"),
            DROP_DOWN_PHOTO = $x("//span[text()='Фотография']/following-sibling::button[@class='MxFR']"),
            OPTION_ALL_PHOTO = $x("//div[@data-locator='all-photos']"),
            OPTION_NO_PHOTO = $x("//div[@data-locator='empty-photos']"),
            OPTION_YES_PHOTO = $x("//div[@data-locator='photos']"),
            DROP_DOWN_DESCRIPTION=$x("//span[text()='Описание']/following-sibling::button[@class='MxFR']"),
            OPTION_ALL_DESCRIPTION = $x("//div[@data-locator='all-description']"),
            OPTION_NO_DESCRIPTION = $x("//div[@data-locator='empty-photos']"),
            OPTION_YES_DESCRIPTION = $x("//div[@data-locator='description']"),
            SHOW_UNPUBLISHED_FEEDBACKS_LIST = $x("//button[text()='Показать']"),
            COUNT_DOCTORS = $x("//div[@class='wYqZ']/span[2]"),
            COUNT_UNPUBLISHED_FEEDBACKS = $x("//span[text()='Неопубликованные отзывы:']/span");
    private final ElementsCollection
            NAMES_DOCTORS = $$x("//div[@class='eF30']/div[@class='jPnI']/span[1]"),
            SPECIALIZATIONS_DOCTORS = $$x("//div[@class='eF30']/div[@class='jPnI']/span[2]"),
            PHOTO_DOCTORS = $$x("//div[@class='eF30']/div[@class='Uw0W']/img");

    @Step("Верифицировать страницу Врачи")
    public void verifyDoctorsPage() {
        TAB_NAME.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SEARCH_DOCTOR.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DROP_DOWN_PHOTO.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DROP_DOWN_DESCRIPTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CARD_DOCTOR.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SHOW_UNPUBLISHED_FEEDBACKS_LIST.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Нажать на кнопку редактирования информации о враче со специализацией '{0}' и именем '{1}'")
    public CardDoctorPage clickButtonEditInfoDoctor(String specialization, String doctorName) {
        SelenideElement edit_button = searchCardDoctor(specialization, doctorName);
        edit_button.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new CardDoctorPage();
    }

    @Step("Получить кнопку редактирования карточки врача по имени '{1}' и специализации '{0}' ")
    public SelenideElement searchCardDoctor(String specialization, String doctorName) {
        return $x("//span[text()='" + specialization + "']/preceding-sibling::span[text()='" + doctorName + "'] /parent::div/following-sibling::button[text()='Редактировать']");
    }

    @Step("Нажать кнопку просмотра неопубликованных отзывов")
    public UnpublishedFeedbacksWindow clickShowUnpublishedFeedbacks() {
        SHOW_UNPUBLISHED_FEEDBACKS_LIST.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new UnpublishedFeedbacksWindow();
    }

    @Step("Нажать на кнопку сортировки врачей без фотографии")
    public void clickSortingPhotoNo() {
        DROP_DOWN_PHOTO.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        OPTION_NO_PHOTO.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать на кнопку сортировки врачей с фотографией")
    public void clickSortingPhotoYes() {
        DROP_DOWN_PHOTO.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        OPTION_YES_PHOTO.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать на кнопку сортировки всех врачей по фотографии")
    public void clickSortingPhotoAll() {
        DROP_DOWN_PHOTO.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        OPTION_ALL_PHOTO.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @SuppressWarnings("unused")
    @Step("Нажать на кнопку сортировки врачей без описания")
    public void clickSortingDescriptionNo() {
        DROP_DOWN_DESCRIPTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        OPTION_NO_DESCRIPTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @SuppressWarnings("unused")
    @Step("Нажать на кнопку сортировки врачей с описанием")
    public void clickSortingDescriptionYes() {
        DROP_DOWN_DESCRIPTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        OPTION_YES_DESCRIPTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @SuppressWarnings("unused")
    @Step("Нажать на кнопку сортировки всех врачей по описанию")
    public void clickSortingDescriptionAll() {
        DROP_DOWN_DESCRIPTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        OPTION_ALL_DESCRIPTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Ввести в поле поиска '{0}'")
    public void searchDoctor(String textSearch) {
        SEARCH_DOCTOR.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        for (char c : textSearch.toCharArray()) {
            SEARCH_DOCTOR.sendKeys(String.valueOf(c));
            Selenide.sleep(1000);
        }
    }

    @Step("Получить имена врачей")
    public ElementsCollection getNamesDoctors() {
        return NAMES_DOCTORS;
    }

    @Step("Получить специализации врачей")
    public ElementsCollection getSpecializationDoctors() {
        return SPECIALIZATIONS_DOCTORS;
    }

    @Step("Получить ссылки на фотографии врачей")
    public List<String> getPhotoDoctorsAttributes() {
        return new ArrayList<>(PHOTO_DOCTORS)
                .stream()
                .map(element -> element.getAttribute("src"))
                .collect(Collectors.toList());
    }

    @Step("Очистить поле поиска")
    public void clearSearchField() {
        SEARCH_DOCTOR.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .sendKeys(Keys.CONTROL, "a");
        SEARCH_DOCTOR.sendKeys(Keys.BACK_SPACE);
    }

    @Step("Получить значение поля поиска")
    public String getValueSearchField() {
        SEARCH_DOCTOR.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return SEARCH_DOCTOR.getValue();
    }

    @Step("Получить количество врачей")
    public int getCountDoctors() {
        COUNT_DOCTORS.shouldBe(Condition.visible);
        return Integer.parseInt(COUNT_DOCTORS.getText().split(" ")[0]);
    }

    @Step("Получить количество неопубликованных отзывов")
    public String getCountUnpublishedFeedback() {
        COUNT_UNPUBLISHED_FEEDBACKS.shouldBe(Condition.visible);
        return COUNT_UNPUBLISHED_FEEDBACKS.getText();
    }

}
