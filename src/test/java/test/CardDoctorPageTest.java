package test;

import pages.CardDoctorPage.*;
import pages.DoctorsPage.DoctorsPage;
import pages.Calendar.Calendar;
import utils.preparationData.doctors.*;
import utils.dbUtils.DataBaseQuery;
import com.codeborne.selenide.Condition;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import java.time.Duration;

import static data.TestData.DataTest.*;
import static data.TestData.UserData.*;
import static org.junit.jupiter.api.Assertions.*;
import static utils.testsUtils.DataGenerator.*;
import static utils.testsUtils.TestHelper.*;

@Epic("Карточка врача")
@DisplayName("Карточка врача")
public class CardDoctorPageTest extends BaseTest {

    @BeforeAll
    static void setUpAuth() {
        getAuthToken(LOGIN_ADMIN, PASSWORD_ADMIN);
    }

    @BeforeEach
    void setUp() {
        openAuthAdminPanel();
        DoctorsPage doctorsPage = new DoctorsPage();
        doctorsPage.searchDoctor(DOCTOR_SURNAME);
        doctorsPage.scrollToCard(doctorsPage.searchCardDoctor(DOCTOR_SPECIALIZATION, DOCTOR));
        doctorsPage.clickButtonEditInfoDoctor(DOCTOR_SPECIALIZATION, DOCTOR);
        cardDoctor.verifyDoctorCardPage();
    }

    @AfterEach()
    void closeWebDriver() {
        closeDriver();
    }


    @Feature("Добавление фотографии врачу")
    @Story("Успешное добавление фотографии врачу в формате Jpeg и Png")
    @DisplayName("Успешное добавление фотографии врачу в формате Jpeg и Png")
    @ExtendWith(DeletePhotoDoctor.class)
    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/images/Photo 3,7mbJpeg.jpg", "src/test/resources/images/Photo 3,2mbPng.png"})
    void addPhotoDoctor(String path) {
        cardDoctor.scrollPageUp("500");
        EditPhotoDoctorWindow editPhoto = cardDoctor.clickButtonEditPhoto();
        editPhoto.verifyEditPhotoDoctorWindow();
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        editPhoto.uploadPhoto(path);
        cardDoctor.getPhoto().should(Condition.not(Condition.attribute("src", srcOriginalPhoto)), Duration.ofSeconds(7));
        assertNotEquals(srcOriginalPhoto, cardDoctor.getSrcPhoto());
        assertNotEquals(srcOriginalPhoto, DataBaseQuery.selectInfoDoctor(DOCTOR, DOCTOR_SPECIALIZATION).getPhoto_uri());
        assertEquals("DOCTOR_PHOTO_CREATED_SUCCESS", DataBaseQuery.selectLogPhoto(LOGIN_ADMIN).getCode());
    }

    @Feature("Замена фотографии врачу")
    @Story("Успешная замена фотографии врачу")
    @DisplayName("Успешная замена фотографии врачу")
    @ExtendWith(AddDeletePhotoDoctor.class)
    @Test
    void changePhotoDoctor() {
        cardDoctor.scrollPageUp("500");
        EditPhotoDoctorWindow editPhoto = cardDoctor.clickButtonEditPhoto();
        editPhoto.verifyEditPhotoDoctorWindow();
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        editPhoto.uploadPhoto("src/test/resources/images/Photo 3,2mbPng.png");
        cardDoctor.getPhoto().should(Condition.not(Condition.attribute("src", srcOriginalPhoto)), Duration.ofSeconds(7));
        assertNotEquals(srcOriginalPhoto, cardDoctor.getSrcPhoto());
        assertNotEquals(srcOriginalPhoto, DataBaseQuery.selectInfoDoctor(DOCTOR, DOCTOR_SPECIALIZATION).getPhoto_uri());
        assertEquals("DOCTOR_PHOTO_CREATED_SUCCESS", DataBaseQuery.selectLogPhoto(LOGIN_ADMIN).getCode());
    }

    @Feature("Замена фотографии врачу")
    @Story("Замена фотографии врачу с файлом весом 4mb")
    @DisplayName("Замена фотографии врачу с файлом весом 4mb")
    @Test
    void changePhotoDoctorWeight4mb() {
        cardDoctor.scrollPageUp("500");
        EditPhotoDoctorWindow editPhoto = cardDoctor.clickButtonEditPhoto();
        editPhoto.verifyEditPhotoDoctorWindow();
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        editPhoto.uploadPhoto("src/test/resources/images/Photo 4mbJpeg.jpg");
        assertEquals("Допускаются файлы размером не выше 4Мб", cardDoctor.getTextNotification());
        assertEquals(srcOriginalPhoto, cardDoctor.getSrcPhoto());
        assertEquals(srcOriginalPhoto, DataBaseQuery.selectInfoDoctor(DOCTOR, DOCTOR_SPECIALIZATION).getPhoto_uri());
    }

