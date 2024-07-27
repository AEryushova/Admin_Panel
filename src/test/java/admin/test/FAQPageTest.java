package admin.test;

import admin.pages.FaqPage.*;
import admin.pages.HeaderMenu.HeaderMenu;
import admin.utils.dbUtils.DataBaseQuery;
import admin.utils.preparationDataTests.faq.*;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static admin.data.TestData.DataTest.*;
import static admin.data.TestData.UserData.*;
import static admin.data.TestData.DataSearch.*;
import static admin.utils.otherUtils.DataGenerator.*;
import static org.junit.jupiter.api.Assertions.*;


@Epic("FAQ")
@DisplayName("Страница FAQ")
public class FAQPageTest extends BaseTest {

    private FaqPage faqPage;

    @BeforeAll
    static void setUpAuth() {
        BaseTest.openAdminPanel(LOGIN_ADMIN,PASSWORD_ADMIN);
        HeaderMenu headerMenu = new HeaderMenu();
        headerMenu.clickFaqTab();
    }

    @BeforeEach
    void setUp(){
        Selenide.refresh();
        faqPage=new FaqPage();
    }

    @AfterAll
    static void closeWebDriver() {
        Selenide.closeWebDriver();
    }

    @Feature("Добавление нового faq-вопроса")
    @Story("Успешное добавление нового faq-вопроса")
    @DisplayName("Успешное добавление нового faq-вопроса")
    @ExtendWith(DeleteFaqDecorator.class)
    @Test
    void addFaqQuestion() {
        AddQuestionWindow addQuestionWindow = faqPage.clickButtonAddQuestion();
        addQuestionWindow.verifyAddQuestionWindow();
        addQuestionWindow.fillQuestionField(generateQuestion());
        addQuestionWindow.fillAnswerField(generateText());
        addQuestionWindow.clickButtonAddQuestion();
        Question question = faqPage.getQuestion();
        assertEquals("Вопрос успешно добавлен", faqPage.getNotification());
        assertEquals(questionFaq,question.getQuestion());
        assertEquals(text,question.getAnswer());
        assertEquals(questionFaq, DataBaseQuery.selectFaq().getQuestion());
        assertTrue(faqPage.isExistQuestions());
        assertFalse(addQuestionWindow.isWindowAppear());
    }

    @Feature("Добавление нового faq-вопроса")
    @Story("Добавление нового faq-вопроса с уже существующим заголовком и ответом")
    @DisplayName("Добавление нового faq-вопроса с уже существующим заголовком и ответом")
    @ExtendWith(AddDeleteFaqDecorator.class)
    @Test
    void addFaqQuestionAlreadyExistQuestionAnswer() {
        AddQuestionWindow addQuestionWindow = faqPage.clickButtonAddQuestion();
        addQuestionWindow.fillQuestionField(questionFaq);
        addQuestionWindow.fillAnswerField(text);
        addQuestionWindow.clickButtonAddQuestion();
        assertEquals("Вопрос уже существует", faqPage.getNotification());
        assertFalse(faqPage.isExistQuestionsByIndex(1));
    }

    @Feature("Добавление нового faq-вопроса")
    @Story("Добавление нового faq-вопроса с уже существующим заголовком")
    @DisplayName("Добавление нового faq-вопроса с уже существующим заголовком")
    @ExtendWith(AddDeleteFaqDecorator.class)
    @Test
    void addFaqQuestionAlreadyExistQuestion() {
        AddQuestionWindow addQuestionWindow = faqPage.clickButtonAddQuestion();
        addQuestionWindow.fillQuestionField(questionFaq);
        addQuestionWindow.fillAnswerField(generateText());
        addQuestionWindow.clickButtonAddQuestion();
        assertEquals("Вопрос уже существует", faqPage.getNotification());
        assertFalse(faqPage.isExistQuestionsByIndex(1));
    }

    @Feature("Добавление нового faq-вопроса")
    @Story("Добавление нового faq-вопроса с уже существующим ответом")
    @DisplayName("Добавление нового faq-вопроса с уже существующим ответом")
    @ExtendWith(AddDeleteFaqDecorator.class)
    @Test
    void addFaqQuestionAlreadyExistAnswer() {
        AddQuestionWindow addQuestionWindow = faqPage.clickButtonAddQuestion();
        addQuestionWindow.fillQuestionField(generateQuestion());
        addQuestionWindow.fillAnswerField(text);
        addQuestionWindow.clickButtonAddQuestion();
        Question question = faqPage.getQuestion();
        assertEquals("Вопрос успешно добавлен", faqPage.getNotification());
        assertEquals(questionFaq,question.getQuestionByIndex(1));
        assertEquals(text,question.getAnswerByIndex(1));
        assertEquals(questionFaq, DataBaseQuery.selectFaqBySequence(1).getQuestion());
        assertTrue(faqPage.isExistQuestionsByIndex(1));
        assertFalse(addQuestionWindow.isWindowAppear());
    }

