package admin.test;

import admin.data.DataConfig;
import admin.pages.BasePage.BasePage;
import admin.pages.FaqPage.*;
import admin.pages.HeaderMenu.HeaderMenu;
import admin.utils.dbUtils.DataBaseQuery;
import admin.utils.preparationDataTests.faq.*;
import admin.utils.preparationDataTests.general.AllureDecorator;
import admin.utils.preparationDataTests.general.NotificationDecorator;
import admin.utils.testUtils.*;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;


@Epic("FAQ")
public class FAQPageTest extends BaseTest{

    private FaqPage faqPage;
    private BasePage basePage;

    @ExtendWith(AllureDecorator.class)

    @BeforeAll
    static void setUpAuth() {
        BrowserManager.openBrowser(DataConfig.UserData.getLOGIN_ADMIN(), DataConfig.UserData.getPASSWORD_ADMIN());
        HeaderMenu headerMenu = new HeaderMenu();
        headerMenu.faqTabOpen();
    }

    @BeforeEach
    void setUp(){
        Selenide.refresh();
        faqPage=new FaqPage();
        basePage = new BasePage();
    }

    @AfterAll
    static void closeWebDriver() {
        Selenide.closeWebDriver();
    }

    @Feature("Добавление нового faq-вопроса")
    @Story("Успешное добавление нового faq-вопроса")
    @ExtendWith({DeleteFaqDecorator.class, NotificationDecorator.class})
    @Test
    void addFaqQuestion() {
        AddQuestionWindow addQuestionWindow = faqPage.openWindowAddQuestion();
        addQuestionWindow.addQuestionWindow();
        addQuestionWindow.fillQuestionField(DataConfig.DataTest.getQUESTION());
        addQuestionWindow.fillAnswerField(DataConfig.DataTest.getANSWER());
        addQuestionWindow.addQuestion();
        Question question = faqPage.getQuestion();
        assertEquals("Вопрос успешно добавлен", faqPage.getNotification());
        assertEquals(DataConfig.DataTest.getQUESTION(),question.getQuestion());
        assertEquals(DataConfig.DataTest.getANSWER(),question.getAnswer());
        assertEquals(DataConfig.DataTest.getQUESTION(), DataBaseQuery.selectFaq().getQuestion());
        assertTrue(faqPage.isExistQuestions());
        assertFalse(addQuestionWindow.isWindowAppear());
    }

    @Feature("Добавление нового faq-вопроса")
    @Story("Добавление нового faq-вопроса с уже существующим заголовком и ответом")
    @ExtendWith({AddDeleteFaqDecorator.class,NotificationDecorator.class})
    @Test
    void addFaqQuestionAlreadyExistQuestionAnswer() {
        AddQuestionWindow addQuestionWindow = faqPage.openWindowAddQuestion();
        addQuestionWindow.fillQuestionField(DataConfig.DataTest.getQUESTION());
        addQuestionWindow.fillAnswerField(DataConfig.DataTest.getANSWER());
        addQuestionWindow.addQuestion();
        assertEquals("Вопрос уже существует", faqPage.getNotification());
        assertFalse(faqPage.isExistQuestionsByIndex(1));
    }

    @Feature("Добавление нового faq-вопроса")
    @Story("Добавление нового faq-вопроса с уже существующим заголовком")
    @ExtendWith({AddDeleteFaqDecorator.class,NotificationDecorator.class})
    @Test
    void addFaqQuestionAlreadyExistQuestion() {
        AddQuestionWindow addQuestionWindow = faqPage.openWindowAddQuestion();
        addQuestionWindow.fillQuestionField(DataConfig.DataTest.getQUESTION());
        addQuestionWindow.fillAnswerField("Деньги можно вернуть при обращении в бухгалтерию");
        addQuestionWindow.addQuestion();
        assertEquals("Вопрос уже существует", faqPage.getNotification());
        assertFalse(faqPage.isExistQuestionsByIndex(1));
    }

    @Feature("Добавление нового faq-вопроса")
    @Story("Добавление нового faq-вопроса с уже существующим ответом")
    @ExtendWith({AddDeleteFaqDecorator.class,NotificationDecorator.class})
    @Test
    void addFaqQuestionAlreadyExistAnswer() {
        AddQuestionWindow addQuestionWindow = faqPage.openWindowAddQuestion();
        addQuestionWindow.fillQuestionField("Могу ли я вернуть денежные средства?");
        addQuestionWindow.fillAnswerField(DataConfig.DataTest.getANSWER());
        addQuestionWindow.addQuestion();
        Question question = faqPage.getQuestion();
        assertEquals("Вопрос успешно добавлен", faqPage.getNotification());
        assertEquals("Могу ли я вернуть денежные средства?",question.getQuestionByIndex(1));
        assertEquals(DataConfig.DataTest.getANSWER(),question.getAnswerByIndex(1));
        assertEquals("Могу ли я вернуть денежные средства?", DataBaseQuery.selectFaqBySequence(1).getQuestion());
        assertTrue(faqPage.isExistQuestionsByIndex(1));
        assertFalse(addQuestionWindow.isWindowAppear());
    }

