package admin.test;

import admin.data.DataInfo;
import admin.pages.AdministrationPage.AdministrationPage;
import admin.pages.FaqPage.FaqPage;
import admin.pages.HeaderMenu.HeaderMenu;
import admin.pages.AdministrationPage.UpdateLegalDocWindow;
import admin.pages.FaqPage.AddQuestionWindow;
import admin.pages.FaqPage.ChangeQuestionWindow;
import admin.pages.FaqPage.Question;
import admin.utils.dbUtils.DataBaseUtils;
import admin.utils.decoratorsTest.*;
import admin.utils.testUtils.*;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static admin.utils.dbUtils.DataBaseUtils.selectFaq;
import static org.junit.jupiter.api.Assertions.*;


@Epic("FAQ")
public class FAQPageTest {

    private FaqPage faqPage;
    private HeaderMenu headerMenu;

    @ExtendWith(AllureDecorator.class)

    @BeforeAll
    static void setUpAuth() {
        BrowserManager.authGetCookie(DataInfo.UserData.getLoginAdmin(), DataInfo.UserData.getPasswordAdmin());
    }

    @BeforeEach
    void setUp(){
        BrowserManager.openPagesAfterAuth();
        faqPage=new FaqPage();
        headerMenu= new HeaderMenu();
        headerMenu.faqTabOpen();
    }

    @Feature("Добавление нового faq-вопроса")
    @Story("Успешное добавление нового faq-вопроса")
    @ExtendWith({DeleteFaqDecorator.class, NotificationDecorator.class})
    @Test
    void addFaqQuestion() {
        faqPage.faqPage();
        AddQuestionWindow addQuestionWindow = faqPage.openWindowAddQuestion();
        addQuestionWindow.addQuestionWindow();
        addQuestionWindow.fillQuestionField(DataInfo.DataTest.getQuestion());
        addQuestionWindow.fillAnswerField(DataInfo.DataTest.getAnswer());
        addQuestionWindow.addQuestion();
        Question question = faqPage.questionCard(0);
        assertEquals(DataInfo.DataTest.getQuestion(),question.getQuestion(0));
        assertEquals(DataInfo.DataTest.getAnswer(),question.getAnswer(0));
        assertEquals("Вопрос успешно добавлен", faqPage.getNotification());
        assertEquals(DataInfo.DataTest.getQuestion(),DataBaseUtils.selectFaq(0).getQuestion());
        assertEquals(DataInfo.DataTest.getAnswer(),DataBaseUtils.selectFaq(0).getAnswer());
        assertTrue(faqPage.isExistQuestions(0));
        assertFalse(addQuestionWindow.isWindowAppear());
    }

    @Feature("Добавление нового faq-вопроса")
    @Story("Добавление нового faq-вопроса с уже существующим заголовком и ответом")
    @ExtendWith({AddDeleteFaqDecorator.class,NotificationDecorator.class})
    @Test
    void addFaqQuestionAlreadyExistQuestionAnswer() {
        AddQuestionWindow addQuestionWindow = faqPage.openWindowAddQuestion();
        addQuestionWindow.fillQuestionField(DataInfo.DataTest.getQuestion());
        addQuestionWindow.fillAnswerField(DataInfo.DataTest.getAnswer());
        addQuestionWindow.addQuestion();
        assertEquals("Вопрос уже существует", faqPage.getNotification());
        assertFalse(faqPage.isExistQuestions(1));
    }

    @Feature("Добавление нового faq-вопроса")
    @Story("Добавление нового faq-вопроса с уже существующим заголовком")
    @ExtendWith({AddDeleteFaqDecorator.class,NotificationDecorator.class})
    @Test
    void addFaqQuestionAlreadyExistQuestion() {
        AddQuestionWindow addQuestionWindow = faqPage.openWindowAddQuestion();
        addQuestionWindow.fillQuestionField(DataInfo.DataTest.getQuestion());
        addQuestionWindow.fillAnswerField("Деньги можно вернуть при обращении в бухгалтерию");
        addQuestionWindow.addQuestion();
        assertEquals("Вопрос уже существует", faqPage.getNotification());
        assertFalse(faqPage.isExistQuestions(1));
    }

