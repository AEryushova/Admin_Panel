package admin.pages.modalWindowAdministration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import com.codeborne.selenide.SelenideElement;
import admin.pages.calendar.Calendar;
import admin.utils.DataHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.files.FileFilters.withExtension;

public class UpdatePriceWindow {

    private final SelenideElement windowUpdatePrice = $x("//span[text()='Обновить прайс']//parent::div//parent::div//parent::div[@class='eV2Y']");
    private final SelenideElement headerWindow = $x("//div[@id='popap_window']/div/div/div/div/div/span[text()='Обновить прайс']");
    private final SelenideElement activationDatesList = $x("//span[text()='Даты активации']//parent::div");
    private final SelenideElement activationDateDownload;
    private final SelenideElement fileInputElement = $("input[type='file']");
    private final SelenideElement uploadPriceButton = $x("//span[text()='Загрузить']");
    private final SelenideElement closeWindowButton = $x("//span[text()='Обновить прайс']//parent::div//parent::div/parent::*/div[@class='UnAf Ee5G']");
    private final SelenideElement calendarButtonToday = $x("//div[@class='field__c8da container__ce0e']");


    public UpdatePriceWindow() {
        this.activationDateDownload = $x("//span[text()='" + DataHelper.generateActivationDateCurrentMonth() + "']/parent::div");
    }

    public void updatePriceWindow() {
        windowUpdatePrice.shouldBe(Condition.visible, Duration.ofSeconds(5));
        headerWindow.shouldBe(Condition.visible, Duration.ofSeconds(5));
        activationDatesList.shouldBe(Condition.visible, Duration.ofSeconds(5));
        calendarButtonToday.shouldBe(Condition.visible, Duration.ofSeconds(5));
        uploadPriceButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        closeWindowButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public Calendar openCalendarUpdatePrice() {
        calendarButtonToday.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new Calendar();
    }

    public void uploadPrice(String pathFilesPrice) {
        fileInputElement.uploadFile(new File(pathFilesPrice));
    }

    public PriceErrorsWindow openPriceErrorsWindow() {
        return new PriceErrorsWindow();
    }

    public String getValuesButtonToday() {
        calendarButtonToday.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return calendarButtonToday.getText();
    }

    public File downloadPriceDateActivation() {
        activationDatesList.click();
        activationDateDownload.shouldBe(Condition.visible, Duration.ofSeconds(5));
        Configuration.fileDownload = FileDownloadMode.FOLDER;
        File downloadedFile;
        try {
            downloadedFile = activationDateDownload.download(withExtension("xlsx"));
            sleep(5000);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return downloadedFile;
    }

    public void closeWindowUpdatePrice() {
        closeWindowButton.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public boolean isWindowAppear() {
        return windowUpdatePrice.exists();
    }
}
