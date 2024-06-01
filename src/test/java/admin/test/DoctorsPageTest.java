package admin.test;

import admin.data.DataTest;
import admin.pages.*;
import admin.pages.calendar.Calendar;
import admin.pages.modalWindowDoctors.*;
import admin.utils.testUtils.CookieUtils;
import admin.utils.dbUtils.DataBaseUtils;
import admin.utils.DataHelper;
import admin.utils.testUtils.TestSetupAuth;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import static admin.utils.dbUtils.DataBaseUtils.selectFeedback;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Epic("Врачи")
public class DoctorsPageTest {


    @BeforeAll
    static void setUpAllAllure() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeAll
    static void setupAdminPanelWithCookies() {
        TestSetupAuth.authAdminPanel(DataTest.getLoginSuperAdmin(), DataTest.getPasswordSuperAdmin());
    }

    @BeforeEach
    void loadCookies() {
        CookieUtils.loadCookies();
    }

    @Feature("Переключение между вкладками")
    @Story("Переход на вкладку администрирование")
    @Test
    void openAdministrationPage() {
        HeaderBar headerBar = new HeaderBar();
        headerBar.headerBarSuperAdmin();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.adminPage();
    }

    @Feature("Переключение между вкладками")
    @Story("Переход на вкладку услуг")
    @Test
    void openServicesPage() {
        HeaderBar headerBar = new HeaderBar();
        headerBar.headerBarSuperAdmin();
        ServicesPage servicesPage = headerBar.servicesTabOpen();
        servicesPage.servicesPage();
    }

    @Feature("Переключение между вкладками")
    @Story("Переход на вкладку faq")
    @Test
    void openFaqPage() {
        HeaderBar headerBar = new HeaderBar();
        headerBar.headerBarSuperAdmin();
        FaqPage faqPage = headerBar.faqTabOpen();
        faqPage.faqPage();
    }

    @Feature("Переключение между вкладками")
    @Story("Переход на вкладку настроек")
    @Test
    void openSettingPage() {
        HeaderBar headerBar = new HeaderBar();
        headerBar.headerBarSuperAdmin();
        SettingPage settingPage = headerBar.settingTabOpen();
        settingPage.settingPage();
    }

    @Feature("Переключение между вкладками")
    @Story("Сохранение состояния страницы при клике по вкладке докторов")
    @Test
    void clickDoctorsPage() {
        HeaderBar headerBar = new HeaderBar();
        headerBar.headerBarSuperAdmin();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
    }

    @Feature("Фотография врача")
    @Story("Успешная смена фотографии врачу в формате Jpeg")
    @Test
    void changePhotoDoctorLess4mbJpeg() {
        HeaderBar headerBar = new HeaderBar();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        EditPhotoDoctorWindow editPhoto = cardDoctor.openWindowEditPhoto();
        editPhoto.editPhotoDoctorWindow();
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        editPhoto.uploadValidPhoto("src/test/resources/Photo 3,7mbJpeg.jpg");
        assertNotEquals(srcOriginalPhoto, cardDoctor.getSrcPhoto());
    }

    @Feature("Фотография врача")
    @Story("Успешная смена фотографии врачу в формате Png")
    @Test
    void changePhotoDoctorLess4mbPng() {
        HeaderBar headerBar = new HeaderBar();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        EditPhotoDoctorWindow editPhoto = cardDoctor.openWindowEditPhoto();
        editPhoto.editPhotoDoctorWindow();
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        editPhoto.uploadValidPhoto("src/test/resources/Photo 3,2mbPng.png");
        assertNotEquals(srcOriginalPhoto, cardDoctor.getSrcPhoto());
    }