    @Feature("Замена фотографии врачу")
    @Story("Замена фотографии врачу с файлом весом более 4mb")
    @DisplayName("Замена фотографии врачу с файлом весом более 4mb")
    @Test
    void changePhotoDoctorWeightMoreThan4mb() {
        cardDoctor.scrollPageUp("500");
        EditPhotoDoctorWindow editPhoto = cardDoctor.clickButtonEditPhoto();
        editPhoto.verifyEditPhotoDoctorWindow();
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        editPhoto.uploadPhoto("src/test/resources/images/Photo 6,8mbJpeg.jpg");
        assertEquals("Допускаются файлы размером не выше 4Мб", cardDoctor.getTextNotification());
        assertEquals(srcOriginalPhoto, cardDoctor.getSrcPhoto());
        assertEquals(srcOriginalPhoto, DataBaseQuery.selectInfoDoctor(DOCTOR, DOCTOR_SPECIALIZATION).getPhoto_uri());
    }

    @Feature("Замена фотографии врачу")
    @Story("Замена фотографии врачу с не валидным файлом")
    @DisplayName("Замена фотографии врачу с не валидным файлом")
    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/files/Оферта,Политика обработки docx.docx", "src/test/resources/files/Оферта, Политика обработки .xlsx.xlsx", "src/test/resources/files/Оферта.pdf"})
    void changePhotoDoctorInvalidFormat(String path) {
        cardDoctor.scrollPageUp("500");
        EditPhotoDoctorWindow editPhoto = cardDoctor.clickButtonEditPhoto();
        editPhoto.verifyEditPhotoDoctorWindow();
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
        cardDoctor.scrollPageUp("500");
        EditPhotoDoctorWindow editPhoto = cardDoctor.clickButtonEditPhoto();
        editPhoto.verifyEditPhotoDoctorWindow()
                .closeWindowEditPhoto();
        assertFalse(editPhoto.isWindowAppear());
    }

