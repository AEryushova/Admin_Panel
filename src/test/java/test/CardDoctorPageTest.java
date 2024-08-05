package test;

import pages.DoctorsPage.CardDoctorPage.*;
import pages.DoctorsPage.DoctorsPage;
import pages.Calendar.Calendar;
import utils.preparationDataTests.doctors.*;
import utils.dbUtils.DataBaseQuery;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import lombok.Getter;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import java.time.Duration;

import static data.TestData.DataTest.*;
import static data.TestData.UserData.*;
import static org.junit.jupiter.api.Assertions.*;
import static utils.otherUtils.DataGenerator.*;
import static utils.otherUtils.TestHelper.*;

@Epic("Карточка врача")
@DisplayName("Карточка врача")
public class CardDoctorPageTest extends BaseTest {

    @Getter
    private static CardDoctorPage cardDoctor;

    @BeforeAll
    static void setUpAuth() {
        BaseTest.openAdminPanel(LOGIN_ADMIN, PASSWORD_ADMIN);
        DoctorsPage doctorsPage = new DoctorsPage();
        doctorsPage.scrollToCard(doctorsPage.searchCardDoctor(DOCTOR_SPECIALIZATION, DOCTOR));
        doctorsPage.clickButtonEditInfoDoctor(DOCTOR_SPECIALIZATION, DOCTOR);
        cardDoctor = new CardDoctorPage();
    }

    @BeforeEach
    void setUp() {
        Selenide.refresh();
        cardDoctor.verifyDoctorCardPage();
    }


    @AfterAll
    static void closeWebDriver() {
        Selenide.closeWebDriver();
    }


    @Feature("Добавление фотографии врачу")
    @Story("Успешное добавление фотографии врачу в формате Jpeg и Png")
    @DisplayName("Успешное добавление фотографии врачу в формате Jpeg и Png")
    @ExtendWith(DeletePhotoDoctorDecorator.class)
    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/Photo 3,7mbJpeg.jpg", "src/test/resources/Photo 3,2mbPng.png"})
    void addPhotoDoctor(String path) {
        EditPhotoDoctorWindow editPhoto = cardDoctor.clickButtonEditPhoto();
        editPhoto.verifyEditPhotoDoctorWindow();
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        editPhoto.uploadPhoto(path);
        cardDoctor.getPhoto().should(Condition.not(Condition.attribute("src", srcOriginalPhoto)), Duration.ofSeconds(7));
        assertNotEquals(srcOriginalPhoto, cardDoctor.getSrcPhoto());
        assertNotEquals(srcOriginalPhoto, DataBaseQuery.selectInfoDoctor(DOCTOR, DOCTOR_SPECIALIZATION).getPhoto_uri());
        assertEquals("DOCTOR_PHOTO_CREATED_SUCCESS", DataBaseQuery.selectLogPhoto(LOGIN_ADMIN).getCode());
        assertFalse(editPhoto.isWindowAppear());
    }

    @Feature("Замена фотографии врачу")
    @Story("Успешная замена фотографии врачу")
    @DisplayName("Успешная замена фотографии врачу")
    @ExtendWith(AddDeletePhotoDoctorDecorator.class)
    @Test
    void changePhotoDoctor() {
        EditPhotoDoctorWindow editPhoto = cardDoctor.clickButtonEditPhoto();
        editPhoto.verifyEditPhotoDoctorWindow();
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        editPhoto.uploadPhoto("src/test/resources/Photo 3,2mbPng.png");
        cardDoctor.getPhoto().should(Condition.not(Condition.attribute("src", srcOriginalPhoto)), Duration.ofSeconds(7));
        assertNotEquals(srcOriginalPhoto, cardDoctor.getSrcPhoto());
        assertNotEquals(srcOriginalPhoto, DataBaseQuery.selectInfoDoctor(DOCTOR, DOCTOR_SPECIALIZATION).getPhoto_uri());
        assertEquals("DOCTOR_PHOTO_CREATED_SUCCESS", DataBaseQuery.selectLogPhoto(LOGIN_ADMIN).getCode());
        assertFalse(editPhoto.isWindowAppear());
    }


