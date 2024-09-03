package test;


import org.junit.jupiter.api.extension.ExtendWith;
import pages.CardDoctorPage.CardDoctorPage;
import com.codeborne.selenide.*;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import pages.CardDoctorPage.Feedback;
import pages.DoctorsPage.UnpublishedFeedback;
import pages.DoctorsPage.UnpublishedFeedbacksWindow;
import utils.preparationDataTests.doctors.AddDeleteFeedbackDecorator;

import java.util.List;
import static data.TestData.DataTest.*;
import static data.TestData.UserData.*;
import static data.TestData.DataSearch.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Врачи")
@DisplayName("Страница Врачи")
public class DoctorPageTest extends BaseTest {

    @BeforeAll
    static void setUpAuth() {
        authAdminPanel(LOGIN_ADMIN, PASSWORD_ADMIN);
    }

    @BeforeEach
    void setUp() {
        openAdminPanel();
        doctorsPage.verifyDoctorsPage();
    }

    @AfterEach()
    void closeWebDriver() {
        Selenide.closeWebDriver();
    }


    @Feature("Открытие карточки врача")
    @Story("Открытие карточки врача и возврат к списку врачей")
    @DisplayName("Открытие карточки врача и возврат к списку врачей")
    @Test
    void returnToDoctorsPageFromCardDoctorPage() {
        doctorsPage.scrollToCard(doctorsPage.searchCardDoctor(DOCTOR_SPECIALIZATION, DOCTOR));
        CardDoctorPage cardDoctor = doctorsPage.clickButtonEditInfoDoctor(DOCTOR_SPECIALIZATION,DOCTOR);
        cardDoctor.scrollPageUp("500");
        assertEquals(DOCTOR,cardDoctor.getNameDoctor());
        cardDoctor.clickButtonComebackDoctorsPage();
        doctorsPage.verifyDoctorsPage();
    }

    @Feature("Обработка неопубликованных отзывов")
    @Story("Просмотр неопубликованного отзыва в карточке врача")
    @DisplayName("Просмотр неопубликованного отзыва в карточке врача")
    @ExtendWith(AddDeleteFeedbackDecorator.class)
    @Test
    void openUnpublishedFeedbackInCardDoctor() {
        UnpublishedFeedbacksWindow unpublishedFeedbacksWindow=doctorsPage.clickShowUnpublishedFeedbacks();
        unpublishedFeedbacksWindow.verifyUnpublishedFeedbacksWindow();
        UnpublishedFeedback unpublishedFeedback= unpublishedFeedbacksWindow.getUnpublishedFeedback();
        unpublishedFeedback.verifyUnpublishedFeedback();
        String nameDoctor=unpublishedFeedback.getNameDoctorFeedback();
        String nameAuthor=unpublishedFeedback.getNameAuthorFeedback();
        String dateFeedback=unpublishedFeedback.getDateFeedback();
        String textFeedback=unpublishedFeedback.getTextFeedback();
        CardDoctorPage cardDoctor=unpublishedFeedback.clickButtonOpenCardDoctor();
        assertEquals(nameDoctor,cardDoctor.getNameDoctor());
        cardDoctor.scrollPageDown("500");
        cardDoctor.clickButtonUnpublishedFeedback();
        Feedback feedback= cardDoctor.getFeedback();
        assertEquals(nameAuthor,feedback.getNameAuthorFeedback());
        assertEquals(dateFeedback, feedback.getDateFeedback());
        assertEquals(textFeedback,feedback.getTextFeedback());
    }

    @Feature("Поиск по врачам")
    @Story("Поиск врачей по имени")
    @DisplayName("Поиск врачей по имени")
    @Test
    void searchNameDoctor() {
        int countAllDoctors = doctorsPage.getCountDoctors();
        doctorsPage.searchDoctor(DOCTOR_NAME_SEARCH.toLowerCase());
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        ElementsCollection namesDoctors = doctorsPage.getNamesDoctors();
        namesDoctors.shouldHave(CollectionCondition.size(countResult));
        for (SelenideElement nameDoctor : namesDoctors) {
            assertThat(nameDoctor.getText().toLowerCase(), containsString(DOCTOR_NAME_SEARCH.toLowerCase()));
        }
        assertTrue(countResult < countAllDoctors);
    }