    @Feature("Фотография врача")
    @Story("Cмена фотографии врачу с файлом весом более 4mb")
    @Test
    void changePhotoDoctorMore4mb() {
        HeaderBar headerBar = new HeaderBar();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        EditPhotoDoctorWindow editPhoto = cardDoctor.openWindowEditPhoto();
        editPhoto.editPhotoDoctorWindow();
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        editPhoto.uploadInvalidPhoto("src/test/resources/Photo 6,8mbJpeg.jpg");
        assertEquals("Допускаются файлы размером не выше 4Мб", cardDoctor.getNotification());
        assertEquals(srcOriginalPhoto, cardDoctor.getSrcPhoto());
    }

    @Feature("Фотография врача")
    @Story("Cмена фотографии врачу с файлом в формате Docx")
    @Test
    void changePhotoDoctorInvalidFormatDocx() {
        HeaderBar headerBar = new HeaderBar();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        EditPhotoDoctorWindow editPhoto = cardDoctor.openWindowEditPhoto();
        editPhoto.editPhotoDoctorWindow();
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        editPhoto.uploadInvalidPhoto("src/test/resources/Оферта,Политика обработки docx.docx");
        assertEquals("Допускаются файлы с расширением jpg jpeg png", cardDoctor.getNotification());
        assertEquals(srcOriginalPhoto, cardDoctor.getSrcPhoto());
    }

    @Feature("Фотография врача")
    @Story("Cмена фотографии врачу с файлом в формате Xlsx")
    @Test
    void changePhotoDoctorInvalidFormatXlsx() {
        HeaderBar headerBar = new HeaderBar();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        EditPhotoDoctorWindow editPhoto = cardDoctor.openWindowEditPhoto();
        editPhoto.editPhotoDoctorWindow();
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        editPhoto.uploadInvalidPhoto("src/test/resources/Оферта, Политика обработки .xlsx.xlsx");
        assertEquals("Допускаются файлы с расширением jpg jpeg png", cardDoctor.getNotification());
        assertEquals(srcOriginalPhoto, cardDoctor.getSrcPhoto());
    }

    @Feature("Фотография врача")
    @Story("Cмена фотографии врачу с файлом в формате PDF")
    @Test
    void changePhotoDoctorInvalidFormatPDF() {
        HeaderBar headerBar = new HeaderBar();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        EditPhotoDoctorWindow editPhoto = cardDoctor.openWindowEditPhoto();
        editPhoto.editPhotoDoctorWindow();
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        editPhoto.uploadInvalidPhoto("src/test/resources/Оферта.pdf");
        assertEquals("Допускаются файлы с расширением jpg jpeg png", cardDoctor.getNotification());
        assertEquals(srcOriginalPhoto, cardDoctor.getSrcPhoto());
    }

    @Feature("Фотография врача")
    @Story("Закрытие окна смены фотографии")
    @Test
    void closeWindowEditPhoto() {
        HeaderBar headerBar = new HeaderBar();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        EditPhotoDoctorWindow editPhoto = cardDoctor.openWindowEditPhoto();
        editPhoto.editPhotoDoctorWindow();
        editPhoto.closeWindowEditPhoto();
    }

    @Feature("Фотография врача")
    @Story("Успешное удаление фотографии врача")
    @Test
    void deletePhoto() {
        HeaderBar headerBar = new HeaderBar();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        cardDoctor.deletePhoto();
        assertEquals("https://lk.mdapp.online/api/storage/img-2a680928-03b0-4ccb-ae99-9be7b4b879c0.jpg", cardDoctor.getSrcPhoto());

    }

    @Feature("Фотография врача")
    @Story("Удаление дефолтной фотографии врача")
    @Test
    void deleteDefaultPhoto() {
        HeaderBar headerBar = new HeaderBar();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        cardDoctor.deletePhoto();
        assertEquals("Конфликт (409)", cardDoctor.getNotification());
    }

    @Feature("Информация о враче")
    @Story("Успешное добавление раздела в инфо о враче")
    @Test
    void addingSection() {
        HeaderBar headerBar = new HeaderBar();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        AddIntelligenceWindow intelligenceWindow = cardDoctor.openWindowAddSection();
        intelligenceWindow.addIntelligenceWindow();
        Section section = intelligenceWindow.addSection("Образова");
        section.sectionEmpty();
        assertEquals("Образова", section.getSectionName());
    }

