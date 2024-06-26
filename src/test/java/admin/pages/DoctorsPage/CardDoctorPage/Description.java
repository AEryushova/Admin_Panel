package admin.pages.DoctorsPage.CardDoctorPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class Description {
    private final SelenideElement DESCRIPTION = $x("//div[@class='IrCo']");
    private final SelenideElement NAME_DESCRIPTION = $x("//div[@class='IrCo']");
    private final SelenideElement EDIT_SAVE_BUTTON = $x("//div[@class='J9zY']");
    private final SelenideElement FIELD_DESCRIPTION = $x("//input[@class='d7ZA']");
    private final SelenideElement DELETE_BUTTON = $x("//div[@class='TRfT']");

    @Step("Верифицировать описание")
    public void description() {
        DESCRIPTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        NAME_DESCRIPTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EDIT_SAVE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Ввести в поле названия пункта '{0}'")
    public void fillDescriptionField(String description){
        FIELD_DESCRIPTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(description);
    }

    @Step("Нажать на кнопку сохранения")
    public void editSaveDescription() {
        EDIT_SAVE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Очистить поле названия пункта")
    public void clearDescriptionField(){
        FIELD_DESCRIPTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .sendKeys(Keys.CONTROL, "a");
        FIELD_DESCRIPTION.sendKeys(Keys.BACK_SPACE);
    }

    @Step("Получить текст поля названия пункта описания")
    public String getDescription() {
        NAME_DESCRIPTION.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return NAME_DESCRIPTION.getText();
    }

    @Step("Нажать на кнопку удаления")
    public void deleteDescription() {
        DELETE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

}
