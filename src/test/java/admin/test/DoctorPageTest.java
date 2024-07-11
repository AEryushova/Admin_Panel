package admin.test;

import admin.pages.DoctorsPage.CardDoctorPage.CardDoctorPage;
import admin.pages.DoctorsPage.DoctorsPage;
import admin.utils.preparationDataTests.general.AllureDecorator;
import admin.utils.testUtils.*;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import static admin.data.TestData.DataTest.*;
import static admin.data.TestData.UserData.*;
import static admin.data.TestData.DataSearch.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Врачи")
@DisplayName("Страница Врачи")
public class DoctorPageTest extends BaseTest {

    private DoctorsPage doctorsPage;

    @ExtendWith(AllureDecorator.class)

    @BeforeAll
    static void setUpAuth() {
        BrowserManager.openAdminPanel(LOGIN_ADMIN, PASSWORD_ADMIN);
    }

    @BeforeEach
    void setUp() {
        Selenide.refresh();
        doctorsPage = new DoctorsPage();
    }

    @AfterAll
    static void closeWebDriver() {
        Selenide.closeWebDriver();
    }

    @Feature("Открытие карточки врача")
    @Story("Открытие карточки врача и возврат к списку врачей")
    @DisplayName("Открытие карточки врача и возврат к списку врачей")
    @Test
    void returnToDoctorsPageFromCardDoctorPage() {
        doctorsPage.verifyDoctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.clickButtonEditInfoDoctor(DOCTOR_SPECIALIZATION,DOCTOR);
        cardDoctor.clickButtonComebackDoctorsPage();
        doctorsPage.verifyDoctorsPage();
    }

    @Feature("Поиск по врачам")
    @Story("Поиск врачей по имени")
    @DisplayName("Поиск врачей по имени")
    @Test
    void searchNameDoctor() {
        doctorsPage.verifyDoctorsPage();
        int countAllDoctors = doctorsPage.getCountDoctors();
        doctorsPage.searchDoctor(DOCTOR_NAME_SEARCH);
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        ElementsCollection namesDoctors = doctorsPage.getNamesDoctors();
        for (SelenideElement nameDoctor : namesDoctors) {
            assertThat(nameDoctor.getText().toLowerCase(), containsString(DOCTOR_NAME_SEARCH.toLowerCase()));
        }
        assertTrue(countResult < countAllDoctors);
    }


    @Feature("Поиск по врачам")
    @Story("Поиск врачей по специальности")
    @DisplayName("Поиск врачей по специальности")
    @Test
    void searchSpecializationDoctor() {
        doctorsPage.verifyDoctorsPage();
        int countAllDoctors = doctorsPage.getCountDoctors();
        doctorsPage.searchDoctor(DOCTOR_SPECIALIZATION_SEARCH);
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        ElementsCollection specializationDoctors = doctorsPage.getSpecializationDoctors();
        for (SelenideElement specializationDoctor : specializationDoctors) {
            assertThat(specializationDoctor.getText().toLowerCase(), containsString(DOCTOR_SPECIALIZATION_SEARCH.toLowerCase()));
        }
        assertTrue(countResult < countAllDoctors);
    }

    @Feature("Поиск по врачам")
    @Story("Поиск врачей по включению")
    @DisplayName("Поиск врачей по включению")
    @Test
    void searchByInclusion() {
        doctorsPage.verifyDoctorsPage();
        int countAllDoctors = doctorsPage.getCountDoctors();
        doctorsPage.searchDoctor(SEARCH_BY_INCLUSION_DOCTORS);
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        ElementsCollection namesDoctors = doctorsPage.getNamesDoctors();
        ElementsCollection specializationDoctors = doctorsPage.getSpecializationDoctors();
        for (int i = 0; i < namesDoctors.size(); i++) {
            String nameDoctor = namesDoctors.get(i).getText();
            String specializationDoctor = specializationDoctors.get(i).getText();
            boolean isNameFound = nameDoctor.toLowerCase().contains(SEARCH_BY_INCLUSION_DOCTORS.toLowerCase());
            boolean isSpecializationFound = specializationDoctor.toLowerCase().contains(SEARCH_BY_INCLUSION_DOCTORS.toLowerCase());
            assertTrue(isNameFound || isSpecializationFound);
        }
        assertTrue(countResult < countAllDoctors);
    }

