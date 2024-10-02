package ru.adminlk.clinica.test;


import org.junit.jupiter.api.extension.ExtendWith;
import ru.adminlk.clinica.pages.Calendar.Calendar;
import ru.adminlk.clinica.pages.CardDoctorPage.CardDoctorPage;
import com.codeborne.selenide.*;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import ru.adminlk.clinica.pages.CardDoctorPage.Feedback;
import ru.adminlk.clinica.pages.DoctorsPage.UnpublishedFeedback;
import ru.adminlk.clinica.pages.DoctorsPage.UnpublishedFeedbacksWindow;
import ru.adminlk.clinica.utils.dbUtils.dbUtils.DataBaseQuery;
import ru.adminlk.clinica.utils.preparationData.doctors.*;

import java.util.List;

import static ru.adminlk.clinica.data.FinalTestData.TestData.*;
import static ru.adminlk.clinica.data.FinalTestData.UserData.*;
import static ru.adminlk.clinica.data.FinalTestData.SearchData.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;
import static ru.adminlk.clinica.utils.testsUtils.DataGenerator.generateText;
import static ru.adminlk.clinica.data.GeneratedTestData.*;
import static ru.adminlk.clinica.utils.testsUtils.TestHelper.*;

@Epic("Врачи")
@DisplayName("Страница Врачи")
public class DoctorPageTest extends BaseTest {

    @BeforeAll
    static void setUpAuth() {
        getAuthToken(LOGIN_ADMIN, PASSWORD_ADMIN);
    }

    @BeforeEach
    void setUp() {
        openAuthAdminPanel();
        doctorsPage.verifyDoctorsPage();
    }

    @AfterEach()
    void closeWebDriver() {
        closeDriver();
    }


    @Feature("Открытие карточки врача")
    @Story("Открытие карточки врача и возврат к списку врачей")
    @DisplayName("Открытие карточки врача и возврат к списку врачей")
    @Test
    void returnToDoctorsPageFromCardDoctorPage() {
        doctorsPage.searchDoctor(DOCTOR_SURNAME);
        doctorsPage.scrollToCard(doctorsPage.searchCardDoctor(DOCTOR_SPECIALIZATION, DOCTOR));
        CardDoctorPage cardDoctor = doctorsPage.clickButtonEditInfoDoctor(DOCTOR_SPECIALIZATION, DOCTOR);
        cardDoctor.scrollPageUp("500");
        assertEquals(DOCTOR, cardDoctor.getNameDoctor());
        cardDoctor.clickButtonComebackDoctorsPage();
        doctorsPage.verifyDoctorsPage();
    }

    @Feature("Обработка неопубликованных отзывов")
    @Story("Просмотр неопубликованного отзыва в карточке врача")
    @DisplayName("Просмотр неопубликованного отзыва в карточке врача")
    @ExtendWith(AddUnpublishedFeedback.class)
    @Test
    void openUnpublishedFeedbackInCardDoctor() {
        UnpublishedFeedbacksWindow unFeedbacksWindow = doctorsPage.clickShowUnpublishedFeedbacks();
        unFeedbacksWindow.verifyUnpublishedFeedbacksWindow();
        UnpublishedFeedback unFeedback = unFeedbacksWindow.getUnpublishedFeedback();
        unFeedback.verifyUnpublishedFeedback();
        String nameDoctor = unFeedback.getNameDoctorFeedback();
        String nameAuthor = unFeedback.getNameAuthorFeedback();
        String dateFeedback = unFeedback.getDateFeedback();
        String textFeedback = unFeedback.getTextFeedback();
        CardDoctorPage cardDoctor = unFeedback.clickButtonOpenCardDoctor();
        assertEquals(nameDoctor, cardDoctor.getNameDoctor());
        cardDoctor.scrollPageDown("500");
        cardDoctor.clickButtonUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        assertEquals(nameAuthor, feedback.getNameAuthorFeedback());
        assertEquals(dateFeedback, feedback.getDateFeedback());
        assertEquals(textFeedback, feedback.getTextFeedback());
    }

