package admin.test;

import admin.data.DataConfig;
import admin.pages.BasePage.BasePage;
import admin.pages.DoctorsPage.CardDoctorPage.*;
import admin.pages.DoctorsPage.DoctorsPage;
import admin.pages.HeaderMenu.HeaderMenu;
import admin.pages.Calendar.Calendar;
import admin.utils.preparationDataTests.doctors.*;
import admin.utils.preparationDataTests.general.AllureDecorator;
import admin.utils.preparationDataTests.general.NotificationDecorator;
import admin.utils.testUtils.*;
import admin.utils.dbUtils.DataBaseQuery;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Врачи")
public class DoctorsPageTest extends BaseTest {

    private DoctorsPage doctorsPage;
    private BasePage basePage;

    @ExtendWith(AllureDecorator.class)

    @BeforeAll
    static void setUpAuth() {
        BrowserManager.authGetCookie(DataConfig.UserData.getLoginAdmin(), DataConfig.UserData.getPasswordAdmin());
    }

    @BeforeEach
    void setUp() {
        BrowserManager.openPagesAfterAuth();
        doctorsPage = new DoctorsPage();
        basePage = new BasePage();
        HeaderMenu headerMenu = new HeaderMenu();
        headerMenu.doctorsTabOpen();
    }

    @Feature("Замена фотографии врачу")
    @Story("Успешная замена фотографии врачу в формате Jpeg и Png")
    @ExtendWith(DeletePhotoDoctorDecorator.class)
    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/Photo 3,7mbJpeg.jpg", "src/test/resources/Photo 3,2mbPng.png"})
    void changePhotoDoctorValidFormat(String path) {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openPhoto();
        EditPhotoDoctorWindow editPhoto = cardDoctor.openWindowEditPhoto();
        editPhoto.editPhotoDoctorWindow();
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        editPhoto.uploadPhoto(path);
        Selenide.sleep(3000);
        assertNotEquals(srcOriginalPhoto, cardDoctor.getSrcPhoto());
        assertNotEquals(srcOriginalPhoto, DataBaseQuery.selectInfoDoctor().getPhoto_uri());
        assertFalse(editPhoto.isWindowAppear());
    }


    @Feature("Замена фотографии врачу")
    @Story("Замена фотографии врачу с файлом весом более 4mb")
    @ExtendWith(NotificationDecorator.class)
    @Test
    void changePhotoDoctorLess4mb() {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openPhoto();
        EditPhotoDoctorWindow editPhoto = cardDoctor.openWindowEditPhoto();
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        editPhoto.uploadPhoto("src/test/resources/Photo 6,8mbJpeg.jpg");
        assertEquals("Допускаются файлы размером не выше 4Мб", cardDoctor.getNotification());
        assertEquals(srcOriginalPhoto, cardDoctor.getSrcPhoto());
        assertEquals(srcOriginalPhoto, DataBaseQuery.selectInfoDoctor().getPhoto_uri());
    }

    @Feature("Замена фотографии врачу")
    @Story("Замена фотографии врачу с файлом в невалидном формате")
    @ExtendWith(NotificationDecorator.class)
    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/Оферта,Политика обработки docx.docx", "src/test/resources/Оферта, Политика обработки .xlsx.xlsx", "src/test/resources/Оферта.pdf"})
    void changePhotoDoctorInvalidFormat(String path) {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openPhoto();
        EditPhotoDoctorWindow editPhoto = cardDoctor.openWindowEditPhoto();
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        editPhoto.uploadPhoto(path);
        assertEquals("Допускаются файлы с расширением jpg jpeg png", cardDoctor.getNotification());
        assertEquals(srcOriginalPhoto, cardDoctor.getSrcPhoto());
        assertEquals(srcOriginalPhoto, DataBaseQuery.selectInfoDoctor().getPhoto_uri());
    }