    @Feature("Информация о враче")
    @Story("Добавление пустого раздела в инфо о враче")
    @Test
    void addSectionEmptyField() {
        HeaderBar headerBar = new HeaderBar();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        AddIntelligenceWindow intelligenceWindow = cardDoctor.openWindowAddSection();
        intelligenceWindow.saveValueSectionDescription();
        assertEquals("Неверный запрос (400)", cardDoctor.getNotification());
    }

    @Feature("Информация о враче")
    @Story("Отмена добавления раздела в инфо о враче")
    @Test
    void cancellationWindowAddSection() {
        HeaderBar headerBar = new HeaderBar();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        AddIntelligenceWindow intelligenceWindow = cardDoctor.openWindowAddSection();
        intelligenceWindow.fillFieldSectionDescription("Образование");
        intelligenceWindow.cancellationAddSectionDescription();
        AddIntelligenceWindow intelligenceWindowOver = cardDoctor.openWindowAddSection();
        assertEquals("", intelligenceWindowOver.getValueSectionDescription());
    }

    @Feature("Информация о враче")
    @Story("Успешное редактирование раздела в инфо о враче")
    @Test
    void editSection() {
        HeaderBar headerBar = new HeaderBar();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        Section section = cardDoctor.getSection();
        section.sectionEmpty();
        section.editTitle("ние");
        assertEquals("Образование", section.getSectionName());
    }

    @Feature("Информация о враче")
    @Story("Успешное удаление раздела в инфо о враче")
    @Test
    void deleteSection() {
        HeaderBar headerBar = new HeaderBar();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        Section section = cardDoctor.getSection();
        section.deleteTitle();
    }

    @Feature("Информация о враче")
    @Story("Успешное добавление описания к разделу в инфо о враче")
    @Test
    void addingDescription() {
        HeaderBar headerBar = new HeaderBar();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        Section section = cardDoctor.getSection();
        section.sectionEmpty();
        AddIntelligenceWindow intelligenceWindow = section.openWindowAddDescription();
        intelligenceWindow.addIntelligenceWindow();
        Description description = intelligenceWindow.addDescription("2022, по специальности \"Лечебное дело\" в ФГБОУ СамГМУ Минздрава Российской Феде");
        description.description();
        section.sectionNotEmpty();
        assertEquals("2022, по специальности \"Лечебное дело\" в ФГБОУ СамГМУ Минздрава Российской Феде", description.getDescriptionName());
    }

    @Feature("Информация о враче")
    @Story("Добавление пустого описания к разделу в инфо о враче")
    @Test
    void addDescriptionEmptyField() {
        HeaderBar headerBar = new HeaderBar();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        Section section = cardDoctor.getSection();
        AddIntelligenceWindow intelligenceWindow = section.openWindowAddDescription();
        intelligenceWindow.saveValueSectionDescription();
        assertEquals("Неверный запрос (400)", cardDoctor.getNotification());
    }

    @Feature("Информация о враче")
    @Story("Отмена добавления описания к разделу в инфо о враче")
    @Test
    void cancellationWindowAddDescription() {
        HeaderBar headerBar = new HeaderBar();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        Section section = cardDoctor.getSection();
        AddIntelligenceWindow intelligenceWindow = section.openWindowAddDescription();
        intelligenceWindow.fillFieldSectionDescription("2023, Получение дополнительного образования за рубежом");
        intelligenceWindow.cancellationAddSectionDescription();
        AddIntelligenceWindow intelligenceWindowOver = section.openWindowAddDescription();
        assertEquals("", intelligenceWindowOver.getValueSectionDescription());

    }

