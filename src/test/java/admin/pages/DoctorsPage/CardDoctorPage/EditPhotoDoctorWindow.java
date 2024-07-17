package admin.pages.DoctorsPage.CardDoctorPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.io.File;
import java.time.Duration;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class EditPhotoDoctorWindow {
    private final SelenideElement DOCTOR_PHOTO = $x("//div[@class='zzfM']/img");
    private final SelenideElement WINDOW = $x("//span[text()='Редактирование фотографии']//parent::div//parent::div//parent::div[@class='eV2Y']");
    private final SelenideElement HEADER_WINDOW = $x("//span[text()='Редактирование фотографии']");
    private final SelenideElement FILE_INPUT_ELEMENT = $("input[type='file']");
    private final SelenideElement UPLOAD_BUTTON = $x("//span[text()='загрузить']");
    private final SelenideElement CLOSE_WINDOW_BUTTON = $x("//span[text()='Редактирование фотографии']//parent::div//parent::div/parent::*/div[@class='UnAf Ee5G']");

    @Step("Верифицировать окно изменения фотографии")
    public void verifyEditPhotoDoctorWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        HEADER_WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DOCTOR_PHOTO.shouldHave(attribute("src")).shouldNotBe(attribute("src", ""));
        UPLOAD_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Загрузить файл '{0}'")
    public void uploadPhoto(String pathFilesOffer) {
        FILE_INPUT_ELEMENT.uploadFile(new File(pathFilesOffer));

    }

    @Step("Закрыть окно изменения фотографии")
    public void closeWindowEditPhoto() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW.shouldNot(Condition.appear, Duration.ofSeconds(10));
    }

    @Step("Проверить отображение окна изменения фотографии")
    public boolean isWindowAppear() {
        return WINDOW.isDisplayed();
    }

}
