package admin.test;

import admin.pages.BasePage.BasePage;
import admin.pages.DoctorsPage.CardDoctorPage.*;
import admin.pages.DoctorsPage.DoctorsPage;
import admin.pages.Calendar.Calendar;
import admin.utils.preparationDataTests.doctors.*;
import admin.utils.preparationDataTests.general.AllureDecorator;
import admin.utils.testUtils.*;
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
import static org.junit.jupiter.api.Assertions.*;

@Epic("Карточка врача")
@DisplayName("Карточка врача")
public class CardDoctorPageTest extends BaseTest {

    private BasePage basePage;
    private CardDoctorPage cardDoctor;

    @ExtendWith(AllureDecorator.class)

    @BeforeAll
    static void setUpAuth() {
        BrowserManager.openAdminPanel(LOGIN_ADMIN, PASSWORD_ADMIN);
        DoctorsPage doctorsPage = new DoctorsPage();
        doctorsPage.openCardDoctor(DOCTOR_SPECIALIZATION,DOCTOR);
    }

    @BeforeEach
    void setUp() {
        Selenide.refresh();
        cardDoctor = new CardDoctorPage();
        basePage = new BasePage();
    }

    @AfterAll
    static void closeWebDriver() {
        Selenide.closeWebDriver();
    }

    @Feature("Замена фотографии врачу")
    @Story("Успешная замена фотографии врачу в формате Jpeg и Png")
    @DisplayName("Успешная замена фотографии врачу в формате Jpeg и Png")
    @ExtendWith(DeletePhotoDoctorDecorator.class)
    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/Photo 3,7mbJpeg.jpg", "src/test/resources/Photo 3,2mbPng.png"})
    void changePhotoDoctor(String path) {
        cardDoctor.cardDoctorPage();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openPhoto();
        navigateMenu.closeNavigateMenu();
        EditPhotoDoctorWindow editPhoto = cardDoctor.openWindowEditPhoto();
        editPhoto.editPhotoDoctorWindow();
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        editPhoto.uploadPhoto(path);
        Selenide.sleep(3000);
        assertNotEquals(srcOriginalPhoto, cardDoctor.getSrcPhoto());
        assertNotEquals(srcOriginalPhoto, DataBaseQuery.selectInfoDoctor(DOCTOR, DOCTOR_SPECIALIZATION).getPhoto_uri());
        assertFalse(editPhoto.isWindowAppear());
    }