    @Feature("Замена фотографии врачу")
    @Story("Замена фотографии врачу с файлом весом более 4mb")
    @DisplayName("Замена фотографии врачу с файлом весом более 4mb")
    @Test
    void changePhotoDoctorWeightMoreThan4mb() {
        EditPhotoDoctorWindow editPhoto = cardDoctor.clickButtonEditPhoto();
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        editPhoto.uploadPhoto("src/test/resources/Photo 6,8mbJpeg.jpg");
        assertEquals("Допускаются файлы размером не выше 4Мб", cardDoctor.getTextNotification());
        assertEquals(srcOriginalPhoto, cardDoctor.getSrcPhoto());
        assertEquals(srcOriginalPhoto, DataBaseQuery.selectInfoDoctor(DOCTOR, DOCTOR_SPECIALIZATION).getPhoto_uri());
    }

    @Feature("Замена фотографии врачу")
    @Story("Замена фотографии врачу с не валидным файлом")
    @DisplayName("Замена фотографии врачу с не валидным файлом")
    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/Оферта,Политика обработки docx.docx", "src/test/resources/Оферта, Политика обработки .xlsx.xlsx", "src/test/resources/Оферта.pdf"})
    void changePhotoDoctorInvalidFormat(String path) {
        EditPhotoDoctorWindow editPhoto = cardDoctor.clickButtonEditPhoto();
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        editPhoto.uploadPhoto(path);
        assertEquals("Допускаются файлы с расширением jpg jpeg png", cardDoctor.getTextNotification());
        assertEquals(srcOriginalPhoto, cardDoctor.getSrcPhoto());
        assertEquals(srcOriginalPhoto, DataBaseQuery.selectInfoDoctor(DOCTOR, DOCTOR_SPECIALIZATION).getPhoto_uri());
    }


    @Feature("Замена фотографии врачу")
    @Story("Закрытие окна смены фотографии")
    @DisplayName("Закрытие окна смены фотографии")
    @Test
    void closeWindowEditPhoto() {
        EditPhotoDoctorWindow editPhoto = cardDoctor.clickButtonEditPhoto();
        editPhoto.closeWindowEditPhoto();
        assertFalse(editPhoto.isWindowAppear());
    }

    @Feature("Замена фотографии врачу")
    @Story("Успешное удаление фотографии врача")
    @DisplayName("Успешное удаление фотографии врача")
    @ExtendWith(AddPhotoDoctorDecorator.class)
    @Test
    void deletePhoto() {
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        cardDoctor.clickButtonDeletePhoto();
        cardDoctor.getPhoto().should(Condition.not(Condition.attribute("src", srcOriginalPhoto)), Duration.ofSeconds(6));
        assertNotEquals(srcOriginalPhoto, cardDoctor.getSrcPhoto());
        assertEquals(DEFAULT_PHOTO, cardDoctor.getSrcPhoto());
        assertEquals(DEFAULT_PHOTO, DataBaseQuery.selectInfoDoctor(DOCTOR, DOCTOR_SPECIALIZATION).getPhoto_uri());
        assertEquals("DOCTOR_PHOTO_DELETED_SUCCESS", DataBaseQuery.selectLogPhoto(LOGIN_ADMIN).getCode());
    }


    @Feature("Замена фотографии врачу")
    @Story("Удаление дефолтной фотографии врача")
    @DisplayName("Удаление дефолтной фотографии врача")
    @ExtendWith(DeletePhotoDoctorDecorator.class)
    @Test
    void deleteDefaultPhoto() {
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        cardDoctor.clickButtonDeletePhoto();
        assertEquals("Конфликт (409)", cardDoctor.getTextNotification());
        assertEquals(srcOriginalPhoto, cardDoctor.getSrcPhoto());
    }

