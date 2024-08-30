package pages.AdministrationPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import com.codeborne.selenide.SelenideElement;
import pages.Calendar.Calendar;
import utils.otherUtils.TestHelper;
import io.qameta.allure.Step;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.files.FileFilters.withExtension;

public class UpdatePriceWindow {

    private final SelenideElement
            WINDOW = $x("//span[text()='Обновить прайс']//parent::div//parent::div//parent::div[@class='eV2Y']"),
            HEADER_WINDOW = $x("//div[@id='popap_window']/div/div/div/div/div/span[text()='Обновить прайс']"),
            ACTIVATIONS_DATES_LIST = $x("//span[text()='Даты активации']//parent::div"),
            ACTIVATION_DATES_DOWNLOAD,
            FILE_INPUT_ELEMENT = $("input[type='file']"),
            UPLOAD_BUTTON = $x("//span[text()='Загрузить']"),
            CLOSE_WINDOW_BUTTON = $x("//span[text()='Обновить прайс']//parent::div//parent::div/parent::*/div[@class='UnAf Ee5G']"),
            CALENDAR_BUTTON = $x("//div[@class='zMyf']");


    public UpdatePriceWindow() {
        this.ACTIVATION_DATES_DOWNLOAD = $x("//span[text()='" + TestHelper.getActivationDateCurrentMonth() + "']/parent::div");
    }

    @Step("Верифицировать окно добавления прайса")
    public UpdatePriceWindow verifyUpdatePriceWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        HEADER_WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ACTIVATIONS_DATES_LIST.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CALENDAR_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        UPLOAD_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return this;
    }

    @Step("Открыть календарь")
    public Calendar openCalendarUpdatePrice() {
        CALENDAR_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new Calendar();
    }

    @Step("Загрузить файл '{0}'")
    public void uploadPrice(String pathFilesPrice) {
        FILE_INPUT_ELEMENT.shouldBe(Condition.exist)
                .uploadFile(new File(pathFilesPrice));
    }

    @Step("Открыть окно ошибок прайса")
    public PriceErrorsWindow priceErrorsWindow() {
        return new PriceErrorsWindow();
    }

    @Step("Получить текущую дату с календаря")
    public String getValuesButtonToday() {
        CALENDAR_BUTTON.shouldBe(Condition.visible);
        return CALENDAR_BUTTON.getText();
    }

    @SuppressWarnings("unused")
    public File downloadPriceDateActivation() {
        ACTIVATIONS_DATES_LIST.click();
        ACTIVATION_DATES_DOWNLOAD.shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.enabled);
        Configuration.fileDownload = FileDownloadMode.FOLDER;
        File downloadedFile;
        try {
            downloadedFile = ACTIVATION_DATES_DOWNLOAD.download(withExtension("xlsx"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return downloadedFile;
    }

    @Step("Закрыть окно добавления прайса")
    public void closeWindowUpdatePrice() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW.shouldNotBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Step("Проверить отображение окна добавления прайса")
    public boolean isWindowAppear() {
        return WINDOW.isDisplayed();
    }
}