    @Feature("Замена фотографии врачу")
    @Story("Закрытие окна смены фотографии")
    @Test
    void closeWindowEditPhoto() {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openPhoto();
        EditPhotoDoctorWindow editPhoto = cardDoctor.openWindowEditPhoto();
        editPhoto.closeWindowEditPhoto();
        assertFalse(editPhoto.isWindowAppear());
    }

    @Feature("Замена фотографии врачу")
    @Story("Успешное удаление фотографии врача")
    @ExtendWith(SetPhotoDoctorDecorator.class)
    @Test
    void deletePhoto() {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openPhoto();
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        cardDoctor.deletePhoto();
        Selenide.sleep(3000);
        assertNotEquals(srcOriginalPhoto, cardDoctor.getSrcPhoto());
        assertEquals(DataConfig.DataTest.getDefaultPhoto(), cardDoctor.getSrcPhoto());
        assertEquals(DataConfig.DataTest.getDefaultPhoto(), DataBaseQuery.selectInfoDoctor().getPhoto_uri());
    }

    @Feature("Замена фотографии врачу")
    @Story("Удаление дефолтной фотографии врача")
    @ExtendWith({DeletePhotoDoctorDecorator.class, NotificationDecorator.class})
    @Test
    void deleteDefaultPhoto() {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openPhoto();
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
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openDescription();
        AddIntelligenceWindow intelligenceWindow = cardDoctor.openWindowAddSection();
        intelligenceWindow.addIntelligenceSectionWindow();
        intelligenceWindow.fillFieldText(DataConfig.DataTest.getSection());
        intelligenceWindow.saveValue();
        Section section = cardDoctor.getSection();
        assertEquals(DataConfig.DataTest.getSection(), section.getSection());
        assertEquals(DataConfig.DataTest.getSection(), DataBaseQuery.selectSection(DeleteSectionDecorator.getDoctorId()).getTitle());
        assertTrue(cardDoctor.isExistSection());
        assertFalse(intelligenceWindow.isWindowSectionAppear());
    }

    @Feature("Информация о враче")
    @Story("Добавление пустого раздела в инфо о враче")
    @ExtendWith({DeleteSectionDecorator.class, NotificationDecorator.class})
    @Test
    void addSectionEmptyField() {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openDescription();
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
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openDescription();
        AddIntelligenceWindow intelligenceWindow = cardDoctor.openWindowAddSection();
        intelligenceWindow.fillFieldText(DataConfig.DataTest.getSection());
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
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openDescription();
        Section section = cardDoctor.getSection();
        section.section();
        section.editSaveTitle();
        section.fillTitleField(DataConfig.DataTest.getNewSection());
        section.editSaveTitle();
        Selenide.sleep(2000);
        assertEquals(DataConfig.DataTest.getNewSection(), section.getSection());
        assertEquals(DataConfig.DataTest.getNewSection(), DataBaseQuery.selectSection(AddDeleteSectionDecorator.getDoctorId()).getTitle());
    }

    @Feature("Информация о враче")
    @Story("Редактирование раздела в инфо о враче с пустым полем")
    @ExtendWith({AddDeleteSectionDecorator.class, NotificationDecorator.class})
    @Test
    void editSectionEmptyField() {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openDescription();
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
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openDescription();
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
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openDescription();
        Section section = cardDoctor.getSection();
        AddIntelligenceWindow intelligenceWindow = section.openWindowAddDescription();
        intelligenceWindow.addIntelligenceDescriptionWindow();
        intelligenceWindow.fillFieldText(DataConfig.DataTest.getDescription());
        intelligenceWindow.saveValue();
        Description description = cardDoctor.getDescription();
        assertEquals(DataConfig.DataTest.getDescription(), description.getDescription());
        assertEquals(DataConfig.DataTest.getDescription(), DataBaseQuery.selectDescription(DeleteDescriptionDecorator.getSectionId()).getTitle());
        assertTrue(cardDoctor.isExistDescription());
        assertFalse(intelligenceWindow.isWindowDescriptionAppear());
    }

