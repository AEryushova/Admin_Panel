package admin.test;

import admin.data.DataInfo;
import admin.pages.BasePage.BasePage;
import admin.pages.CardDoctorPage.*;
import admin.pages.DoctorsPage.DoctorsPage;
import admin.pages.HeaderMenu.HeaderMenu;
import admin.utils.decoratorsTest.doctors.AddDeleteSectionDecorator;
import admin.utils.decoratorsTest.doctors.DeletePhotoDoctorDecorator;
import admin.utils.decoratorsTest.doctors.DeleteSectionDecorator;
import admin.utils.decoratorsTest.doctors.SetPhotoDoctorDecorator;
import admin.utils.decoratorsTest.general.AllureDecorator;
import admin.utils.decoratorsTest.general.NotificationDecorator;
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
public class DoctorsPageTest extends BaseTest {

    private DoctorsPage doctorsPage;
    private HeaderMenu headerMenu;
    private BasePage basePage;

    @ExtendWith(AllureDecorator.class)

    @BeforeAll
    static void setUpAuth() {
        BrowserManager.authGetCookie(DataInfo.UserData.getLoginAdmin(), DataInfo.UserData.getPasswordAdmin());
    }

    @BeforeEach
    void setUp(){
        BrowserManager.openPagesAfterAuth();
        doctorsPage=new DoctorsPage();
        headerMenu= new HeaderMenu();
        basePage = new BasePage();
        headerMenu.doctorsTabOpen();
    }

