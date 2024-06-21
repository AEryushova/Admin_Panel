package admin.test;

import admin.data.DataConfig;

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

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Врачи")
public class DoctorPageTest extends BaseTest {

    private DoctorsPage doctorsPage;

    @ExtendWith(AllureDecorator.class)

    @BeforeAll
    static void setUpAuth() {
        BrowserManager.openBrowser(DataConfig.UserData.getLOGIN_ADMIN(), DataConfig.UserData.getPASSWORD_ADMIN());
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


    @Story("Возврат на страницу Врачи с карточки врача")
    @Test
    void returnToDoctorsPageFromCardDoctorPage() {
        doctorsPage.doctorsPage();
        CardDoctorPage cardDoctor = doctorsPage.openCardDoctor(DataConfig.DataTest.getDOCTOR_SPECIALIZATION(), DataConfig.DataTest.getDOCTOR());
        cardDoctor.comebackDoctorsPage();
        doctorsPage.doctorsPage();
    }

    @Feature("Поиск по врачам")
    @Story("Поиск врача по имени")
    @Test
    void searchNameDoctor() {
        doctorsPage.doctorsPage();
        int countAllDoctors = doctorsPage.getCountDoctors();
        doctorsPage.searchDoctor(DataConfig.DataSearch.getDOCTOR_NAME_SEARCH());
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        ElementsCollection namesDoctors = doctorsPage.getNamesDoctors();
        for (SelenideElement nameDoctor : namesDoctors) {
            assertThat(nameDoctor.getText().toLowerCase(), containsString(DataConfig.DataSearch.getDOCTOR_NAME_SEARCH().toLowerCase()));
        }
        assertTrue(countResult < countAllDoctors);
    }


    @Feature("Поиск по врачам")
    @Story("Поиск врача по специальности")
    @Test
    void searchSpecializationDoctor() {
        doctorsPage.doctorsPage();
        int countAllDoctors = doctorsPage.getCountDoctors();
        doctorsPage.searchDoctor(DataConfig.DataSearch.getDOCTOR_SPECIALIZATION_SEARCH());
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        ElementsCollection specializationDoctors = doctorsPage.getSpecializationDoctors();
        for (SelenideElement specializationDoctor : specializationDoctors) {
            assertThat(specializationDoctor.getText().toLowerCase(), containsString(DataConfig.DataSearch.getDOCTOR_SPECIALIZATION_SEARCH().toLowerCase()));
        }
        assertTrue(countResult < countAllDoctors);
    }

    @Feature("Поиск по врачам")
    @Story("Поиск по включению")
    @Test
    void searchByInclusion() {
        doctorsPage.doctorsPage();
        int countAllDoctors = doctorsPage.getCountDoctors();
        doctorsPage.searchDoctor(DataConfig.DataSearch.getSEARCH_BY_INCLUSION_DOCTORS());
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        ElementsCollection namesDoctors = doctorsPage.getNamesDoctors();
        ElementsCollection specializationDoctors = doctorsPage.getSpecializationDoctors();
        for (int i = 0; i < namesDoctors.size(); i++) {
            String nameDoctor = namesDoctors.get(i).getText();
            String specializationDoctor = specializationDoctors.get(i).getText();
            boolean isNameFound = nameDoctor.toLowerCase().contains(DataConfig.DataSearch.getSEARCH_BY_INCLUSION_DOCTORS().toLowerCase());
            boolean isSpecializationFound = specializationDoctor.toLowerCase().contains(DataConfig.DataSearch.getSEARCH_BY_INCLUSION_DOCTORS().toLowerCase());
            assertTrue(isNameFound || isSpecializationFound);
        }
        assertTrue(countResult < countAllDoctors);
    }

    @Feature("Поиск по врачам")
    @Story("Сброс поискового результата после очистки поля")
    @Test
    void resetSearchResultDoctors() {
        doctorsPage.doctorsPage();
        doctorsPage.searchDoctor(DataConfig.DataSearch.getDOCTOR_NAME_SEARCH());
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
    @Story("Поиск по значению в верхнем регистре")
    @Test
    void searchHighRegister() {
        doctorsPage.doctorsPage();
        int countAllDoctors = doctorsPage.getCountDoctors();
        doctorsPage.searchDoctor(DataConfig.DataSearch.getDOCTOR_NAME_HIGH_REGISTER());
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        ElementsCollection namesDoctors = doctorsPage.getNamesDoctors();
        for (SelenideElement nameDoctor : namesDoctors) {
            assertThat(nameDoctor.getText().toLowerCase(), containsString(DataConfig.DataSearch.getDOCTOR_NAME_HIGH_REGISTER().toLowerCase()));
        }
        assertTrue(countResult < countAllDoctors);
    }

    @Feature("Поиск по врачам")
    @Story("Поиск по значению в различном регистре")
    @Test
    void searchDifferentRegister() {
        doctorsPage.doctorsPage();
        int countAllDoctors = doctorsPage.getCountDoctors();
        doctorsPage.searchDoctor(DataConfig.DataSearch.getDOCTOR_NAME_DIFFERENT_REGISTER());
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        ElementsCollection namesDoctors = doctorsPage.getNamesDoctors();
        for (SelenideElement nameDoctor : namesDoctors) {
            assertThat(nameDoctor.getText().toLowerCase(), containsString(DataConfig.DataSearch.getDOCTOR_NAME_DIFFERENT_REGISTER().toLowerCase()));
        }
        assertTrue(countResult < countAllDoctors);
    }

    @Feature("Сортировка по врачам")
    @Story("Сортировка по отсутствию фото")
    @Test
    void sortingWithoutPhoto() {
        doctorsPage.doctorsPage();
        int countAllDoctors = doctorsPage.getCountDoctors();
        doctorsPage.sortingPhotoNo();
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        List<String> photoDoctorsAttributes = doctorsPage.getPhotoDoctorsAttributes();
        for (String attributeValue : photoDoctorsAttributes) {
            assertThat(attributeValue, equalTo(DataConfig.DataTest.getDEFAULT_PHOTO().toLowerCase()));
        }
        assertTrue(countResult < countAllDoctors);
    }

    @Feature("Сортировка по врачам")
    @Story("Сортировка по наличию фото")
    @Test
    void sortingWithPhoto() {
        doctorsPage.doctorsPage();
        int countAllDoctors = doctorsPage.getCountDoctors();
        doctorsPage.sortingPhotoYes();
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        List<String> photoDoctorsAttributes = doctorsPage.getPhotoDoctorsAttributes();
        for (String attributeValue : photoDoctorsAttributes) {
            assertThat(attributeValue, not(DataConfig.DataTest.getDEFAULT_PHOTO().toLowerCase()));
        }
        assertTrue(countResult < countAllDoctors);
    }

    @Feature("Сортировка по врачам")
    @Story("Сортировка по всем врачам")
    @Test
    void sortingWithAndWithoutPhoto() {
        doctorsPage.doctorsPage();
        doctorsPage.sortingPhotoNo();
        Selenide.sleep(5000);
        int countResult = doctorsPage.getCountDoctors();
        doctorsPage.sortingPhotoAll();
        Selenide.sleep(5000);
        int countAllDoctors = doctorsPage.getCountDoctors();
        List<String> photoDoctorsAttributes = doctorsPage.getPhotoDoctorsAttributes();
        boolean withoutPhoto = false;
        boolean withPhoto = false;
        for (String attributeValue : photoDoctorsAttributes) {
            if (attributeValue.equals(DataConfig.DataTest.getDEFAULT_PHOTO().toLowerCase())) {
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



