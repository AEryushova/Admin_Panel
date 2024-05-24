package admin.test;

import admin.pages.AuthorizationPage;
import admin.pages.CardDoctorPage;
import admin.pages.DoctorsPage;
import admin.pages.HeaderBar;
import admin.pages.calendar.Calendar;
import admin.pages.modalWindowDoctors.*;
import admin.utils.DataHelper;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import admin.data.DataInfo;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DoctorsPageTest {
    private HeaderBar headerBar;
    private DoctorsPage doctorsPage;


    @BeforeAll
    static void setUpAllAllure() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1920x1080";
        open("http://192.168.6.48:8083");
        localStorage().setItem("Environment", "demo");
        clearBrowserCookies();
        AuthorizationPage authorizationPage = new AuthorizationPage();
        DataInfo dataInfo = new DataInfo("SUPER_ADMIN", "Qqqq123#");
        authorizationPage.authorizationAdminPanel(dataInfo);
        headerBar = new HeaderBar();
        headerBar.headerBarSuperAdmin();
    }


    @Test
    @Order(1)
    void changePhotoDoctorLess4mbJpeg_11553() {
        doctorsPage=headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor=doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        EditPhotoDoctorWindow editPhoto=cardDoctor.openWindowEditPhoto();
        editPhoto.editPhotoDoctorWindow();
        String srcOriginalPhoto=cardDoctor.getSrcPhoto();
        editPhoto.uploadValidPhoto("src/test/resources/Photo 3,7mbJpeg.jpg");
        assertNotEquals(srcOriginalPhoto,cardDoctor.getSrcPhoto());
    }

    @Test
    @Order(2)
    void changePhotoDoctorLess4mbPng_12100() {
        doctorsPage=headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor=doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        EditPhotoDoctorWindow editPhoto=cardDoctor.openWindowEditPhoto();
        editPhoto.editPhotoDoctorWindow();
        String srcOriginalPhoto=cardDoctor.getSrcPhoto();
        editPhoto.uploadValidPhoto("src/test/resources/Photo 3,2mbPng.png");
        assertNotEquals(srcOriginalPhoto,cardDoctor.getSrcPhoto());
    }

    @Test
    @Order(3)
    void changePhotoDoctorMore4mb_9265() {
        doctorsPage=headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor=doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        EditPhotoDoctorWindow editPhoto=cardDoctor.openWindowEditPhoto();
        editPhoto.editPhotoDoctorWindow();
        String srcOriginalPhoto=cardDoctor.getSrcPhoto();
        editPhoto.uploadInvalidPhoto("src/test/resources/Photo 6,8mbJpeg.jpg");
        assertEquals("Допускаются файлы размером не выше 4Мб",cardDoctor.getNotification());
        assertEquals(srcOriginalPhoto,cardDoctor.getSrcPhoto());
    }
    @Test
    @Order(4)
    void changePhotoDoctorInvalidFormatDocx_9268() {
        doctorsPage=headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor=doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        EditPhotoDoctorWindow editPhoto=cardDoctor.openWindowEditPhoto();
        editPhoto.editPhotoDoctorWindow();
        String srcOriginalPhoto=cardDoctor.getSrcPhoto();
        editPhoto.uploadInvalidPhoto("src/test/resources/Оферта,Политика обработки docx.docx");
        assertEquals("Допускаются файлы с расширением jpg jpeg png",cardDoctor.getNotification());
        assertEquals(srcOriginalPhoto,cardDoctor.getSrcPhoto());
    }

    @Test
    @Order(5)
    void changePhotoDoctorInvalidFormatXlsx_12098() {
        doctorsPage=headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor=doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        EditPhotoDoctorWindow editPhoto=cardDoctor.openWindowEditPhoto();
        editPhoto.editPhotoDoctorWindow();
        String srcOriginalPhoto=cardDoctor.getSrcPhoto();
        editPhoto.uploadInvalidPhoto("src/test/resources/Оферта, Политика обработки .xlsx.xlsx");
        assertEquals("Допускаются файлы с расширением jpg jpeg png",cardDoctor.getNotification());
        assertEquals(srcOriginalPhoto,cardDoctor.getSrcPhoto());
    }

    @Test
    @Order(6)
    void changePhotoDoctorInvalidFormatPDF_12099() {
        doctorsPage=headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor=doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        EditPhotoDoctorWindow editPhoto=cardDoctor.openWindowEditPhoto();
        editPhoto.editPhotoDoctorWindow();
        String srcOriginalPhoto=cardDoctor.getSrcPhoto();
        editPhoto.uploadInvalidPhoto("src/test/resources/Оферта.pdf");
        assertEquals("Допускаются файлы с расширением jpg jpeg png",cardDoctor.getNotification());
        assertEquals(srcOriginalPhoto,cardDoctor.getSrcPhoto());
    }

    @Test
    @Order(7)
    void closeWindowEditPhoto() {
        doctorsPage=headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor=doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        EditPhotoDoctorWindow editPhoto=cardDoctor.openWindowEditPhoto();
        editPhoto.editPhotoDoctorWindow();
        editPhoto.closeWindowEditPhoto();
    }

    @Test
    @Order(8)
    void deletePhoto_11554() {
        doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        cardDoctor.deletePhoto();
        assertEquals("https://lk.mdapp.online/api/storage/img-2a680928-03b0-4ccb-ae99-9be7b4b879c0.jpg",cardDoctor.getSrcPhoto());

    }

    @Test
    @Order(9)
    void deleteDefaultPhoto_11774() {
        doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        cardDoctor.deletePhoto();
        assertEquals("Конфликт (409)",cardDoctor.getNotification());
    }

    @Test
    @Order(10)
    void addingSection_9218(){
        doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        AddIntelligenceWindow intelligenceWindow=cardDoctor.openWindowAddSection();
        intelligenceWindow.addIntelligenceWindow();
        Section section = intelligenceWindow.addSection("Образова");
        section.sectionEmpty();
        assertEquals("Образова",section.getSectionName());
    }

    @Test
    @Order(11)
    void addSectionEmptyField(){
        doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        AddIntelligenceWindow intelligenceWindow=cardDoctor.openWindowAddSection();
        intelligenceWindow.saveValueSectionDescription();
        assertEquals("Неверный запрос (400)", cardDoctor.getNotification());
    }

    @Test
    @Order(12)
    void clearFieldWindowAddSection_9291(){
        doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        AddIntelligenceWindow intelligenceWindow=cardDoctor.openWindowAddSection();
        intelligenceWindow.fillingFieldSectionDescription("Образование");
        intelligenceWindow.clearFieldSectionDescription();
        assertEquals("",intelligenceWindow.getValueSectionDescription());
    }

    @Test
    @Order(13)
    void cancellationWindowAddSection_(){
        doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        AddIntelligenceWindow intelligenceWindow=cardDoctor.openWindowAddSection();
        intelligenceWindow.fillingFieldSectionDescription("Образование");
        intelligenceWindow.cancellationAddSectionDescription();
        AddIntelligenceWindow intelligenceWindowOver=cardDoctor.openWindowAddSection();
        assertEquals("",intelligenceWindowOver.getValueSectionDescription());
    }


    @Test
    @Order(14)
    void editSection_(){
        doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        Section section = cardDoctor.getSection();
        section.sectionEmpty();
        section.editTitle("ние");
        assertEquals("Образование",section.getSectionName());
    }

    @Test
    @Order(15)
    void addingDescription_9296(){
        doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        Section section = cardDoctor.getSection();
        section.sectionEmpty();
        AddIntelligenceWindow intelligenceWindow= section.openWindowAddDescription();
        intelligenceWindow.addIntelligenceWindow();
        Description description=intelligenceWindow.addDescription("2022, по специальности \"Лечебное дело\" в ФГБОУ СамГМУ Минздрава Российской Феде");
        description.description();
        section.sectionNotEmpty();
        assertEquals("2022, по специальности \"Лечебное дело\" в ФГБОУ СамГМУ Минздрава Российской Феде",description.getDescriptionName());
    }

    @Test
    @Order(16)
    void addDescriptionEmptyField(){
        doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        Section section = cardDoctor.getSection();
        AddIntelligenceWindow intelligenceWindow= section.openWindowAddDescription();
        intelligenceWindow.saveValueSectionDescription();
        assertEquals("Неверный запрос (400)", cardDoctor.getNotification());
    }

    @Test
    @Order(17)
    void clearFieldWindowAddDescription_9291(){
        doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        Section section = cardDoctor.getSection();
        AddIntelligenceWindow intelligenceWindow= section.openWindowAddDescription();
        intelligenceWindow.fillingFieldSectionDescription("2023, Получение дополнительного образования за рубежом");
        intelligenceWindow.clearFieldSectionDescription();
        assertEquals("",intelligenceWindow.getValueSectionDescription());
    }
    @Test
    @Order(18)
    void cancellationWindowAddDescription(){
        doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        Section section = cardDoctor.getSection();
        AddIntelligenceWindow intelligenceWindow= section.openWindowAddDescription();
        intelligenceWindow.fillingFieldSectionDescription("2023, Получение дополнительного образования за рубежом");
        intelligenceWindow.cancellationAddSectionDescription();
        AddIntelligenceWindow intelligenceWindowOver= section.openWindowAddDescription();
        assertEquals("",intelligenceWindowOver.getValueSectionDescription());

    }

    @Test
    @Order(19)
    void editDescription_(){
        doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        Description description = cardDoctor.getDescription();
        description.editDescription("рации");
        assertEquals("2022, по специальности \"Лечебное дело\" в ФГБОУ СамГМУ Минздрава Российской Федерации",description.getDescriptionName());

    }

    @Test
    @Order(20)
    void deleteDescription_9303(){
        doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        Section section = cardDoctor.getSection();
        Description description = cardDoctor.getDescription();
        description.deleteDescription();
        section.sectionEmpty();

    }
    @Test
    @Order(21)
    void deleteSection_9301(){
        doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        Section section = cardDoctor.getSection();
        section.deleteTitle();
    }
    @Test
    @Order(22)
    void addFeedbackToday() {
        doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        cardDoctor.publishedCheckbox();
        AddFeedbackWindow feedbackWindow = cardDoctor.openWindowAddFeedback();
        feedbackWindow.addFeedbackWindow();
        assertEquals(DataHelper.getCurrentDate(), feedbackWindow.getValuesButtonToday());
        feedbackWindow.fillingFieldFio("Иванов Иван Иванович");
        feedbackWindow.fillingFieldTextFeedback("Очень хороший врач");
        Calendar calendar = feedbackWindow.openCalendarSelectDate();
        calendar.calendar();
        calendar.selectDateActivationToday();
        feedbackWindow.clickPublishButton();
        assertEquals("Отзыв успешно добавлен", cardDoctor.getNotification());
        cardDoctor.unpublishedCheckbox();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.feedbackUnpublished();
        assertEquals(DataHelper.getCurrentDateRu(), feedback.getDateFeedback());
        assertEquals("Иванов Иван Иванович", feedback.getAuthorFeedback());
        assertEquals("Очень хороший врач", feedback.getTextFeedback());
    }

    @Test
    @Order(22)
    void addFeedbackCurrentMonth_9219() {
        doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        cardDoctor.publishedCheckbox();
        AddFeedbackWindow feedbackWindow = cardDoctor.openWindowAddFeedback();
        feedbackWindow.addFeedbackWindow();
        assertEquals(DataHelper.getCurrentDate(), feedbackWindow.getValuesButtonToday());
        feedbackWindow.fillingFieldFio("Иванов Иван Иванович");
        feedbackWindow.fillingFieldTextFeedback("Очень хороший врач");
        Calendar calendar = feedbackWindow.openCalendarSelectDate();
        calendar.calendar();
        calendar.selectDateActivation();
        feedbackWindow.clickPublishButton();
        assertEquals("Отзыв успешно добавлен", cardDoctor.getNotification());
        cardDoctor.unpublishedCheckbox();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.feedbackUnpublished();
        assertEquals(DataHelper.generateFutureDateCurrentMonth(), feedback.getDateFeedback());
        assertEquals("Иванов Иван Иванович", feedback.getAuthorFeedback());
        assertEquals("Очень хороший врач", feedback.getTextFeedback());
    }

    @Test
    @Order(23)
    void addFeedbackFutureMonth_9305(){
        doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        cardDoctor.publishedCheckbox();
        AddFeedbackWindow feedbackWindow=cardDoctor.openWindowAddFeedback();
        feedbackWindow.addFeedbackWindow();
        assertEquals(DataHelper.getCurrentDate(), feedbackWindow.getValuesButtonToday());
        feedbackWindow.fillingFieldFio("Федоров Федор Федорович");
        feedbackWindow.fillingFieldTextFeedback("Так себе врач");
        Calendar calendar= feedbackWindow.openCalendarSelectDate();
        calendar.calendar();
        calendar.switchFutureMonth();
        calendar.selectDateActivation();
        feedbackWindow.clickPublishButton();
        assertEquals("Отзыв успешно добавлен",cardDoctor.getNotification());
        cardDoctor.unpublishedCheckbox();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.feedbackUnpublished();
        assertEquals(DataHelper.getNextMonthDate(), feedback.getDateFeedback());
        assertEquals("Федоров Федор Федорович", feedback.getAuthorFeedback());
        assertEquals("Так себе врач", feedback.getTextFeedback());


    }

    @Test
    @Order(24)
    void addFeedbackPreviousMonth_9305(){
        doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        cardDoctor.publishedCheckbox();
        AddFeedbackWindow feedbackWindow=cardDoctor.openWindowAddFeedback();
        feedbackWindow.addFeedbackWindow();
        assertEquals(DataHelper.getCurrentDate(), feedbackWindow.getValuesButtonToday());
        feedbackWindow.fillingFieldFio("Степанов Степан Степанович");
        feedbackWindow.fillingFieldTextFeedback("Обязательно приду еще на прием к этому врачу");
        Calendar calendar= feedbackWindow.openCalendarSelectDate();
        calendar.calendar();
        calendar.switchPreviousMonth();
        calendar.selectDateActivation();
        feedbackWindow.clickPublishButton();
        assertEquals("Отзыв успешно добавлен",cardDoctor.getNotification());
        cardDoctor.unpublishedCheckbox();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.feedbackUnpublished();
        assertEquals(DataHelper.getPreviousMonthDate(), feedback.getDateFeedback());
        assertEquals("Степанов Степан Степанович", feedback.getAuthorFeedback());
        assertEquals("Обязательно приду еще на прием к этому врачу", feedback.getTextFeedback());
    }

    @Test
    @Order(25)
    void editUnpublishedFeedback_9314(){
        doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        cardDoctor.publishedCheckbox();
        cardDoctor.switchUnpublishedFeedback();
       Feedback feedback = cardDoctor.getFeedback();
       feedback.feedbackUnpublished();
       ChangeFeedbackWindow changeFeedback = feedback.editFeedback();
       changeFeedback.changeFeedbackWindow();
       changeFeedback.clearFieldText();
       changeFeedback.fillingFieldText("Внимательно отнесся к моей проблеме");
       changeFeedback.saveChanges();
       assertEquals("Отзыв успешно изменен",cardDoctor.getNotification());
       feedback.feedbackUnpublished();
       cardDoctor.unpublishedCheckbox();
       assertEquals("Внимательно отнесся к моей проблеме", feedback.getTextFeedback());
    }

    @Test
    @Order(26)
    void publicationUnpublishedFeedback_9309(){
        doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        cardDoctor.publishedCheckbox();
        cardDoctor.switchUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.feedbackUnpublished();
        feedback.publicationFeedback();
        assertEquals("Отзыв успешно изменен",cardDoctor.getNotification());
        feedback.feedbackPublished();
        cardDoctor.publishedCheckbox();
    }

    @Test
    @Order(27)
    void editPublishedFeedback_9313(){
        doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        cardDoctor.publishedCheckbox();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.feedbackPublished();
        ChangeFeedbackWindow changeFeedback = feedback.editFeedback();
        changeFeedback.changeFeedbackWindow();
        changeFeedback.clearFieldText();
        changeFeedback.fillingFieldText("Прием у этого врача прошел замечательно");
        changeFeedback.saveChanges();
        assertEquals("Отзыв успешно изменен",cardDoctor.getNotification());
        assertEquals("Прием у этого врача прошел замечательно", feedback.getTextFeedback());
        feedback.feedbackPublished();
        cardDoctor.publishedCheckbox();
    }

    @Test
    @Order(28)
    void withdrawalPublicationFeedback_9311(){
        doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        cardDoctor.publishedCheckbox();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.feedbackPublished();
        feedback.withdrawalPublication();
        assertEquals("Отзыв успешно изменен",cardDoctor.getNotification());
        cardDoctor.unpublishedCheckbox();
        feedback.feedbackUnpublished();
    }

    @Test
    @Order(29)
    void deleteUnpublishedFeedback_9310(){
        doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        cardDoctor.publishedCheckbox();
        cardDoctor.switchUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.feedbackUnpublished();
        feedback.deleteFeedback();
        assertEquals("Отзыв успешно удален",cardDoctor.getNotification());
        cardDoctor.unpublishedCheckbox();
    }

    @Test
    @Order(30)
    void sotringUnpublishedFeedbacks_9221(){
        doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
        cardDoctor.publishedCheckbox();
        cardDoctor.switchUnpublishedFeedback();

    }








}
