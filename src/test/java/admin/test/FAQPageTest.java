package admin.test;

import admin.data.DataTest;
import admin.pages.AdministrationPage;
import admin.pages.FaqPage;
import admin.pages.HeaderBar;
import admin.pages.modalWindowAdministration.UpdateLegalDocWindow;
import admin.pages.modalWindowFAQ.AddQuestionWindow;
import admin.pages.modalWindowFAQ.ChangeQuestionWindow;
import admin.pages.modalWindowFAQ.Question;
import admin.utils.DataBaseUtils;
import admin.utils.TestSetupAuthAdminPanel;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static admin.utils.DataBaseUtils.selectFaq;
import static org.junit.jupiter.api.Assertions.assertEquals;


@Epic("FAQ")
public class FAQPageTest {


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
        TestSetupAuthAdminPanel.authAdminPanel(DataTest.getLoginAdmin(),DataTest.getPasswordAdmin());
    }

    @Feature("Добавление нового faq-вопроса")
    @Story("Успешное добавление нового faq-вопроса")
    @Test
    void addFaqQuestion_11778() {
        DataBaseUtils.clearAllFaq();
        HeaderBar headerBar = new HeaderBar();
        FaqPage faqPage = headerBar.faqTabOpen();
        faqPage.faqPage();
        AddQuestionWindow addQuestionWindow = faqPage.openWindowAddQuestion();
        addQuestionWindow.addQuestionWindow();
        addQuestionWindow.fillingQuestionField("Как вернуть денежные средства?");
        addQuestionWindow.fillAnswerField("Деньги можно вернуть при обращении в бухгалтерию");
        addQuestionWindow.addQuestion();
        assertEquals("Вопрос успешно добавлен", faqPage.getNotification());
        Question question = faqPage.questionCard(1);
        question.question();
        assertEquals("Как вернуть денежные средства?", question.getQuestion(0));
        assertEquals("Деньги можно вернуть при обращении в бухгалтерию", question.getAnswer(0));
        assertEquals("Как вернуть денежные средства?", selectFaq().getQuestion());
        assertEquals("Деньги можно вернуть при обращении в бухгалтерию", selectFaq().getAnswer());
        assertEquals("1", selectFaq().getSequence());
    }

    @Feature("Добавление нового faq-вопроса")
    @Story("Добавление нового faq-вопроса с уже существующим заголовком и ответом")
    @Test
    void addFaqQuestionAlreadyExistQuestionAnswer_9352() {
        HeaderBar headerBar = new HeaderBar();
        FaqPage faqPage = headerBar.faqTabOpen();
        faqPage.faqPage();
        AddQuestionWindow addQuestionWindow = faqPage.openWindowAddQuestion();
        addQuestionWindow.addQuestionWindow();
        addQuestionWindow.fillingQuestionField("Как вернуть денежные средства?");
        addQuestionWindow.fillAnswerField("Деньги можно вернуть при обращении в бухгалтерию");
        addQuestionWindow.addQuestion();
        assertEquals("Вопрос уже существует", faqPage.getNotification());
    }

    @Feature("Добавление нового faq-вопроса")
    @Story("Добавление нового faq-вопроса с уже существующим заголовком")
    @Test
    void addFaqQuestionAlreadyExistQuestion_9355() {
        HeaderBar headerBar = new HeaderBar();
        FaqPage faqPage = headerBar.faqTabOpen();
        faqPage.faqPage();
        AddQuestionWindow addQuestionWindow = faqPage.openWindowAddQuestion();
        addQuestionWindow.addQuestionWindow();
        addQuestionWindow.fillingQuestionField("Как вернуть денежные средства?");
        addQuestionWindow.fillAnswerField("Деньги можно вернуть при обращении в булочную");
        addQuestionWindow.addQuestion();
        assertEquals("Вопрос уже существует", faqPage.getNotification());
    }

    @Feature("Добавление нового faq-вопроса")
    @Story("Добавление нового faq-вопроса с уже существующим ответом")
    @Test
    void addFaqQuestionAlreadyExistAnswer_9356() {
        HeaderBar headerBar = new HeaderBar();
        FaqPage faqPage = headerBar.faqTabOpen();
        faqPage.faqPage();
        AddQuestionWindow addQuestionWindow = faqPage.openWindowAddQuestion();
        addQuestionWindow.addQuestionWindow();
        addQuestionWindow.fillingQuestionField("Могу ли я вернуть денежные средства?");
        addQuestionWindow.fillAnswerField("Деньги можно вернуть при обращении в бухгалтерию");
        addQuestionWindow.addQuestion();
        assertEquals("Вопрос успешно добавлен", faqPage.getNotification());
        Question question = faqPage.questionCard(1);
        question.question();
        assertEquals("Могу ли я вернуть денежные средства?", question.getQuestion(0));
        assertEquals("Деньги можно вернуть при обращении в бухгалтерию", question.getAnswer(0));
    }

    @Feature("Добавление нового faq-вопроса")
    @Story("Зануление полей в окне добавления faq-вопроса после закрытия окна")
    @Test
    void closeWindowAddNewQuestion() {
        HeaderBar headerBar = new HeaderBar();
        FaqPage faqPage = headerBar.faqTabOpen();
        faqPage.faqPage();
        AddQuestionWindow addQuestionWindow = faqPage.openWindowAddQuestion();
        addQuestionWindow.addQuestionWindow();
        addQuestionWindow.fillingQuestionField("Могу ли я вернуть денежные средства?");
        addQuestionWindow.fillAnswerField("Деньги можно вернуть при обращении в бухгалтерию");
        addQuestionWindow.closeWindowAddQuestion();
        AddQuestionWindow addQuestionWindowOver = faqPage.openWindowAddQuestion();
        assertEquals("", addQuestionWindowOver.getValueQuestionField());
        assertEquals("", addQuestionWindowOver.getValueAnswerField());
    }

    @Feature("Редактирование faq-вопроса")
    @Story("Успешное редактирование faq-вопроса")
    @Test
    void editQuestion_9154() {
        HeaderBar headerBar = new HeaderBar();
        FaqPage faqPage = headerBar.faqTabOpen();
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

    @Feature("Редактирование faq-вопроса")
    @Story("Сохранение faq-вопроса без изменений данных")
    @Test
    void editQuestionNotChangeSave_9159() {
        HeaderBar headerBar = new HeaderBar();
        FaqPage faqPage = headerBar.faqTabOpen();
        faqPage.faqPage();
        Question question = faqPage.questionCard(0);
        question.question();
        ChangeQuestionWindow changeQuestionWindow = question.openWindowChangeQuestion();
        changeQuestionWindow.changeQuestionWindow();
        changeQuestionWindow.saveChangesQuestion();
        assertEquals("Вопрос успешно обновлен", faqPage.getNotification());
        question.question();
        assertEquals("Отредактированный вопрос", question.getQuestion(0));
        assertEquals("Отредактированный ответ", question.getAnswer(0));
    }

    @Feature("Редактирование faq-вопроса")
    @Story("Зануление полей в окне редактирования faq-вопроса после закрытия окна")
    @Test
    void closeWindowEditQuestion() {
        HeaderBar headerBar = new HeaderBar();
        FaqPage faqPage = headerBar.faqTabOpen();
        faqPage.faqPage();
        Question question = faqPage.questionCard(0);
        question.question();
        ChangeQuestionWindow changeQuestionWindow = question.openWindowChangeQuestion();
        changeQuestionWindow.changeQuestionWindow();
        changeQuestionWindow.clearQuestionField();
        changeQuestionWindow.fillingQuestionField("Не сохранять заголовок");
        changeQuestionWindow.clearAnswerField();
        changeQuestionWindow.fillingAnswerField("Не сохранять текст");
        changeQuestionWindow.closeWindowEditQuestion();
        question.question();
        assertEquals("Отредактированный вопрос", question.getQuestion(0));
        assertEquals("Отредактированный ответ", question.getAnswer(0));
    }

    @Feature("Редактирование faq-вопроса")
    @Story("Смена последовательности faq-вопросов")
    @Test
    void sequenceChangeQuestion() {
        HeaderBar headerBar = new HeaderBar();
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
        HeaderBar headerBar = new HeaderBar();
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
        HeaderBar headerBar = new HeaderBar();
        FaqPage faqPage = headerBar.faqTabOpen();
        faqPage.faqPage();
        faqPage.scrollPageToBottom();
        faqPage.returnToStartPage();
        faqPage.returnButtonDisappears();
    }

    @Story("Закрытие уведомления на странице faq")
    @Test
    void closeNotification() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.updateProcessingPolicy();
        updateLegalDocWindow.uploadDocWindow();
        updateLegalDocWindow.uploadInvalidDoc("src/test/resources/Оферта, Политика обработки jpeg.jpg");
        assertEquals("Допускаются файлы с расширением PDF", adminPage.getNotification());
        adminPage.closeNotification();
    }


}