    @Feature("Добавление нового faq-вопроса")
    @Story("Добавление нового faq-вопроса с пустым полем вопроса")
    @DisplayName("Добавление нового faq-вопроса с пустым полем вопроса")
    @Test
    void addFaqQuestionEmptyFieldQuestion() {
        AddQuestionWindow addQuestionWindow = faqPage.clickButtonAddQuestion();
        addQuestionWindow.fillAnswerField(generateText());
        assertFalse(addQuestionWindow.isEnabledAddButton());
    }


    @Feature("Добавление нового faq-вопроса")
    @Story("Добавление нового faq-вопроса с пустым полем ответа")
    @DisplayName("Добавление нового faq-вопроса с пустым полем ответа")
    @Test
    void addFaqQuestionEmptyFieldAnswer() {
        AddQuestionWindow addQuestionWindow = faqPage.clickButtonAddQuestion();
        addQuestionWindow.fillQuestionField(generateQuestion());
        assertFalse(addQuestionWindow.isEnabledAddButton());
    }

    @Feature("Добавление нового faq-вопроса")
    @Story("Сброс значений полей в окне добавления faq-вопроса при закрытии окна")
    @DisplayName("Сброс значений полей в окне добавления faq-вопроса при закрытии окна")
    @Test
    void closeWindowAddNewQuestionFaq() {
        AddQuestionWindow addQuestionWindow = faqPage.clickButtonAddQuestion();
        addQuestionWindow.fillQuestionField(generateQuestion());
        addQuestionWindow.fillAnswerField(generateText());
        addQuestionWindow.closeWindowAddQuestion();
        assertFalse(addQuestionWindow.isWindowAppear());
        faqPage.clickButtonAddQuestion();
        assertEquals("", addQuestionWindow.getValueQuestionField());
        assertEquals("", addQuestionWindow .getValueAnswerField());
    }

    @Feature("Редактирование faq-вопроса")
    @Story("Успешное редактирование faq-вопроса")
    @DisplayName("Успешное редактирование faq-вопроса")
    @ExtendWith(AddDeleteFaqDecorator.class)
    @Test
    void editFaqQuestion() {
        Question question = faqPage.getQuestion();
        question.verifyQuestion();
        EditQuestionWindow editQuestionWindow = question.clickButtonChangeQuestion();
        editQuestionWindow.verifyChangeQuestionWindow();
        editQuestionWindow.fillQuestionField(generateQuestion());
        editQuestionWindow.fillAnswerField(generateText());
        editQuestionWindow.clickButtonSaveChangesQuestion();
        assertEquals("Вопрос успешно обновлен", faqPage.getNotification());
        assertEquals(questionFaq,question.getQuestion());
        assertEquals(text,question.getAnswer());
        assertEquals(questionFaq, DataBaseQuery.selectFaq().getQuestion());
        assertFalse(editQuestionWindow.isWindowAppear());
    }

    @Feature("Редактирование faq-вопроса")
    @Story("Редактирование faq-вопроса с пустым полем вопроса")
    @DisplayName("Редактирование faq-вопроса с пустым полем вопроса")
    @ExtendWith(AddDeleteFaqDecorator.class)
    @Test
    void editFaqQuestionEmptyFieldQuestion() {
        Question question = faqPage.getQuestion();
        EditQuestionWindow editQuestionWindow = question.clickButtonChangeQuestion();
        editQuestionWindow.clearQuestionField();
        editQuestionWindow.fillAnswerField(generateText());
        assertFalse(editQuestionWindow.isEnabledSaveButton());
    }

    @Feature("Редактирование faq-вопроса")
    @Story("Редактирование faq-вопроса с пустым полем ответа")
    @DisplayName("Редактирование faq-вопроса с пустым полем ответа")
    @ExtendWith(AddDeleteFaqDecorator.class)
    @Test
    void editFaqQuestionEmptyFieldAnswer() {
        Question question = faqPage.getQuestion();
        EditQuestionWindow editQuestionWindow = question.clickButtonChangeQuestion();
        editQuestionWindow.clearAnswerField();
        assertFalse(editQuestionWindow.isEnabledSaveButton());
    }