    @Feature("Поиск по врачам")
    @Story("Поиск врачей по специальности")
    @DisplayName("Поиск врачей по специальности")
    @Test
    void searchSpecializationDoctor() {
        int countAllDoctors = doctorsPage.getCountDoctors();
        doctorsPage.searchDoctor(DOCTOR_SPECIALIZATION_SEARCH);
        Selenide.sleep(5000);
        doctorsPage.scrollPageDown("3000");
        Selenide.sleep(5000);
        doctorsPage.scrollPageDown("3000");
        int countResult = doctorsPage.getCountDoctors();
        ElementsCollection specializationDoctors = doctorsPage.getSpecializationDoctors();
        specializationDoctors.shouldHave(CollectionCondition.size(countResult));
        for (SelenideElement specializationDoctor : specializationDoctors) {
            assertThat(specializationDoctor.getText().toLowerCase(), containsString(DOCTOR_SPECIALIZATION_SEARCH.toLowerCase()));
        }
        assertTrue(countResult < countAllDoctors);
    }

    @Feature("Поиск по врачам")
    @Story("Поиск врачей по включению")
    @DisplayName("Поиск врачей по включению")
    @Test
    void searchByInclusion() {
        int countAllDoctors = doctorsPage.getCountDoctors();
        doctorsPage.searchDoctor(SEARCH_BY_INCLUSION_DOCTORS);
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        ElementsCollection namesDoctors = doctorsPage.getNamesDoctors();
        ElementsCollection specializationDoctors = doctorsPage.getSpecializationDoctors();
        namesDoctors.shouldHave(CollectionCondition.size(countResult));
        specializationDoctors.shouldHave(CollectionCondition.size(countResult));
        for (int i = 0; i < namesDoctors.size(); i++) {
            String nameDoctor = namesDoctors.get(i).getText();
            String specializationDoctor = specializationDoctors.get(i).getText();
            boolean isNameFound = nameDoctor.toLowerCase().contains(SEARCH_BY_INCLUSION_DOCTORS.toLowerCase());
            boolean isSpecializationFound = specializationDoctor.toLowerCase().contains(SEARCH_BY_INCLUSION_DOCTORS.toLowerCase());
            assertTrue(isNameFound || isSpecializationFound);
        }
        assertTrue(countResult < countAllDoctors);
    }

    @Feature("Поиск по врачам")
    @Story("Сброс поискового результата врачей после очистки поля")
    @DisplayName("Сброс поискового результата врачей после очистки поля")
    @Test
    void resetSearchResultDoctors() {
        int countAllDoctors = doctorsPage.getCountDoctors();
        doctorsPage.searchDoctor(DOCTOR_NAME_SEARCH);
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        ElementsCollection namesDoctorsSearch = doctorsPage.getNamesDoctors();
        int resultSearch = namesDoctorsSearch.size();
        doctorsPage.clearSearchField();
        Selenide.sleep(5000);
        int countAllDoctorsAfterReset = doctorsPage.getCountDoctors();
        ElementsCollection nameDoctorsAll = doctorsPage.getNamesDoctors();
        int allDoctors = nameDoctorsAll.size();
        assertEquals("", doctorsPage.getValueSearchField());
        assertTrue(resultSearch < allDoctors);
        assertTrue(countResult < countAllDoctorsAfterReset);
        assertEquals(countAllDoctors, countAllDoctorsAfterReset);
    }

    @Feature("Поиск по врачам")
    @Story("Поиск врачей по значению в верхнем регистре")
    @DisplayName("Поиск врачей по значению в верхнем регистре")
    @Test
    void searchHighRegister() {
        int countAllDoctors = doctorsPage.getCountDoctors();
        doctorsPage.searchDoctor(DOCTOR_NAME_SEARCH.toUpperCase());
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        ElementsCollection namesDoctors = doctorsPage.getNamesDoctors();
        namesDoctors.shouldHave(CollectionCondition.size(countResult));
        for (SelenideElement nameDoctor : namesDoctors) {
            assertThat(nameDoctor.getText().toLowerCase(), containsString(DOCTOR_NAME_SEARCH.toLowerCase()));
        }
        assertTrue(countResult < countAllDoctors);
    }

