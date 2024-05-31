package admin.pages;

import admin.pages.CardDoctorPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class DoctorsPage extends BasePage {

    private final SelenideElement tabNameDoctors=$x("//div[@class='wYqZ']/span[text()='Врачи']");
    private final SelenideElement searchDoctors= $x("//input[@placeholder='Поиск по врачам']");
    private final SelenideElement editDoctorInfo = $x("//span[text()='Fer3 Копия Тест']/parent::div/following-sibling::button[text()='Редактировать']");
    private final SelenideElement dropDownPhoto = $x("//div[@class='uTSS']");
    private final SelenideElement optionAll=$x("//div[@class='U2Xk']/div[text()='Все']");
    private final SelenideElement optionNo=$x("//div[@class='U2Xk']/div[text()='Нет']");
    private final SelenideElement optionYes=$x("//div[@class='U2Xk']/div[text()='Есть']");
    private final SelenideElement buttonShowDoctorsWithoutDescription=$x("//span[text()='Показать']//parent::button");


    public void doctorsPage() {
        tabNameDoctors.shouldBe(Condition.visible, Duration.ofSeconds(10));
        searchDoctors.shouldBe(Condition.visible, Duration.ofSeconds(10));
        dropDownPhoto.shouldBe(Condition.visible, Duration.ofSeconds(10));
        buttonShowDoctorsWithoutDescription.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    public CardDoctorPage openCardDoctor(){
        editDoctorInfo.click();
        return new CardDoctorPage();
    }

    public void sortingPhotoNo(){
        dropDownPhoto.click();
        optionNo.click();
    }

    public void sortingPhotoYes(){
        dropDownPhoto.click();
        optionYes.click();
    }

    public void sortingPhotoAll(){
        dropDownPhoto.click();
        optionAll.click();
    }

    public void showDoctorCardNoDescription() {
        buttonShowDoctorsWithoutDescription.click();
    }


    public void searchDoctor(String doctorName) {
        searchDoctors.setValue(doctorName);
    }


}