    @Feature("Редактирование faq-вопроса")
    @Story("Сохранение faq-вопроса без изменений данных")
    @DisplayName("Сохранение faq-вопроса без изменений данных")
    @ExtendWith(AddDeleteFaqDecorator.class)
    @Test
    void savaFaqQuestionNotChange() {
        Question question = faqPage.getQuestion();
        EditQuestionWindow editQuestionWindow = question.clickButtonChangeQuestion();
        editQuestionWindow.clickButtonSaveChangesQuestion();
        assertEquals("Вопрос успешно обновлен", faqPage.getNotification());
        assertEquals(questionFaq,question.getQuestion());
        assertEquals(text,question.getAnswer());
        assertEquals(questionFaq, DataBaseQuery.selectFaq().getQuestion());
        assertFalse(editQuestionWindow.isWindowAppear());
    }

    @Feature("Редактирование faq-вопроса")
    @Story("Сохранение значений полей в окне редактирования faq-вопроса после закрытия окна")
    @DisplayName("Сохранение значений полей в окне редактирования faq-вопроса после закрытия окна")
    @ExtendWith(AddDeleteFaqDecorator.class)
    @Test
    void closeWindowEditQuestionFaq() {
        Question question = faqPage.getQuestion();
        EditQuestionWindow editQuestionWindow = question.clickButtonChangeQuestion();
        editQuestionWindow.fillQuestionField(generateNamePatient());
        editQuestionWindow.fillAnswerField(generateWord());
        editQuestionWindow.closeWindowEditQuestion();
        Selenide.sleep(1000);
        assertFalse(editQuestionWindow.isWindowAppear());
        question.clickButtonChangeQuestion();
        assertEquals(questionFaq,question.getQuestion());
        assertEquals(text,question.getAnswer());
        assertEquals(questionFaq, DataBaseQuery.selectFaq().getQuestion());
    }