    @Feature("Поиск по врачам")
    @Story("Поиск врачей по значению в различном регистре")
    @DisplayName("Поиск врачей по значению в различном регистре")
    @Test
    void searchDifferentRegister() {
        int countAllDoctors = doctorsPage.getCountDoctors();
        doctorsPage.searchDoctor(DOCTOR_NAME_SEARCH);
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        ElementsCollection namesDoctors = doctorsPage.getNamesDoctors();
        namesDoctors.shouldHave(CollectionCondition.size(countResult));
        for (SelenideElement nameDoctor : namesDoctors) {
            assertThat(nameDoctor.getText().toLowerCase(), containsString(DOCTOR_NAME_SEARCH.toLowerCase()));
        }
        assertTrue(countResult < countAllDoctors);
    }

    @Feature("Сортировка по врачам")
    @Story("Сортировка врачей по отсутствию фото")
    @DisplayName("Сортировка врачей по отсутствию фото")
    @Test
    void sortingWithoutPhoto() {
        int countAllDoctors = doctorsPage.getCountDoctors();
        doctorsPage.clickSortingPhotoNo();
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        ElementsCollection namesDoctors = doctorsPage.getNamesDoctors();
        namesDoctors.shouldHave(CollectionCondition.sizeLessThan(countAllDoctors));
        List<String> photoDoctorsAttributes = doctorsPage.getPhotoDoctorsAttributes();
        for (String attributeValue : photoDoctorsAttributes) {
            assertThat(attributeValue, equalTo(DEFAULT_PHOTO.toLowerCase()));
        }
        assertTrue(countResult < countAllDoctors);
    }

    @Feature("Сортировка по врачам")
    @Story("Сортировка врачей по наличию фото")
    @DisplayName("Сортировка врачей по наличию фото")
    @Test
    void sortingWithPhoto() {
        int countAllDoctors = doctorsPage.getCountDoctors();
        doctorsPage.clickSortingPhotoYes();
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        ElementsCollection namesDoctors = doctorsPage.getNamesDoctors();
        namesDoctors.shouldHave(CollectionCondition.sizeLessThan(countAllDoctors));
        List<String> photoDoctorsAttributes = doctorsPage.getPhotoDoctorsAttributes();
        for (String attributeValue : photoDoctorsAttributes) {
            assertThat(attributeValue, not(DEFAULT_PHOTO.toLowerCase()));
        }
        assertTrue(countResult < countAllDoctors);
    }

    @Feature("Сортировка по врачам")
    @Story("Сортировка по всем врачам")
    @DisplayName("Сортировка по всем врачам")
    @Test
    void sortingWithAndWithoutPhoto() {
        int countAllDoctors = doctorsPage.getCountDoctors();
        doctorsPage.clickSortingPhotoNo();
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        doctorsPage.clickSortingPhotoAll();
        Selenide.sleep(5000);
        int countAllDoctorsPhoto=doctorsPage.getCountDoctors();
        List<String> photoDoctorsAttributes = doctorsPage.getPhotoDoctorsAttributes();
        boolean withoutPhoto = false;
        boolean withPhoto = false;
        for (String attributeValue : photoDoctorsAttributes) {
            if (attributeValue.equals(DEFAULT_PHOTO.toLowerCase())) {
                withoutPhoto = true;
            } else {
                withPhoto = true;
            }
        }
        assertTrue(withoutPhoto);
        assertTrue(withPhoto);
        assertEquals(countAllDoctors, countAllDoctorsPhoto);
        assertTrue(countResult < countAllDoctorsPhoto);
    }

    @Story("Возврат к хэдеру на странице врачей")
    @DisplayName("Возврат к хэдеру на странице врачей")
    @Test
    void returnToHeaderPageAdministration() {
        assertFalse(basePage.isVisibleButtonReturnToHeader());
        basePage.scrollPageDown("700");
        basePage.getButtonReturnToHeader().shouldBe(Condition.visible);
        assertTrue(basePage.isVisibleButtonReturnToHeader());
        basePage.clickButtonReturnToHeader();
        basePage.getButtonReturnToHeader().shouldNotBe(Condition.visible);
        assertFalse(basePage.isVisibleButtonReturnToHeader());
    }
}