    @Feature("Обработка неопубликованных отзывов")
    @Story("Публикация неопубликованного отзыва")
    @DisplayName("Публикация неопубликованного отзыва")
    @ExtendWith(AddUnpublishedFeedback.class)
    @Test
    void publishedUnpublishedFeedback() {
        UnpublishedFeedbacksWindow unFeedbacksWindow = doctorsPage.clickShowUnpublishedFeedbacks();
        unFeedbacksWindow.verifyUnpublishedFeedbacksWindow();
        UnpublishedFeedback unFeedback = unFeedbacksWindow.getUnpublishedFeedback();
        unFeedback.verifyUnpublishedFeedback()
                .clickButtonPublicationFeedback();
        unFeedbacksWindow.getEmptyListFeedback().shouldBe(Condition.visible);
        assertFalse(unFeedbacksWindow.isExistUnpublishedFeedback());
        unFeedbacksWindow.closeWindowUnpublishedFeedbacksWindow();
        assertFalse(unFeedbacksWindow.isWindowAppear());
        doctorsPage.searchDoctor(DOCTOR_SURNAME);
        doctorsPage.scrollToCard(doctorsPage.searchCardDoctor(DOCTOR_SPECIALIZATION, DOCTOR));
        doctorsPage.clickButtonEditInfoDoctor(DOCTOR_SPECIALIZATION, DOCTOR);
        cardDoctor.scrollPageDown("500");
        Feedback feedback = cardDoctor.getFeedback();
        feedback.verifyFeedbackPublished();
        assertEquals(getDate("current",0,0,"dd MMMM yyyy"), feedback.getDateFeedback());
        assertEquals(namePatient, feedback.getNameAuthorFeedback());
        assertEquals(text, feedback.getTextFeedback());
        assertEquals(namePatient, DataBaseQuery.selectFeedback().getAuthor());
        assertEquals(text, DataBaseQuery.selectFeedback().getContent());
        assertEquals(true, DataBaseQuery.selectFeedback().getIs_published());
    }