    @Feature("Информация о враче")
    @Story("Успешное редактирование описания к разделу в инфо о враче")
    @Test
    void editDescription() {
        HeaderBar headerBar = new HeaderBar();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        Description description = cardDoctor.getDescription();
        description.editDescription("рации");
        assertEquals("2022, по специальности \"Лечебное дело\" в ФГБОУ СамГМУ Минздрава Российской Федерации", description.getDescriptionName());

    }

    @Feature("Информация о враче")
    @Story("Успешное удаление описания к разделу в инфо о враче")
    @Test
    void deleteDescription() {
        HeaderBar headerBar = new HeaderBar();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        Section section = cardDoctor.getSection();
        Description description = cardDoctor.getDescription();
        description.deleteDescription();
        section.sectionEmpty();

    }

    @Feature("Отзывы о враче")
    @Story("Успешное добавление отзыва о враче текущей датой")
    @Test
    void addFeedbackToday() {
        DataBaseUtils.clearAllFeedback();
        HeaderBar headerBar = new HeaderBar();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        cardDoctor.togglePublishedCheckbox();
        AddFeedbackWindow feedbackWindow = cardDoctor.openWindowAddFeedback();
        feedbackWindow.addFeedbackWindow();
        assertEquals(DataHelper.getCurrentDate(), feedbackWindow.getValuesButtonToday());
        feedbackWindow.fillFieldFio("Иванов Иван Иванович");
        feedbackWindow.fillFieldTextFeedback("Тестовый отзыв");
        Calendar calendar = feedbackWindow.openCalendarSelectDate();
        calendar.calendar();
        calendar.selectDateActivationToday();
        feedbackWindow.clickPublishButton();
        assertEquals("Отзыв успешно добавлен", cardDoctor.getNotification());
        cardDoctor.toggleUnpublishedCheckbox();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.feedbackUnpublished();
        assertEquals(DataHelper.getCurrentDateRu(), feedback.getDateFeedback());
        assertEquals("Иванов Иван Иванович", feedback.getAuthorFeedback());
        assertEquals("Тестовый отзыв", feedback.getTextFeedback());
        assertEquals("Иванов Иван Иванович", selectFeedback().getAuthor());
        assertEquals("Тестовый отзыв", selectFeedback().getContent());
        assertEquals(false,selectFeedback().getIs_published());
    }

    @Feature("Отзывы о враче")
    @Story("Успешное добавление отзыва о врачу датой в текущем месяце")
    @Test
    void addFeedbackCurrentMonth() {
        DataBaseUtils.clearAllFeedback();
        HeaderBar headerBar = new HeaderBar();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        cardDoctor.togglePublishedCheckbox();
        AddFeedbackWindow feedbackWindow = cardDoctor.openWindowAddFeedback();
        feedbackWindow.addFeedbackWindow();
        assertEquals(DataHelper.getCurrentDate(), feedbackWindow.getValuesButtonToday());
        feedbackWindow.fillFieldFio("Степанов Степан Степанович");
        feedbackWindow.fillFieldTextFeedback("Очень хороший врач");
        Calendar calendar = feedbackWindow.openCalendarSelectDate();
        calendar.calendar();
        calendar.selectDateActivation();
        feedbackWindow.clickPublishButton();
        assertEquals("Отзыв успешно добавлен", cardDoctor.getNotification());
        cardDoctor.toggleUnpublishedCheckbox();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.feedbackUnpublished();
        assertEquals(DataHelper.generateFutureDateCurrentMonth(), feedback.getDateFeedback());
        assertEquals("Степанов Степан Степанович", feedback.getAuthorFeedback());
        assertEquals("Очень хороший врач", feedback.getTextFeedback());
        assertEquals("Степанов Степан Степанович", selectFeedback().getAuthor());
        assertEquals("Очень хороший врач", selectFeedback().getContent());
        assertEquals(false,selectFeedback().getIs_published());
        assertEquals(DataHelper.convertDateForDB(), DataHelper.trimDateStringToDay(selectFeedback().getCreated_at()));


    }