    @Feature("Информация о враче")
    @Story("Добавление пустого описания к разделу в инфо о враче")
    @ExtendWith({DeleteDescriptionDecorator.class, NotificationDecorator.class})
    @Test
    void addDescriptionEmptyField() {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openDescription();
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
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openDescription();
        Section section = cardDoctor.getSection();
        AddIntelligenceWindow intelligenceWindow = section.openWindowAddDescription();
        intelligenceWindow.fillFieldText(DataConfig.DataTest.getDescription());
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
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openDescription();
        Description description = cardDoctor.getDescription();
        description.description();
        description.editSaveDescription();
        description.fillDescriptionField(DataConfig.DataTest.getNewDescription());
        description.editSaveDescription();
        Selenide.sleep(2000);
        assertEquals(DataConfig.DataTest.getNewDescription(), description.getDescription());
        assertEquals(DataConfig.DataTest.getNewDescription(), DataBaseQuery.selectDescription(AddDeleteDescriptionDecorator.getSectionId()).getTitle());
    }

    @Feature("Информация о враче")
    @Story("Редактирование описания в инфо о враче с пустым полем")
    @ExtendWith({AddDeleteDescriptionDecorator.class, NotificationDecorator.class})
    @Test
    void editDescriptionEmptyField() {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openDescription();
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
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openDescription();
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
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openFeedback();
        Selenide.sleep(2000);
        AddFeedbackWindow addFeedbackWindow = cardDoctor.openWindowAddFeedback();
        addFeedbackWindow.fillFieldFio(DataConfig.DataTest.getNamePatient());
        addFeedbackWindow.fillFieldTextFeedback(DataConfig.DataTest.getFeedback());
        Calendar calendar = addFeedbackWindow.openCalendarSelectDate();
        calendar.selectDateActivation();
        addFeedbackWindow.publishFeedbackButton();
        cardDoctor.selectedUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        assertEquals("Отзыв успешно добавлен", cardDoctor.getNotification());
        assertEquals(DataHelper.generateFutureDateCurrentMonth(), feedback.getDateFeedback());
        assertEquals(DataConfig.DataTest.getNamePatient(), feedback.getAuthorFeedback());
        assertEquals(DataConfig.DataTest.getFeedback(), feedback.getTextFeedback());
        assertEquals(DataConfig.DataTest.getNamePatient(), DataBaseQuery.selectFeedback().getAuthor());
        assertEquals(DataConfig.DataTest.getFeedback(), DataBaseQuery.selectFeedback().getContent());
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
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openFeedback();
        Selenide.sleep(2000);
        AddFeedbackWindow addFeedbackWindow = cardDoctor.openWindowAddFeedback();
        addFeedbackWindow.fillFieldFio(DataConfig.DataTest.getNamePatient());
        addFeedbackWindow.fillFieldTextFeedback(DataConfig.DataTest.getFeedback());
        Calendar calendar = addFeedbackWindow.openCalendarSelectDate();
        calendar.switchFutureMonth();
        calendar.selectDateActivation();
        addFeedbackWindow.publishFeedbackButton();
        cardDoctor.selectedUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        assertEquals("Отзыв успешно добавлен", cardDoctor.getNotification());
        assertEquals(DataHelper.getNextMonthDate(), feedback.getDateFeedback());
        assertEquals(DataConfig.DataTest.getNamePatient(), feedback.getAuthorFeedback());
        assertEquals(DataConfig.DataTest.getFeedback(), feedback.getTextFeedback());
        assertEquals(DataConfig.DataTest.getNamePatient(), DataBaseQuery.selectFeedback().getAuthor());
        assertEquals(DataConfig.DataTest.getFeedback(), DataBaseQuery.selectFeedback().getContent());
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
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openFeedback();
        Selenide.sleep(2000);
        AddFeedbackWindow addFeedbackWindow = cardDoctor.openWindowAddFeedback();
        addFeedbackWindow.fillFieldFio(DataConfig.DataTest.getNamePatient());
        addFeedbackWindow.fillFieldTextFeedback(DataConfig.DataTest.getFeedback());
        Calendar calendar = addFeedbackWindow.openCalendarSelectDate();
        calendar.switchPreviousMonth();
        calendar.selectDateActivation();
        addFeedbackWindow.publishFeedbackButton();
        cardDoctor.selectedUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        assertEquals("Отзыв успешно добавлен", cardDoctor.getNotification());
        assertEquals(DataHelper.getPreviousMonthDate(), feedback.getDateFeedback());
        assertEquals(DataConfig.DataTest.getNamePatient(), feedback.getAuthorFeedback());
        assertEquals(DataConfig.DataTest.getFeedback(), feedback.getTextFeedback());
        assertEquals(DataConfig.DataTest.getNamePatient(), DataBaseQuery.selectFeedback().getAuthor());
        assertEquals(DataConfig.DataTest.getFeedback(), DataBaseQuery.selectFeedback().getContent());
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
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openFeedback();
        Selenide.sleep(2000);
        AddFeedbackWindow addFeedbackWindow = cardDoctor.openWindowAddFeedback();
        addFeedbackWindow.addFeedbackWindow();
        addFeedbackWindow.fillFieldFio(DataConfig.DataTest.getNamePatient());
        addFeedbackWindow.fillFieldTextFeedback(DataConfig.DataTest.getFeedback());
        addFeedbackWindow.publishFeedbackButton();
        cardDoctor.selectedUnpublishedFeedback();
        cardDoctor.noSelectedPublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        assertEquals("Отзыв успешно добавлен", cardDoctor.getNotification());
        assertEquals(DataHelper.getCurrentDateRu(), feedback.getDateFeedback());
        assertEquals(DataConfig.DataTest.getNamePatient(), feedback.getAuthorFeedback());
        assertEquals(DataConfig.DataTest.getFeedback(), feedback.getTextFeedback());
        assertEquals(DataConfig.DataTest.getNamePatient(), DataBaseQuery.selectFeedback().getAuthor());
        assertEquals(DataConfig.DataTest.getFeedback(), DataBaseQuery.selectFeedback().getContent());
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
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openFeedback();
        Selenide.sleep(2000);
        AddFeedbackWindow addFeedbackWindow = cardDoctor.openWindowAddFeedback();
        addFeedbackWindow.fillFieldFio(DataConfig.DataTest.getNamePatient());
        addFeedbackWindow.fillFieldTextFeedback(DataConfig.DataTest.getFeedback());
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
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openFeedback();
        Selenide.sleep(2000);
        AddFeedbackWindow addFeedbackWindow = cardDoctor.openWindowAddFeedback();
        addFeedbackWindow.fillFieldTextFeedback(DataConfig.DataTest.getFeedback());
        assertFalse(addFeedbackWindow.isEnabledPublishButton());
    }


