package admin.pages.modalWindowDoctors;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.io.File;
import java.time.Duration;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class EditPhotoDoctorWindow {
    private final SelenideElement doctorPhoto=$x("//div[@class='zzfM']/img");
    private final SelenideElement windowUploadPhoto=$x("//span[text()='Редактирование фотографии']//parent::div//parent::div//parent::div[@class='eV2Y']");
    private final SelenideElement headerWindow = $x("//span[text()='Редактирование фотографии']");
    private final SelenideElement fileInputElement = $("input[type='file']");
    private final SelenideElement uploadPhotoButton = $x("//span[text()='загрузить']");
    private final SelenideElement closeWindowButton=$x("//span[text()='Редактирование фотографии']//parent::div//parent::div/parent::*/div[@class='UnAf Ee5G']");


    public void editPhotoDoctorWindow(){
        windowUploadPhoto.shouldBe(Condition.visible, Duration.ofSeconds(10));
        headerWindow.shouldBe(Condition.visible, Duration.ofSeconds(10));
        doctorPhoto.shouldHave(attribute("src")).shouldNotBe(attribute("src", ""));
        uploadPhotoButton .shouldBe(Condition.visible, Duration.ofSeconds(10));
        closeWindowButton.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    public void uploadValidPhoto(String pathFilesOffer) {
        fileInputElement.uploadFile(new File(pathFilesOffer));
        windowUploadPhoto.shouldBe(Condition.hidden, Duration.ofSeconds(5));
    }

    public void uploadInvalidPhoto(String pathFilesOffer) {
        fileInputElement.uploadFile(new File(pathFilesOffer));
        windowUploadPhoto.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void closeWindowEditPhoto(){
        closeWindowButton.click();
        windowUploadPhoto.shouldBe(Condition.hidden, Duration.ofSeconds(5));
    }






}