    @Feature("Отзывы о враче")
    @Story("Успешное добавление отзыва о враче датой в следующем месяце")
    @Test
    void addFeedbackFutureMonth() {
        HeaderBar headerBar = new HeaderBar();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        cardDoctor.togglePublishedCheckbox();
        AddFeedbackWindow feedbackWindow = cardDoctor.openWindowAddFeedback();
        feedbackWindow.addFeedbackWindow();
        assertEquals(DataHelper.getCurrentDate(), feedbackWindow.getValuesButtonToday());
        feedbackWindow.fillFieldFio("Федоров Федор Федорович");
        feedbackWindow.fillFieldTextFeedback("Так себе врач");
        Calendar calendar = feedbackWindow.openCalendarSelectDate();
        calendar.calendar();
        calendar.switchFutureMonth();
        calendar.selectDateActivation();
        feedbackWindow.clickPublishButton();
        assertEquals("Отзыв успешно добавлен", cardDoctor.getNotification());
        cardDoctor.toggleUnpublishedCheckbox();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.feedbackUnpublished();
        assertEquals(DataHelper.getNextMonthDate(), feedback.getDateFeedback());
        assertEquals("Федоров Федор Федорович", feedback.getAuthorFeedback());
        assertEquals("Так себе врач", feedback.getTextFeedback());


    }

    @Feature("Отзывы о враче")
    @Story("Успешное добавление отзыва о враче датой в предыдущем месяце")
    @Test
    void addFeedbackPreviousMonth() {
        HeaderBar headerBar = new HeaderBar();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        cardDoctor.togglePublishedCheckbox();
        AddFeedbackWindow feedbackWindow = cardDoctor.openWindowAddFeedback();
        feedbackWindow.addFeedbackWindow();
        assertEquals(DataHelper.getCurrentDate(), feedbackWindow.getValuesButtonToday());
        feedbackWindow.fillFieldFio("Степанов Степан Степанович");
        feedbackWindow.fillFieldTextFeedback("Обязательно приду еще на прием к этому врачу");
        Calendar calendar = feedbackWindow.openCalendarSelectDate();
        calendar.calendar();
        calendar.switchPreviousMonth();
        calendar.selectDateActivation();
        feedbackWindow.clickPublishButton();
        assertEquals("Отзыв успешно добавлен", cardDoctor.getNotification());
        cardDoctor.toggleUnpublishedCheckbox();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.feedbackUnpublished();
        assertEquals(DataHelper.getPreviousMonthDate(), feedback.getDateFeedback());
        assertEquals("Степанов Степан Степанович", feedback.getAuthorFeedback());
        assertEquals("Обязательно приду еще на прием к этому врачу", feedback.getTextFeedback());
    }

    @Feature("Отзывы о враче")
    @Story("Успешное редактирование неопубликованного отзыва о враче")
    @Test
    void editUnpublishedFeedback() {
        HeaderBar headerBar = new HeaderBar();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        cardDoctor.togglePublishedCheckbox();
        cardDoctor.switchUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.feedbackUnpublished();
        ChangeFeedbackWindow changeFeedback = feedback.editFeedback();
        changeFeedback.changeFeedbackWindow();
        changeFeedback.clearFieldText();
        changeFeedback.fillFieldText("Внимательно отнесся к моей проблеме");
        changeFeedback.saveChanges();
        assertEquals("Отзыв успешно изменен", cardDoctor.getNotification());
        feedback.feedbackUnpublished();
        cardDoctor.toggleUnpublishedCheckbox();
        assertEquals("Внимательно отнесся к моей проблеме", feedback.getTextFeedback());
    }

    @Feature("Отзывы о враче")
    @Story("Успешная публикация неопубликованного отзыва о враче")
    @Test
    void publicationUnpublishedFeedback() {
        HeaderBar headerBar = new HeaderBar();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        cardDoctor.togglePublishedCheckbox();
        cardDoctor.switchUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.feedbackUnpublished();
        feedback.publicationFeedback();
        assertEquals("Отзыв успешно изменен", cardDoctor.getNotification());
        feedback.feedbackPublished();
        cardDoctor.togglePublishedCheckbox();
    }