    @Feature("Добавление нового faq-вопроса")
    @Story("Добавление нового faq-вопроса с пустым полем вопроса")
    @Test
    void addFaqQuestionEmptyFieldQuestion() {
        AddQuestionWindow addQuestionWindow = faqPage.openWindowAddQuestion();
        addQuestionWindow.fillAnswerField(DataConfig.DataTest.getANSWER());
        assertFalse(addQuestionWindow.isEnabledAddButton());
    }


    @Feature("Добавление нового faq-вопроса")
    @Story("Добавление нового faq-вопроса с пустым полем ответа")
    @Test
    void addFaqQuestionEmptyFieldAnswer() {
        AddQuestionWindow addQuestionWindow = faqPage.openWindowAddQuestion();
        addQuestionWindow.fillQuestionField(DataConfig.DataTest.getQUESTION());
        assertFalse(addQuestionWindow.isEnabledAddButton());
    }

    @Feature("Добавление нового faq-вопроса")
    @Story("Зануление полей в окне добавления faq-вопроса после закрытия окна")
    @Test
    void closeWindowAddNewQuestion() {
        AddQuestionWindow addQuestionWindow = faqPage.openWindowAddQuestion();
        addQuestionWindow.fillQuestionField(DataConfig.DataTest.getQUESTION());
        addQuestionWindow.fillAnswerField(DataConfig.DataTest.getANSWER());
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
        Question question = faqPage.getQuestion();
        question.question();
        ChangeQuestionWindow changeQuestionWindow = question.openWindowChangeQuestion();
        changeQuestionWindow.changeQuestionWindow();
        changeQuestionWindow.fillQuestionField(DataConfig.DataTest.getCHANGE_QUESTION());
        changeQuestionWindow.fillAnswerField(DataConfig.DataTest.getCHANGE_ANSWER());
        changeQuestionWindow.saveChangesQuestion();
        assertEquals("Вопрос успешно обновлен", faqPage.getNotification());
        assertEquals(DataConfig.DataTest.getCHANGE_QUESTION(),question.getQuestion());
        assertEquals(DataConfig.DataTest.getCHANGE_ANSWER(),question.getAnswer());
        assertEquals(DataConfig.DataTest.getCHANGE_QUESTION(), DataBaseQuery.selectFaq().getQuestion());
        assertFalse(changeQuestionWindow.isWindowAppear());
    }

    @Feature("Редактирование faq-вопроса")
    @Story("Редактирование faq-вопроса с пустым полем вопроса")
    @ExtendWith(AddDeleteFaqDecorator.class)
    @Test
    void editQuestionEmptyFieldQuestion() {
        Question question = faqPage.getQuestion();
        ChangeQuestionWindow changeQuestionWindow = question.openWindowChangeQuestion();
        changeQuestionWindow.clearQuestionField();
        changeQuestionWindow.fillAnswerField(DataConfig.DataTest.getCHANGE_ANSWER());
        assertFalse(changeQuestionWindow.isEnabledSaveButton());
    }

    @Feature("Редактирование faq-вопроса")
    @Story("Редактирование faq-вопроса с пустым полем ответа")
    @ExtendWith(AddDeleteFaqDecorator.class)
    @Test
    void editQuestionEmptyFieldAnswer() {
        Question question = faqPage.getQuestion();
        ChangeQuestionWindow changeQuestionWindow = question.openWindowChangeQuestion();
        changeQuestionWindow.clearAnswerField();
        assertFalse(changeQuestionWindow.isEnabledSaveButton());
    }

    @Feature("Редактирование faq-вопроса")
    @Story("Сохранение faq-вопроса без изменений данных")
    @ExtendWith({AddDeleteFaqDecorator.class,NotificationDecorator.class})
    @Test
    void editQuestionNotChangeSave() {
        Question question = faqPage.getQuestion();
        ChangeQuestionWindow changeQuestionWindow = question.openWindowChangeQuestion();
        changeQuestionWindow.saveChangesQuestion();
        assertEquals("Вопрос успешно обновлен", faqPage.getNotification());
        assertEquals(DataConfig.DataTest.getQUESTION(),question.getQuestion());
        assertEquals(DataConfig.DataTest.getANSWER(),question.getAnswer());
        assertEquals(DataConfig.DataTest.getQUESTION(), DataBaseQuery.selectFaq().getQuestion());
        assertFalse(changeQuestionWindow.isWindowAppear());
    }

