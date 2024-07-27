package admin.pages.DoctorsPage.CardDoctorPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class AddInfoDoctorWindow {

    private final SelenideElement TEXT_FIELD = $x("//input[@placeholder='Укажите название пункта']");
    private final SelenideElement SAVE_BUTTON = $x("//button[text()='Сохранить']");
    private final SelenideElement CANCEL_BUTTON = $x("//button[text()='Отмена']");
    private final SelenideElement WINDOW_SECTION = $x("//input[@placeholder='Укажите название пункта']//parent::div//parent::div[@class='ijoD']");
    private final SelenideElement WINDOW_DESCRIPTION = $x("//input[@placeholder='Укажите название пункта']//parent::div//parent::div[@class='S8Lv']");


    @Step("Верифицировать окно добавления информации о враче")
    public void verifyAddSectionDoctorWindow() {
        WINDOW_SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TEXT_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SAVE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CANCEL_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Верифицировать окно добавления информации о враче")
    public void verifyAddDescriptionDoctorWindow() {
        WINDOW_DESCRIPTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TEXT_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SAVE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CANCEL_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Ввести в поле названия пункта '{0}'")
    public void fillFieldText(String title) {
        TEXT_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(title);
    }

    @Step("Получить значение поля названия пункта")
    public String getValueField() {
        TEXT_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return TEXT_FIELD.getValue();
    }

    @Step("Нажать на кнопку сохранения")
    public void clickButtonSaveValue() {
        SAVE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать на кнопку отмены добавления раздела")
    public void clickCancelButtonAddSectionDoctor() {
        CANCEL_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW_SECTION.shouldNotBe(Condition.visible, Duration.ofSeconds(10));
    }

    @Step("Нажать на кнопку отмены добавления описания в раздел")
    public void clickCancelButtonAddDescriptionDoctor() {
        CANCEL_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW_DESCRIPTION.shouldNotBe(Condition.visible, Duration.ofSeconds(10));
    }

    @Step("Проверить отображение окна добавления раздела в инфо о враче")
    public boolean isWindowAppearSection() {return WINDOW_SECTION.isDisplayed();
    }

    @Step("Проверить отображение окна добавления описания в инфо о враче")
    public boolean isWindowAppearDescription() {return WINDOW_DESCRIPTION.isDisplayed();
    }


}
