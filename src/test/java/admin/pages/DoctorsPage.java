package admin.pages;

import admin.data.DataTest;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class DoctorsPage extends BasePage {

    private final SelenideElement TAB_NAME = $x("//div[@class='wYqZ']/span[text()='Врачи']");
    private final SelenideElement SEARCH_DOCTOR = $x("//input[@placeholder='Поиск по врачам']");
    private final SelenideElement EDIT_BUTTON = $x("//span[text()='" + DataTest.getDoctorName() + "']/parent::div/following-sibling::button[text()='Редактировать']");
    private final SelenideElement DROP_DOWN_PHOTO = $x("//div[@class='uTSS']");
    private final SelenideElement OPTION_ALL = $x("//div[@class='U2Xk']/div[text()='Все']");
    private final SelenideElement OPTION_NO = $x("//div[@class='U2Xk']/div[text()='Нет']");
    private final SelenideElement OPTION_YES = $x("//div[@class='U2Xk']/div[text()='Есть']");
    private final SelenideElement SHOW_DOCTOR_WITHOUT_DESCRIPTION = $x("//span[text()='Показать']//parent::button");


    public void doctorsPage() {
        TAB_NAME.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SEARCH_DOCTOR.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DROP_DOWN_PHOTO.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SHOW_DOCTOR_WITHOUT_DESCRIPTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public CardDoctorPage openCardDoctor() {
        EDIT_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new CardDoctorPage();
    }

    public void sortingPhotoNo() {
        DROP_DOWN_PHOTO.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        OPTION_NO.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void sortingPhotoYes() {
        DROP_DOWN_PHOTO.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        OPTION_YES.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void sortingPhotoAll() {
        DROP_DOWN_PHOTO.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        OPTION_ALL.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void showDoctorCardNoDescription() {
        SHOW_DOCTOR_WITHOUT_DESCRIPTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }


    public void searchDoctor(String doctorName) {
        SEARCH_DOCTOR.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(doctorName);
    }


}