    @Feature("Отзывы о враче")
    @Story("Добавление нового отзыва с пустым полем отзыва")
    @Test
    void addFeedbackEmptyFieldText() {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openFeedback();
        Selenide.sleep(2000);
        AddFeedbackWindow addFeedbackWindow = cardDoctor.openWindowAddFeedback();
        addFeedbackWindow.fillFieldFio(DataConfig.DataTest.getNamePatient());
        assertFalse(addFeedbackWindow.isEnabledPublishButton());
    }

    @Feature("Отзывы о враче")
    @Story("Успешное редактирование неопубликованного отзыва о враче")
    @ExtendWith({AddDeleteFeedbackDecorator.class, NotificationDecorator.class})
    @Test
    void editUnpublishedFeedback() {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openFeedback();
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
        changeFeedback.fillFieldText(DataConfig.DataTest.getNewFeedback());
        changeFeedback.saveChanges();
        feedback.feedbackUnpublished();
        assertEquals("Отзыв успешно изменен", cardDoctor.getNotification());
        assertEquals(DataConfig.DataTest.getNewFeedback(), feedback.getTextFeedback());
        assertEquals(DataConfig.DataTest.getNewFeedback(), DataBaseQuery.selectFeedback().getContent());
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
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openFeedback();
        Selenide.sleep(2000);
        cardDoctor.switchUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.feedbackUnpublished();
        feedback.publicationFeedback();
        feedback.feedbackPublished();
        assertEquals("Отзыв успешно изменен", cardDoctor.getNotification());
        assertEquals(DataConfig.DataTest.getFeedback(), feedback.getTextFeedback());
        assertEquals(DataConfig.DataTest.getFeedback(), DataBaseQuery.selectFeedback().getContent());
        assertEquals(true, DataBaseQuery.selectFeedback().getIs_published());
        assertFalse(cardDoctor.isSelectUnPublishedFeedback());
        assertTrue(cardDoctor.isSelectPublishedFeedback());
    }


