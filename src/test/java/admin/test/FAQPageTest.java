package admin.test;

import admin.data.DataInfo;
import admin.pages.BasePage.BasePage;
import admin.pages.FaqPage.FaqPage;
import admin.pages.HeaderMenu.HeaderMenu;
import admin.pages.FaqPage.AddQuestionWindow;
import admin.pages.FaqPage.ChangeQuestionWindow;
import admin.pages.FaqPage.Question;
import admin.utils.dbUtils.DataBaseQuery;
import admin.utils.decoratorsTest.faq.*;
import admin.utils.decoratorsTest.general.AllureDecorator;
import admin.utils.decoratorsTest.general.NotificationDecorator;
import admin.utils.testUtils.*;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;


@Epic("FAQ")
public class FAQPageTest extends BaseTest{

    private FaqPage faqPage;
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
        faqPage=new FaqPage();
        headerMenu= new HeaderMenu();
        basePage = new BasePage();
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
        Question question = faqPage.getQuestionCard(0);
        assertEquals("Вопрос успешно добавлен", faqPage.getNotification());
        assertEquals(DataInfo.DataTest.getQuestion(),question.getQuestion(0));
        assertEquals(DataInfo.DataTest.getAnswer(),question.getAnswer(0));
        assertEquals(DataInfo.DataTest.getQuestion(), DataBaseQuery.selectFaq(0).getQuestion());
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
        Question question = faqPage.getQuestionCard(1);
        assertEquals("Вопрос успешно добавлен", faqPage.getNotification());
        assertEquals("Могу ли я вернуть денежные средства?",question.getQuestion(1));
        assertEquals(DataInfo.DataTest.getAnswer(),question.getAnswer(1));
        assertEquals("Могу ли я вернуть денежные средства?", DataBaseQuery.selectFaq(1).getQuestion());
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
        Question question = faqPage.getQuestionCard(0);
        question.question();
        ChangeQuestionWindow changeQuestionWindow = question.openWindowChangeQuestion();
        changeQuestionWindow.changeQuestionWindow();
        changeQuestionWindow.fillQuestionField(DataInfo.DataTest.getChangeQuestion());
        changeQuestionWindow.fillAnswerField(DataInfo.DataTest.getChangeAnswer());
        changeQuestionWindow.saveChangesQuestion();
        assertEquals("Вопрос успешно обновлен", faqPage.getNotification());
        assertEquals(DataInfo.DataTest.getChangeQuestion(),question.getQuestion(0));
        assertEquals(DataInfo.DataTest.getChangeAnswer(),question.getAnswer(0));
        assertEquals(DataInfo.DataTest.getChangeQuestion(), DataBaseQuery.selectFaq(0).getQuestion());
        assertFalse(changeQuestionWindow.isWindowAppear());
    }

    @Feature("Редактирование faq-вопроса")
    @Story("Сохранение faq-вопроса без изменений данных")
    @ExtendWith({AddDeleteFaqDecorator.class,NotificationDecorator.class})
    @Test
    void editQuestionNotChangeSave() {
        Question question = faqPage.getQuestionCard(0);
        ChangeQuestionWindow changeQuestionWindow = question.openWindowChangeQuestion();
        changeQuestionWindow.saveChangesQuestion();
        assertEquals("Вопрос успешно обновлен", faqPage.getNotification());
        assertEquals(DataInfo.DataTest.getQuestion(),question.getQuestion(0));
        assertEquals(DataInfo.DataTest.getAnswer(),question.getAnswer(0));
        assertEquals(DataInfo.DataTest.getQuestion(), DataBaseQuery.selectFaq(0).getQuestion());
        assertFalse(changeQuestionWindow.isWindowAppear());
    }

    @Feature("Редактирование faq-вопроса")
    @Story("Зануление полей в окне редактирования faq-вопроса после закрытия окна")
    @ExtendWith(AddDeleteFaqDecorator.class)
    @Test
    void closeWindowEditQuestion() {
        Question question = faqPage.getQuestionCard(0);
        ChangeQuestionWindow changeQuestionWindow = question.openWindowChangeQuestion();
        changeQuestionWindow.fillQuestionField(DataHelper.generateText());
        changeQuestionWindow.fillAnswerField(DataHelper.generateText());
        changeQuestionWindow.closeWindowEditQuestion();
        Selenide.sleep(1000);
        assertFalse(changeQuestionWindow.isWindowAppear());
        question.openWindowChangeQuestion();
        assertEquals(DataInfo.DataTest.getQuestion(),question.getQuestion(0));
        assertEquals(DataInfo.DataTest.getAnswer(),question.getAnswer(0));
        assertEquals(DataInfo.DataTest.getQuestion(), DataBaseQuery.selectFaq(0).getQuestion());
    }

    @Feature("Редактирование faq-вопроса")
    @Story("Смена последовательности faq-вопросов")
    @ExtendWith(AddTwoQuestionFaqDecorator.class)
    @Test
    void sequenceChangeQuestion() {
        Question questionFirst = faqPage.getQuestionCard(0);
        String firstQuestionText = questionFirst.getQuestion(0);
        Question questionSecond = faqPage.getQuestionCard(1);
        String secondQuestionText = questionSecond.getQuestion(1);
        faqPage.sequenceChangeQuestions(0, 1);
        Question questionFirstChange = faqPage.getQuestionCard(0);
        Question questionSecondChange = faqPage.getQuestionCard(1);
        Selenide.sleep(1000);
        assertEquals(firstQuestionText, questionSecondChange.getQuestion(1));
        assertEquals(secondQuestionText, questionFirstChange.getQuestion(0));
        assertEquals(firstQuestionText,DataBaseQuery.selectFaq(1).getQuestion());
        assertEquals(secondQuestionText,DataBaseQuery.selectFaq(0).getQuestion());
    }

    @Feature("Удаление faq-вопроса")
    @Story("Успешное удаление faq-вопроса")
    @ExtendWith({AddFaqDecorator.class,NotificationDecorator.class})
    @Test
    void deleteQuestion() {
        Question question = faqPage.getQuestionCard(0);
        ChangeQuestionWindow changeQuestionWindow = question.openWindowChangeQuestion();
        changeQuestionWindow.deleteQuestion();
        assertEquals("Вопрос успешно удален", faqPage.getNotification());
        assertFalse(faqPage.isExistQuestions(0));
        assertTrue(faqPage.isExistsEmptyList());
        assertNull(DataBaseQuery.selectFaq(0));
    }

    @Story("Возврат к хэдеру страницы faq")
    @ExtendWith(AddSomeFaq.class)
    @Test
    void returnToStartPage() {
        faqPage.scrollPage();
        Selenide.sleep(2000);
        assertTrue(faqPage.isReturnButtonAppear());
        faqPage.returnToStartPage();
        Selenide.sleep(2000);
        assertFalse(faqPage.isReturnButtonAppear());
    }

    @Story("Закрытие уведомления на странице faq по таймауту")
    @ExtendWith(AddFaqDecorator.class)
    @Test
    void closeNotificationTimeout() {
        Question question = faqPage.getQuestionCard(0);
        ChangeQuestionWindow changeQuestionWindow = question.openWindowChangeQuestion();
        changeQuestionWindow.deleteQuestion();
        checkCloseNotificationTimeout(basePage);
    }

    @Story("Закрытие уведомления на странице faq")
    @ExtendWith(AddFaqDecorator.class)
    @Test
    void closeNotification() {
        Question question = faqPage.getQuestionCard(0);
        ChangeQuestionWindow changeQuestionWindow = question.openWindowChangeQuestion();
        changeQuestionWindow.deleteQuestion();
        checkCloseNotification(basePage);
    }
}

