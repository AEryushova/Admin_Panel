package admin.pages;

import admin.pages.CardDoctorPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class DoctorsPage extends BasePage {

    private final SelenideElement tabNameDoctors = $x("//div[@class='wYqZ']/span[text()='Врачи']");
    private final SelenideElement searchDoctors = $x("//input[@placeholder='Поиск по врачам']");
    private final SelenideElement editDoctorInfo = $x("//span[text()='Fer3 Копия Тест']/parent::div/following-sibling::button[text()='Редактировать']");
    private final SelenideElement dropDownPhoto = $x("//div[@class='uTSS']");
    private final SelenideElement optionAll = $x("//div[@class='U2Xk']/div[text()='Все']");
    private final SelenideElement optionNo = $x("//div[@class='U2Xk']/div[text()='Нет']");
    private final SelenideElement optionYes = $x("//div[@class='U2Xk']/div[text()='Есть']");
    private final SelenideElement buttonShowDoctorsWithoutDescription = $x("//span[text()='Показать']//parent::button");


    public void doctorsPage() {
        tabNameDoctors.shouldBe(Condition.visible, Duration.ofSeconds(5));
        searchDoctors.shouldBe(Condition.visible, Duration.ofSeconds(5));
        dropDownPhoto.shouldBe(Condition.visible, Duration.ofSeconds(5));
        buttonShowDoctorsWithoutDescription.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public CardDoctorPage openCardDoctor() {
        editDoctorInfo.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new CardDoctorPage();
    }

    public void sortingPhotoNo() {
        dropDownPhoto.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        optionNo.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void sortingPhotoYes() {
        dropDownPhoto.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        optionYes.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void sortingPhotoAll() {
        dropDownPhoto.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        optionAll.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void showDoctorCardNoDescription() {
        buttonShowDoctorsWithoutDescription.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }


    public void searchDoctor(String doctorName) {
        searchDoctors.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(doctorName);
    }


}