    @Feature("Редактирование faq-вопроса")
    @Story("Сохранение значений полей в окне редактирования faq-вопроса после закрытия окна")
    @ExtendWith(AddDeleteFaqDecorator.class)
    @Test
    void closeWindowEditQuestion() {
        Question question = faqPage.getQuestion();
        ChangeQuestionWindow changeQuestionWindow = question.openWindowChangeQuestion();
        changeQuestionWindow.fillQuestionField(DataConfig.DataTest.getCHANGE_QUESTION());
        changeQuestionWindow.fillAnswerField(DataConfig.DataTest.getCHANGE_ANSWER());
        changeQuestionWindow.closeWindowEditQuestion();
        Selenide.sleep(1000);
        assertFalse(changeQuestionWindow.isWindowAppear());
        question.openWindowChangeQuestion();
        assertEquals(DataConfig.DataTest.getQUESTION(),question.getQuestion());
        assertEquals(DataConfig.DataTest.getANSWER(),question.getAnswer());
        assertEquals(DataConfig.DataTest.getQUESTION(), DataBaseQuery.selectFaq().getQuestion());
    }

    @Feature("Редактирование faq-вопроса")
    @Story("Смена последовательности faq-вопросов")
    @ExtendWith(AddTwoQuestionFaqDecorator.class)
    @Test
    void sequenceChangeQuestion() {
        Question question = faqPage.getQuestion();
        String firstQuestionText = question.getQuestionByIndex(0);
        String secondQuestionText = question.getQuestionByIndex(1);
        String firstAnswerText=question.getAnswerByIndex(0);
        String secondAnswerText=question.getAnswerByIndex(1);
        faqPage.sequenceChangeQuestions(0, 1);
        Selenide.sleep(1000);
        assertEquals(secondQuestionText, question.getQuestionByIndex(0));
        assertEquals(firstQuestionText, question.getQuestionByIndex(1));
        assertEquals(secondAnswerText,question.getAnswerByIndex(0));
        assertEquals(firstAnswerText,question.getAnswerByIndex(1));
        assertEquals(firstQuestionText,DataBaseQuery.selectFaqBySequence(1).getQuestion());
        assertEquals(secondQuestionText,DataBaseQuery.selectFaqBySequence(0).getQuestion());
    }

    @Feature("Удаление faq-вопроса")
    @Story("Успешное удаление faq-вопроса")
    @ExtendWith({AddFaqDecorator.class,NotificationDecorator.class})
    @Test
    void deleteQuestion() {
        Question question = faqPage.getQuestion();
        ChangeQuestionWindow changeQuestionWindow = question.openWindowChangeQuestion();
        changeQuestionWindow.deleteQuestion();
        assertEquals("Вопрос успешно удален", faqPage.getNotification());
        assertFalse(faqPage.isExistQuestions());
        assertTrue(faqPage.isExistsEmptyList());
        assertNull(DataBaseQuery.selectFaq());
    }

    @Story("Закрытие уведомления на странице faq по таймауту")
    @ExtendWith(AddFaqDecorator.class)
    @Test
    void closeNotificationTimeout() {
        Question question = faqPage.getQuestion();
        ChangeQuestionWindow changeQuestionWindow = question.openWindowChangeQuestion();
        changeQuestionWindow.deleteQuestion();
        checkCloseNotificationTimeout(basePage);
    }

    @Story("Закрытие уведомления на странице faq")
    @ExtendWith(AddFaqDecorator.class)
    @Test
    void closeNotification() {
        Question question = faqPage.getQuestion();
        ChangeQuestionWindow changeQuestionWindow = question.openWindowChangeQuestion();
        changeQuestionWindow.deleteQuestion();
        checkCloseNotification(basePage);
    }

    @Feature("Поиск по faq")
    @Story("Поиск вопроса по заголовку и ответу")
    @ExtendWith(AddSomeFaq.class)
    @Test
    void searchNameFaq() {
        faqPage.faqPage();
        int countAllFaq= faqPage.getCountFaq();
        faqPage.searchFaq(DataConfig.DataSearch.getFAQ_SEARCH());
        Selenide.sleep(5000);
        int countResult= faqPage.getCountFaq();
        ElementsCollection questionTexts = faqPage.getQuestionsFields();
        ElementsCollection answerTexts = faqPage.getAnswerFields();
        for (int i = 0; i < questionTexts.size(); i++) {
            String questionText = questionTexts.get(i).getAttribute("value");
            String answerText = answerTexts.get(i).getAttribute("value");
            boolean isQuestionFound = questionText.toLowerCase().contains(DataConfig.DataSearch.getFAQ_SEARCH().toLowerCase());
            boolean isAnswerFound = answerText.toLowerCase().contains(DataConfig.DataSearch.getFAQ_SEARCH().toLowerCase());
            assertTrue(isQuestionFound || isAnswerFound);
        }
        assertTrue(countResult<countAllFaq);
    }

