package admin.test;

import admin.data.TestData;
import admin.pages.BasePage.BasePage;
import admin.pages.DoctorsPage.CardDoctorPage.*;
import admin.pages.DoctorsPage.DoctorsPage;
import admin.pages.Calendar.Calendar;
import admin.utils.preparationDataTests.doctors.*;
import admin.utils.preparationDataTests.general.AllureDecorator;
import admin.utils.preparationDataTests.general.NotificationDecorator;
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

import static org.junit.jupiter.api.Assertions.*;

@Epic("Врачи")
public class CardDoctorPageTest extends BaseTest {

    private BasePage basePage;
    private CardDoctorPage cardDoctor;

    @ExtendWith(AllureDecorator.class)

    @BeforeAll
    static void setUpAuth() {
        BrowserManager.openAdminPanel(TestData.UserData.LOGIN_ADMIN, TestData.UserData.PASSWORD_ADMIN);
        DoctorsPage doctorsPage = new DoctorsPage();
        doctorsPage.openCardDoctor(TestData.DataTest.getDOCTOR_SPECIALIZATION(), TestData.DataTest.getDOCTOR());
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
    @ExtendWith(DeletePhotoDoctorDecorator.class)
    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/Photo 3,7mbJpeg.jpg", "src/test/resources/Photo 3,2mbPng.png"})
    void changePhotoDoctorValidFormat(String path) {
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
        assertNotEquals(srcOriginalPhoto, DataBaseQuery.selectInfoDoctor(TestData.DataTest.getDOCTOR(), TestData.DataTest.getDOCTOR_SPECIALIZATION()).getPhoto_uri());
        assertFalse(editPhoto.isWindowAppear());
    }


    @Feature("Замена фотографии врачу")
    @Story("Замена фотографии врачу с файлом весом более 4mb")
    @ExtendWith(NotificationDecorator.class)
    @Test
    void changePhotoDoctorLess4mb() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openPhoto();
        navigateMenu.closeNavigateMenu();
        EditPhotoDoctorWindow editPhoto = cardDoctor.openWindowEditPhoto();
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        editPhoto.uploadPhoto("src/test/resources/Photo 6,8mbJpeg.jpg");
        assertEquals("Допускаются файлы размером не выше 4Мб", cardDoctor.getNotification());
        assertEquals(srcOriginalPhoto, cardDoctor.getSrcPhoto());
        assertEquals(srcOriginalPhoto, DataBaseQuery.selectInfoDoctor(TestData.DataTest.getDOCTOR(), TestData.DataTest.getDOCTOR_SPECIALIZATION()).getPhoto_uri());
    }