    @Feature("Информация о враче")
    @Story("Успешное добавление раздела в инфо о враче")
    @DisplayName("Успешное добавление раздела в инфо о враче")
    @ExtendWith(DeleteSectionDecorator.class)
    @Test
    void addSection() {
        AddInfoDoctorWindow addInfoDoctorWindow = cardDoctor.clickButtonAddSection();
        addInfoDoctorWindow.verifyAddSectionDoctorWindow();
        addInfoDoctorWindow.fillFieldText(generateWord());
        addInfoDoctorWindow.clickButtonSaveValue();
        Section section = cardDoctor.getSection();
        assertEquals(word, section.getTextSection());
        assertEquals(word, DataBaseQuery.selectSection(DeleteSectionDecorator.getDoctorId()).getTitle());
        assertEquals("DOCTOR_GROUP_EXPERTISE_CREATED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
        assertTrue(cardDoctor.isExistSection());
        assertFalse(addInfoDoctorWindow.isWindowAppearSection());
    }

    @Feature("Информация о враче")
    @Story("Добавление пустого раздела в инфо о враче")
    @DisplayName("Добавление пустого раздела в инфо о враче")
    @ExtendWith(DeleteSectionDecorator.class)
    @Test
    void addSectionEmptyField() {
        AddInfoDoctorWindow addInfoDoctorWindow = cardDoctor.clickButtonAddSection();
        addInfoDoctorWindow.clickButtonSaveValue();
        addInfoDoctorWindow.clickCancelButtonAddSectionDoctor();
        assertEquals("Неверный запрос (400)", cardDoctor.getTextNotification());
        assertFalse(cardDoctor.isExistSection());
        assertNull(DataBaseQuery.selectSection(DeleteSectionDecorator.getDoctorId()));
    }


    @Feature("Информация о враче")
    @Story("Отмена добавления раздела в инфо о враче и зануление полей")
    @DisplayName("Отмена добавления раздела в инфо о враче и зануление полей")
    @Test
    void cancelAddSection() {
        AddInfoDoctorWindow addInfoDoctorWindow = cardDoctor.clickButtonAddSection();
        addInfoDoctorWindow.fillFieldText(generateWord());
        addInfoDoctorWindow.clickCancelButtonAddSectionDoctor();
        assertFalse(addInfoDoctorWindow.isWindowAppearSection());
        cardDoctor.clickButtonAddSection();
        assertEquals("", addInfoDoctorWindow.getValueField());
        addInfoDoctorWindow.clickCancelButtonAddSectionDoctor();
    }


    @Feature("Информация о враче")
    @Story("Успешное редактирование раздела в инфо о враче")
    @DisplayName("Успешное редактирование раздела в инфо о враче")
    @ExtendWith(AddDeleteSectionDecorator.class)
    @Test
    void editSection() {
        Section section = cardDoctor.getSection();
        section.verifySection();
        section.clickButtonEditSaveTitle();
        section.fillTitleField(generateWord());
        section.clickButtonEditSaveTitle();
        section.getSection().shouldHave(Condition.text(word));
        assertEquals(word, section.getTextSection());
        assertEquals(word, DataBaseQuery.selectSection(AddDeleteSectionDecorator.getDoctorId()).getTitle());
        assertEquals("DOCTOR_GROUP_EXPERTISE_CHANGED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Информация о враче")
    @Story("Редактирование раздела в инфо о враче с пустым полем")
    @DisplayName("Редактирование раздела в инфо о враче с пустым полем")
    @ExtendWith(AddDeleteSectionDecorator.class)
    @Test
    void editSectionEmptyField() {
        Section section = cardDoctor.getSection();
        section.clickButtonEditSaveTitle();
        section.clearTitleField();
        section.clickButtonEditSaveTitle();
        assertEquals("Неверный запрос (400)", cardDoctor.getTextNotification());
    }


    @Feature("Информация о враче")
    @Story("Успешное удаление раздела в инфо о враче")
    @DisplayName("Успешное удаление раздела в инфо о враче")
    @ExtendWith(AddSectionDecorator.class)
    @Test
    void deleteSection() {
        Section section = cardDoctor.getSection();
        section.clickButtonDeleteTitle();
        section.getSection().shouldNot(Condition.exist);
        cardDoctor.getEmptyListSection().shouldBe(Condition.visible);
        assertFalse(cardDoctor.isExistSection());
        assertTrue(cardDoctor.isExistsEmptyListSection());
        assertNull(DataBaseQuery.selectSection(AddSectionDecorator.getDoctorId()));
        assertEquals("DOCTOR_GROUP_EXPERTISE_DELETED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }


    @Feature("Информация о враче")
    @Story("Успешное добавление описания к разделу в инфо о враче")
    @DisplayName("Успешное добавление описания к разделу в инфо о враче")
    @ExtendWith(DeleteDescriptionDecorator.class)
    @Test
    void addDescription() {
        Section section = cardDoctor.getSection();
        AddInfoDoctorWindow addInfoDoctorWindow = section.clickButtonAddDescription();
        addInfoDoctorWindow.verifyAddDescriptionDoctorWindow();
        addInfoDoctorWindow.fillFieldText(generateText());
        addInfoDoctorWindow.clickButtonSaveValue();
        Description description = cardDoctor.getDescription();
        assertEquals(text, description.getTextDescription());
        assertEquals(text, DataBaseQuery.selectDescription(DeleteDescriptionDecorator.getSectionId()).getTitle());
        assertEquals("DOCTOR_EXPERTISE_CREATED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
        assertTrue(cardDoctor.isExistDescription());
        assertFalse(addInfoDoctorWindow.isWindowAppearDescription());
    }

    @Feature("Информация о враче")
    @Story("Добавление пустого описания к разделу в инфо о враче")
    @DisplayName("Добавление пустого описания к разделу в инфо о враче")
    @ExtendWith(DeleteDescriptionDecorator.class)
    @Test
    void addDescriptionEmptyField() {
        Section section = cardDoctor.getSection();
        AddInfoDoctorWindow addInfoDoctorWindow = section.clickButtonAddDescription();
        addInfoDoctorWindow.clickButtonSaveValue();
        assertEquals("Неверный запрос (400)", cardDoctor.getTextNotification());
        assertFalse(cardDoctor.isExistDescription());
        assertNull(DataBaseQuery.selectDescription(DeleteDescriptionDecorator.getSectionId()));
    }


    @Feature("Информация о враче")
    @Story("Отмена добавления описания к разделу в инфо о враче и зануление полей")
    @DisplayName("Отмена добавления описания к разделу в инфо о враче и зануление полей")
    @ExtendWith(DeleteDescriptionDecorator.class)
    @Test
    void cancelAddDescription() {
        Section section = cardDoctor.getSection();
        AddInfoDoctorWindow addInfoDoctorWindow = section.clickButtonAddDescription();
        addInfoDoctorWindow.fillFieldText(generateText());
        addInfoDoctorWindow.clickCancelButtonAddDescriptionDoctor();
        assertFalse(addInfoDoctorWindow.isWindowAppearDescription());
        section.clickButtonAddDescription();
        assertEquals("", addInfoDoctorWindow.getValueField());
        addInfoDoctorWindow.clickCancelButtonAddDescriptionDoctor();
    }


    @Feature("Информация о враче")
    @Story("Успешное редактирование описания к разделу в инфо о враче")
    @DisplayName("Успешное редактирование описания к разделу в инфо о враче")
    @ExtendWith(AddDeleteDescriptionDecorator.class)
    @Test
    void editDescription() {
        Description description = cardDoctor.getDescription();
        description.verifyDescription();
        description.clickButtonEditSaveDescription();
        description.fillDescriptionField(generateText());
        description.clickButtonEditSaveDescription();
        description.getDescription().shouldHave(Condition.text(text));
        assertEquals(text, description.getTextDescription());
        assertEquals(text, DataBaseQuery.selectDescription(AddDeleteDescriptionDecorator.getSectionId()).getTitle());
        assertEquals("DOCTOR_EXPERTISE_CHANGED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Информация о враче")
    @Story("Редактирование описания в инфо о враче с пустым полем")
    @DisplayName("Редактирование описания в инфо о враче с пустым полем")
    @ExtendWith(AddDeleteDescriptionDecorator.class)
    @Test
    void editDescriptionEmptyField() {
        Description description = cardDoctor.getDescription();
        description.clickButtonEditSaveDescription();
        description.clearDescriptionField();
        description.clickButtonEditSaveDescription();
        assertEquals("Неверный запрос (400)", cardDoctor.getTextNotification());
    }

    @Feature("Информация о враче")
    @Story("Успешное удаление описания к разделу в инфо о враче")
    @DisplayName("Успешное удаление описания к разделу в инфо о враче")
    @ExtendWith(AddDescriptionDecorator.class)
    @Test
    void deleteDescription() {
        Description description = cardDoctor.getDescription();
        description.clickButtonDeleteDescription();
        description.getDescription().shouldNot(Condition.exist);
        cardDoctor.getEmptyListDescription().shouldBe(Condition.visible);
        assertFalse(cardDoctor.isExistDescription());
        assertTrue(cardDoctor.isExistsEmptyListDescription());
        assertNull(DataBaseQuery.selectDescription(AddDescriptionDecorator.getSectionId()));
        assertEquals("DOCTOR_EXPERTISE_DELETED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Отзывы о враче")
    @Story("Успешное добавление отзыва о врачу датой в текущем месяце")
    @DisplayName("Успешное добавление отзыва о врачу датой в текущем месяце")
    @ExtendWith(DeleteFeedbackDecorator.class)
    @Test
    void addFeedbackCurrentMonth() {
        cardDoctor.scrollPage("500");
        AddFeedbackWindow addFeedbackWindow = cardDoctor.clickButtonAddFeedback();
        addFeedbackWindow.fillFieldFio(generateNamePatient());
        addFeedbackWindow.fillFieldTextFeedback(generateText());
        assertEquals(getCurrentDate(), addFeedbackWindow.getCurrentDateButton());
        Calendar calendar = addFeedbackWindow.openCalendarAddFeedback();
        calendar.verifyCalendar();
        assertEquals(getNameCurrentMonth(), calendar.getNameCurrentMonth());
        calendar.clickDateActivation(getDayCurrentMonth());
        addFeedbackWindow.clickButtonPublishFeedbackButton();
        cardDoctor.checkSelectUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        assertEquals("Отзыв успешно добавлен", cardDoctor.getTextNotification());
        assertEquals(getFutureDateCurrentMonth(), feedback.getDateFeedback());
        assertEquals(namePatient, feedback.getAuthorFeedback());
        assertEquals(text, feedback.getTextFeedback());
        assertEquals(namePatient, DataBaseQuery.selectFeedback().getAuthor());
        assertEquals(text, DataBaseQuery.selectFeedback().getContent());
        assertEquals(false, DataBaseQuery.selectFeedback().getIs_published());
        assertEquals("FEEDBACK_CREATED_ADMIN_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
        assertTrue(cardDoctor.isExistFeedback());
        assertTrue(cardDoctor.isSelectUnPublishedFeedback());
        assertFalse(cardDoctor.isSelectPublishedFeedback());
        assertFalse(addFeedbackWindow.isWindowAppear());
    }


    @Feature("Отзывы о враче")
    @Story("Успешное добавление отзыва о враче датой в следующем месяце")
    @DisplayName("Успешное добавление отзыва о враче датой в следующем месяце")
    @ExtendWith(DeleteFeedbackDecorator.class)
    @Test
    void addFeedbackFutureMonth() {
        cardDoctor.scrollPage("500");
        AddFeedbackWindow addFeedbackWindow = cardDoctor.clickButtonAddFeedback();
        addFeedbackWindow.fillFieldFio(generateNamePatient());
        addFeedbackWindow.fillFieldTextFeedback(generateText());
        Calendar calendar = addFeedbackWindow.openCalendarAddFeedback();
        calendar.verifyCalendar();
        calendar.switchFutureMonth();
        assertEquals(getNameFutureMonth(), calendar.getNameCurrentMonth());
        calendar.clickDateActivation(getDayFutureMonth());
        addFeedbackWindow.clickButtonPublishFeedbackButton();
        cardDoctor.checkSelectUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        assertEquals("Отзыв успешно добавлен", cardDoctor.getTextNotification());
        assertEquals(getNextMonthDate(), feedback.getDateFeedback());
        assertEquals(namePatient, feedback.getAuthorFeedback());
        assertEquals(text, feedback.getTextFeedback());
        assertEquals(namePatient, DataBaseQuery.selectFeedback().getAuthor());
        assertEquals(text, DataBaseQuery.selectFeedback().getContent());
        assertEquals(false, DataBaseQuery.selectFeedback().getIs_published());
        assertEquals("FEEDBACK_CREATED_ADMIN_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
        assertTrue(cardDoctor.isExistFeedback());
        assertTrue(cardDoctor.isSelectUnPublishedFeedback());
        assertFalse(cardDoctor.isSelectPublishedFeedback());
        assertFalse(addFeedbackWindow.isWindowAppear());
    }


    @Feature("Отзывы о враче")
    @Story("Успешное добавление отзыва о враче датой в предыдущем месяце")
    @DisplayName("Успешное добавление отзыва о враче датой в предыдущем месяце")
    @ExtendWith(DeleteFeedbackDecorator.class)
    @Test
    void addFeedbackPreviousMonth() {
        cardDoctor.scrollPage("500");
        AddFeedbackWindow addFeedbackWindow = cardDoctor.clickButtonAddFeedback();
        addFeedbackWindow.fillFieldFio(generateNamePatient());
        addFeedbackWindow.fillFieldTextFeedback(generateText());
        Calendar calendar = addFeedbackWindow.openCalendarAddFeedback();
        calendar.verifyCalendar();
        calendar.switchPreviousMonth();
        assertEquals(getNamePreviousMonth(), calendar.getNameCurrentMonth());
        calendar.clickDateActivation(getDayPreviousMonth());
        addFeedbackWindow.clickButtonPublishFeedbackButton();
        cardDoctor.checkSelectUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        assertEquals("Отзыв успешно добавлен", cardDoctor.getTextNotification());
        assertEquals(getPreviousMonthDate(), feedback.getDateFeedback());
        assertEquals(namePatient, feedback.getAuthorFeedback());
        assertEquals(text, feedback.getTextFeedback());
        assertEquals(namePatient, DataBaseQuery.selectFeedback().getAuthor());
        assertEquals(text, DataBaseQuery.selectFeedback().getContent());
        assertEquals(false, DataBaseQuery.selectFeedback().getIs_published());
        assertEquals("FEEDBACK_CREATED_ADMIN_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
        assertTrue(cardDoctor.isExistFeedback());
        assertTrue(cardDoctor.isSelectUnPublishedFeedback());
        assertFalse(cardDoctor.isSelectPublishedFeedback());
        assertFalse(addFeedbackWindow.isWindowAppear());
    }

    @Feature("Отзывы о враче")
    @Story("Успешное добавление отзыва о враче текущей датой")
    @DisplayName("Успешное добавление отзыва о враче текущей датой")
    @ExtendWith(DeleteFeedbackDecorator.class)
    @Test
    void addFeedbackTodayNotUseCalendar() {
        cardDoctor.scrollPage("500");
        AddFeedbackWindow addFeedbackWindow = cardDoctor.clickButtonAddFeedback();
        addFeedbackWindow.verifyAddFeedbackWindow();
        addFeedbackWindow.fillFieldFio(generateNamePatient());
        addFeedbackWindow.fillFieldTextFeedback(generateText());
        addFeedbackWindow.clickButtonPublishFeedbackButton();
        cardDoctor.checkSelectUnpublishedFeedback();
        cardDoctor.checkNoSelectPublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        assertEquals("Отзыв успешно добавлен", cardDoctor.getTextNotification());
        assertEquals(getCurrentDateRu(), feedback.getDateFeedback());
        assertEquals(namePatient, feedback.getAuthorFeedback());
        assertEquals(text, feedback.getTextFeedback());
        assertEquals(namePatient, DataBaseQuery.selectFeedback().getAuthor());
        assertEquals(text, DataBaseQuery.selectFeedback().getContent());
        assertEquals(false, DataBaseQuery.selectFeedback().getIs_published());
        assertEquals("FEEDBACK_CREATED_ADMIN_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
        assertTrue(cardDoctor.isExistFeedback());
        assertTrue(cardDoctor.isSelectUnPublishedFeedback());
        assertFalse(cardDoctor.isSelectPublishedFeedback());
        assertFalse(addFeedbackWindow.isWindowAppear());
    }

    @Feature("Отзывы о враче")
    @Story("Очистка поля ФИО через кнопку в окне добавления отзыва")
    @DisplayName("Очистка поля ФИО через кнопку в окне добавления отзыва")
    @Test
    void clearFioFieldWindowAddFeedbackThroughButtonClear() {
        cardDoctor.scrollPage("500");
        AddFeedbackWindow addFeedbackWindow = cardDoctor.clickButtonAddFeedback();
        addFeedbackWindow.fillFieldFio(generateNamePatient());
        addFeedbackWindow.clickClearButtonFioField();
        assertEquals("", addFeedbackWindow.getValueFioField());
    }

    @Feature("Отзывы о враче")
    @Story("Сброс значений полей в окне добавления отзыва при закрытии окна")
    @DisplayName("Сброс значений полей в окне добавления отзыва при закрытии окна")
    @Test
    void closeWindowAddNewFeedback() {
        cardDoctor.scrollPage("500");
        AddFeedbackWindow addFeedbackWindow = cardDoctor.clickButtonAddFeedback();
        addFeedbackWindow.fillFieldFio(generateNamePatient());
        addFeedbackWindow.fillFieldTextFeedback(generateText());
        addFeedbackWindow.closeWindowAddFeedback();
        assertFalse(addFeedbackWindow.isWindowAppear());
        cardDoctor.clickButtonAddFeedback();
        assertEquals("", addFeedbackWindow.getValueFioField());
        assertEquals("", addFeedbackWindow.getValueTextFeedbackField());
    }

    @Feature("Отзывы о враче")
    @Story("Добавление нового отзыва с пустым полем ФИО")
    @DisplayName("Добавление нового отзыва с пустым полем ФИО")
    @Test
    void addFeedbackEmptyFieldFio() {
        cardDoctor.scrollPage("500");
        AddFeedbackWindow addFeedbackWindow = cardDoctor.clickButtonAddFeedback();
        addFeedbackWindow.fillFieldTextFeedback(generateText());
        assertFalse(addFeedbackWindow.isEnabledPublishButton());
    }


    @Feature("Отзывы о враче")
    @Story("Добавление нового отзыва с пустым полем отзыва")
    @DisplayName("Добавление нового отзыва с пустым полем отзыва")
    @Test
    void addFeedbackEmptyFieldText() {
        cardDoctor.scrollPage("500");
        AddFeedbackWindow addFeedbackWindow = cardDoctor.clickButtonAddFeedback();
        addFeedbackWindow.fillFieldFio(generateNamePatient());
        assertFalse(addFeedbackWindow.isEnabledPublishButton());
    }

    @Feature("Отзывы о враче")
    @Story("Успешное редактирование неопубликованного отзыва о враче")
    @DisplayName("Успешное редактирование неопубликованного отзыва о враче")
    @ExtendWith(AddDeleteFeedbackDecorator.class)
    @Test
    void editUnpublishedFeedback() {
        cardDoctor.scrollPage("500");
        cardDoctor.checkSelectPublishedFeedback();
        cardDoctor.checkNoSelectUnpublishedFeedback();
        cardDoctor.clickButtonUnpublishedFeedback();
        cardDoctor.checkNoSelectPublishedFeedback();
        cardDoctor.checkSelectUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.verifyFeedbackUnpublished();
        EditFeedbackWindow editFeedback = feedback.clickButtonEditFeedback();
        editFeedback.verifyChangeFeedbackWindow();
        editFeedback.fillFieldText(generateText());
        editFeedback.clickButtonSaveChanges();
        feedback.verifyFeedbackUnpublished();
        assertEquals("Отзыв успешно изменен", cardDoctor.getTextNotification());
        assertEquals(text, feedback.getTextFeedback());
        assertEquals(text, DataBaseQuery.selectFeedback().getContent());
        assertEquals(false, DataBaseQuery.selectFeedback().getIs_published());
        assertEquals("FEEDBACK_CHANGED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
        assertTrue(cardDoctor.isSelectUnPublishedFeedback());
        assertFalse(cardDoctor.isSelectPublishedFeedback());
        assertFalse(editFeedback.isWindowAppear());
    }


    @Feature("Отзывы о враче")
    @Story("Успешная публикация неопубликованного отзыва о враче")
    @DisplayName("Успешная публикация неопубликованного отзыва о враче")
    @ExtendWith(AddDeleteFeedbackDecorator.class)
    @Test
    void publicationUnpublishedFeedback() {
        cardDoctor.scrollPage("500");
        cardDoctor.clickButtonUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.verifyFeedbackUnpublished();
        feedback.clickButtonPublicationFeedback();
        feedback.verifyFeedbackPublished();
        assertEquals("Отзыв успешно изменен", cardDoctor.getTextNotification());
        assertEquals(text, feedback.getTextFeedback());
        assertEquals(text, DataBaseQuery.selectFeedback().getContent());
        assertEquals(true, DataBaseQuery.selectFeedback().getIs_published());
        assertFalse(cardDoctor.isSelectUnPublishedFeedback());
        assertTrue(cardDoctor.isSelectPublishedFeedback());
    }


    @Feature("Отзывы о враче")
    @Story("Успешное редактирование опубликованного отзыва о враче")
    @DisplayName("Успешное редактирование опубликованного отзыва о враче")
    @ExtendWith(AddPublishedDeleteFeedback.class)
    @Test
    void editPublishedFeedback() {
        cardDoctor.scrollPage("500");
        cardDoctor.checkSelectPublishedFeedback();
        cardDoctor.checkNoSelectUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.verifyFeedbackPublished();
        EditFeedbackWindow editFeedback = feedback.clickButtonEditFeedback();
        editFeedback.verifyChangeFeedbackWindow();
        editFeedback.fillFieldText(generateText());
        editFeedback.clickButtonSaveChanges();
        feedback.verifyFeedbackPublished();
        assertEquals("Отзыв успешно изменен", cardDoctor.getTextNotification());
        assertEquals(text, feedback.getTextFeedback());
        assertEquals(text, DataBaseQuery.selectFeedback().getContent());
        assertEquals(true, DataBaseQuery.selectFeedback().getIs_published());
        assertEquals("FEEDBACK_CHANGED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
        assertFalse(cardDoctor.isSelectUnPublishedFeedback());
        assertTrue(cardDoctor.isSelectPublishedFeedback());
        assertFalse(editFeedback.isWindowAppear());
    }


    @Feature("Отзывы о враче")
    @Story("Успешное снятие с публикации опубликованного отзыва о враче")
    @DisplayName("Успешное снятие с публикации опубликованного отзыва о враче")
    @ExtendWith(AddPublishedDeleteFeedback.class)
    @Test
    void withdrawalPublicationFeedback() {
        cardDoctor.scrollPage("500");
        Feedback feedback = cardDoctor.getFeedback();
        feedback.verifyFeedbackPublished();
        feedback.clickButtonWithdrawalPublication();
        feedback.verifyFeedbackUnpublished();
        assertEquals("Отзыв успешно изменен", cardDoctor.getTextNotification());
        assertEquals(text, feedback.getTextFeedback());
        assertEquals(text, DataBaseQuery.selectFeedback().getContent());
        assertEquals(false, DataBaseQuery.selectFeedback().getIs_published());
        assertTrue(cardDoctor.isSelectUnPublishedFeedback());
        assertFalse(cardDoctor.isSelectPublishedFeedback());
    }

    @Feature("Отзывы о враче")
    @Story("Сохранение значений полей в окне редактирования отзыва после закрытия окна")
    @DisplayName("Сохранение значений полей в окне редактирования отзыва после закрытия окна")
    @ExtendWith(AddPublishedDeleteFeedback.class)
    @Test
    void closeWindowEditFeedback() {
        cardDoctor.scrollPage("500");
        Feedback feedback = cardDoctor.getFeedback();
        EditFeedbackWindow editFeedback = feedback.clickButtonEditFeedback();
        editFeedback.fillFieldText(generateNamePatient());
        editFeedback.closeWindowChangeFeedback();
        assertFalse(editFeedback.isWindowAppear());
        feedback.clickButtonEditFeedback();
        assertEquals(text, editFeedback.getValueTextFeedbackField());
        assertEquals(text, DataBaseQuery.selectFeedback().getContent());
    }

    @Feature("Отзывы о враче")
    @Story("Редактирование отзыва с пустым полем отзыва")
    @DisplayName("Редактирование отзыва с пустым полем отзыва")
    @ExtendWith(AddPublishedDeleteFeedback.class)
    @Test
    void editFeedbackEmptyFieldText() {
        cardDoctor.scrollPage("500");
        Feedback feedback = cardDoctor.getFeedback();
        EditFeedbackWindow editFeedback = feedback.clickButtonEditFeedback();
        editFeedback.clearTextField();
        assertFalse(editFeedback.isEnabledSaveButton());
    }


    @Feature("Отзывы о враче")
    @Story("Успешное удаление неопубликованного отзыва о враче")
    @DisplayName("Успешное удаление неопубликованного отзыва о враче")
    @ExtendWith(AddFeedbackDecorator.class)
    @Test
    void deleteUnpublishedFeedback() {
        cardDoctor.scrollPage("500");
        cardDoctor.clickButtonUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.verifyFeedbackUnpublished();
        feedback.clickButtonDeleteFeedback();
        assertEquals("Отзыв успешно удален", cardDoctor.getTextNotification());
        assertFalse(cardDoctor.isExistFeedback());
        assertNull(DataBaseQuery.selectFeedback());
        assertEquals("FEEDBACK_DELETED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Отзывы о враче")
    @Story("Сортировка неопубликованных отзывов о враче")
    @DisplayName("Сортировка неопубликованных отзывов о враче")
    @ExtendWith(AddTwoFeedbackDecorator.class)
    @Test
    void sortingUnpublishedFeedbacks() {
        cardDoctor.scrollPage("500");
        cardDoctor.clickButtonUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        assertTrue(cardDoctor.isSortingNewAppear());
        String dateFeedbackToday = feedback.getDateFeedbackByIndex(0);
        String dateFeedbackYesterday = feedback.getDateFeedbackByIndex(1);
        cardDoctor.clickSortingFeedbackNew();
        assertFalse(cardDoctor.isSortingNewAppear());
        assertTrue(cardDoctor.isSortingOldAppear());
        assertEquals(dateFeedbackYesterday, feedback.getDateFeedbackByIndex(0));
        assertEquals(dateFeedbackToday, feedback.getDateFeedbackByIndex(1));
        cardDoctor.clickSortingFeedbackOld();
        assertTrue(cardDoctor.isSortingNewAppear());
        assertFalse(cardDoctor.isSortingOldAppear());
        assertEquals(dateFeedbackToday, feedback.getDateFeedbackByIndex(0));
        assertEquals(dateFeedbackYesterday, feedback.getDateFeedbackByIndex(1));
    }

    @Story("Закрытие навигационного меню")
    @DisplayName("Закрытие навигационного меню")
    @Test
    void closeNavigateMenu() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.verifyNavigateMenu();
        navigateMenu.closeNavigateMenu();
        navigateMenu.getTabFeedbackNavigateMenu().shouldNotBe(Condition.visible);
        assertFalse(navigateMenu.isNavigateMenuDisplayed());
    }

}

