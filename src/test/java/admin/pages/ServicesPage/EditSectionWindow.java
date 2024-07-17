package admin.pages.ServicesPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class EditSectionWindow {

    public final SelenideElement WINDOW = $x("//input[@name='edit-category-name']/parent::div/parent::div");
    public final SelenideElement NAME_FIELD = $x("//input[@name='edit-category-name']");
    public final SelenideElement SAVE_BUTTON = $x("//input[@name='edit-category-name']/parent::div//following-sibling::div[@class='n6DU']");
    public final SelenideElement CLOSE_WINDOW_BUTTON = $x("//input[@name='edit-category-name']/parent::div//following-sibling::div[@class='V5So']");

    @Step("Верифицировать окно изменения раздела")
    public void verifyEditSectionWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        NAME_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SAVE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Ввести в поле названия '{0}'")
    public void fillNameField(String name) {
        NAME_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(name);
    }

    @Step("Нажать кнопку сохранения")
    public void clickButtonSaveChange() {
        SAVE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Очистить поле названия")
    public void clearNameField(){
        NAME_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .sendKeys(Keys.CONTROL, "a");
        NAME_FIELD.sendKeys(Keys.BACK_SPACE);
    }

    @Step("Закрыть окно изменения раздела")
    public void closeWindowEditSection() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW.shouldNot(Condition.appear, Duration.ofSeconds(10));
    }

    @Step("Проверить отображение окна изменения раздела")
    public boolean isWindowAppear() {
        return WINDOW.isDisplayed();
    }
}