    @Feature("Замена фотографии врачу")
    @Story("Успешная замена фотографии врачу в формате Jpeg и Png")
    @ExtendWith(DeletePhotoDoctorDecorator.class)
    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/Photo 3,7mbJpeg.jpg","src/test/resources/Photo 3,2mbPng.png"})
    void changePhotoDoctorValidFormat(String path) {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.cardDoctorPage();
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
    @ValueSource(strings = {"src/test/resources/Оферта,Политика обработки docx.docx","src/test/resources/Оферта, Политика обработки .xlsx.xlsx", "src/test/resources/Оферта.pdf"})
    void changePhotoDoctorInvalidFormat(String path) {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        EditPhotoDoctorWindow editPhoto = cardDoctor.openWindowEditPhoto();
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        editPhoto.uploadPhoto(path);;
        assertEquals("Допускаются файлы с расширением jpg jpeg png", cardDoctor.getNotification());
        assertEquals(srcOriginalPhoto, cardDoctor.getSrcPhoto());
        assertEquals(srcOriginalPhoto, DataBaseQuery.selectInfoDoctor().getPhoto_uri());
    }


    @Feature("Замена фотографии врачу")
    @Story("Закрытие окна смены фотографии")
    @Test
    void closeWindowEditPhoto() {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
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
        String srcOriginalPhoto = cardDoctor.getSrcPhoto();
        cardDoctor.deletePhoto();
        Selenide.sleep(3000);
        assertNotEquals(srcOriginalPhoto, cardDoctor.getSrcPhoto());
        assertEquals(DataInfo.DataTest.getDefaultPhoto(), cardDoctor.getSrcPhoto());
        assertEquals(DataInfo.DataTest.getDefaultPhoto(), DataBaseQuery.selectInfoDoctor().getPhoto_uri());
    }

    @Feature("Замена фотографии врачу")
    @Story("Удаление дефолтной фотографии врача")
    @ExtendWith({DeletePhotoDoctorDecorator.class, NotificationDecorator.class})
    @Test
    void deleteDefaultPhoto() {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
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
        AddIntelligenceWindow intelligenceWindow = cardDoctor.openWindowAddSection();
        intelligenceWindow.addIntelligenceWindow();
        intelligenceWindow.fillFieldText(DataInfo.DataTest.getSection());
        intelligenceWindow.saveValue();
        Section section = cardDoctor.getSection();
        assertEquals(DataInfo.DataTest.getSection(),section.getSection());
        assertEquals(DataInfo.DataTest.getSection(),DataBaseQuery.selectSection(DeleteSectionDecorator.getDoctorId()).getTitle());
        assertTrue(cardDoctor.isExistSection());
        assertFalse(intelligenceWindow.isWindowAppear());
    }

    @Feature("Информация о враче")
    @Story("Добавление пустого раздела в инфо о враче")
    @ExtendWith({DeleteSectionDecorator.class,NotificationDecorator.class})
    @Test
    void addSectionEmptyField() {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
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
        AddIntelligenceWindow intelligenceWindow = cardDoctor.openWindowAddSection();
        intelligenceWindow.fillFieldText(DataInfo.DataTest.getSection());
        intelligenceWindow.cancelAdd();
        assertFalse(intelligenceWindow.isWindowAppear());
        cardDoctor.openWindowAddSection();
        assertEquals("", intelligenceWindow.getValueField());
    }


    @Feature("Информация о враче")
    @Story("Успешное редактирование раздела в инфо о враче")
    @ExtendWith(AddDeleteSectionDecorator.class)
    @Test
    void editSection() {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        Section section = cardDoctor.getSection();
        section.section();
        section.editTitle();
        section.fillTitleField(DataInfo.DataTest.getNewSection());
        section.editTitle();
        assertEquals(DataInfo.DataTest.getNewSection(),section.getSection());
        assertEquals(DataInfo.DataTest.getNewSection(),DataBaseQuery.selectSection(AddDeleteSectionDecorator.getDoctorId()).getTitle());
    }


    /*

    @Feature("Информация о враче")
    @Story("Успешное удаление раздела в инфо о враче")
    @Test
    void deleteSection() {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        Section section = cardDoctor.getSection();
        section.deleteTitle();
    }

    @Feature("Информация о враче")
    @Story("Успешное добавление описания к разделу в инфо о враче")
    @Test
    void addingDescription() {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        Section section = cardDoctor.getSection();
        AddIntelligenceWindow intelligenceWindow = section.openWindowAddDescription();
        intelligenceWindow.addIntelligenceWindow();
        Description description = intelligenceWindow.addDescription("2022, по специальности \"Лечебное дело\" в ФГБОУ СамГМУ Минздрава Российской Феде");
        description.description();
        assertEquals("2022, по специальности \"Лечебное дело\" в ФГБОУ СамГМУ Минздрава Российской Феде", description.getDescriptionName());
    }

    @Feature("Информация о враче")
    @Story("Добавление пустого описания к разделу в инфо о враче")
    @ExtendWith(NotificationDecorator.class)
    @Test
    void addDescriptionEmptyField() {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        Section section = cardDoctor.getSection();
        AddIntelligenceWindow intelligenceWindow = section.openWindowAddDescription();
        intelligenceWindow.saveValue();
        assertEquals("Неверный запрос (400)", cardDoctor.getNotification());
    }

    @Feature("Информация о враче")
    @Story("Отмена добавления описания к разделу в инфо о враче")
    @Test
    void cancellationWindowAddDescription() {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        Section section = cardDoctor.getSection();
        AddIntelligenceWindow intelligenceWindow = section.openWindowAddDescription();
        intelligenceWindow.fillFieldText("2023, Получение дополнительного образования за рубежом");
        intelligenceWindow.cancelAdd();
        AddIntelligenceWindow intelligenceWindowOver = section.openWindowAddDescription();
        assertEquals("", intelligenceWindowOver.getValueField());

    }

    @Feature("Информация о враче")
    @Story("Успешное редактирование описания к разделу в инфо о враче")
    @Test
    void editDescription() {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        Description description = cardDoctor.getDescription();
        description.description();
        description.editDescription("рации");
        assertEquals("2022, по специальности \"Лечебное дело\" в ФГБОУ СамГМУ Минздрава Российской Федерации", description.getDescriptionName());

    }

    @Feature("Информация о враче")
    @Story("Успешное удаление описания к разделу в инфо о враче")
    @Test
    void deleteDescription() {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        Section section = cardDoctor.getSection();
        Description description = cardDoctor.getDescription();
        description.deleteDescription();

    }

    @Feature("Отзывы о враче")
    @Story("Успешное добавление отзыва о враче текущей датой")
    @Test
    void addFeedbackToday() {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
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
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
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
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
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
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
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
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.togglePublishedCheckbox();
        cardDoctor.switchUnpublishedFeedback();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.feedbackUnpublished();
        ChangeFeedbackWindow changeFeedback = feedback.editFeedback();
        changeFeedback.changeFeedbackWindow();
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
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
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
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.togglePublishedCheckbox();
        Feedback feedback = cardDoctor.getFeedback();
        feedback.feedbackPublished();
        ChangeFeedbackWindow changeFeedback = feedback.editFeedback();
        changeFeedback.changeFeedbackWindow();
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
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
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
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
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
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.togglePublishedCheckbox();
        cardDoctor.switchUnpublishedFeedback();
    }

    @Story("Возврат на страницу Врачи с карточки врача")
    @Test
    void returnToDoctorsPageFromCardDoctorPage() {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.comebackDoctorsPage();
        doctorsPage.doctorsPage();
    }

    @Story("Возврат к хэдеру страницы врачей")
    @Test
    void returnToStartPageDoctors() {
        doctorsPage.scrollPage();
        assertTrue(doctorsPage.isReturnButtonAppear());
        doctorsPage.returnToStartPage();
        assertFalse(doctorsPage.isReturnButtonAppear());
    }

    @Story("Возврат к хэдеру страницы карточки врача")
    @Test
    void returnToStartPageCardDoctor() {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        cardDoctor.scrollPage();
        assertTrue(cardDoctor.isReturnButtonAppear());
        cardDoctor.returnToStartPage();
        assertFalse(cardDoctor.isReturnButtonAppear());
    }

    @Story("Закрытие уведомления на странице карточки врача по таймауту")
    @Test
    void closeNotificationTimeout() throws InterruptedException {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        EditPhotoDoctorWindow editPhoto = cardDoctor.openWindowEditPhoto();
        editPhoto.uploadPhoto("src/test/resources/Photo 6,8mbJpeg.jpg");
        assertTrue(cardDoctor.notificationAppear());
        Thread.sleep(6000);
        assertFalse(cardDoctor.notificationAppear());
    }

    @Story("Закрытие уведомления на странице карточки врача")
    @Test
    void closeNotification() {
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor();
        EditPhotoDoctorWindow editPhoto = cardDoctor.openWindowEditPhoto();
        editPhoto.uploadPhoto("src/test/resources/Photo 6,8mbJpeg.jpg");
        assertTrue(cardDoctor.notificationAppear());
        cardDoctor.closeNotification();
        assertFalse(cardDoctor.notificationAppear());
    }

*/
}