    @Feature("Отзывы о враче")
    @Story("Успешное редактирование опубликованного отзыва о враче")
    @ExtendWith({AddPublishedDeleteFeedback.class, NotificationDecorator.class})
    @Test
    void editPublishedFeedback() {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openFeedback();
        Selenide.sleep(2000);
        cardDoctor.selectedPublishedFeedback();
        cardDoctor.noSelectedUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.feedbackPublished();
        ChangeFeedbackWindow changeFeedback = feedback.editFeedback();
        changeFeedback.changeFeedbackWindow();
        changeFeedback.fillFieldText(DataConfig.DataTest.getNewFeedback());
        changeFeedback.saveChanges();
        feedback.feedbackPublished();
        assertEquals("Отзыв успешно изменен", cardDoctor.getNotification());
        assertEquals(DataConfig.DataTest.getNewFeedback(), feedback.getTextFeedback());
        assertEquals(DataConfig.DataTest.getNewFeedback(), DataBaseQuery.selectFeedback().getContent());
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
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openFeedback();
        Selenide.sleep(2000);
        Feedback feedback = cardDoctor.getFeedback();
        feedback.feedbackPublished();
        feedback.withdrawalPublication();
        feedback.feedbackUnpublished();
        assertEquals("Отзыв успешно изменен", cardDoctor.getNotification());
        assertEquals(DataConfig.DataTest.getFeedback(), feedback.getTextFeedback());
        assertEquals(DataConfig.DataTest.getFeedback(), DataBaseQuery.selectFeedback().getContent());
        assertEquals(false, DataBaseQuery.selectFeedback().getIs_published());
        assertTrue(cardDoctor.isSelectUnPublishedFeedback());
        assertFalse(cardDoctor.isSelectPublishedFeedback());
    }

    @Feature("Отзывы о враче")
    @Story("Сохранение значений полей в окне редактирования отзыва после закрытия окна")
    @ExtendWith({AddPublishedDeleteFeedback.class})
    @Test
    void closeWindowEditFeedback() {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openFeedback();
        Selenide.sleep(2000);
        Feedback feedback = cardDoctor.getFeedback();
        ChangeFeedbackWindow changeFeedback = feedback.editFeedback();
        changeFeedback.fillFieldText(DataConfig.DataTest.getNewFeedback());
        changeFeedback.closeWindowChangeFeedback();
        assertFalse(changeFeedback.isWindowAppear());
        feedback.editFeedback();
        assertEquals(DataConfig.DataTest.getFeedback(), changeFeedback.getValueTextFeedbackField());
        assertEquals(DataConfig.DataTest.getFeedback(), DataBaseQuery.selectFeedback().getContent());
    }