    @Feature("Замена фотографии врачу")
    @Story("Замена фотографии врачу с файлом в невалидном формате")
    @ExtendWith(NotificationDecorator.class)
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
        assertEquals(srcOriginalPhoto, DataBaseQuery.selectInfoDoctor(TestData.DataTest.getDOCTOR(), TestData.DataTest.getDOCTOR_SPECIALIZATION()).getPhoto_uri());
    }


    @Feature("Замена фотографии врачу")
    @Story("Закрытие окна смены фотографии")
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
        assertEquals(TestData.DataTest.getDEFAULT_PHOTO(), cardDoctor.getSrcPhoto());
        assertEquals(TestData.DataTest.getDEFAULT_PHOTO(), DataBaseQuery.selectInfoDoctor(TestData.DataTest.getDOCTOR(), TestData.DataTest.getDOCTOR_SPECIALIZATION()).getPhoto_uri());
    }

    @Feature("Замена фотографии врачу")
    @Story("Удаление дефолтной фотографии врача")
    @ExtendWith({DeletePhotoDoctorDecorator.class, NotificationDecorator.class})
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
    @ExtendWith(DeleteSectionDecorator.class)
    @Test
    void addingSection() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openDescription();
        navigateMenu.closeNavigateMenu();
        AddIntelligenceWindow intelligenceWindow = cardDoctor.openWindowAddSection();
        intelligenceWindow.addIntelligenceSectionWindow();
        intelligenceWindow.fillFieldText(TestData.DataTest.getSECTION());
        intelligenceWindow.saveValue();
        Section section = cardDoctor.getSection();
        assertEquals(TestData.DataTest.getSECTION(), section.getSection());
        assertEquals(TestData.DataTest.getSECTION(), DataBaseQuery.selectSection(DeleteSectionDecorator.getDoctorId()).getTitle());
        assertTrue(cardDoctor.isExistSection());
        assertFalse(intelligenceWindow.isWindowSectionAppear());
    }

    @Feature("Информация о враче")
    @Story("Добавление пустого раздела в инфо о враче")
    @ExtendWith({DeleteSectionDecorator.class, NotificationDecorator.class})
    @Test
    void addSectionEmptyField() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openDescription();
        navigateMenu.closeNavigateMenu();
        AddIntelligenceWindow intelligenceWindow = cardDoctor.openWindowAddSection();
        intelligenceWindow.saveValue();
        assertEquals("Неверный запрос (400)", cardDoctor.getNotification());
        assertFalse(cardDoctor.isExistSection());
        assertNull(DataBaseQuery.selectSection(DeleteSectionDecorator.getDoctorId()));
    }


    @Feature("Информация о враче")
    @Story("Отмена добавления раздела в инфо о враче и зануление полей")
    @Test
    void cancelWindowAddSection() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openDescription();
        navigateMenu.closeNavigateMenu();
        AddIntelligenceWindow intelligenceWindow = cardDoctor.openWindowAddSection();
        intelligenceWindow.fillFieldText(TestData.DataTest.getSECTION());
        intelligenceWindow.cancelAdd();
        assertFalse(intelligenceWindow.isWindowSectionAppear());
        cardDoctor.openWindowAddSection();
        assertEquals("", intelligenceWindow.getValueField());
    }


    @Feature("Информация о враче")
    @Story("Успешное редактирование раздела в инфо о враче")
    @ExtendWith(AddDeleteSectionDecorator.class)
    @Test
    void editSection() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openDescription();
        navigateMenu.closeNavigateMenu();
        Section section = cardDoctor.getSection();
        section.section();
        section.editSaveTitle();
        section.fillTitleField(TestData.DataTest.getNEW_SECTION());
        section.editSaveTitle();
        Selenide.sleep(2000);
        assertEquals(TestData.DataTest.getNEW_SECTION(), section.getSection());
        assertEquals(TestData.DataTest.getNEW_SECTION(), DataBaseQuery.selectSection(AddDeleteSectionDecorator.getDoctorId()).getTitle());
    }

    @Feature("Информация о враче")
    @Story("Редактирование раздела в инфо о враче с пустым полем")
    @ExtendWith({AddDeleteSectionDecorator.class, NotificationDecorator.class})
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
    @ExtendWith(DeleteDescriptionDecorator.class)
    @Test
    void addingDescription() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openDescription();
        navigateMenu.closeNavigateMenu();
        Section section = cardDoctor.getSection();
        AddIntelligenceWindow intelligenceWindow = section.openWindowAddDescription();
        intelligenceWindow.addIntelligenceDescriptionWindow();
        intelligenceWindow.fillFieldText(TestData.DataTest.getDESCRIPTION());
        intelligenceWindow.saveValue();
        Description description = cardDoctor.getDescription();
        assertEquals(TestData.DataTest.getDESCRIPTION(), description.getDescription());
        assertEquals(TestData.DataTest.getDESCRIPTION(), DataBaseQuery.selectDescription(DeleteDescriptionDecorator.getSectionId()).getTitle());
        assertTrue(cardDoctor.isExistDescription());
        assertFalse(intelligenceWindow.isWindowDescriptionAppear());
    }

    @Feature("Информация о враче")
    @Story("Добавление пустого описания к разделу в инфо о враче")
    @ExtendWith({DeleteDescriptionDecorator.class, NotificationDecorator.class})
    @Test
    void addDescriptionEmptyField() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openDescription();
        navigateMenu.closeNavigateMenu();
        Section section = cardDoctor.getSection();
        AddIntelligenceWindow intelligenceWindow = section.openWindowAddDescription();
        intelligenceWindow.saveValue();
        assertEquals("Неверный запрос (400)", cardDoctor.getNotification());
        assertFalse(cardDoctor.isExistDescription());
        assertNull(DataBaseQuery.selectDescription(DeleteDescriptionDecorator.getSectionId()));
    }


    @Feature("Информация о враче")
    @Story("Отмена добавления описания к разделу в инфо о враче и зануление полей")
    @ExtendWith(DeleteDescriptionDecorator.class)
    @Test
    void cancellationWindowAddDescription() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openDescription();
        navigateMenu.closeNavigateMenu();
        Section section = cardDoctor.getSection();
        AddIntelligenceWindow intelligenceWindow = section.openWindowAddDescription();
        intelligenceWindow.fillFieldText(TestData.DataTest.getDESCRIPTION());
        intelligenceWindow.cancelAdd();
        assertFalse(intelligenceWindow.isWindowDescriptionAppear());
        section.openWindowAddDescription();
        assertEquals("", intelligenceWindow.getValueField());
    }


    @Feature("Информация о враче")
    @Story("Успешное редактирование описания к разделу в инфо о враче")
    @ExtendWith(AddDeleteDescriptionDecorator.class)
    @Test
    void editDescription() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openDescription();
        navigateMenu.closeNavigateMenu();
        Description description = cardDoctor.getDescription();
        description.description();
        description.editSaveDescription();
        description.fillDescriptionField(TestData.DataTest.getNEW_DESCRIPTION());
        description.editSaveDescription();
        Selenide.sleep(2000);
        assertEquals(TestData.DataTest.getNEW_DESCRIPTION(), description.getDescription());
        assertEquals(TestData.DataTest.getNEW_DESCRIPTION(), DataBaseQuery.selectDescription(AddDeleteDescriptionDecorator.getSectionId()).getTitle());
    }

    @Feature("Информация о враче")
    @Story("Редактирование описания в инфо о враче с пустым полем")
    @ExtendWith({AddDeleteDescriptionDecorator.class, NotificationDecorator.class})
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
    @ExtendWith({DeleteFeedbackDecorator.class, NotificationDecorator.class})
    @Test
    void addFeedbackCurrentMonth() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
        AddFeedbackWindow addFeedbackWindow = cardDoctor.openWindowAddFeedback();
        addFeedbackWindow.fillFieldFio(TestData.DataTest.getNAME_PATIENT());
        addFeedbackWindow.fillFieldTextFeedback(TestData.DataTest.getFEEDBACK());
        Calendar calendar = addFeedbackWindow.openCalendarSelectDate();
        calendar.selectDateActivation();
        addFeedbackWindow.publishFeedbackButton();
        cardDoctor.selectedUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        assertEquals("Отзыв успешно добавлен", cardDoctor.getNotification());
        assertEquals(DataHelper.generateFutureDateCurrentMonth(), feedback.getDateFeedback());
        assertEquals(TestData.DataTest.getNAME_PATIENT(), feedback.getAuthorFeedback());
        assertEquals(TestData.DataTest.getFEEDBACK(), feedback.getTextFeedback());
        assertEquals(TestData.DataTest.getNAME_PATIENT(), DataBaseQuery.selectFeedback().getAuthor());
        assertEquals(TestData.DataTest.getFEEDBACK(), DataBaseQuery.selectFeedback().getContent());
        assertEquals(false, DataBaseQuery.selectFeedback().getIs_published());
        assertTrue(cardDoctor.isExistFeedback());
        assertTrue(cardDoctor.isSelectUnPublishedFeedback());
        assertFalse(cardDoctor.isSelectPublishedFeedback());
        assertFalse(addFeedbackWindow.isWindowAppear());
    }


    @Feature("Отзывы о враче")
    @Story("Успешное добавление отзыва о враче датой в следующем месяце")
    @ExtendWith({DeleteFeedbackDecorator.class, NotificationDecorator.class})
    @Test
    void addFeedbackFutureMonth() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
        AddFeedbackWindow addFeedbackWindow = cardDoctor.openWindowAddFeedback();
        addFeedbackWindow.fillFieldFio(TestData.DataTest.getNAME_PATIENT());
        addFeedbackWindow.fillFieldTextFeedback(TestData.DataTest.getFEEDBACK());
        Calendar calendar = addFeedbackWindow.openCalendarSelectDate();
        calendar.switchFutureMonth();
        calendar.selectDateActivation();
        addFeedbackWindow.publishFeedbackButton();
        cardDoctor.selectedUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        assertEquals("Отзыв успешно добавлен", cardDoctor.getNotification());
        assertEquals(DataHelper.getNextMonthDate(), feedback.getDateFeedback());
        assertEquals(TestData.DataTest.getNAME_PATIENT(), feedback.getAuthorFeedback());
        assertEquals(TestData.DataTest.getFEEDBACK(), feedback.getTextFeedback());
        assertEquals(TestData.DataTest.getNAME_PATIENT(), DataBaseQuery.selectFeedback().getAuthor());
        assertEquals(TestData.DataTest.getFEEDBACK(), DataBaseQuery.selectFeedback().getContent());
        assertEquals(false, DataBaseQuery.selectFeedback().getIs_published());
        assertTrue(cardDoctor.isExistFeedback());
        assertTrue(cardDoctor.isSelectUnPublishedFeedback());
        assertFalse(cardDoctor.isSelectPublishedFeedback());
        assertFalse(addFeedbackWindow.isWindowAppear());
    }


    @Feature("Отзывы о враче")
    @Story("Успешное добавление отзыва о враче датой в предыдущем месяце")
    @ExtendWith({DeleteFeedbackDecorator.class, NotificationDecorator.class})
    @Test
    void addFeedbackPreviousMonth() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
        AddFeedbackWindow addFeedbackWindow = cardDoctor.openWindowAddFeedback();
        addFeedbackWindow.fillFieldFio(TestData.DataTest.getNAME_PATIENT());
        addFeedbackWindow.fillFieldTextFeedback(TestData.DataTest.getFEEDBACK());
        Calendar calendar = addFeedbackWindow.openCalendarSelectDate();
        calendar.switchPreviousMonth();
        calendar.selectDateActivation();
        addFeedbackWindow.publishFeedbackButton();
        cardDoctor.selectedUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        assertEquals("Отзыв успешно добавлен", cardDoctor.getNotification());
        assertEquals(DataHelper.getPreviousMonthDate(), feedback.getDateFeedback());
        assertEquals(TestData.DataTest.getNAME_PATIENT(), feedback.getAuthorFeedback());
        assertEquals(TestData.DataTest.getFEEDBACK(), feedback.getTextFeedback());
        assertEquals(TestData.DataTest.getNAME_PATIENT(), DataBaseQuery.selectFeedback().getAuthor());
        assertEquals(TestData.DataTest.getFEEDBACK(), DataBaseQuery.selectFeedback().getContent());
        assertEquals(false, DataBaseQuery.selectFeedback().getIs_published());
        assertTrue(cardDoctor.isExistFeedback());
        assertTrue(cardDoctor.isSelectUnPublishedFeedback());
        assertFalse(cardDoctor.isSelectPublishedFeedback());
        assertFalse(addFeedbackWindow.isWindowAppear());
    }

    @Feature("Отзывы о враче")
    @Story("Успешное добавление отзыва о враче текущей датой")
    @ExtendWith({DeleteFeedbackDecorator.class, NotificationDecorator.class})
    @Test
    void addFeedbackTodayNotUseCalendar() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
        AddFeedbackWindow addFeedbackWindow = cardDoctor.openWindowAddFeedback();
        addFeedbackWindow.addFeedbackWindow();
        addFeedbackWindow.fillFieldFio(TestData.DataTest.getNAME_PATIENT());
        addFeedbackWindow.fillFieldTextFeedback(TestData.DataTest.getFEEDBACK());
        addFeedbackWindow.publishFeedbackButton();
        cardDoctor.selectedUnpublishedFeedback();
        cardDoctor.noSelectedPublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        assertEquals("Отзыв успешно добавлен", cardDoctor.getNotification());
        assertEquals(DataHelper.getCurrentDateRu(), feedback.getDateFeedback());
        assertEquals(TestData.DataTest.getNAME_PATIENT(), feedback.getAuthorFeedback());
        assertEquals(TestData.DataTest.getFEEDBACK(), feedback.getTextFeedback());
        assertEquals(TestData.DataTest.getNAME_PATIENT(), DataBaseQuery.selectFeedback().getAuthor());
        assertEquals(TestData.DataTest.getFEEDBACK(), DataBaseQuery.selectFeedback().getContent());
        assertEquals(false, DataBaseQuery.selectFeedback().getIs_published());
        assertTrue(cardDoctor.isExistFeedback());
        assertTrue(cardDoctor.isSelectUnPublishedFeedback());
        assertFalse(cardDoctor.isSelectPublishedFeedback());
        assertFalse(addFeedbackWindow.isWindowAppear());
    }

    @Feature("Отзывы о враче")
    @Story("Зануление полей в окне добавления отзыва после закрытия окна")
    @Test
    void closeWindowAddNewFeedback() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
        AddFeedbackWindow addFeedbackWindow = cardDoctor.openWindowAddFeedback();
        addFeedbackWindow.fillFieldFio(TestData.DataTest.getNAME_PATIENT());
        addFeedbackWindow.fillFieldTextFeedback(TestData.DataTest.getFEEDBACK());
        addFeedbackWindow.closeWindowAddFeedback();
        assertFalse(addFeedbackWindow.isWindowAppear());
        cardDoctor.openWindowAddFeedback();
        assertEquals("", addFeedbackWindow.getValueFioField());
        assertEquals("", addFeedbackWindow.getValueTextFeedbackField());
    }

    @Feature("Отзывы о враче")
    @Story("Добавление нового отзыва с пустым полем ФИО")
    @Test
    void addFeedbackEmptyFieldFio() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
        AddFeedbackWindow addFeedbackWindow = cardDoctor.openWindowAddFeedback();
        addFeedbackWindow.fillFieldTextFeedback(TestData.DataTest.getFEEDBACK());
        assertFalse(addFeedbackWindow.isEnabledPublishButton());
    }


    @Feature("Отзывы о враче")
    @Story("Добавление нового отзыва с пустым полем отзыва")
    @Test
    void addFeedbackEmptyFieldText() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
        AddFeedbackWindow addFeedbackWindow = cardDoctor.openWindowAddFeedback();
        addFeedbackWindow.fillFieldFio(TestData.DataTest.getNAME_PATIENT());
        assertFalse(addFeedbackWindow.isEnabledPublishButton());
    }

    @Feature("Отзывы о враче")
    @Story("Успешное редактирование неопубликованного отзыва о враче")
    @ExtendWith({AddDeleteFeedbackDecorator.class, NotificationDecorator.class})
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
        changeFeedback.fillFieldText(TestData.DataTest.getNEW_FEEDBACK());
        changeFeedback.saveChanges();
        feedback.feedbackUnpublished();
        assertEquals("Отзыв успешно изменен", cardDoctor.getNotification());
        assertEquals(TestData.DataTest.getNEW_FEEDBACK(), feedback.getTextFeedback());
        assertEquals(TestData.DataTest.getNEW_FEEDBACK(), DataBaseQuery.selectFeedback().getContent());
        assertEquals(false, DataBaseQuery.selectFeedback().getIs_published());
        assertTrue(cardDoctor.isSelectUnPublishedFeedback());
        assertFalse(cardDoctor.isSelectPublishedFeedback());
        assertFalse(changeFeedback.isWindowAppear());
    }


    @Feature("Отзывы о враче")
    @Story("Успешная публикация неопубликованного отзыва о враче")
    @ExtendWith({AddDeleteFeedbackDecorator.class, NotificationDecorator.class})
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
        assertEquals(TestData.DataTest.getFEEDBACK(), feedback.getTextFeedback());
        assertEquals(TestData.DataTest.getFEEDBACK(), DataBaseQuery.selectFeedback().getContent());
        assertEquals(true, DataBaseQuery.selectFeedback().getIs_published());
        assertFalse(cardDoctor.isSelectUnPublishedFeedback());
        assertTrue(cardDoctor.isSelectPublishedFeedback());
    }


    @Feature("Отзывы о враче")
    @Story("Успешное редактирование опубликованного отзыва о враче")
    @ExtendWith({AddPublishedDeleteFeedback.class, NotificationDecorator.class})
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
        changeFeedback.fillFieldText(TestData.DataTest.getNEW_FEEDBACK());
        changeFeedback.saveChanges();
        feedback.feedbackPublished();
        assertEquals("Отзыв успешно изменен", cardDoctor.getNotification());
        assertEquals(TestData.DataTest.getNEW_FEEDBACK(), feedback.getTextFeedback());
        assertEquals(TestData.DataTest.getNEW_FEEDBACK(), DataBaseQuery.selectFeedback().getContent());
        assertEquals(true, DataBaseQuery.selectFeedback().getIs_published());
        assertFalse(cardDoctor.isSelectUnPublishedFeedback());
        assertTrue(cardDoctor.isSelectPublishedFeedback());
        assertFalse(changeFeedback.isWindowAppear());
    }


    @Feature("Отзывы о враче")
    @Story("Успешное снятие с публикации опубликованного отзыва о враче")
    @ExtendWith({AddPublishedDeleteFeedback.class, NotificationDecorator.class})
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
        assertEquals(TestData.DataTest.getFEEDBACK(), feedback.getTextFeedback());
        assertEquals(TestData.DataTest.getFEEDBACK(), DataBaseQuery.selectFeedback().getContent());
        assertEquals(false, DataBaseQuery.selectFeedback().getIs_published());
        assertTrue(cardDoctor.isSelectUnPublishedFeedback());
        assertFalse(cardDoctor.isSelectPublishedFeedback());
    }

    @Feature("Отзывы о враче")
    @Story("Сохранение значений полей в окне редактирования отзыва после закрытия окна")
    @ExtendWith({AddPublishedDeleteFeedback.class})
    @Test
    void closeWindowEditFeedback() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openFeedback();
        navigateMenu.closeNavigateMenu();
        Selenide.sleep(2000);
        Feedback feedback = cardDoctor.getFeedback();
        ChangeFeedbackWindow changeFeedback = feedback.editFeedback();
        changeFeedback.fillFieldText(TestData.DataTest.getNEW_FEEDBACK());
        changeFeedback.closeWindowChangeFeedback();
        assertFalse(changeFeedback.isWindowAppear());
        feedback.editFeedback();
        assertEquals(TestData.DataTest.getFEEDBACK(), changeFeedback.getValueTextFeedbackField());
        assertEquals(TestData.DataTest.getFEEDBACK(), DataBaseQuery.selectFeedback().getContent());
    }

    @Feature("Отзывы о враче")
    @Story("Редактирование отзыва с пустым полем отзыва")
    @ExtendWith({AddPublishedDeleteFeedback.class})
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
    @ExtendWith({AddFeedbackDecorator.class, NotificationDecorator.class})
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
    @Test
    void closeNotificationTimeout() {
        EditPhotoDoctorWindow editPhoto = cardDoctor.openWindowEditPhoto();
        editPhoto.uploadPhoto("src/test/resources/Photo 6,8mbJpeg.jpg");
        checkCloseNotificationTimeout(basePage);
    }

    @Story("Закрытие уведомления на странице карточки врача")
    @Test
    void closeNotification() {
        EditPhotoDoctorWindow editPhoto = cardDoctor.openWindowEditPhoto();
        editPhoto.uploadPhoto("src/test/resources/Photo 6,8mbJpeg.jpg");
        checkCloseNotification(basePage);
    }

    @Story("Закрытие навигационного меню")
    @Test
    void closeNavigateMenu() {
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.navigateMenu();
        navigateMenu.closeNavigateMenu();
        assertFalse(navigateMenu.isNavigateMenuDisplayed());
    }

}



