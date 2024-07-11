package admin.pages.ServicesPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class AddRuleWindow {
    public final SelenideElement WINDOW = $x("//span[text()='вернуться назад']/parent::div/parent::div/parent::div/parent::div");
    public final SelenideElement RETURN_BUTTON = $x("//span[text()='вернуться назад']");
    public final SelenideElement TITLE_FIELD = $x("//input[@placeholder='Укажите заголовок правила']");
    public final SelenideElement DESCRIPTION_FIELD = $x("//textarea[@placeholder='Укажите описание правила']");
    public final SelenideElement SAVE_BUTTON = $x("//button[text()='Сохранить']");
    private final SelenideElement CLOSE_WINDOW_BUTTON = $x("//span[text()='вернуться назад']/parent::div/parent::div/parent::div/parent::div/preceding-sibling::div[@class='UnAf Ee5G']");

    @Step("Верифицировать окно добавления нового правила")
    public void verifyAddRuleWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        RETURN_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TITLE_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DESCRIPTION_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SAVE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Ввести в поле заголовка '{0}'")
    public void fillFieldTitle(String header) {
        TITLE_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(header);
    }

    @Step("Ввести в поле описания '{0}'")
    public void fillFieldDescription(String description) {
        DESCRIPTION_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(description);
    }

    @Step("Нажать кнопку сохранения")
    public void clickSaveButton() {
        SAVE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Получить значение поля заголовка")
    public String getValueTitleField() {
        TITLE_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return TITLE_FIELD.getValue();
    }

    @Step("Получить значение поля описания")
    public String getValueDescriptionField() {
        DESCRIPTION_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return DESCRIPTION_FIELD.getValue();
    }

    @Step("Закрыть окно добавления нового правила")
    public void closeWindowAddRule() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать кнопку возвращения назад")
    public void clickButtonReturnRulesList() {
        RETURN_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Проверить отображение окна добавления нового правила")
    public boolean isWindowAppear() {
        return WINDOW.isDisplayed();
    }

}
