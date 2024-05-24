package admin.test;

import admin.data.DataInfo;
import admin.pages.AuthorizationPage;
import admin.pages.FaqPage;
import admin.pages.HeaderBar;
import admin.pages.modalWindowFAQ.AddQuestionWindow;
import admin.pages.modalWindowFAQ.ChangeQuestionWindow;
import admin.pages.modalWindowFAQ.Question;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class FAQPageTest {

    private HeaderBar headerBar;
    private FaqPage faqPage;

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
    void addFaqQuestion_11778() {
        faqPage=headerBar.faqTabOpen();
        faqPage.faqPage();
        AddQuestionWindow addQuestionWindow=faqPage.openWindowAddQuestion();
        addQuestionWindow.addQuestionWindow();
        addQuestionWindow.fillingQuestionField("Как вернуть денежные средства?");
        addQuestionWindow.fillAnswerField("Деньги можно вернуть при обращении в бухгалтерию");
        addQuestionWindow.addQuestion();
        assertEquals("Вопрос успешно добавлен",faqPage.getNotification());
        Question question = faqPage.questionCard(1);
        question.question();
        assertEquals("Как вернуть денежные средства?",question.getQuestion(0));
        assertEquals("Деньги можно вернуть при обращении в бухгалтерию", question.getAnswer(0));
    }

    @Test
    void addFaqQuestionAlreadyExistQuestionAnswer_9352() {
        faqPage=headerBar.faqTabOpen();
        faqPage.faqPage();
        AddQuestionWindow addQuestionWindow=faqPage.openWindowAddQuestion();
        addQuestionWindow.addQuestionWindow();
        addQuestionWindow.fillingQuestionField("Как вернуть денежные средства?");
        addQuestionWindow.fillAnswerField("Деньги можно вернуть при обращении в бухгалтерию");
        addQuestionWindow.addQuestion();
        assertEquals("Вопрос уже существует",faqPage.getNotification());
    }

    @Test
    void addFaqQuestionAlreadyExistQuestion_9355() {
        faqPage=headerBar.faqTabOpen();
        faqPage.faqPage();
        AddQuestionWindow addQuestionWindow=faqPage.openWindowAddQuestion();
        addQuestionWindow.addQuestionWindow();
        addQuestionWindow.fillingQuestionField("Как вернуть денежные средства?");
        addQuestionWindow.fillAnswerField("Деньги можно вернуть при обращении в булочную");
        addQuestionWindow.addQuestion();
        assertEquals("Вопрос уже существует",faqPage.getNotification());
    }

    @Test
    void addFaqQuestionAlreadyExistAnswer_9356() {
        faqPage=headerBar.faqTabOpen();
        faqPage.faqPage();
        AddQuestionWindow addQuestionWindow=faqPage.openWindowAddQuestion();
        addQuestionWindow.addQuestionWindow();
        addQuestionWindow.fillingQuestionField("Могу ли я вернуть денежные средства?");
        addQuestionWindow.fillAnswerField("Деньги можно вернуть при обращении в бухгалтерию");
        addQuestionWindow.addQuestion();
        assertEquals("Вопрос успешно добавлен",faqPage.getNotification());
        Question question = faqPage.questionCard(1);
        question.question();
        assertEquals("Могу ли я вернуть денежные средства?",question.getQuestion(0));
        assertEquals("Деньги можно вернуть при обращении в бухгалтерию", question.getAnswer(0));
    }

    @Test
    void clearFieldsAddNewQuestion() {
        faqPage=headerBar.faqTabOpen();
        faqPage.faqPage();
        AddQuestionWindow addQuestionWindow=faqPage.openWindowAddQuestion();
        addQuestionWindow.addQuestionWindow();
        addQuestionWindow.fillingQuestionField("Могу ли я вернуть денежные средства?");
        addQuestionWindow.clearFieldQuestion();
        assertEquals("",addQuestionWindow.getValueQuestionField());
        addQuestionWindow.fillAnswerField("Деньги можно вернуть при обращении в бухгалтерию");
        addQuestionWindow.clearFieldAnswer();
        assertEquals("",addQuestionWindow.getValueAnswerField());
    }

    @Test
    void closeWindowAddNewQuestion() {
        faqPage=headerBar.faqTabOpen();
        faqPage.faqPage();
        AddQuestionWindow addQuestionWindow=faqPage.openWindowAddQuestion();
        addQuestionWindow.addQuestionWindow();
        addQuestionWindow.fillingQuestionField("Могу ли я вернуть денежные средства?");
        addQuestionWindow.fillAnswerField("Деньги можно вернуть при обращении в бухгалтерию");
        addQuestionWindow.closeWindowAddQuestion();
        AddQuestionWindow addQuestionWindowOver=faqPage.openWindowAddQuestion();
        assertEquals("",addQuestionWindowOver.getValueQuestionField());
        assertEquals("",addQuestionWindowOver.getValueAnswerField());
    }

    @Test
    void editQuestion_9154() {
        faqPage = headerBar.faqTabOpen();
        faqPage.faqPage();
        Question question = faqPage.questionCard(0);
        question.question();
        ChangeQuestionWindow changeQuestionWindow = question.openWindowChangeQuestion();
        changeQuestionWindow.changeQuestionWindow();
        changeQuestionWindow.clearQuestionField();
        changeQuestionWindow.fillingQuestionField("Отредактированный вопрос");
        changeQuestionWindow.clearAnswerField();
        changeQuestionWindow.fillingAnswerField("Отредактированный ответ");
        changeQuestionWindow.saveChangesQuestion();
        assertEquals("Вопрос успешно обновлен", faqPage.getNotification());
        question.question();
        assertEquals("Отредактированный вопрос", question.getQuestion(0));
        assertEquals("Отредактированный ответ", question.getAnswer(0));
    }

    @Test
    void editQuestion_9159() {
        faqPage=headerBar.faqTabOpen();
        faqPage.faqPage();
        Question question=faqPage.questionCard(0);
        question.question();
        ChangeQuestionWindow changeQuestionWindow=question.openWindowChangeQuestion();
        changeQuestionWindow.changeQuestionWindow();
        changeQuestionWindow.saveChangesQuestion();
        assertEquals("Вопрос успешно обновлен",faqPage.getNotification());
        question.question();
        assertEquals("Отредактированный вопрос",question.getQuestion(0));
        assertEquals("Отредактированный ответ", question.getAnswer(0));
    }

    @Test
    void closeWindowEditQuestion() {
        faqPage=headerBar.faqTabOpen();
        faqPage.faqPage();
        Question question=faqPage.questionCard(0);
        question.question();
        ChangeQuestionWindow changeQuestionWindow=question.openWindowChangeQuestion();
        changeQuestionWindow.changeQuestionWindow();
        changeQuestionWindow.clearQuestionField();
        changeQuestionWindow.fillingQuestionField("Не сохранять заголовок");
        changeQuestionWindow.clearAnswerField();
        changeQuestionWindow.fillingAnswerField("Не сохранять текст");
        changeQuestionWindow.closeWindowEditQuestion();
        question.question();
        assertEquals("Отредактированный вопрос",question.getQuestion(0));
        assertEquals("Отредактированный ответ", question.getAnswer(0));
    }
    @Test
    void sequenceChangeQuestion() {
        faqPage=headerBar.faqTabOpen();
        faqPage.faqPage();
        Question questionFirst=faqPage.questionCard(0);
        questionFirst.question();
        String firstQuestionText=questionFirst.getQuestion(0);
        Question questionSecond=faqPage.questionCard(1);
        questionSecond.question();
        String secondQuestionText=questionSecond.getQuestion(1);
        faqPage.sequenceChangeQuestions(0,1);
        Question questionFirstChange=faqPage.questionCard(0);
        Question questionSecondChange=faqPage.questionCard(1);
        assertEquals(firstQuestionText,questionSecondChange.getQuestion(1));
        assertEquals(secondQuestionText,questionFirstChange.getQuestion(0));
    }

    @Test
    void deleteQuestion() {
        faqPage=headerBar.faqTabOpen();
        faqPage.faqPage();
        Question question=faqPage.questionCard(0);
        question.question();
        ChangeQuestionWindow changeQuestionWindow=question.openWindowChangeQuestion();
        changeQuestionWindow.deleteQuestion();
        assertEquals("Вопрос успешно удален",faqPage.getNotification());
    }

    @Test
    void returnToStartPage(){
        faqPage=headerBar.faqTabOpen();
        faqPage.faqPage();
        faqPage.returnToStartPage();
    }


}