    @Feature("Замена фотографии врачу")
    @Story("Успешное удаление фотографии врача")
    @DisplayName("Успешное удаление фотографии врача")
    @ExtendWith(AddPhotoDoctor.class)
    @Test
    void deletePhoto() {
        cardDoctor.scrollPageUp("500");
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
    @ExtendWith(DeletePhotoDoctor.class)
    @Test
    void deleteDefaultPhoto() {
        cardDoctor.scrollPageUp("500");
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        cardDoctor.clickButtonDeletePhoto();
        assertEquals("Конфликт (409)", cardDoctor.getTextNotification());
        assertEquals(srcOriginalPhoto, cardDoctor.getSrcPhoto());
    }

    @Feature("Информация о враче")
    @Story("Успешное добавление раздела в инфо о враче")
    @DisplayName("Успешное добавление раздела в инфо о враче")
    @ExtendWith(DeleteSection.class)
    @Test
    void addSection() {
        AddInfoDoctorWindow addInfoDoctorWindow = cardDoctor.clickButtonAddSection();
        addInfoDoctorWindow.verifyAddSectionDoctorWindow()
                .fillFieldText(generateWord())
                .clickButtonSaveValue();
        Section section = cardDoctor.getSection();
        assertTrue(cardDoctor.isExistSection());
        assertEquals(word, section.getTextSection());
        assertEquals(word, DataBaseQuery.selectSection(DeleteSection.getDoctorId()).getTitle());
        assertEquals("DOCTOR_GROUP_EXPERTISE_CREATED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Информация о враче")
    @Story("Добавление пустого раздела в инфо о враче")
    @DisplayName("Добавление пустого раздела в инфо о враче")
    @ExtendWith(DeleteSection.class)
    @Test
    void addSectionEmptyField() {
        AddInfoDoctorWindow addInfoDoctorWindow = cardDoctor.clickButtonAddSection();
        addInfoDoctorWindow.verifyAddSectionDoctorWindow()
                .clickButtonSaveValue();
        addInfoDoctorWindow.clickCancelButtonAddSectionDoctor();
        assertEquals("Неверный запрос (400)", cardDoctor.getTextNotification());
        assertFalse(cardDoctor.isExistSection());
        assertNull(DataBaseQuery.selectSection(DeleteSection.getDoctorId()));
    }

    @Feature("Информация о враче")
    @Story("Отмена добавления раздела в инфо о враче и зануление полей")
    @DisplayName("Отмена добавления раздела в инфо о враче и зануление полей")
    @Test
    void cancelAddSection() {
        AddInfoDoctorWindow addInfoDoctorWindow = cardDoctor.clickButtonAddSection();
        addInfoDoctorWindow.verifyAddSectionDoctorWindow()
                .fillFieldText(generateWord());
        addInfoDoctorWindow.clickCancelButtonAddSectionDoctor();
        assertFalse(addInfoDoctorWindow.isWindowAppearSection());
        cardDoctor.clickButtonAddSection();
        assertEquals("", addInfoDoctorWindow.getValueField());
    }

    @Feature("Информация о враче")
    @Story("Успешное редактирование раздела в инфо о враче")
    @DisplayName("Успешное редактирование раздела в инфо о враче")
    @ExtendWith(AddDeleteSection.class)
    @Test
    void editSection() {
        Section section = cardDoctor.getSection();
        section.verifySection()
                .clickButtonEditSaveTitle()
                .fillTitleField(generateWord())
                .clickButtonEditSaveTitle()
                .getSection().shouldHave(Condition.text(word));
        assertEquals(word, section.getTextSection());
        assertEquals(word, DataBaseQuery.selectSection(AddDeleteSection.getDoctorId()).getTitle());
        assertEquals("DOCTOR_GROUP_EXPERTISE_CHANGED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Информация о враче")
    @Story("Редактирование раздела в инфо о враче с пустым полем")
    @DisplayName("Редактирование раздела в инфо о враче с пустым полем")
    @ExtendWith(AddDeleteSection.class)
    @Test
    void editSectionEmptyField() {
        Section section = cardDoctor.getSection();
        section.verifySection()
                .clickButtonEditSaveTitle()
                .clearTitleField()
                .clickButtonEditSaveTitle();
        assertEquals("Неверный запрос (400)", cardDoctor.getTextNotification());
        assertEquals(word, section.getTextSection());
        assertEquals(word, DataBaseQuery.selectSection(AddDeleteSection.getDoctorId()).getTitle());
    }

    @Feature("Информация о враче")
    @Story("Успешное удаление раздела в инфо о враче")
    @DisplayName("Успешное удаление раздела в инфо о враче")
    @ExtendWith(AddSection.class)
    @Test
    void deleteSection() {
        Section section = cardDoctor.getSection();
        section.verifySection()
                .clickButtonDeleteTitle()
                .getSection().shouldNot(Condition.exist);
        cardDoctor.getEmptyListSection().shouldBe(Condition.visible);
        assertFalse(cardDoctor.isExistSection());
        assertTrue(cardDoctor.isExistsEmptyListSection());
        assertNull(DataBaseQuery.selectSection(AddSection.getDoctorId()));
        assertEquals("DOCTOR_GROUP_EXPERTISE_DELETED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Информация о враче")
    @Story("Успешное добавление описания к разделу в инфо о враче")
    @DisplayName("Успешное добавление описания к разделу в инфо о враче")
    @ExtendWith(DeleteDescription.class)
    @Test
    void addDescription() {
        Section section = cardDoctor.getSection();
        section.verifySection();
        AddInfoDoctorWindow addInfoDoctorWindow = section.clickButtonAddDescription();
        addInfoDoctorWindow.verifyAddDescriptionDoctorWindow()
                .fillFieldText(generateText())
                .clickButtonSaveValue();
        Description description = cardDoctor.getDescription();
        assertTrue(cardDoctor.isExistDescription());
        assertEquals(text, description.getTextDescription());
        assertEquals(text, DataBaseQuery.selectDescription(DeleteDescription.getSectionId()).getTitle());
        assertEquals("DOCTOR_EXPERTISE_CREATED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Информация о враче")
    @Story("Добавление пустого описания к разделу в инфо о враче")
    @DisplayName("Добавление пустого описания к разделу в инфо о враче")
    @ExtendWith(DeleteDescription.class)
    @Test
    void addDescriptionEmptyField() {
        Section section = cardDoctor.getSection();
        section.verifySection();
        AddInfoDoctorWindow addInfoDoctorWindow = section.clickButtonAddDescription();
        addInfoDoctorWindow.verifyAddDescriptionDoctorWindow()
                .clickButtonSaveValue();
        assertEquals("Неверный запрос (400)", cardDoctor.getTextNotification());
        assertFalse(cardDoctor.isExistDescription());
        assertNull(DataBaseQuery.selectDescription(DeleteDescription.getSectionId()));
    }

    @Feature("Информация о враче")
    @Story("Отмена добавления описания к разделу в инфо о враче и зануление полей")
    @DisplayName("Отмена добавления описания к разделу в инфо о враче и зануление полей")
    @ExtendWith(DeleteDescription.class)
    @Test
    void cancelAddDescription() {
        Section section = cardDoctor.getSection();
        section.verifySection();
        AddInfoDoctorWindow addInfoDoctorWindow = section.clickButtonAddDescription();
        addInfoDoctorWindow.verifyAddDescriptionDoctorWindow()
                .fillFieldText(generateText())
                .clickCancelButtonAddDescriptionDoctor();
        assertFalse(addInfoDoctorWindow.isWindowAppearDescription());
        section.clickButtonAddDescription();
        assertEquals("", addInfoDoctorWindow.getValueField());
    }

    @Feature("Информация о враче")
    @Story("Успешное редактирование описания к разделу в инфо о враче")
    @DisplayName("Успешное редактирование описания к разделу в инфо о враче")
    @ExtendWith(AddDeleteDescription.class)
    @Test
    void editDescription() {
        Description description = cardDoctor.getDescription();
        description.verifyDescription()
                .clickButtonEditSaveDescription()
                .fillDescriptionField(generateText())
                .clickButtonEditSaveDescription()
                .getDescription().shouldHave(Condition.text(text));
        assertEquals(text, description.getTextDescription());
        assertEquals(text, DataBaseQuery.selectDescription(AddDeleteDescription.getSectionId()).getTitle());
        assertEquals("DOCTOR_EXPERTISE_CHANGED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Информация о враче")
    @Story("Редактирование описания в инфо о враче с пустым полем")
    @DisplayName("Редактирование описания в инфо о враче с пустым полем")
    @ExtendWith(AddDeleteDescription.class)
    @Test
    void editDescriptionEmptyField() {
        Description description = cardDoctor.getDescription();
        description.verifyDescription()
                .clickButtonEditSaveDescription()
                .clearDescriptionField()
                .clickButtonEditSaveDescription();
        assertEquals("Неверный запрос (400)", cardDoctor.getTextNotification());
        assertEquals(text, description.getTextDescription());
        assertEquals(text, DataBaseQuery.selectDescription(AddDeleteDescription.getSectionId()).getTitle());
    }

    @Feature("Информация о враче")
    @Story("Успешное удаление описания к разделу в инфо о враче")
    @DisplayName("Успешное удаление описания к разделу в инфо о враче")
    @ExtendWith(AddDescription.class)
    @Test
    void deleteDescription() {
        Description description = cardDoctor.getDescription();
        description.verifyDescription()
                .clickButtonDeleteDescription()
                .getDescription().shouldNot(Condition.exist);
        cardDoctor.getEmptyListDescription().shouldBe(Condition.visible);
        assertFalse(cardDoctor.isExistDescription());
        assertTrue(cardDoctor.isExistsEmptyListDescription());
        assertNull(DataBaseQuery.selectDescription(AddDescription.getSectionId()));
        assertEquals("DOCTOR_EXPERTISE_DELETED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Отзывы о враче")
    @Story("Успешное добавление отзыва о врачу датой в текущем месяце")
    @DisplayName("Успешное добавление отзыва о врачу датой в текущем месяце")
    @ExtendWith(DeleteFeedback.class)
    @Test
    void addFeedbackCurrentMonth() {
        cardDoctor.scrollPageDown("500");
        AddFeedbackWindow addFeedbackWindow = cardDoctor.clickButtonAddFeedback();
        addFeedbackWindow.verifyAddFeedbackWindow()
                .fillFieldFio(generateNamePatient())
                .fillFieldTextFeedback(generateText());
        assertEquals(getDate("current",0,0,"dd.MM.yyyy"), addFeedbackWindow.getCurrentDateButton());
        Calendar calendar = addFeedbackWindow.openCalendarAddFeedback();
        calendar.verifyCalendar();
        assertEquals(getDate("current",0,0,"LLLL yyyy"), calendar.getNameCurrentMonth());
        calendar.clickDateActivation(getDay("current",null,0,2));
        assertFalse(calendar.isCalendarAppear());
        addFeedbackWindow.clickButtonPublishFeedbackButton();
        cardDoctor.checkSelectUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        assertTrue(cardDoctor.isExistFeedback());
        assertEquals("Отзыв успешно добавлен", cardDoctor.getTextNotification());
        assertEquals(getDate("current", 0,2,"dd MMMM yyyy"), feedback.getDateFeedback());
        assertEquals(namePatient, feedback.getNameAuthorFeedback());
        assertEquals(text, feedback.getTextFeedback());
        assertEquals(namePatient, DataBaseQuery.selectFeedback().getAuthor());
        assertEquals(text, DataBaseQuery.selectFeedback().getContent());
        assertEquals(false, DataBaseQuery.selectFeedback().getIs_published());
        assertEquals("FEEDBACK_CREATED_ADMIN_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
        assertTrue(cardDoctor.isSelectUnPublishedFeedback());
        assertFalse(cardDoctor.isSelectPublishedFeedback());
    }

    @Feature("Отзывы о враче")
    @Story("Успешное добавление отзыва о враче датой в следующем месяце")
    @DisplayName("Успешное добавление отзыва о враче датой в следующем месяце")
    @ExtendWith(DeleteFeedback.class)
    @Test
    void addFeedbackFutureMonth() {
        cardDoctor.scrollPageDown("500");
        AddFeedbackWindow addFeedbackWindow = cardDoctor.clickButtonAddFeedback();
        addFeedbackWindow.verifyAddFeedbackWindow()
                .fillFieldFio(generateNamePatient())
                .fillFieldTextFeedback(generateText());
        Calendar calendar = addFeedbackWindow.openCalendarAddFeedback();
        calendar.verifyCalendar()
                .switchFutureMonth();
        assertEquals(getDate("future",1,0,"LLLL yyyy"), calendar.getNameCurrentMonth());
        calendar.clickDateActivation(getDay("future",null,1,2));
        assertFalse(calendar.isCalendarAppear());
        addFeedbackWindow.clickButtonPublishFeedbackButton();
        cardDoctor.checkSelectUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        assertTrue(cardDoctor.isExistFeedback());
        assertEquals("Отзыв успешно добавлен", cardDoctor.getTextNotification());
        assertEquals(getDate("future", 1,2,"dd MMMM yyyy"), feedback.getDateFeedback());
        assertEquals(namePatient, feedback.getNameAuthorFeedback());
        assertEquals(text, feedback.getTextFeedback());
        assertEquals(namePatient, DataBaseQuery.selectFeedback().getAuthor());
        assertEquals(text, DataBaseQuery.selectFeedback().getContent());
        assertEquals(false, DataBaseQuery.selectFeedback().getIs_published());
        assertEquals("FEEDBACK_CREATED_ADMIN_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
        assertTrue(cardDoctor.isSelectUnPublishedFeedback());
        assertFalse(cardDoctor.isSelectPublishedFeedback());
    }

    @Feature("Отзывы о враче")
    @Story("Успешное добавление отзыва о враче датой в предыдущем месяце")
    @DisplayName("Успешное добавление отзыва о враче датой в предыдущем месяце")
    @ExtendWith(DeleteFeedback.class)
    @Test
    void addFeedbackPreviousMonth() {
        cardDoctor.scrollPageDown("500");
        AddFeedbackWindow addFeedbackWindow = cardDoctor.clickButtonAddFeedback();
        addFeedbackWindow.verifyAddFeedbackWindow()
                .fillFieldFio(generateNamePatient())
                .fillFieldTextFeedback(generateText());
        Calendar calendar = addFeedbackWindow.openCalendarAddFeedback();
        calendar.verifyCalendar()
                .switchPreviousMonth();
        assertEquals(getDate("previous",1,0,"LLLL yyyy"), calendar.getNameCurrentMonth());
        calendar.clickDateActivation(getDay("previous",null,1,2));
        assertFalse(calendar.isCalendarAppear());
        addFeedbackWindow.clickButtonPublishFeedbackButton();
        cardDoctor.checkSelectUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        assertTrue(cardDoctor.isExistFeedback());
        assertEquals("Отзыв успешно добавлен", cardDoctor.getTextNotification());
        assertEquals(getDate("previous", 1,2,"dd MMMM yyyy"), feedback.getDateFeedback());
        assertEquals(namePatient, feedback.getNameAuthorFeedback());
        assertEquals(text, feedback.getTextFeedback());
        assertEquals(namePatient, DataBaseQuery.selectFeedback().getAuthor());
        assertEquals(text, DataBaseQuery.selectFeedback().getContent());
        assertEquals(false, DataBaseQuery.selectFeedback().getIs_published());
        assertEquals("FEEDBACK_CREATED_ADMIN_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
        assertTrue(cardDoctor.isSelectUnPublishedFeedback());
        assertFalse(cardDoctor.isSelectPublishedFeedback());
    }

    @Feature("Отзывы о враче")
    @Story("Успешное добавление отзыва о враче текущей датой")
    @DisplayName("Успешное добавление отзыва о враче текущей датой")
    @ExtendWith(DeleteFeedback.class)
    @Test
    void addFeedbackTodayNotUseCalendar() {
        cardDoctor.scrollPageDown("500");
        AddFeedbackWindow addFeedbackWindow = cardDoctor.clickButtonAddFeedback();
        addFeedbackWindow.verifyAddFeedbackWindow()
                .fillFieldFio(generateNamePatient())
                .fillFieldTextFeedback(generateText())
                .clickButtonPublishFeedbackButton();
        cardDoctor.checkSelectUnpublishedFeedback();
        cardDoctor.checkNoSelectPublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        assertTrue(cardDoctor.isExistFeedback());
        assertEquals("Отзыв успешно добавлен", cardDoctor.getTextNotification());
        assertEquals(getDate("current",0,0,"dd MMMM yyyy"), feedback.getDateFeedback());
        assertEquals(namePatient, feedback.getNameAuthorFeedback());
        assertEquals(text, feedback.getTextFeedback());
        assertEquals(namePatient, DataBaseQuery.selectFeedback().getAuthor());
        assertEquals(text, DataBaseQuery.selectFeedback().getContent());
        assertEquals(false, DataBaseQuery.selectFeedback().getIs_published());
        assertEquals("FEEDBACK_CREATED_ADMIN_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
        assertTrue(cardDoctor.isSelectUnPublishedFeedback());
        assertFalse(cardDoctor.isSelectPublishedFeedback());
    }

    @Feature("Отзывы о враче")
    @Story("Очистка поля ФИО через кнопку в окне добавления отзыва")
    @DisplayName("Очистка поля ФИО через кнопку в окне добавления отзыва")
    @Test
    void clearFioFieldWindowAddFeedbackThroughButtonClear() {
        cardDoctor.scrollPageDown("500");
        AddFeedbackWindow addFeedbackWindow = cardDoctor.clickButtonAddFeedback();
        addFeedbackWindow.verifyAddFeedbackWindow()
                .fillFieldFio(generateNamePatient())
                .clickClearButtonFioField();
        assertEquals("", addFeedbackWindow.getValueFioField());
    }

    @Feature("Отзывы о враче")
    @Story("Сброс значений полей в окне добавления отзыва при закрытии окна")
    @DisplayName("Сброс значений полей в окне добавления отзыва при закрытии окна")
    @Test
    void closeWindowAddNewFeedback() {
        cardDoctor.scrollPageDown("500");
        AddFeedbackWindow addFeedbackWindow = cardDoctor.clickButtonAddFeedback();
        addFeedbackWindow.verifyAddFeedbackWindow()
                .fillFieldFio(generateNamePatient())
                .fillFieldTextFeedback(generateText())
                .closeWindowAddFeedback();
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
        cardDoctor.scrollPageDown("500");
        AddFeedbackWindow addFeedbackWindow = cardDoctor.clickButtonAddFeedback();
        addFeedbackWindow.verifyAddFeedbackWindow()
                .fillFieldTextFeedback(generateText());
        assertFalse(addFeedbackWindow.isEnabledPublishButton());
    }

    @Feature("Отзывы о враче")
    @Story("Добавление нового отзыва с пустым полем отзыва")
    @DisplayName("Добавление нового отзыва с пустым полем отзыва")
    @Test
    void addFeedbackEmptyFieldText() {
        cardDoctor.scrollPageDown("500");
        AddFeedbackWindow addFeedbackWindow = cardDoctor.clickButtonAddFeedback();
        addFeedbackWindow.verifyAddFeedbackWindow()
                .fillFieldFio(generateNamePatient());
        assertFalse(addFeedbackWindow.isEnabledPublishButton());
    }

    @Feature("Отзывы о враче")
    @Story("Успешное редактирование неопубликованного отзыва о враче")
    @DisplayName("Успешное редактирование неопубликованного отзыва о враче")
    @ExtendWith(AddUnpublishedFeedback.class)
    @Test
    void editUnpublishedFeedback() {
        cardDoctor.scrollPageDown("500");
        cardDoctor.checkSelectPublishedFeedback();
        cardDoctor.checkNoSelectUnpublishedFeedback();
        cardDoctor.clickButtonUnpublishedFeedback();
        cardDoctor.checkNoSelectPublishedFeedback();
        cardDoctor.checkSelectUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.verifyFeedbackUnpublished();
        EditFeedbackWindow editFeedback = feedback.clickButtonEditFeedback();
        editFeedback.verifyChangeFeedbackWindow()
                .fillFieldText(generateText())
                .clickButtonSaveChanges();
        feedback.verifyFeedbackUnpublished();
        assertEquals("Отзыв успешно изменен", cardDoctor.getTextNotification());
        assertEquals(getDate("current",0,0,"dd MMMM yyyy"), feedback.getDateFeedback());
        assertEquals(namePatient, feedback.getNameAuthorFeedback());
        assertEquals(text, feedback.getTextFeedback());
        assertEquals(namePatient, DataBaseQuery.selectFeedback().getAuthor());
        assertEquals(text, DataBaseQuery.selectFeedback().getContent());
        assertEquals(false, DataBaseQuery.selectFeedback().getIs_published());
        assertEquals("FEEDBACK_CHANGED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
        assertTrue(cardDoctor.isSelectUnPublishedFeedback());
        assertFalse(cardDoctor.isSelectPublishedFeedback());
    }

    @Feature("Отзывы о враче")
    @Story("Успешная публикация неопубликованного отзыва о враче")
    @DisplayName("Успешная публикация неопубликованного отзыва о враче")
    @ExtendWith(AddUnpublishedFeedback.class)
    @Test
    void publicationUnpublishedFeedback() {
        cardDoctor.scrollPageDown("500");
        cardDoctor.clickButtonUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.verifyFeedbackUnpublished()
                .clickButtonPublicationFeedback()
                .verifyFeedbackPublished();
        assertEquals("Отзыв успешно изменен", cardDoctor.getTextNotification());
        assertEquals(getDate("current",0,0,"dd MMMM yyyy"), feedback.getDateFeedback());
        assertEquals(namePatient, feedback.getNameAuthorFeedback());
        assertEquals(text, feedback.getTextFeedback());
        assertEquals(namePatient, DataBaseQuery.selectFeedback().getAuthor());
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
        cardDoctor.scrollPageDown("500");
        cardDoctor.checkSelectPublishedFeedback();
        cardDoctor.checkNoSelectUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.verifyFeedbackPublished();
        EditFeedbackWindow editFeedback = feedback.clickButtonEditFeedback();
        editFeedback.verifyChangeFeedbackWindow()
                .fillFieldText(generateText())
                .clickButtonSaveChanges();
        feedback.verifyFeedbackPublished();
        assertEquals("Отзыв успешно изменен", cardDoctor.getTextNotification());
        assertEquals(getDate("current",0,0,"dd MMMM yyyy"), feedback.getDateFeedback());
        assertEquals(namePatient, feedback.getNameAuthorFeedback());
        assertEquals(text, feedback.getTextFeedback());
        assertEquals(namePatient, DataBaseQuery.selectFeedback().getAuthor());
        assertEquals(text, DataBaseQuery.selectFeedback().getContent());
        assertEquals(true, DataBaseQuery.selectFeedback().getIs_published());
        assertEquals("FEEDBACK_CHANGED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
        assertFalse(cardDoctor.isSelectUnPublishedFeedback());
        assertTrue(cardDoctor.isSelectPublishedFeedback());
    }

    @Feature("Отзывы о враче")
    @Story("Успешное снятие с публикации опубликованного отзыва о враче")
    @DisplayName("Успешное снятие с публикации опубликованного отзыва о враче")
    @ExtendWith(AddPublishedDeleteFeedback.class)
    @Test
    void withdrawalPublicationFeedback() {
        cardDoctor.scrollPageDown("500");
        Feedback feedback = cardDoctor.getFeedback();
        feedback.verifyFeedbackPublished()
                .clickButtonWithdrawalPublication()
                .verifyFeedbackUnpublished();
        assertEquals("Отзыв успешно изменен", cardDoctor.getTextNotification());
        assertEquals(getDate("current",0,0,"dd MMMM yyyy"), feedback.getDateFeedback());
        assertEquals(namePatient, feedback.getNameAuthorFeedback());
        assertEquals(text, feedback.getTextFeedback());
        assertEquals(namePatient, DataBaseQuery.selectFeedback().getAuthor());
        assertEquals(text, DataBaseQuery.selectFeedback().getContent());
        assertEquals(false, DataBaseQuery.selectFeedback().getIs_published());
        assertTrue(cardDoctor.isSelectUnPublishedFeedback());
        assertFalse(cardDoctor.isSelectPublishedFeedback());
    }

    @Feature("Отзывы о враче")
    @Story("Сохранение значений полей в окне редактирования отзыва после закрытия окна")
    @DisplayName("Сохранение значений полей в окне редактирования отзыва после закрытия окна")
    @ExtendWith(AddUnpublishedFeedback.class)
    @Test
    void closeWindowEditFeedback() {
        cardDoctor.scrollPageDown("500");
        cardDoctor.clickButtonUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        EditFeedbackWindow editFeedback = feedback.clickButtonEditFeedback();
        editFeedback.verifyChangeFeedbackWindow()
                .fillFieldText(generateNamePatient())
                .closeWindowChangeFeedback();
        assertFalse(editFeedback.isWindowAppear());
        feedback.clickButtonEditFeedback();
        assertEquals(text, editFeedback.getValueTextFeedbackField());
        assertEquals(text, DataBaseQuery.selectFeedback().getContent());
    }

    @Feature("Отзывы о враче")
    @Story("Редактирование отзыва с пустым полем отзыва")
    @DisplayName("Редактирование отзыва с пустым полем отзыва")
    @ExtendWith(AddUnpublishedFeedback.class)
    @Test
    void editFeedbackEmptyFieldText() {
        cardDoctor.scrollPageDown("500");
        cardDoctor.clickButtonUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        EditFeedbackWindow editFeedback = feedback.clickButtonEditFeedback();
        editFeedback.verifyChangeFeedbackWindow()
                .clearTextField();
        assertFalse(editFeedback.isEnabledSaveButton());
    }

    @Feature("Отзывы о враче")
    @Story("Успешное удаление неопубликованного отзыва о враче")
    @DisplayName("Успешное удаление неопубликованного отзыва о враче")
    @ExtendWith(AddUnpublishedFeedback.class)
    @Test
    void deleteUnpublishedFeedback() {
        cardDoctor.scrollPageDown("500");
        cardDoctor.clickButtonUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.verifyFeedbackUnpublished()
                .clickButtonDeleteFeedback();
        assertEquals("Отзыв успешно удален", cardDoctor.getTextNotification());
        assertFalse(cardDoctor.isExistFeedback());
        assertNull(DataBaseQuery.selectFeedback());
        assertEquals("FEEDBACK_DELETED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Отзывы о враче")
    @Story("Сортировка неопубликованных отзывов о враче")
    @DisplayName("Сортировка неопубликованных отзывов о враче")
    @ExtendWith(AddTwoFeedback.class)
    @Test
    void sortingUnpublishedFeedbacks() {
        cardDoctor.scrollPageDown("500");
        cardDoctor.clickButtonUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        assertTrue(cardDoctor.isSortingNewAppear());
        String dateFeedbackToday = feedback.getDateFeedbackByIndex(0);
        String dateFeedbackYesterday = feedback.getDateFeedbackByIndex(1);
        cardDoctor.clickSortingFeedbackNew();
        assertTrue(cardDoctor.isSortingOldAppear());
        assertEquals(dateFeedbackYesterday, feedback.getDateFeedbackByIndex(0));
        assertEquals(dateFeedbackToday, feedback.getDateFeedbackByIndex(1));
        cardDoctor.clickSortingFeedbackOld();
        assertTrue(cardDoctor.isSortingNewAppear());
        assertEquals(dateFeedbackToday, feedback.getDateFeedbackByIndex(0));
        assertEquals(dateFeedbackYesterday, feedback.getDateFeedbackByIndex(1));
    }

    @Story("Закрытие навигационного меню")
    @DisplayName("Закрытие навигационного меню")
    @Test
    void closeNavigateMenu() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.verifyNavigateMenu()
                .closeNavigateMenu();
        navigateMenu.getTabFeedbackNavigateMenu().shouldNotBe(Condition.visible);
        assertFalse(navigateMenu.isNavigateMenuDisplayed());
    }
}