    @Feature("Обработка неопубликованных отзывов")
    @Story("Публикация отредактированного неопубликованного отзыва")
    @DisplayName("Публикация отредактированного неопубликованного отзыва")
    @ExtendWith(AddUnpublishedFeedback.class)
    @Test
    void publishedChangedUnpublishedFeedback() {
        UnpublishedFeedbacksWindow unFeedbacksWindow = doctorsPage.clickShowUnpublishedFeedbacks();
        unFeedbacksWindow.verifyUnpublishedFeedbacksWindow();
        UnpublishedFeedback unFeedback = unFeedbacksWindow.getUnpublishedFeedback();
        unFeedback.verifyUnpublishedFeedback()
                .fillFieldText(generateText())
                .clickButtonPublicationFeedback();
        unFeedbacksWindow.getEmptyListFeedback().shouldBe(Condition.visible);
        assertFalse(unFeedbacksWindow.isExistUnpublishedFeedback());
        unFeedbacksWindow.closeWindowUnpublishedFeedbacksWindow();
        doctorsPage.searchDoctor(DOCTOR_SURNAME);
        doctorsPage.scrollToCard(doctorsPage.searchCardDoctor(DOCTOR_SPECIALIZATION, DOCTOR));
        doctorsPage.clickButtonEditInfoDoctor(DOCTOR_SPECIALIZATION, DOCTOR);
        cardDoctor.scrollPageDown("500");
        Feedback feedback = cardDoctor.getFeedback();
        feedback.verifyFeedbackPublished();
        assertEquals(getDate("current",0,0,"dd MMMM yyyy"), feedback.getDateFeedback());
        assertEquals(namePatient, feedback.getNameAuthorFeedback());
        assertEquals(text, feedback.getTextFeedback());
        assertEquals(namePatient, DataBaseQuery.selectFeedback().getAuthor());
        assertEquals(text, DataBaseQuery.selectFeedback().getContent());
        assertEquals(true, DataBaseQuery.selectFeedback().getIs_published());
        assertEquals("FEEDBACK_CHANGED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Обработка неопубликованных отзывов")
    @Story("Очистка фильтра по дате")
    @DisplayName("Очистка фильтра по дате")
    @ExtendWith(AddDeleteOldFeedback.class)
    @Test
    void clearFilterPeriodUnpublishedFeedback() {
        UnpublishedFeedbacksWindow unFeedbacksWindow = doctorsPage.clickShowUnpublishedFeedbacks();
        unFeedbacksWindow.verifyUnpublishedFeedbacksWindow();
        String period = unFeedbacksWindow.getValuesButtonPeriod();
        assertEquals(getPeriod(null,0,true), period);
        assertFalse(unFeedbacksWindow.isExistUnpublishedFeedback());
        unFeedbacksWindow.clickClearFilter();
        unFeedbacksWindow.getEmptyListFeedback().shouldNotBe(Condition.visible);
        UnpublishedFeedback unFeedback = unFeedbacksWindow.getUnpublishedFeedback();
        unFeedback.verifyUnpublishedFeedback();
        assertTrue(unFeedbacksWindow.isExistUnpublishedFeedback());
        assertEquals("Укажите даты", unFeedbacksWindow.getValuesButtonPeriod());
        assertFalse(unFeedbacksWindow.isDateInThisPeriod(unFeedback.getDateFeedback(), period));
    }

    @Feature("Обработка неопубликованных отзывов")
    @Story("Просмотр неопубликованных отзывов за прошедший период")
    @DisplayName("Просмотр неопубликованных отзывов за прошедший период")
    @ExtendWith(AddDeleteOldFeedback.class)
    @Test
    void openUnpublishedFeedbackPreviousPeriod() {
        UnpublishedFeedbacksWindow unFeedbacksWindow = doctorsPage.clickShowUnpublishedFeedbacks();
        unFeedbacksWindow.verifyUnpublishedFeedbacksWindow();
        assertFalse(unFeedbacksWindow.isExistUnpublishedFeedback());
        unFeedbacksWindow.clickClearFilter();
        Calendar calendar = unFeedbacksWindow.openCalendarSelectPeriod();
        calendar.verifyCalendar()
                .switchPreviousMonth()
                .switchPreviousMonth();
        assertEquals(getDate("previous",2,0,"LLLL yyyy"), calendar.getNameCurrentMonth());
        calendar.clickDateActivation(getDay("previous","first",2,0));
        calendar.clickDateActivation(getDay("previous","last",2,0));
        assertFalse(calendar.isCalendarAppear());
        UnpublishedFeedback unFeedback = unFeedbacksWindow.getUnpublishedFeedback();
        unFeedback.verifyUnpublishedFeedback();
        assertEquals(getPeriod("previous",2,false), unFeedbacksWindow.getValuesButtonPeriod());
        assertTrue(unFeedbacksWindow.isDateInThisPeriod(unFeedback.getDateFeedback(), unFeedbacksWindow.getValuesButtonPeriod()));
    }

    @Feature("Обработка неопубликованных отзывов")
    @Story("Просмотр неопубликованных отзывов за будущий период")
    @DisplayName("Просмотр неопубликованных отзывов за будущий период")
    @ExtendWith(AddDeleteNewFeedback.class)
    @Test
    void openUnpublishedFeedbackFuturePeriod() {
        UnpublishedFeedbacksWindow unFeedbacksWindow = doctorsPage.clickShowUnpublishedFeedbacks();
        unFeedbacksWindow.verifyUnpublishedFeedbacksWindow();
        assertFalse(unFeedbacksWindow.isExistUnpublishedFeedback());
        unFeedbacksWindow.clickClearFilter();
        Calendar calendar = unFeedbacksWindow.openCalendarSelectPeriod();
        calendar.verifyCalendar()
                .switchFutureMonth()
                .switchFutureMonth();
        assertEquals(getDate("future",2,0,"LLLL yyyy"), calendar.getNameCurrentMonth());
        calendar.clickDateActivation(getDay("future","first",2,0));
        calendar.clickDateActivation(getDay("future","last",2,0));
        assertFalse(calendar.isCalendarAppear());
        UnpublishedFeedback unFeedback = unFeedbacksWindow.getUnpublishedFeedback();
        unFeedback.verifyUnpublishedFeedback();
        assertEquals(getPeriod("future",2,false), unFeedbacksWindow.getValuesButtonPeriod());
        assertTrue(unFeedbacksWindow.isDateInThisPeriod(unFeedback.getDateFeedback(), unFeedbacksWindow.getValuesButtonPeriod()));
    }

    @Feature("Обработка неопубликованных отзывов")
    @Story("Возвращение отзыва в список неопубликованных после снятия с публикации")
    @DisplayName("Возвращение отзыва в список неопубликованных после снятия с публикации")
    @ExtendWith(AddPublishedDeleteFeedback.class)
    @Test
    void filterFeedbackPublicationStatus() {
        UnpublishedFeedbacksWindow unFeedbacksWindow = doctorsPage.clickShowUnpublishedFeedbacks();
        unFeedbacksWindow.verifyUnpublishedFeedbacksWindow()
                .clickClearFilter();
        assertFalse(unFeedbacksWindow.isExistUnpublishedFeedback());
        unFeedbacksWindow.closeWindowUnpublishedFeedbacksWindow();
        doctorsPage.searchDoctor(DOCTOR_SURNAME);
        doctorsPage.scrollToCard(doctorsPage.searchCardDoctor(DOCTOR_SPECIALIZATION, DOCTOR));
        doctorsPage.clickButtonEditInfoDoctor(DOCTOR_SPECIALIZATION, DOCTOR);
        cardDoctor.scrollPageDown("500");
        Feedback feedback = cardDoctor.getFeedback();
        feedback.verifyFeedbackPublished()
                .clickButtonWithdrawalPublication()
                .verifyFeedbackUnpublished();
        cardDoctor.scrollPageUp("500");
        cardDoctor.clickButtonComebackDoctorsPage();
        doctorsPage.clickShowUnpublishedFeedbacks();
        assertTrue(unFeedbacksWindow.isExistUnpublishedFeedback());
    }

    @Feature("Обработка неопубликованных отзывов")
    @Story("Публикация пустого отзыва")
    @DisplayName("Публикация пустого отзыва")
    @ExtendWith(AddUnpublishedFeedback.class)
    @Test
    void publishedEmptyUnpublishedFeedback() {
        UnpublishedFeedbacksWindow unFeedbacksWindow = doctorsPage.clickShowUnpublishedFeedbacks();
        unFeedbacksWindow.verifyUnpublishedFeedbacksWindow();
        UnpublishedFeedback unFeedback = unFeedbacksWindow.getUnpublishedFeedback();
        unFeedback.verifyUnpublishedFeedback()
                .clearFieldText()
                .clickButtonPublicationFeedback();
        assertTrue(unFeedbacksWindow.isExistUnpublishedFeedback());
    }

    @Feature("Обработка неопубликованных отзывов")
    @Story("Удаление неопубликованного отзыва")
    @DisplayName("Удаление неопубликованного отзыва")
    @ExtendWith(AddUnpublishedFeedback.class)
    @Test
    void deleteUnpublishedFeedback() {
        UnpublishedFeedbacksWindow unFeedbacksWindow = doctorsPage.clickShowUnpublishedFeedbacks();
        unFeedbacksWindow.verifyUnpublishedFeedbacksWindow();
        UnpublishedFeedback unFeedback = unFeedbacksWindow.getUnpublishedFeedback();
        unFeedback.verifyUnpublishedFeedback()
                .clickButtonDeleteFeedback();
        unFeedbacksWindow.getEmptyListFeedback().shouldBe(Condition.visible);
        assertFalse(unFeedbacksWindow.isExistUnpublishedFeedback());
        assertTrue(unFeedbacksWindow.isExistsEmptyListFeedback());
        assertEquals("Отзыв успешно удален", cardDoctor.getTextNotification());
        assertNull(DataBaseQuery.selectFeedback());
        assertEquals("FEEDBACK_DELETED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Обработка неопубликованных отзывов")
    @Story("Изменение отображения количества отзывов")
    @DisplayName("Изменение отображения количества отзывов")
    @ExtendWith(AddFeedbackDifferentDate.class)
    @Test
    void changeCountUnpublishedFeedback() {
        String countFeedbacks = doctorsPage.getCountUnpublishedFeedback();
        UnpublishedFeedbacksWindow unFeedbacksWindow = doctorsPage.clickShowUnpublishedFeedbacks();
        unFeedbacksWindow.verifyUnpublishedFeedbacksWindow()
                .clickClearFilter()
                .closeWindowUnpublishedFeedbacksWindow();
        assertNotEquals(Integer.parseInt(countFeedbacks), Integer.parseInt(doctorsPage.getCountUnpublishedFeedback()));
        Selenide.refresh();
        assertEquals(Integer.parseInt(countFeedbacks), Integer.parseInt(doctorsPage.getCountUnpublishedFeedback()));
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
    @Story("Сортировка по всем врачам по фотографии")
    @DisplayName("Сортировка по всем врачам по фотографии")
    @Test
    void sortingWithAndWithoutPhoto() {
        int countAllDoctors = doctorsPage.getCountDoctors();
        doctorsPage.clickSortingPhotoNo();
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        doctorsPage.clickSortingPhotoAll();
        Selenide.sleep(5000);
        int countAllDoctorsPhoto = doctorsPage.getCountDoctors();
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



