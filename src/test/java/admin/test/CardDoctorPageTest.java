package admin.test;

import admin.pages.DoctorsPage.CardDoctorPage.*;
import admin.pages.DoctorsPage.DoctorsPage;
import admin.pages.Calendar.Calendar;
import admin.utils.preparationDataTests.doctors.*;
import admin.utils.otherUtils.*;
import admin.utils.dbUtils.DataBaseQuery;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static admin.data.TestData.DataTest.*;
import static admin.data.TestData.UserData.*;
import static admin.utils.otherUtils.DataGenerator.*;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Карточка врача")
@DisplayName("Карточка врача")
public class CardDoctorPageTest extends BaseTest {

    private CardDoctorPage cardDoctor;

    @BeforeAll
    static void setUpAuth() {
        BaseTest.openAdminPanel(LOGIN_ADMIN, PASSWORD_ADMIN);
        DoctorsPage doctorsPage = new DoctorsPage();
        doctorsPage.clickButtonEditInfoDoctor(DOCTOR_SPECIALIZATION,DOCTOR);
    }

    @BeforeEach
    void setUp() {
        Selenide.refresh();
        cardDoctor = new CardDoctorPage();
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
        cardDoctor.verifyCardDoctorPage();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenPhoto();
        navigateMenu.closeNavigateMenu();
        EditPhotoDoctorWindow editPhoto = cardDoctor.clickButtonEditPhoto();
        editPhoto.verifyEditPhotoDoctorWindow();
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        editPhoto.uploadPhoto(path);
        Selenide.sleep(4000);
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
        cardDoctor.verifyCardDoctorPage();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenPhoto();
        navigateMenu.closeNavigateMenu();
        EditPhotoDoctorWindow editPhoto = cardDoctor.clickButtonEditPhoto();
        editPhoto.verifyEditPhotoDoctorWindow();
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        editPhoto.uploadPhoto("src/test/resources/Photo 3,2mbPng.png");
        Selenide.sleep(5000);
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
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenPhoto();
        navigateMenu.closeNavigateMenu();
        EditPhotoDoctorWindow editPhoto = cardDoctor.clickButtonEditPhoto();
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        editPhoto.uploadPhoto("src/test/resources/Photo 6,8mbJpeg.jpg");
        assertEquals("Допускаются файлы размером не выше 4Мб", cardDoctor.getNotification());
        assertEquals(srcOriginalPhoto, cardDoctor.getSrcPhoto());
        assertEquals(srcOriginalPhoto, DataBaseQuery.selectInfoDoctor(DOCTOR, DOCTOR_SPECIALIZATION).getPhoto_uri());
    }

    @Feature("Замена фотографии врачу")
    @Story("Замена фотографии врачу с не валидным файлом")
    @DisplayName("Замена фотографии врачу с не валидным файлом")
    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/Оферта,Политика обработки docx.docx", "src/test/resources/Оферта, Политика обработки .xlsx.xlsx", "src/test/resources/Оферта.pdf"})
    void changePhotoDoctorInvalidFormat(String path) {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenPhoto();
        navigateMenu.closeNavigateMenu();
        EditPhotoDoctorWindow editPhoto = cardDoctor.clickButtonEditPhoto();
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        editPhoto.uploadPhoto(path);
        assertEquals("Допускаются файлы с расширением jpg jpeg png", cardDoctor.getNotification());
        assertEquals(srcOriginalPhoto, cardDoctor.getSrcPhoto());
        assertEquals(srcOriginalPhoto, DataBaseQuery.selectInfoDoctor(DOCTOR, DOCTOR_SPECIALIZATION).getPhoto_uri());
    }


