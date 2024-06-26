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
    public void addInfoDoctorWindow() {
        WINDOW_SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
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
                .shouldBe(Condition.exist);
        return TEXT_FIELD.getValue();
    }

    @Step("Нажать на кнопку сохранения")
    public void saveValue() {
        SAVE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать на кнопку отмены")
    public void cancelAddInfoDoctor() {
        CANCEL_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Проверить отображение окна добавления информации о враче")
    public boolean isWindowAppear() {
        return WINDOW_SECTION.isDisplayed();
    }


}
