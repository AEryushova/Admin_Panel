package pages.DoctorsPage;

import pages.BasePage.BasePage;
import pages.DoctorsPage.CardDoctorPage.CardDoctorPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class DoctorsPage extends BasePage {

    private final SelenideElement TAB_NAME = $x("//div[@class='wYqZ']/span[text()='Врачи']");
    private final SelenideElement SEARCH_DOCTOR = $x("//input[@placeholder='Поиск по врачам']");
    private final SelenideElement CARD_DOCTOR = $x("//div[@class='eF30']");
    private final SelenideElement DROP_DOWN_PHOTO = $x("//button[@class='MxFR']");
    private final SelenideElement OPTION_ALL = $x("//div[@class='U2Xk']/div[text()='Все']");
    private final SelenideElement OPTION_NO = $x("//div[@class='U2Xk']/div[text()='Нет']");
    private final SelenideElement OPTION_YES = $x("//div[@class='U2Xk']/div[text()='Есть']");
    private final SelenideElement SHOW_DOCTOR_WITHOUT_DESCRIPTION = $x("//span[text()='Показать']//parent::button");
    private final SelenideElement COUNT_DOCTORS=$x("//div[@class='wYqZ']/span[2]");
    private final ElementsCollection NAMES_DOCTORS = $$x("//div[@class='eF30']/div[@class='jPnI']/span[1]");
    private final ElementsCollection SPECIALIZATIONS_DOCTORS = $$x("//div[@class='eF30']/div[@class='jPnI']/span[2]");
    private final ElementsCollection PHOTO_DOCTORS=$$x("//div[@class='eF30']/div[@class='Uw0W']/img");

    @Step("Верифицировать страницу Врачи")
    public void verifyDoctorsPage() {
        TAB_NAME.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SEARCH_DOCTOR.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DROP_DOWN_PHOTO.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SHOW_DOCTOR_WITHOUT_DESCRIPTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CARD_DOCTOR.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Нажать на кнопку редактирования информации о враче с именем '{1}' и специализацией '{0}'")
    public CardDoctorPage clickButtonEditInfoDoctor(String specialization, String doctorName) {
        SelenideElement edit_button =searchCardDoctor(specialization,doctorName);
        edit_button.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new CardDoctorPage();
    }

    @Step("Получить кнопку редактирования карточки врача по имени '{1}' и специализации '{0}' ")
    public SelenideElement searchCardDoctor(String specialization, String doctorName){
        return $x("//span[text()='" + specialization + "']/preceding-sibling::span[text()='" + doctorName + "'] /parent::div/following-sibling::button[text()='Редактировать']");
    }

    @Step("Нажать на кнопку сортировки врачей без фотографии")
    public void clickSortingPhotoNo() {
        DROP_DOWN_PHOTO.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        OPTION_NO.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать на кнопку сортировки врачей с фотографией")
    public void clickSortingPhotoYes() {
        DROP_DOWN_PHOTO.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        OPTION_YES.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать на кнопку сортировки всех врачей")
    public void clickSortingPhotoAll() {
        DROP_DOWN_PHOTO.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        OPTION_ALL.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @SuppressWarnings("unused")
    @Step("Нажать на кнопку сортировки врачей без описания")
    public void showDoctorCardNoDescription() {
        SHOW_DOCTOR_WITHOUT_DESCRIPTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }


    @Step("Ввести в поле поиска '{0}'")
    public void searchDoctor(String textSearch) {
        SEARCH_DOCTOR.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        for (char c : textSearch.toCharArray()) {
            SEARCH_DOCTOR.sendKeys(String.valueOf(c));
            Selenide.sleep(1000);
        }
    }

    @Step("Получить имена врачей")
    public ElementsCollection getNamesDoctors() {
        return NAMES_DOCTORS;
    }

    @Step("Получить специализации врачей")
    public ElementsCollection getSpecializationDoctors() {
        return SPECIALIZATIONS_DOCTORS;
    }

    @Step("Получить ссылки на фотографии врачей")
    public List<String> getPhotoDoctorsAttributes() {
        return new ArrayList<>(PHOTO_DOCTORS)
                .stream()
                .map(element -> element.getAttribute("src"))
                .collect(Collectors.toList());
    }

    @Step("Очистить поле поиска")
    public void clearSearchField() {
        SEARCH_DOCTOR.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .sendKeys(Keys.CONTROL, "a");
        SEARCH_DOCTOR.sendKeys(Keys.BACK_SPACE);
    }

    @Step("Получить значение поля поиска")
    public String getValueSearchField() {
        SEARCH_DOCTOR.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return SEARCH_DOCTOR.getValue();
    }

    @Step("Получить количество врачей")
    public int getCountDoctors() {
        COUNT_DOCTORS.shouldBe(Condition.visible);
        return Integer.parseInt(COUNT_DOCTORS.getText().split(" ")[0]);
    }

    @Step("Проскроллить страницу до элемента '{0}'")
    public void scrollToCard(SelenideElement element) {
        element.scrollIntoView("{behavior: 'auto', block: 'center'}");
        Selenide.sleep(3000);
    }

}