    @Feature("Отзывы о враче")
    @Story("Редактирование отзыва с пустым полем отзыва")
    @ExtendWith({AddPublishedDeleteFeedback.class})
    @Test
    void editFeedbackEmptyFieldText() {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openFeedback();
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
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openFeedback();
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
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.openFeedback();
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

    @Story("Возврат на страницу Врачи с карточки врача")
    @Test
    void returnToDoctorsPageFromCardDoctorPage() {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.comebackDoctorsPage();
        doctorsPage.doctorsPage();
    }

    @Story("Закрытие уведомления на странице карточки врача по таймауту")
    @Test
    void closeNotificationTimeout() {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        EditPhotoDoctorWindow editPhoto = cardDoctor.openWindowEditPhoto();
        editPhoto.uploadPhoto("src/test/resources/Photo 6,8mbJpeg.jpg");
        checkCloseNotificationTimeout(basePage);
    }

    @Story("Закрытие уведомления на странице карточки врача")
    @Test
    void closeNotification() {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        EditPhotoDoctorWindow editPhoto = cardDoctor.openWindowEditPhoto();
        editPhoto.uploadPhoto("src/test/resources/Photo 6,8mbJpeg.jpg");
        checkCloseNotification(basePage);
    }

    @Story("Закрытие навигационного меню")
    @Test
    void closeNavigateMenu() {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        NavigateMenu navigateMenu = cardDoctor.openNavigateMenu();
        navigateMenu.navigateMenu();
        navigateMenu.closeNavigateMenu();
        assertFalse(navigateMenu.isNavigateMenuDisplayed());
    }


    @Feature("Поиск по врачам")
    @Story("Поиск врача по имени")
    @Test
    void searchNameDoctor() {
        doctorsPage.doctorsPage();
        int countAllDoctors = doctorsPage.getCountDoctors();
        doctorsPage.searchDoctor(DataConfig.DataSearch.getDoctorNameSearch());
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        ElementsCollection namesDoctors = doctorsPage.getNamesDoctors();
        for (SelenideElement nameDoctor : namesDoctors) {
            assertThat(nameDoctor.getText().toLowerCase(), containsString(DataConfig.DataSearch.getDoctorNameSearch().toLowerCase()));
        }
        assertTrue(countResult < countAllDoctors);
    }


    @Feature("Поиск по врачам")
    @Story("Поиск врача по специальности")
    @Test
    void searchSpecializationDoctor() {
        doctorsPage.doctorsPage();
        int countAllDoctors = doctorsPage.getCountDoctors();
        doctorsPage.searchDoctor(DataConfig.DataSearch.getDoctorSpecializationSearch());
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        ElementsCollection specializationDoctors = doctorsPage.getSpecializationDoctors();
        for (SelenideElement specializationDoctor : specializationDoctors) {
            assertThat(specializationDoctor.getText().toLowerCase(), containsString(DataConfig.DataSearch.getDoctorSpecializationSearch().toLowerCase()));
        }
        assertTrue(countResult < countAllDoctors);
    }

    @Feature("Поиск по врачам")
    @Story("Поиск по включению")
    @Test
    void searchByInclusion() {
        doctorsPage.doctorsPage();
        int countAllDoctors = doctorsPage.getCountDoctors();
        doctorsPage.searchDoctor(DataConfig.DataSearch.getSearchByInclusionDoctors());
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        ElementsCollection namesDoctors = doctorsPage.getNamesDoctors();
        ElementsCollection specializationDoctors = doctorsPage.getSpecializationDoctors();
        for (int i = 0; i < namesDoctors.size(); i++) {
            String nameDoctor = namesDoctors.get(i).getText();
            String specializationDoctor = specializationDoctors.get(i).getText();
            boolean isNameFound = nameDoctor.toLowerCase().contains(DataConfig.DataSearch.getSearchByInclusionDoctors().toLowerCase());
            boolean isSpecializationFound = specializationDoctor.toLowerCase().contains(DataConfig.DataSearch.getSearchByInclusionDoctors().toLowerCase());
            assertTrue(isNameFound || isSpecializationFound);
        }
        assertTrue(countResult < countAllDoctors);
    }

    @Feature("Поиск по врачам")
    @Story("Сброс поискового результата после очистки поля")
    @Test
    void resetSearchResultDoctors() {
        doctorsPage.searchDoctor(DataConfig.DataSearch.getDoctorNameSearch());
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        ElementsCollection namesDoctorsSearch = doctorsPage.getNamesDoctors();
        int resultSearch = namesDoctorsSearch.size();
        doctorsPage.clearSearchField();
        Selenide.sleep(5000);
        int countAllDoctors = doctorsPage.getCountDoctors();
        ElementsCollection nameDoctorsAll = doctorsPage.getNamesDoctors();
        int allDoctors = nameDoctorsAll.size();
        assertEquals("", doctorsPage.getValueSearchField());
        assertTrue(resultSearch < allDoctors);
        assertTrue(countResult < countAllDoctors);
    }

    @Feature("Поиск по врачам")
    @Story("Поиск по значению в верхнем регистре")
    @Test
    void searchHighRegister() {
        doctorsPage.doctorsPage();
        int countAllDoctors = doctorsPage.getCountDoctors();
        doctorsPage.searchDoctor(DataConfig.DataSearch.getDoctorNameHighRegister());
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        ElementsCollection namesDoctors = doctorsPage.getNamesDoctors();
        for (SelenideElement nameDoctor : namesDoctors) {
            assertThat(nameDoctor.getText().toLowerCase(), containsString(DataConfig.DataSearch.getDoctorNameHighRegister().toLowerCase()));
        }
        assertTrue(countResult < countAllDoctors);
    }

    @Feature("Поиск по врачам")
    @Story("Поиск по значению в различном регистре")
    @Test
    void searchDifferentRegister() {
        doctorsPage.doctorsPage();
        int countAllDoctors = doctorsPage.getCountDoctors();
        doctorsPage.searchDoctor(DataConfig.DataSearch.getDoctorNameDifferentRegister());
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        ElementsCollection namesDoctors = doctorsPage.getNamesDoctors();
        for (SelenideElement nameDoctor : namesDoctors) {
            assertThat(nameDoctor.getText().toLowerCase(), containsString(DataConfig.DataSearch.getDoctorNameDifferentRegister().toLowerCase()));
        }
        assertTrue(countResult < countAllDoctors);
    }

    @Feature("Сортировка по врачам")
    @Story("Сортировка по отсутствию фото")
    @Test
    void sortingWithoutPhoto() {
        doctorsPage.doctorsPage();
        int countAllDoctors = doctorsPage.getCountDoctors();
        doctorsPage.sortingPhotoNo();
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        List<String> photoDoctorsAttributes = doctorsPage.getPhotoDoctorsAttributes();
        for (String attributeValue : photoDoctorsAttributes) {
            assertThat(attributeValue, equalTo(DataConfig.DataTest.getDefaultPhoto().toLowerCase()));
        }
        assertTrue(countResult < countAllDoctors);
    }

    @Feature("Сортировка по врачам")
    @Story("Сортировка по наличию фото")
    @Test
    void sortingWithPhoto() {
        doctorsPage.doctorsPage();
        int countAllDoctors = doctorsPage.getCountDoctors();
        doctorsPage.sortingPhotoYes();
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        List<String> photoDoctorsAttributes = doctorsPage.getPhotoDoctorsAttributes();
        for (String attributeValue : photoDoctorsAttributes) {
            assertThat(attributeValue, not(DataConfig.DataTest.getDefaultPhoto().toLowerCase()));
        }
        assertTrue(countResult < countAllDoctors);
    }

    @Feature("Сортировка по врачам")
    @Story("Сортировка по всем врачам")
    @Test
    void sortingWithAndWithoutPhoto() {
        doctorsPage.sortingPhotoNo();
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        doctorsPage.sortingPhotoAll();
        Selenide.sleep(5000);
        int countAllDoctors = doctorsPage.getCountDoctors();
        List<String> photoDoctorsAttributes = doctorsPage.getPhotoDoctorsAttributes();
        boolean withoutPhoto = false;
        boolean withPhoto = false;
        for (String attributeValue : photoDoctorsAttributes) {
            if (attributeValue.equals(DataConfig.DataTest.getDefaultPhoto().toLowerCase())) {
                withoutPhoto = true;
            } else {
                withPhoto = true;
            }
        }
        assertTrue(withoutPhoto);
        assertTrue(withPhoto);
        assertTrue(countResult < countAllDoctors);
    }
}



