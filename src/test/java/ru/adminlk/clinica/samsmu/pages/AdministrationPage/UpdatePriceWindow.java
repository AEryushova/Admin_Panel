package ru.adminlk.clinica.samsmu.pages.AdministrationPage;

import com.codeborne.selenide.*;
import ru.adminlk.clinica.samsmu.pages.Calendar.Calendar;
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
            FILE_INPUT_ELEMENT = $("input[type='file']"),
            UPLOAD_BUTTON = $x("//span[text()='Загрузить']"),
            CLOSE_WINDOW_BUTTON = $x("//span[text()='Обновить прайс']//parent::div//parent::div/parent::*/div[@class='UnAf Ee5G']"),
            CALENDAR_BUTTON = $x("//div[@class='zMyf']");
    private final ElementsCollection ACTIVATION_DATES_DOWNLOAD = $$x("//div[@class='wDKS']/span");


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
    @Step("Нажать на кнопку раскрытия списка с датами активации")
    public void openActivationList() {
        ACTIVATIONS_DATES_LIST.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @SuppressWarnings("unused")
    @Step("Нажать на дату '{0}' в списке дат активации")
    public File clickDateActivationInList(String date) {
        SelenideElement dateDownload = getDate(date);
        dateDownload.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        Configuration.fileDownload = FileDownloadMode.FOLDER;
        File downloadedFile;
        try {
            downloadedFile = dateDownload.download(withExtension("xlsx"));
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

    @Step("Получить дату '{0}' из списка дат активации")
    private SelenideElement getDate(String date) {
        for (SelenideElement element : ACTIVATION_DATES_DOWNLOAD) {
            if (element != null && element.getText().equals(date)) {
                return element;
            }
        }
        throw new IllegalArgumentException("Date not found: " + date);
    }
}