    @Feature("Редактирование faq-вопроса")
    @Story("Смена последовательности отображения faq-вопросов")
    @DisplayName("Смена последовательности отображения faq-вопросов")
    @ExtendWith(AddTwoQuestionFaqDecorator.class)
    @Test
    void changeDisplaySequenceFaqQuestion() {
        Question question = faqPage.getQuestion();
        String firstQuestionText = question.getQuestionByIndex(0);
        String secondQuestionText = question.getQuestionByIndex(1);
        String firstAnswerText=question.getAnswerByIndex(0);
        String secondAnswerText=question.getAnswerByIndex(1);
        faqPage.changeSequenceDisplayQuestions(0, 1);
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
    @DisplayName("Успешное удаление faq-вопроса")
    @ExtendWith(AddFaqDecorator.class)
    @Test
    void deleteFaqQuestion() {
        Question question = faqPage.getQuestion();
        EditQuestionWindow editQuestionWindow = question.clickButtonChangeQuestion();
        editQuestionWindow.clickButtonDeleteQuestion();
        assertEquals("Вопрос успешно удален", faqPage.getNotification());
        assertFalse(faqPage.isExistQuestions());
        assertTrue(faqPage.isExistsEmptyList());
        assertNull(DataBaseQuery.selectFaq());
    }

    @Feature("Поиск по faq")
    @Story("Поиск вопроса по заголовку и ответу")
    @DisplayName("Поиск вопроса по заголовку и ответу")
    @ExtendWith(AddSomeFaq.class)
    @Test
    void searchNameFaqQuestion() {
        faqPage.verifyFaqPage();
        int countAllFaq= faqPage.getCountFaq();
        faqPage.searchFaq(FAQ_SEARCH);
        Selenide.sleep(5000);
        int countResult= faqPage.getCountFaq();
        ElementsCollection questionTexts = faqPage.getQuestionsFields();
        ElementsCollection answerTexts = faqPage.getAnswerFields();
        for (int i = 0; i < questionTexts.size(); i++) {
            String questionText = questionTexts.get(i).getAttribute("value");
            String answerText = answerTexts.get(i).getAttribute("value");
            assertNotNull(questionText);
            assertNotNull(answerText);
            boolean isQuestionFound = questionText.toLowerCase().contains(FAQ_SEARCH.toLowerCase());
            boolean isAnswerFound = answerText.toLowerCase().contains(FAQ_SEARCH.toLowerCase());
            assertTrue(isQuestionFound || isAnswerFound);
        }
        assertTrue(countResult<countAllFaq);
    }

    @Feature("Поиск по faq")
    @Story("Поиск вопроса по включению")
    @DisplayName("Поиск вопроса по включению")
    @ExtendWith(AddSomeFaq.class)
    @Test
    void searchByInclusion() {
        faqPage.verifyFaqPage();
        int countAllFaq= faqPage.getCountFaq();
        faqPage.searchFaq(SEARCH_BY_INCLUSION_FAQ);
        Selenide.sleep(5000);
        int countResult= faqPage.getCountFaq();
        ElementsCollection questionTexts = faqPage.getQuestionsFields();
        ElementsCollection answerTexts = faqPage.getAnswerFields();
        for (int i = 0; i < questionTexts.size(); i++) {
            String questionText = questionTexts.get(i).getAttribute("value");
            String answerText = answerTexts.get(i).getAttribute("value");
            assertNotNull(questionText);
            assertNotNull(answerText);
            boolean isQuestionFound = questionText.toLowerCase().contains(SEARCH_BY_INCLUSION_FAQ.toLowerCase());
            boolean isAnswerFound = answerText.toLowerCase().contains(SEARCH_BY_INCLUSION_FAQ.toLowerCase());
            assertTrue(isQuestionFound || isAnswerFound);
        }
        assertTrue(countResult<countAllFaq);
    }


    @Feature("Поиск по faq")
    @Story("Сброс поискового результата вопроса после очистки поля")
    @DisplayName("Сброс поискового результата вопроса после очистки поля")
    @ExtendWith(AddSomeFaq.class)
    @Test
    void resetSearchResultFaqQuestion() {
        faqPage.searchFaq(FAQ_SEARCH);
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
    @Story("Поиск вопроса по значению в верхнем регистре")
    @DisplayName("Поиск вопроса по значению в верхнем регистре")
    @ExtendWith(AddSomeFaq.class)
    @Test
    void searchHighRegister() {
        faqPage.verifyFaqPage();
        int countAllFaq= faqPage.getCountFaq();
        faqPage.searchFaq(FAQ_HIGH_REGISTER);
        Selenide.sleep(5000);
        int countResult= faqPage.getCountFaq();
        ElementsCollection questionTexts = faqPage.getQuestionsFields();
        ElementsCollection answerTexts = faqPage.getAnswerFields();
        for (int i = 0; i < questionTexts.size(); i++) {
            String questionText = questionTexts.get(i).getAttribute("value");
            String answerText = answerTexts.get(i).getAttribute("value");
            assertNotNull(questionText);
            assertNotNull(answerText);
            boolean isQuestionFound = questionText.toLowerCase().contains(FAQ_HIGH_REGISTER.toLowerCase());
            boolean isAnswerFound = answerText.toLowerCase().contains(FAQ_HIGH_REGISTER.toLowerCase());
            assertTrue(isQuestionFound || isAnswerFound);
        }
        assertTrue(countResult<countAllFaq);
    }

    @Feature("Поиск по faq")
    @Story("Поиск вопроса по значению в верхнем регистре")
    @DisplayName("Поиск вопроса по значению в верхнем регистре")
    @ExtendWith(AddSomeFaq.class)
    @Test
    void searchDifferentRegister() {
        faqPage.verifyFaqPage();
        int countAllFaq= faqPage.getCountFaq();
        faqPage.searchFaq(FAQ_HIGH_REGISTER);
        Selenide.sleep(5000);
        int countResult= faqPage.getCountFaq();
        ElementsCollection questionTexts = faqPage.getQuestionsFields();
        ElementsCollection answerTexts = faqPage.getAnswerFields();
        for (int i = 0; i < questionTexts.size(); i++) {
            String questionText = questionTexts.get(i).getAttribute("value");
            String answerText = answerTexts.get(i).getAttribute("value");
            assertNotNull(questionText);
            assertNotNull(answerText);
            boolean isQuestionFound = questionText.toLowerCase().contains(FAQ_HIGH_REGISTER.toLowerCase());
            boolean isAnswerFound = answerText.toLowerCase().contains(FAQ_HIGH_REGISTER.toLowerCase());
            assertTrue(isQuestionFound || isAnswerFound);
        }
        assertTrue(countResult<countAllFaq);
    }

}