    @Feature("Поиск по врачам")
    @Story("Сброс поискового результата врачей после очистки поля")
    @DisplayName("Сброс поискового результата врачей после очистки поля")
    @Test
    void resetSearchResultDoctors() {
        doctorsPage.verifyDoctorsPage();
        doctorsPage.searchDoctor(DOCTOR_NAME_SEARCH);
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
    @Story("Поиск врачей по значению в верхнем регистре")
    @DisplayName("Поиск врачей по значению в верхнем регистре")
    @Test
    void searchHighRegister() {
        doctorsPage.verifyDoctorsPage();
        int countAllDoctors = doctorsPage.getCountDoctors();
        doctorsPage.searchDoctor(DOCTOR_NAME_HIGH_REGISTER);
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        ElementsCollection namesDoctors = doctorsPage.getNamesDoctors();
        for (SelenideElement nameDoctor : namesDoctors) {
            assertThat(nameDoctor.getText().toLowerCase(), containsString(DOCTOR_NAME_HIGH_REGISTER.toLowerCase()));
        }
        assertTrue(countResult < countAllDoctors);
    }

    @Feature("Поиск по врачам")
    @Story("Поиск врачей по значению в различном регистре")
    @DisplayName("Поиск врачей по значению в различном регистре")
    @Test
    void searchDifferentRegister() {
        doctorsPage.verifyDoctorsPage();
        int countAllDoctors = doctorsPage.getCountDoctors();
        doctorsPage.searchDoctor(DOCTOR_NAME_DIFFERENT_REGISTER);
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        ElementsCollection namesDoctors = doctorsPage.getNamesDoctors();
        for (SelenideElement nameDoctor : namesDoctors) {
            assertThat(nameDoctor.getText().toLowerCase(), containsString(DOCTOR_NAME_DIFFERENT_REGISTER.toLowerCase()));
        }
        assertTrue(countResult < countAllDoctors);
    }

    @Feature("Сортировка по врачам")
    @Story("Сортировка врачей по отсутствию фото")
    @DisplayName("Сортировка врачей по отсутствию фото")
    @Test
    void sortingWithoutPhoto() {
        doctorsPage.verifyDoctorsPage();
        int countAllDoctors = doctorsPage.getCountDoctors();
        doctorsPage.clickSortingPhotoNo();
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        List<String> photoDoctorsAttributes = doctorsPage.getPhotoDoctorsAttributes();
        for (String attributeValue : photoDoctorsAttributes) {
            assertThat(attributeValue, equalTo(DEFAULT_PHOTO.toLowerCase()));
        }
        assertTrue(countResult < countAllDoctors);
    }

    @Feature("Сортировка по врачам")
    @Story("Сортировка врачей по наличию фото")
    @DisplayName("Сортировка врачей по наличию фото")
    @Test
    void sortingWithPhoto() {
        doctorsPage.verifyDoctorsPage();
        int countAllDoctors = doctorsPage.getCountDoctors();
        doctorsPage.clickSortingPhotoYes();
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        List<String> photoDoctorsAttributes = doctorsPage.getPhotoDoctorsAttributes();
        for (String attributeValue : photoDoctorsAttributes) {
            assertThat(attributeValue, not(DEFAULT_PHOTO.toLowerCase()));
        }
        assertTrue(countResult < countAllDoctors);
    }

    @Feature("Сортировка по врачам")
    @Story("Сортировка по всем врачам")
    @DisplayName("Сортировка по всем врачам")
    @Test
    void sortingWithAndWithoutPhoto() {
        doctorsPage.verifyDoctorsPage();
        doctorsPage.clickSortingPhotoNo();
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        doctorsPage.clickSortingPhotoAll();
        Selenide.sleep(5000);
        int countAllDoctors = doctorsPage.getCountDoctors();
        List<String> photoDoctorsAttributes = doctorsPage.getPhotoDoctorsAttributes();
        boolean withoutPhoto = false;
        boolean withPhoto = false;
        for (String attributeValue : photoDoctorsAttributes) {
            if (attributeValue.equals(DEFAULT_PHOTO.toLowerCase())) {
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