    @Feature("Поиск по faq")
    @Story("Поиск по включению")
    @ExtendWith(AddSomeFaq.class)
    @Test
    void searchByInclusion() {
        faqPage.faqPage();
        int countAllFaq= faqPage.getCountFaq();
        faqPage.searchFaq(DataConfig.DataSearch.getSEARCH_BY_INCLUSION_FAQ());
        Selenide.sleep(5000);
        int countResult= faqPage.getCountFaq();
        ElementsCollection questionTexts = faqPage.getQuestionsFields();
        ElementsCollection answerTexts = faqPage.getAnswerFields();
        for (int i = 0; i < questionTexts.size(); i++) {
            String questionText = questionTexts.get(i).getAttribute("value");
            String answerText = answerTexts.get(i).getAttribute("value");
            boolean isQuestionFound = questionText.toLowerCase().contains(DataConfig.DataSearch.getSEARCH_BY_INCLUSION_FAQ().toLowerCase());
            boolean isAnswerFound = answerText.toLowerCase().contains(DataConfig.DataSearch.getSEARCH_BY_INCLUSION_FAQ().toLowerCase());
            assertTrue(isQuestionFound || isAnswerFound);
        }
        assertTrue(countResult<countAllFaq);
    }


    @Feature("Поиск по faq")
    @Story("Сброс поискового результата после очистки поля")
    @ExtendWith(AddSomeFaq.class)
    @Test
    void resetSearchResultFaq() {
        faqPage.searchFaq(DataConfig.DataSearch.getFAQ_SEARCH());
        Selenide.sleep(5000);
        int countResult= faqPage.getCountFaq();
        ElementsCollection questionTexts = faqPage.getQuestionsFields();
        int resultSearch=questionTexts.size();
        faqPage.clearSearchField();
        Selenide.sleep(5000);
        int countAllFaq= faqPage.getCountFaq();
        ElementsCollection questionAll =faqPage.getQuestionsFields();
        int allFaq=questionAll.size();
        assertEquals("", faqPage.getValueSearchField());
        assertTrue(resultSearch < allFaq);
        assertTrue(countResult<countAllFaq);
    }

    @Feature("Поиск по faq")
    @Story("Поиск по значению в верхнем регистре")
    @ExtendWith(AddSomeFaq.class)
    @Test
    void searchHighRegister() {
        faqPage.faqPage();
        int countAllFaq= faqPage.getCountFaq();
        faqPage.searchFaq(DataConfig.DataSearch.getFAQ_HIGH_REGISTER());
        Selenide.sleep(5000);
        int countResult= faqPage.getCountFaq();
        ElementsCollection questionTexts = faqPage.getQuestionsFields();
        ElementsCollection answerTexts = faqPage.getAnswerFields();
        for (int i = 0; i < questionTexts.size(); i++) {
            String questionText = questionTexts.get(i).getAttribute("value");
            String answerText = answerTexts.get(i).getAttribute("value");
            boolean isQuestionFound = questionText.toLowerCase().contains(DataConfig.DataSearch.getFAQ_HIGH_REGISTER().toLowerCase());
            boolean isAnswerFound = answerText.toLowerCase().contains(DataConfig.DataSearch.getFAQ_HIGH_REGISTER().toLowerCase());
            assertTrue(isQuestionFound || isAnswerFound);
        }
        assertTrue(countResult<countAllFaq);
    }

    @Feature("Поиск по faq")
    @Story("Поиск по значению в верхнем регистре")
    @ExtendWith(AddSomeFaq.class)
    @Test
    void searchDifferentRegister() {
        faqPage.faqPage();
        int countAllFaq= faqPage.getCountFaq();
        faqPage.searchFaq(DataConfig.DataSearch.getFAQ_HIGH_REGISTER());
        Selenide.sleep(5000);
        int countResult= faqPage.getCountFaq();
        ElementsCollection questionTexts = faqPage.getQuestionsFields();
        ElementsCollection answerTexts = faqPage.getAnswerFields();
        for (int i = 0; i < questionTexts.size(); i++) {
            String questionText = questionTexts.get(i).getAttribute("value");
            String answerText = answerTexts.get(i).getAttribute("value");
            boolean isQuestionFound = questionText.toLowerCase().contains(DataConfig.DataSearch.getFAQ_HIGH_REGISTER().toLowerCase());
            boolean isAnswerFound = answerText.toLowerCase().contains(DataConfig.DataSearch.getFAQ_HIGH_REGISTER().toLowerCase());
            assertTrue(isQuestionFound || isAnswerFound);
        }
        assertTrue(countResult<countAllFaq);
    }

}