    @Feature("Добавление нового faq-вопроса")
    @Story("Добавление нового faq-вопроса с уже существующим ответом")
    @ExtendWith({AddDeleteFaqDecorator.class,NotificationDecorator.class})
    @Test
    void addFaqQuestionAlreadyExistAnswer() {
        AddQuestionWindow addQuestionWindow = faqPage.openWindowAddQuestion();
        addQuestionWindow.fillQuestionField("Могу ли я вернуть денежные средства?");
        addQuestionWindow.fillAnswerField(DataInfo.DataTest.getAnswer());
        addQuestionWindow.addQuestion();
        assertEquals("Вопрос успешно добавлен", faqPage.getNotification());
        assertEquals("Могу ли я вернуть денежные средства?",DataBaseUtils.selectFaq(1).getQuestion());
        assertTrue(faqPage.isExistQuestions(1));
        assertFalse(addQuestionWindow.isWindowAppear());
    }

    @Feature("Добавление нового faq-вопроса")
    @Story("Зануление полей в окне добавления faq-вопроса после закрытия окна")
    @Test
    void closeWindowAddNewQuestion() {
        AddQuestionWindow addQuestionWindow = faqPage.openWindowAddQuestion();
        addQuestionWindow.fillQuestionField(DataInfo.DataTest.getQuestion());
        addQuestionWindow.fillAnswerField(DataInfo.DataTest.getAnswer());
        addQuestionWindow.closeWindowAddQuestion();
        assertFalse(addQuestionWindow.isWindowAppear());
        faqPage.openWindowAddQuestion();
        assertEquals("", addQuestionWindow.getValueQuestionField());
        assertEquals("", addQuestionWindow .getValueAnswerField());
    }

    @Feature("Редактирование faq-вопроса")
    @Story("Успешное редактирование faq-вопроса")
    @ExtendWith({AddDeleteFaqDecorator.class,NotificationDecorator.class})
    @Test
    void editQuestion() {
        Question question = faqPage.questionCard(0);
        question.question();
        ChangeQuestionWindow changeQuestionWindow = question.openWindowChangeQuestion();
        changeQuestionWindow.changeQuestionWindow();
        changeQuestionWindow.fillQuestionField(DataInfo.DataTest.getChangeQuestion());
        changeQuestionWindow.fillAnswerField(DataInfo.DataTest.getChangeAnswer());
        changeQuestionWindow.saveChangesQuestion();
        assertEquals(DataInfo.DataTest.getChangeQuestion(),question.getQuestion(0));
        assertEquals(DataInfo.DataTest.getChangeAnswer(),question.getAnswer(0));
        assertEquals("Вопрос успешно обновлен", faqPage.getNotification());
        assertEquals(DataInfo.DataTest.getChangeQuestion(),DataBaseUtils.selectFaq(0).getQuestion());
        assertEquals(DataInfo.DataTest.getChangeAnswer(),DataBaseUtils.selectFaq(0).getAnswer());
        assertTrue(faqPage.isExistQuestions(0));
        assertFalse(changeQuestionWindow.isWindowAppear());

    }