    @Feature("Замена фотографии врачу")
    @Story("Закрытие окна смены фотографии")
    @DisplayName("Закрытие окна смены фотографии")
    @Test
    void closeWindowEditPhoto() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenPhoto();
        navigateMenu.closeNavigateMenu();
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
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenPhoto();
        navigateMenu.closeNavigateMenu();
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        cardDoctor.clickButtonDeletePhoto();
        Selenide.sleep(3000);
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
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenPhoto();
        navigateMenu.closeNavigateMenu();
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        cardDoctor.clickButtonDeletePhoto();
        assertEquals("Конфликт (409)", cardDoctor.getNotification());
        assertEquals(srcOriginalPhoto, cardDoctor.getSrcPhoto());
    }

    @Feature("Информация о враче")
    @Story("Успешное добавление раздела в инфо о враче")
    @DisplayName("Успешное добавление раздела в инфо о враче")
    @ExtendWith(DeleteSectionDecorator.class)
    @Test
    void addSection() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenDescription();
        navigateMenu.closeNavigateMenu();
        AddInfoDoctorWindow addInfoDoctorWindow = cardDoctor.clickButtonAddSection();
        addInfoDoctorWindow.verifyAddSectionDoctorWindow();
        addInfoDoctorWindow.fillFieldText(generateWord());
        addInfoDoctorWindow.clickButtonSaveValue();
        Section section = cardDoctor.getSection();
        assertEquals(word, section.getSection());
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
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenDescription();
        navigateMenu.closeNavigateMenu();
        AddInfoDoctorWindow addInfoDoctorWindow = cardDoctor.clickButtonAddSection();
        addInfoDoctorWindow.clickButtonSaveValue();
        assertEquals("Неверный запрос (400)", cardDoctor.getNotification());
        assertFalse(cardDoctor.isExistSection());
        assertNull(DataBaseQuery.selectSection(DeleteSectionDecorator.getDoctorId()));
    }


    @Feature("Информация о враче")
    @Story("Отмена добавления раздела в инфо о враче и зануление полей")
    @DisplayName("Отмена добавления раздела в инфо о враче и зануление полей")
    @Test
    void cancelAddSection() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenDescription();
        navigateMenu.closeNavigateMenu();
        AddInfoDoctorWindow addInfoDoctorWindow = cardDoctor.clickButtonAddSection();
        addInfoDoctorWindow.fillFieldText(generateWord());
        addInfoDoctorWindow.clickCancelButtonAddSectionDoctor();
        assertFalse(addInfoDoctorWindow.isWindowAppearSection());
        cardDoctor.clickButtonAddSection();
        assertEquals("", addInfoDoctorWindow.getValueField());
    }


    @Feature("Информация о враче")
    @Story("Успешное редактирование раздела в инфо о враче")
    @DisplayName("Успешное редактирование раздела в инфо о враче")
    @ExtendWith(AddDeleteSectionDecorator.class)
    @Test
    void editSection() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenDescription();
        navigateMenu.closeNavigateMenu();
        Section section = cardDoctor.getSection();
        section.verifySection();
        section.clickButtonEditSaveTitle();
        section.fillTitleField(generateWord());
        section.clickButtonEditSaveTitle();
        Selenide.sleep(2000);
        assertEquals(word, section.getSection());
        assertEquals(word, DataBaseQuery.selectSection(AddDeleteSectionDecorator.getDoctorId()).getTitle());
        assertEquals("DOCTOR_GROUP_EXPERTISE_CHANGED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Информация о враче")
    @Story("Редактирование раздела в инфо о враче с пустым полем")
    @DisplayName("Редактирование раздела в инфо о враче с пустым полем")
    @ExtendWith(AddDeleteSectionDecorator.class)
    @Test
    void editSectionEmptyField() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenDescription();
        navigateMenu.closeNavigateMenu();
        Section section = cardDoctor.getSection();
        section.clickButtonEditSaveTitle();
        section.clearTitleField();
        section.clickButtonEditSaveTitle();
        assertEquals("Неверный запрос (400)", cardDoctor.getNotification());
    }


    @Feature("Информация о враче")
    @Story("Успешное удаление раздела в инфо о враче")
    @DisplayName("Успешное удаление раздела в инфо о враче")
    @ExtendWith(AddSectionDecorator.class)
    @Test
    void deleteSection() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenDescription();
        navigateMenu.closeNavigateMenu();
        Section section = cardDoctor.getSection();
        section.clickButtonDeleteTitle();
        Selenide.sleep(3000);
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
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenDescription();
        navigateMenu.closeNavigateMenu();
        Section section = cardDoctor.getSection();
        AddInfoDoctorWindow addInfoDoctorWindow = section.clickButtonAddDescription();
        addInfoDoctorWindow.verifyAddDescriptionDoctorWindow();
        addInfoDoctorWindow.fillFieldText(generateText());
        addInfoDoctorWindow.clickButtonSaveValue();
        Description description = cardDoctor.getDescription();
        assertEquals(text, description.getDescription());
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
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenDescription();
        navigateMenu.closeNavigateMenu();
        Section section = cardDoctor.getSection();
        AddInfoDoctorWindow addInfoDoctorWindow = section.clickButtonAddDescription();
        addInfoDoctorWindow.clickButtonSaveValue();
        assertEquals("Неверный запрос (400)", cardDoctor.getNotification());
        assertFalse(cardDoctor.isExistDescription());
        assertNull(DataBaseQuery.selectDescription(DeleteDescriptionDecorator.getSectionId()));
    }


    @Feature("Информация о враче")
    @Story("Отмена добавления описания к разделу в инфо о враче и зануление полей")
    @DisplayName("Отмена добавления описания к разделу в инфо о враче и зануление полей")
    @ExtendWith(DeleteDescriptionDecorator.class)
    @Test
    void cancelAddDescription() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenDescription();
        navigateMenu.closeNavigateMenu();
        Section section = cardDoctor.getSection();
        AddInfoDoctorWindow addInfoDoctorWindow = section.clickButtonAddDescription();
        addInfoDoctorWindow.fillFieldText(generateText());
        addInfoDoctorWindow.clickCancelButtonAddDescriptionDoctor();
        assertFalse(addInfoDoctorWindow.isWindowAppearDescription());
        section.clickButtonAddDescription();
        assertEquals("", addInfoDoctorWindow.getValueField());
    }


    @Feature("Информация о враче")
    @Story("Успешное редактирование описания к разделу в инфо о враче")
    @DisplayName("Успешное редактирование описания к разделу в инфо о враче")
    @ExtendWith(AddDeleteDescriptionDecorator.class)
    @Test
    void editDescription() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenDescription();
        navigateMenu.closeNavigateMenu();
        Description description = cardDoctor.getDescription();
        description.verifyDescription();
        description.clickButtonEditSaveDescription();
        description.fillDescriptionField(generateText());
        description.clickButtonEditSaveDescription();
        Selenide.sleep(2000);
        assertEquals(text, description.getDescription());
        assertEquals(text, DataBaseQuery.selectDescription(AddDeleteDescriptionDecorator.getSectionId()).getTitle());
        assertEquals("DOCTOR_EXPERTISE_CHANGED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Информация о враче")
    @Story("Редактирование описания в инфо о враче с пустым полем")
    @DisplayName("Редактирование описания в инфо о враче с пустым полем")
    @ExtendWith(AddDeleteDescriptionDecorator.class)
    @Test
    void editDescriptionEmptyField() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenDescription();
        navigateMenu.closeNavigateMenu();
        Description description = cardDoctor.getDescription();
        description.clickButtonEditSaveDescription();
        description.clearDescriptionField();
        description.clickButtonEditSaveDescription();
        assertEquals("Неверный запрос (400)", cardDoctor.getNotification());
    }

    @Feature("Информация о враче")
    @Story("Успешное удаление описания к разделу в инфо о враче")
    @DisplayName("Успешное удаление описания к разделу в инфо о враче")
    @ExtendWith(AddDescriptionDecorator.class)
    @Test
    void deleteDescription() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenDescription();
        navigateMenu.closeNavigateMenu();
        Description description = cardDoctor.getDescription();
        description.clickButtonDeleteDescription();
        Selenide.sleep(3000);
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
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
        AddFeedbackWindow addFeedbackWindow = cardDoctor.clickButtonAddFeedback();
        addFeedbackWindow.fillFieldFio(generateNamePatient());
        addFeedbackWindow.fillFieldTextFeedback(generateText());
        assertEquals(TestHelper.getCurrentDate(), addFeedbackWindow.getCurrentDateButton());
        Calendar calendar = addFeedbackWindow.openCalendarAddFeedback();
        calendar.clickDateActivation();
        addFeedbackWindow.clickButtonPublishFeedbackButton();
        cardDoctor.checkSelectUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        assertEquals("Отзыв успешно добавлен", cardDoctor.getNotification());
        assertEquals(TestHelper.generateFutureDateCurrentMonth(), feedback.getDateFeedback());
        assertEquals(namePatient, feedback.getAuthorFeedback());
        assertEquals(text, feedback.getTextFeedback());
        assertEquals(namePatient, DataBaseQuery.selectFeedback().getAuthor());
        assertEquals(text, DataBaseQuery.selectFeedback().getContent());
        assertEquals(false, DataBaseQuery.selectFeedback().getIs_published());
        assertEquals("FEEDBACK_CREATED_ADMIN_SUCCESS",DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
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
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
        AddFeedbackWindow addFeedbackWindow = cardDoctor.clickButtonAddFeedback();
        addFeedbackWindow.fillFieldFio(generateNamePatient());
        addFeedbackWindow.fillFieldTextFeedback(generateText());
        Calendar calendar = addFeedbackWindow.openCalendarAddFeedback();
        calendar.switchFutureMonth();
        calendar.clickDateActivation();
        addFeedbackWindow.clickButtonPublishFeedbackButton();
        cardDoctor.checkSelectUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        assertEquals("Отзыв успешно добавлен", cardDoctor.getNotification());
        assertEquals(TestHelper.getNextMonthDate(), feedback.getDateFeedback());
        assertEquals(namePatient, feedback.getAuthorFeedback());
        assertEquals(text, feedback.getTextFeedback());
        assertEquals(namePatient, DataBaseQuery.selectFeedback().getAuthor());
        assertEquals(text, DataBaseQuery.selectFeedback().getContent());
        assertEquals(false, DataBaseQuery.selectFeedback().getIs_published());
        assertEquals("FEEDBACK_CREATED_ADMIN_SUCCESS",DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
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
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
        AddFeedbackWindow addFeedbackWindow = cardDoctor.clickButtonAddFeedback();
        addFeedbackWindow.fillFieldFio(generateNamePatient());
        addFeedbackWindow.fillFieldTextFeedback(generateText());
        Calendar calendar = addFeedbackWindow.openCalendarAddFeedback();
        calendar.switchPreviousMonth();
        calendar.clickDateActivation();
        addFeedbackWindow.clickButtonPublishFeedbackButton();
        cardDoctor.checkSelectUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        assertEquals("Отзыв успешно добавлен", cardDoctor.getNotification());
        assertEquals(TestHelper.getPreviousMonthDate(), feedback.getDateFeedback());
        assertEquals(namePatient, feedback.getAuthorFeedback());
        assertEquals(text, feedback.getTextFeedback());
        assertEquals(namePatient, DataBaseQuery.selectFeedback().getAuthor());
        assertEquals(text, DataBaseQuery.selectFeedback().getContent());
        assertEquals(false, DataBaseQuery.selectFeedback().getIs_published());
        assertEquals("FEEDBACK_CREATED_ADMIN_SUCCESS",DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
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
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
        AddFeedbackWindow addFeedbackWindow = cardDoctor.clickButtonAddFeedback();
        addFeedbackWindow.verifyAddFeedbackWindow();
        addFeedbackWindow.fillFieldFio(generateNamePatient());
        addFeedbackWindow.fillFieldTextFeedback(generateText());
        addFeedbackWindow.clickButtonPublishFeedbackButton();
        cardDoctor.checkSelectUnpublishedFeedback();
        cardDoctor.checkNoSelectPublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        assertEquals("Отзыв успешно добавлен", cardDoctor.getNotification());
        assertEquals(TestHelper.getCurrentDateRu(), feedback.getDateFeedback());
        assertEquals(namePatient, feedback.getAuthorFeedback());
        assertEquals(text, feedback.getTextFeedback());
        assertEquals(namePatient, DataBaseQuery.selectFeedback().getAuthor());
        assertEquals(text, DataBaseQuery.selectFeedback().getContent());
        assertEquals(false, DataBaseQuery.selectFeedback().getIs_published());
        assertEquals("FEEDBACK_CREATED_ADMIN_SUCCESS",DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
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
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
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
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
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
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
        AddFeedbackWindow addFeedbackWindow = cardDoctor.clickButtonAddFeedback();
        addFeedbackWindow.fillFieldTextFeedback(generateText());
        assertFalse(addFeedbackWindow.isEnabledPublishButton());
    }


    @Feature("Отзывы о враче")
    @Story("Добавление нового отзыва с пустым полем отзыва")
    @DisplayName("Добавление нового отзыва с пустым полем отзыва")
    @Test
    void addFeedbackEmptyFieldText() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
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
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
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
        assertEquals("Отзыв успешно изменен", cardDoctor.getNotification());
        assertEquals(text, feedback.getTextFeedback());
        assertEquals(text, DataBaseQuery.selectFeedback().getContent());
        assertEquals(false, DataBaseQuery.selectFeedback().getIs_published());
        assertEquals("FEEDBACK_CHANGED_SUCCESS",DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
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
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
        cardDoctor.clickButtonUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.verifyFeedbackUnpublished();
        feedback.clickButtonPublicationFeedback();
        feedback.verifyFeedbackPublished();
        assertEquals("Отзыв успешно изменен", cardDoctor.getNotification());
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
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
        cardDoctor.checkSelectPublishedFeedback();
        cardDoctor.checkNoSelectUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.verifyFeedbackPublished();
        EditFeedbackWindow editFeedback = feedback.clickButtonEditFeedback();
        editFeedback.verifyChangeFeedbackWindow();
        editFeedback.fillFieldText(generateText());
        editFeedback.clickButtonSaveChanges();
        feedback.verifyFeedbackPublished();
        assertEquals("Отзыв успешно изменен", cardDoctor.getNotification());
        assertEquals(text, feedback.getTextFeedback());
        assertEquals(text, DataBaseQuery.selectFeedback().getContent());
        assertEquals(true, DataBaseQuery.selectFeedback().getIs_published());
        assertEquals("FEEDBACK_CHANGED_SUCCESS",DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
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
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
        Feedback feedback = cardDoctor.getFeedback();
        feedback.verifyFeedbackPublished();
        feedback.clickButtonWithdrawalPublication();
        feedback.verifyFeedbackUnpublished();
        assertEquals("Отзыв успешно изменен", cardDoctor.getNotification());
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
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
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
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
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
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
        cardDoctor.clickButtonUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.verifyFeedbackUnpublished();
        feedback.clickButtonDeleteFeedback();
        assertEquals("Отзыв успешно удален", cardDoctor.getNotification());
        assertFalse(cardDoctor.isExistFeedback());
        assertNull(DataBaseQuery.selectFeedback());
        assertEquals("FEEDBACK_DELETED_SUCCESS",DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Отзывы о враче")
    @Story("Сортировка неопубликованных отзывов о враче")
    @DisplayName("Сортировка неопубликованных отзывов о враче")
    @ExtendWith(AddTwoFeedbackDecorator.class)
    @Test
    void sortingUnpublishedFeedbacks() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.clickTabOpenFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
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
        assertFalse(navigateMenu.isNavigateMenuDisplayed());
    }

}



