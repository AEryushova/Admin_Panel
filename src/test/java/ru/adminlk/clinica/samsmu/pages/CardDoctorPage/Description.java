package ru.adminlk.clinica.samsmu.pages.CardDoctorPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class Description {
    private final SelenideElement
            DESCRIPTION = $x("//div[@class='IrCo']"),
            NAME_DESCRIPTION = $x("//div[@class='IrCo']"),
            EDIT_SAVE_BUTTON = $x("//div[@class='J9zY']"),
            FIELD_DESCRIPTION = $x("//input[@class='d7ZA']"),
            DELETE_BUTTON = $x("//div[@class='TRfT']");

    @Step("Верифицировать описание")
    public Description verifyDescription() {
        DESCRIPTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        NAME_DESCRIPTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EDIT_SAVE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return this;
    }

    @Step("Ввести в поле описания '{0}'")
    public Description fillDescriptionField(String description) {
        FIELD_DESCRIPTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(description);
        return this;
    }

    @Step("Нажать на кнопку сохранения")
    public Description clickButtonEditSaveDescription() {
        EDIT_SAVE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return this;
    }

    @Step("Очистить поле описания")
    public Description clearDescriptionField() {
        FIELD_DESCRIPTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .sendKeys(Keys.CONTROL, "a");
        FIELD_DESCRIPTION.sendKeys(Keys.BACK_SPACE);
        return this;
    }

    @Step("Получить текст поля описания")
    public String getTextDescription() {
        NAME_DESCRIPTION.shouldBe(Condition.visible);
        return NAME_DESCRIPTION.getText();
    }

    @Step("Получить поле описания")
    public SelenideElement getDescription() {
        return NAME_DESCRIPTION;
    }

    @Step("Нажать на кнопку удаления")
    public Description clickButtonDeleteDescription() {
        DELETE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return this;
    }

}