    @Feature("Отзывы о враче")
    @Story("Успешное редактирование опубликованного отзыва о враче")
    @Test
    void editPublishedFeedback() {
        HeaderBar headerBar = new HeaderBar();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        cardDoctor.togglePublishedCheckbox();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.feedbackPublished();
        ChangeFeedbackWindow changeFeedback = feedback.editFeedback();
        changeFeedback.changeFeedbackWindow();
        changeFeedback.clearFieldText();
        changeFeedback.fillFieldText("Прием у этого врача прошел замечательно");
        changeFeedback.saveChanges();
        assertEquals("Отзыв успешно изменен", cardDoctor.getNotification());
        assertEquals("Прием у этого врача прошел замечательно", feedback.getTextFeedback());
        feedback.feedbackPublished();
        cardDoctor.togglePublishedCheckbox();
    }

    @Feature("Отзывы о враче")
    @Story("Успешное снятие с публикации опубликованного отзыва о враче")
    @Test
    void withdrawalPublicationFeedback() {
        HeaderBar headerBar = new HeaderBar();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        cardDoctor.togglePublishedCheckbox();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.feedbackPublished();
        feedback.withdrawalPublication();
        assertEquals("Отзыв успешно изменен", cardDoctor.getNotification());
        cardDoctor.toggleUnpublishedCheckbox();
        feedback.feedbackUnpublished();
    }

    @Feature("Отзывы о враче")
    @Story("Успешное удаление неопубликованного отзыва о враче")
    @Test
    void deleteUnpublishedFeedback() {
        HeaderBar headerBar = new HeaderBar();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        cardDoctor.togglePublishedCheckbox();
        cardDoctor.switchUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.feedbackUnpublished();
        feedback.deleteFeedback();
        assertEquals("Отзыв успешно удален", cardDoctor.getNotification());
        cardDoctor.toggleUnpublishedCheckbox();
    }

    @Feature("Отзывы о враче")
    @Story("Сортировка неопубликованных отзывов о враче")
    @Test
    void sotringUnpublishedFeedbacks() {
        HeaderBar headerBar = new HeaderBar();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        cardDoctor.togglePublishedCheckbox();
        cardDoctor.switchUnpublishedFeedback();
    }

    @Story("Возврат на страницу Врачи с карточки врача")
    @Test
    void returnToDoctorsPageFromCardDoctorPage() {
        HeaderBar headerBar = new HeaderBar();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        cardDoctor.comebackDoctorsPage();
        doctorsPage.doctorsPage();
    }

    @Story("Возврат к хэдеру страницы врачей")
    @Test
    void returnToStartPageDoctors() {
        HeaderBar headerBar = new HeaderBar();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        doctorsPage.scrollPageToBottom();
        doctorsPage.returnToStartPage();
        doctorsPage.isReturnButtonAppear();
    }

    @Story("Возврат к хэдеру страницы карточки врача")
    @Test
    void returnToStartPageCardDoctor() {
        HeaderBar headerBar = new HeaderBar();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        cardDoctor.scrollPageToBottom();
        cardDoctor.returnToStartPage();
        cardDoctor.isReturnButtonAppear();
    }

    @Story("Закрытие уведомления на странице карточки врача")
    @Test
    void closeNotification() {
        HeaderBar headerBar = new HeaderBar();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        EditPhotoDoctorWindow editPhoto = cardDoctor.openWindowEditPhoto();
        editPhoto.editPhotoDoctorWindow();
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        editPhoto.uploadInvalidPhoto("src/test/resources/Photo 6,8mbJpeg.jpg");
        assertEquals("Допускаются файлы размером не выше 4Мб", cardDoctor.getNotification());
        assertEquals(srcOriginalPhoto, cardDoctor.getSrcPhoto());
        cardDoctor.closeNotification();
    }


}