    @Feature("Редактирование faq-вопроса")
    @Story("Сохранение faq-вопроса без изменений данных")
    @ExtendWith(AddDeleteFaqDecorator.class)
    @Test
    void editQuestionNotChangeSave() {
        Question question = faqPage.questionCard(0);
        ChangeQuestionWindow changeQuestionWindow = question.openWindowChangeQuestion();
        changeQuestionWindow.saveChangesQuestion();
        assertEquals(DataInfo.DataTest.getQuestion(),question.getQuestion(0));
        assertEquals(DataInfo.DataTest.getAnswer(),question.getAnswer(0));
        assertEquals("Вопрос успешно обновлен", faqPage.getNotification());
        assertEquals(DataInfo.DataTest.getQuestion(),DataBaseUtils.selectFaq(0).getQuestion());
        assertEquals(DataInfo.DataTest.getAnswer(),DataBaseUtils.selectFaq(0).getAnswer());
        assertTrue(faqPage.isExistQuestions(0));
        assertFalse(changeQuestionWindow.isWindowAppear());
    }

    @Feature("Редактирование faq-вопроса")
    @Story("Зануление полей в окне редактирования faq-вопроса после закрытия окна")
    @Test
    void closeWindowEditQuestion() {
        HeaderMenu headerBar = new HeaderMenu();
        FaqPage faqPage = headerBar.faqTabOpen();
        faqPage.faqPage();
        Question question = faqPage.questionCard(0);
        question.question();
        ChangeQuestionWindow changeQuestionWindow = question.openWindowChangeQuestion();
        changeQuestionWindow.changeQuestionWindow();
        changeQuestionWindow.fillQuestionField("Не сохранять заголовок");
        changeQuestionWindow.fillAnswerField("Не сохранять текст");
        changeQuestionWindow.closeWindowEditQuestion();
        question.question();
        assertEquals("Отредактированный вопрос", question.getQuestion(0));
        assertEquals("Отредактированный ответ", question.getAnswer(0));
    }

    @Feature("Редактирование faq-вопроса")
    @Story("Смена последовательности faq-вопросов")
    @Test
    void sequenceChangeQuestion() {
        HeaderMenu headerBar = new HeaderMenu();
        FaqPage faqPage = headerBar.faqTabOpen();
        faqPage.faqPage();
        Question questionFirst = faqPage.questionCard(0);
        questionFirst.question();
        String firstQuestionText = questionFirst.getQuestion(0);
        Question questionSecond = faqPage.questionCard(1);
        questionSecond.question();
        String secondQuestionText = questionSecond.getQuestion(1);
        faqPage.sequenceChangeQuestions(0, 1);
        Question questionFirstChange = faqPage.questionCard(0);
        Question questionSecondChange = faqPage.questionCard(1);
        assertEquals(firstQuestionText, questionSecondChange.getQuestion(1));
        assertEquals(secondQuestionText, questionFirstChange.getQuestion(0));
    }

    @Feature("Удаление faq-вопроса")
    @Story("Успешное удаление faq-вопроса")
    @Test
    void deleteQuestion() {
        HeaderMenu headerBar = new HeaderMenu();
        FaqPage faqPage = headerBar.faqTabOpen();
        faqPage.faqPage();
        Question question = faqPage.questionCard(0);
        question.question();
        ChangeQuestionWindow changeQuestionWindow = question.openWindowChangeQuestion();
        changeQuestionWindow.deleteQuestion();
        assertEquals("Вопрос успешно удален", faqPage.getNotification());
    }

    @Story("Возврат к хэдеру страницы faq")
    @Test
    void returnToStartPage() {
        HeaderMenu headerBar = new HeaderMenu();
        FaqPage faqPage = headerBar.faqTabOpen();
        faqPage.faqPage();
        faqPage.scrollPage();
        faqPage.returnToStartPage();
        faqPage.isReturnButtonAppear();
    }

    @Story("Закрытие уведомления на странице faq")
    @Test
    void closeNotification() {
        HeaderMenu headerBar = new HeaderMenu();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.updateProcessingPolicy();
        updateLegalDocWindow.uploadDocWindow();
        updateLegalDocWindow.uploadInvalidDoc("src/test/resources/Оферта, Политика обработки jpeg.jpg");
        assertEquals("Допускаются файлы с расширением PDF", adminPage.getNotification());
        adminPage.closeNotification();
    }


}