    @Feature("Замена фотографии врачу")
    @Story("Замена фотографии врачу с файлом весом более 4mb")
    @DisplayName("Замена фотографии врачу с файлом весом более 4mb")
    @Test
    void changePhotoDoctorWeightMoreThan4mb() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openPhoto();
        navigateMenu.closeNavigateMenu();
        EditPhotoDoctorWindow editPhoto = cardDoctor.openWindowEditPhoto();
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
        navigateMenu.openPhoto();
        navigateMenu.closeNavigateMenu();
        EditPhotoDoctorWindow editPhoto = cardDoctor.openWindowEditPhoto();
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
        navigateMenu.openPhoto();
        navigateMenu.closeNavigateMenu();
        EditPhotoDoctorWindow editPhoto = cardDoctor.openWindowEditPhoto();
        editPhoto.closeWindowEditPhoto();
        assertFalse(editPhoto.isWindowAppear());
    }

    @Feature("Замена фотографии врачу")
    @Story("Успешное удаление фотографии врача")
    @DisplayName("Успешное удаление фотографии врача")
    @ExtendWith(SetPhotoDoctorDecorator.class)
    @Test
    void deletePhoto() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openPhoto();
        navigateMenu.closeNavigateMenu();
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        cardDoctor.deletePhoto();
        Selenide.sleep(3000);
        assertNotEquals(srcOriginalPhoto, cardDoctor.getSrcPhoto());
        assertEquals(DEFAULT_PHOTO, cardDoctor.getSrcPhoto());
        assertEquals(DEFAULT_PHOTO, DataBaseQuery.selectInfoDoctor(DOCTOR, DOCTOR_SPECIALIZATION).getPhoto_uri());
    }

    @Feature("Замена фотографии врачу")
    @Story("Удаление дефолтной фотографии врача")
    @DisplayName("Удаление дефолтной фотографии врача")
    @ExtendWith(DeletePhotoDoctorDecorator.class)
    @Test
    void deleteDefaultPhoto() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openPhoto();
        navigateMenu.closeNavigateMenu();
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        cardDoctor.deletePhoto();
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
        navigateMenu.openDescription();
        navigateMenu.closeNavigateMenu();
        AddInfoDoctorWindow addInfoDoctorWindow = cardDoctor.openWindowAddSection();
        addInfoDoctorWindow.addInfoDoctorWindow();
        addInfoDoctorWindow.fillFieldText(SECTION);
        addInfoDoctorWindow.saveValue();
        Section section = cardDoctor.getSection();
        assertEquals(SECTION, section.getSection());
        assertEquals(SECTION, DataBaseQuery.selectSection(DeleteSectionDecorator.getDoctorId()).getTitle());
        assertTrue(cardDoctor.isExistSection());
        assertFalse(addInfoDoctorWindow.isWindowAppear());
    }

    @Feature("Информация о враче")
    @Story("Добавление пустого раздела в инфо о враче")
    @DisplayName("Добавление пустого раздела в инфо о враче")
    @ExtendWith(DeleteSectionDecorator.class)
    @Test
    void addSectionEmptyField() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openDescription();
        navigateMenu.closeNavigateMenu();
        AddInfoDoctorWindow addInfoDoctorWindow = cardDoctor.openWindowAddSection();
        addInfoDoctorWindow.saveValue();
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
        navigateMenu.openDescription();
        navigateMenu.closeNavigateMenu();
        AddInfoDoctorWindow addInfoDoctorWindow = cardDoctor.openWindowAddSection();
        addInfoDoctorWindow.fillFieldText(SECTION);
        addInfoDoctorWindow.cancelAddInfoDoctor();
        assertFalse(addInfoDoctorWindow.isWindowAppear());
        cardDoctor.openWindowAddSection();
        assertEquals("", addInfoDoctorWindow.getValueField());
    }


    @Feature("Информация о враче")
    @Story("Успешное редактирование раздела в инфо о враче")
    @DisplayName("Успешное редактирование раздела в инфо о враче")
    @ExtendWith(AddDeleteSectionDecorator.class)
    @Test
    void editSection() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openDescription();
        navigateMenu.closeNavigateMenu();
        Section section = cardDoctor.getSection();
        section.section();
        section.editSaveTitle();
        section.fillTitleField(NEW_SECTION);
        section.editSaveTitle();
        Selenide.sleep(2000);
        assertEquals(NEW_SECTION, section.getSection());
        assertEquals(NEW_SECTION, DataBaseQuery.selectSection(AddDeleteSectionDecorator.getDoctorId()).getTitle());
    }

    @Feature("Информация о враче")
    @Story("Редактирование раздела в инфо о враче с пустым полем")
    @DisplayName("Редактирование раздела в инфо о враче с пустым полем")
    @ExtendWith(AddDeleteSectionDecorator.class)
    @Test
    void editSectionEmptyField() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openDescription();
        navigateMenu.closeNavigateMenu();
        Section section = cardDoctor.getSection();
        section.editSaveTitle();
        section.clearTitleField();
        section.editSaveTitle();
        assertEquals("Неверный запрос (400)", cardDoctor.getNotification());
    }


    @Feature("Информация о враче")
    @Story("Успешное удаление раздела в инфо о враче")
    @DisplayName("Успешное удаление раздела в инфо о враче")
    @ExtendWith(AddSectionDecorator.class)
    @Test
    void deleteSection() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openDescription();
        navigateMenu.closeNavigateMenu();
        Section section = cardDoctor.getSection();
        section.deleteTitle();
        Selenide.sleep(3000);
        assertFalse(cardDoctor.isExistSection());
        assertTrue(cardDoctor.isExistsEmptyListSection());
        assertNull(DataBaseQuery.selectSection(AddSectionDecorator.getDoctorId()));
    }


    @Feature("Информация о враче")
    @Story("Успешное добавление описания к разделу в инфо о враче")
    @DisplayName("Успешное добавление описания к разделу в инфо о враче")
    @ExtendWith(DeleteDescriptionDecorator.class)
    @Test
    void addDescription() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openDescription();
        navigateMenu.closeNavigateMenu();
        Section section = cardDoctor.getSection();
        AddInfoDoctorWindow addInfoDoctorWindow = section.openWindowAddDescription();
        addInfoDoctorWindow.addInfoDoctorWindow();
        addInfoDoctorWindow.fillFieldText(DESCRIPTION);
        addInfoDoctorWindow.saveValue();
        Description description = cardDoctor.getDescription();
        assertEquals(DESCRIPTION, description.getDescription());
        assertEquals(DESCRIPTION, DataBaseQuery.selectDescription(DeleteDescriptionDecorator.getSectionId()).getTitle());
        assertTrue(cardDoctor.isExistDescription());
        assertFalse(addInfoDoctorWindow.isWindowAppear());
    }

    @Feature("Информация о враче")
    @Story("Добавление пустого описания к разделу в инфо о враче")
    @DisplayName("Добавление пустого описания к разделу в инфо о враче")
    @ExtendWith(DeleteDescriptionDecorator.class)
    @Test
    void addDescriptionEmptyField() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openDescription();
        navigateMenu.closeNavigateMenu();
        Section section = cardDoctor.getSection();
        AddInfoDoctorWindow addInfoDoctorWindow = section.openWindowAddDescription();
        addInfoDoctorWindow.saveValue();
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
        navigateMenu.openDescription();
        navigateMenu.closeNavigateMenu();
        Section section = cardDoctor.getSection();
        AddInfoDoctorWindow addInfoDoctorWindow = section.openWindowAddDescription();
        addInfoDoctorWindow.fillFieldText(DESCRIPTION);
        addInfoDoctorWindow.cancelAddInfoDoctor();
        assertFalse(addInfoDoctorWindow.isWindowAppear());
        section.openWindowAddDescription();
        assertEquals("", addInfoDoctorWindow.getValueField());
    }


    @Feature("Информация о враче")
    @Story("Успешное редактирование описания к разделу в инфо о враче")
    @DisplayName("Успешное редактирование описания к разделу в инфо о враче")
    @ExtendWith(AddDeleteDescriptionDecorator.class)
    @Test
    void editDescription() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openDescription();
        navigateMenu.closeNavigateMenu();
        Description description = cardDoctor.getDescription();
        description.description();
        description.editSaveDescription();
        description.fillDescriptionField(NEW_DESCRIPTION);
        description.editSaveDescription();
        Selenide.sleep(2000);
        assertEquals(NEW_DESCRIPTION, description.getDescription());
        assertEquals(NEW_DESCRIPTION, DataBaseQuery.selectDescription(AddDeleteDescriptionDecorator.getSectionId()).getTitle());
    }

    @Feature("Информация о враче")
    @Story("Редактирование описания в инфо о враче с пустым полем")
    @DisplayName("Редактирование описания в инфо о враче с пустым полем")
    @ExtendWith(AddDeleteDescriptionDecorator.class)
    @Test
    void editDescriptionEmptyField() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openDescription();
        navigateMenu.closeNavigateMenu();
        Description description = cardDoctor.getDescription();
        description.editSaveDescription();
        description.clearDescriptionField();
        description.editSaveDescription();
        assertEquals("Неверный запрос (400)", cardDoctor.getNotification());
    }

    @Feature("Информация о враче")
    @Story("Успешное удаление описания к разделу в инфо о враче")
    @DisplayName("Успешное удаление описания к разделу в инфо о враче")
    @ExtendWith(AddDescriptionDecorator.class)
    @Test
    void deleteDescription() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openDescription();
        navigateMenu.closeNavigateMenu();
        Description description = cardDoctor.getDescription();
        description.deleteDescription();
        Selenide.sleep(3000);
        assertFalse(cardDoctor.isExistDescription());
        assertTrue(cardDoctor.isExistsEmptyListDescription());
        assertNull(DataBaseQuery.selectDescription(AddDescriptionDecorator.getSectionId()));
    }


    @Feature("Отзывы о враче")
    @Story("Успешное добавление отзыва о врачу датой в текущем месяце")
    @DisplayName("Успешное добавление отзыва о врачу датой в текущем месяце")
    @ExtendWith(DeleteFeedbackDecorator.class)
    @Test
    void addFeedbackCurrentMonth() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
        AddFeedbackWindow addFeedbackWindow = cardDoctor.openWindowAddFeedback();
        addFeedbackWindow.fillFieldFio(NAME_PATIENT);
        addFeedbackWindow.fillFieldTextFeedback(FEEDBACK);
        assertEquals(DataHelper.getCurrentDate(), addFeedbackWindow.getValuesButtonToday());
        Calendar calendar = addFeedbackWindow.openCalendarAddFeedback();
        calendar.selectDateActivation();
        addFeedbackWindow.publishFeedbackButton();
        cardDoctor.selectedUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        assertEquals("Отзыв успешно добавлен", cardDoctor.getNotification());
        assertEquals(DataHelper.generateFutureDateCurrentMonth(), feedback.getDateFeedback());
        assertEquals(NAME_PATIENT, feedback.getAuthorFeedback());
        assertEquals(FEEDBACK, feedback.getTextFeedback());
        assertEquals(NAME_PATIENT, DataBaseQuery.selectFeedback().getAuthor());
        assertEquals(FEEDBACK, DataBaseQuery.selectFeedback().getContent());
        assertEquals(false, DataBaseQuery.selectFeedback().getIs_published());
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
        navigateMenu.openFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
        AddFeedbackWindow addFeedbackWindow = cardDoctor.openWindowAddFeedback();
        addFeedbackWindow.fillFieldFio(NAME_PATIENT);
        addFeedbackWindow.fillFieldTextFeedback(FEEDBACK);
        Calendar calendar = addFeedbackWindow.openCalendarAddFeedback();
        calendar.switchFutureMonth();
        calendar.selectDateActivation();
        addFeedbackWindow.publishFeedbackButton();
        cardDoctor.selectedUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        assertEquals("Отзыв успешно добавлен", cardDoctor.getNotification());
        assertEquals(DataHelper.getNextMonthDate(), feedback.getDateFeedback());
        assertEquals(NAME_PATIENT, feedback.getAuthorFeedback());
        assertEquals(FEEDBACK, feedback.getTextFeedback());
        assertEquals(NAME_PATIENT, DataBaseQuery.selectFeedback().getAuthor());
        assertEquals(FEEDBACK, DataBaseQuery.selectFeedback().getContent());
        assertEquals(false, DataBaseQuery.selectFeedback().getIs_published());
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
        navigateMenu.openFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
        AddFeedbackWindow addFeedbackWindow = cardDoctor.openWindowAddFeedback();
        addFeedbackWindow.fillFieldFio(NAME_PATIENT);
        addFeedbackWindow.fillFieldTextFeedback(FEEDBACK);
        Calendar calendar = addFeedbackWindow.openCalendarAddFeedback();
        calendar.switchPreviousMonth();
        calendar.selectDateActivation();
        addFeedbackWindow.publishFeedbackButton();
        cardDoctor.selectedUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        assertEquals("Отзыв успешно добавлен", cardDoctor.getNotification());
        assertEquals(DataHelper.getPreviousMonthDate(), feedback.getDateFeedback());
        assertEquals(NAME_PATIENT, feedback.getAuthorFeedback());
        assertEquals(FEEDBACK, feedback.getTextFeedback());
        assertEquals(NAME_PATIENT, DataBaseQuery.selectFeedback().getAuthor());
        assertEquals(FEEDBACK, DataBaseQuery.selectFeedback().getContent());
        assertEquals(false, DataBaseQuery.selectFeedback().getIs_published());
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
        navigateMenu.openFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
        AddFeedbackWindow addFeedbackWindow = cardDoctor.openWindowAddFeedback();
        addFeedbackWindow.addFeedbackWindow();
        addFeedbackWindow.fillFieldFio(NAME_PATIENT);
        addFeedbackWindow.fillFieldTextFeedback(FEEDBACK);
        addFeedbackWindow.publishFeedbackButton();
        cardDoctor.selectedUnpublishedFeedback();
        cardDoctor.noSelectedPublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        assertEquals("Отзыв успешно добавлен", cardDoctor.getNotification());
        assertEquals(DataHelper.getCurrentDateRu(), feedback.getDateFeedback());
        assertEquals(NAME_PATIENT, feedback.getAuthorFeedback());
        assertEquals(FEEDBACK, feedback.getTextFeedback());
        assertEquals(NAME_PATIENT, DataBaseQuery.selectFeedback().getAuthor());
        assertEquals(FEEDBACK, DataBaseQuery.selectFeedback().getContent());
        assertEquals(false, DataBaseQuery.selectFeedback().getIs_published());
        assertTrue(cardDoctor.isExistFeedback());
        assertTrue(cardDoctor.isSelectUnPublishedFeedback());
        assertFalse(cardDoctor.isSelectPublishedFeedback());
        assertFalse(addFeedbackWindow.isWindowAppear());
    }

    @Feature("Отзывы о враче")
    @Story("Сброс значений полей в окне добавления отзыва при закрытии окна")
    @DisplayName("Сброс значений полей в окне добавления отзыва при закрытии окна")
    @Test
    void closeWindowAddNewFeedback() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
        AddFeedbackWindow addFeedbackWindow = cardDoctor.openWindowAddFeedback();
        addFeedbackWindow.fillFieldFio(NAME_PATIENT);
        addFeedbackWindow.fillFieldTextFeedback(FEEDBACK);
        addFeedbackWindow.closeWindowAddFeedback();
        assertFalse(addFeedbackWindow.isWindowAppear());
        cardDoctor.openWindowAddFeedback();
        assertEquals("", addFeedbackWindow.getValueFioField());
        assertEquals("", addFeedbackWindow.getValueTextFeedbackField());
    }

    @Feature("Отзывы о враче")
    @Story("Добавление нового отзыва с пустым полем ФИО")
    @DisplayName("Добавление нового отзыва с пустым полем ФИО")
    @Test
    void addFeedbackEmptyFieldFio() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
        AddFeedbackWindow addFeedbackWindow = cardDoctor.openWindowAddFeedback();
        addFeedbackWindow.fillFieldTextFeedback(FEEDBACK);
        assertFalse(addFeedbackWindow.isEnabledPublishButton());
    }


    @Feature("Отзывы о враче")
    @Story("Добавление нового отзыва с пустым полем отзыва")
    @DisplayName("Добавление нового отзыва с пустым полем отзыва")
    @Test
    void addFeedbackEmptyFieldText() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
        AddFeedbackWindow addFeedbackWindow = cardDoctor.openWindowAddFeedback();
        addFeedbackWindow.fillFieldFio(NAME_PATIENT);
        assertFalse(addFeedbackWindow.isEnabledPublishButton());
    }

    @Feature("Отзывы о враче")
    @Story("Успешное редактирование неопубликованного отзыва о враче")
    @DisplayName("Успешное редактирование неопубликованного отзыва о враче")
    @ExtendWith(AddDeleteFeedbackDecorator.class)
    @Test
    void editUnpublishedFeedback() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
        cardDoctor.selectedPublishedFeedback();
        cardDoctor.noSelectedUnpublishedFeedback();
        cardDoctor.switchUnpublishedFeedback();
        cardDoctor.noSelectedPublishedFeedback();
        cardDoctor.selectedUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.feedbackUnpublished();
        ChangeFeedbackWindow changeFeedback = feedback.editFeedback();
        changeFeedback.changeFeedbackWindow();
        changeFeedback.fillFieldText(NEW_FEEDBACK);
        changeFeedback.saveChanges();
        feedback.feedbackUnpublished();
        assertEquals("Отзыв успешно изменен", cardDoctor.getNotification());
        assertEquals(NEW_FEEDBACK, feedback.getTextFeedback());
        assertEquals(NEW_FEEDBACK, DataBaseQuery.selectFeedback().getContent());
        assertEquals(false, DataBaseQuery.selectFeedback().getIs_published());
        assertTrue(cardDoctor.isSelectUnPublishedFeedback());
        assertFalse(cardDoctor.isSelectPublishedFeedback());
        assertFalse(changeFeedback.isWindowAppear());
    }


    @Feature("Отзывы о враче")
    @Story("Успешная публикация неопубликованного отзыва о враче")
    @DisplayName("Успешная публикация неопубликованного отзыва о враче")
    @ExtendWith(AddDeleteFeedbackDecorator.class)
    @Test
    void publicationUnpublishedFeedback() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
        cardDoctor.switchUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.feedbackUnpublished();
        feedback.publicationFeedback();
        feedback.feedbackPublished();
        assertEquals("Отзыв успешно изменен", cardDoctor.getNotification());
        assertEquals(FEEDBACK, feedback.getTextFeedback());
        assertEquals(FEEDBACK, DataBaseQuery.selectFeedback().getContent());
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
        navigateMenu.openFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
        cardDoctor.selectedPublishedFeedback();
        cardDoctor.noSelectedUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.feedbackPublished();
        ChangeFeedbackWindow changeFeedback = feedback.editFeedback();
        changeFeedback.changeFeedbackWindow();
        changeFeedback.fillFieldText(NEW_FEEDBACK);
        changeFeedback.saveChanges();
        feedback.feedbackPublished();
        assertEquals("Отзыв успешно изменен", cardDoctor.getNotification());
        assertEquals(NEW_FEEDBACK, feedback.getTextFeedback());
        assertEquals(NEW_FEEDBACK, DataBaseQuery.selectFeedback().getContent());
        assertEquals(true, DataBaseQuery.selectFeedback().getIs_published());
        assertFalse(cardDoctor.isSelectUnPublishedFeedback());
        assertTrue(cardDoctor.isSelectPublishedFeedback());
        assertFalse(changeFeedback.isWindowAppear());
    }


    @Feature("Отзывы о враче")
    @Story("Успешное снятие с публикации опубликованного отзыва о враче")
    @DisplayName("Успешное снятие с публикации опубликованного отзыва о враче")
    @ExtendWith(AddPublishedDeleteFeedback.class)
    @Test
    void withdrawalPublicationFeedback() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
        Feedback feedback = cardDoctor.getFeedback();
        feedback.feedbackPublished();
        feedback.withdrawalPublication();
        feedback.feedbackUnpublished();
        assertEquals("Отзыв успешно изменен", cardDoctor.getNotification());
        assertEquals(FEEDBACK, feedback.getTextFeedback());
        assertEquals(FEEDBACK, DataBaseQuery.selectFeedback().getContent());
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
        navigateMenu.openFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
        Feedback feedback = cardDoctor.getFeedback();
        ChangeFeedbackWindow changeFeedback = feedback.editFeedback();
        changeFeedback.fillFieldText(NEW_FEEDBACK);
        changeFeedback.closeWindowChangeFeedback();
        assertFalse(changeFeedback.isWindowAppear());
        feedback.editFeedback();
        assertEquals(FEEDBACK, changeFeedback.getValueTextFeedbackField());
        assertEquals(FEEDBACK, DataBaseQuery.selectFeedback().getContent());
    }

    @Feature("Отзывы о враче")
    @Story("Редактирование отзыва с пустым полем отзыва")
    @DisplayName("Редактирование отзыва с пустым полем отзыва")
    @ExtendWith(AddPublishedDeleteFeedback.class)
    @Test
    void editFeedbackEmptyFieldText() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
        Feedback feedback = cardDoctor.getFeedback();
        ChangeFeedbackWindow changeFeedback = feedback.editFeedback();
        changeFeedback.clearTextField();
        assertFalse(changeFeedback.isEnabledSaveButton());
    }


    @Feature("Отзывы о враче")
    @Story("Успешное удаление неопубликованного отзыва о враче")
    @DisplayName("Успешное удаление неопубликованного отзыва о враче")
    @ExtendWith(AddFeedbackDecorator.class)
    @Test
    void deleteUnpublishedFeedback() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
        cardDoctor.switchUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.feedbackUnpublished();
        feedback.deleteFeedback();
        assertEquals("Отзыв успешно удален", cardDoctor.getNotification());
        assertFalse(cardDoctor.isExistFeedback());
        assertNull(DataBaseQuery.selectFeedback());
    }

    @Feature("Отзывы о враче")
    @Story("Сортировка неопубликованных отзывов о враче")
    @DisplayName("Сортировка неопубликованных отзывов о враче")
    @ExtendWith(AddTwoFeedbackDecorator.class)
    @Test
    void sortingUnpublishedFeedbacks() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
        cardDoctor.switchUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        assertTrue(cardDoctor.isSortingNewAppear());
        String dateFeedbackToday = feedback.getDateFeedbackByIndex(0);
        String dateFeedbackYesterday = feedback.getDateFeedbackByIndex(1);
        cardDoctor.sortingFeedbackNew();
        assertFalse(cardDoctor.isSortingNewAppear());
        assertTrue(cardDoctor.isSortingOldAppear());
        assertEquals(dateFeedbackYesterday, feedback.getDateFeedbackByIndex(0));
        assertEquals(dateFeedbackToday, feedback.getDateFeedbackByIndex(1));
        cardDoctor.sortingFeedbackOld();
        assertTrue(cardDoctor.isSortingNewAppear());
        assertFalse(cardDoctor.isSortingOldAppear());
        assertEquals(dateFeedbackToday, feedback.getDateFeedbackByIndex(0));
        assertEquals(dateFeedbackYesterday, feedback.getDateFeedbackByIndex(1));
    }


    @Story("Закрытие уведомления на странице карточки врача по таймауту")
    @DisplayName("Закрытие уведомления на странице карточки врача по таймауту")
    @Test
    void closeNotificationTimeout() {
        EditPhotoDoctorWindow editPhoto = cardDoctor.openWindowEditPhoto();
        editPhoto.uploadPhoto("src/test/resources/Photo 6,8mbJpeg.jpg");
        checkCloseNotificationTimeout(basePage);
    }

    @Story("Закрытие уведомления на странице карточки врача")
    @DisplayName("Закрытие уведомления на странице карточки врача")
    @Test
    void closeNotification() {
        EditPhotoDoctorWindow editPhoto = cardDoctor.openWindowEditPhoto();
        editPhoto.uploadPhoto("src/test/resources/Photo 6,8mbJpeg.jpg");
        checkCloseNotification(basePage);
    }

    @Story("Закрытие навигационного меню")
    @DisplayName("Закрытие навигационного меню")
    @Test
    void closeNavigateMenu() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.navigateMenu();
        navigateMenu.closeNavigateMenu();
        assertFalse(navigateMenu.isNavigateMenuDisplayed());
    }

}



