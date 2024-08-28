package pages.DoctorsPage.CardDoctorPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class Section {
    private final SelenideElement SECTION = $x("//div[@class='aksW']"),
            NAME_SECTION = $x("//div[@class='aksW']/span"),
            EDIT_SAVE_BUTTON = $x("//div[@class='wmqb']"),
            FIELD_TITLE = $x("//input[@class='yxN5']"),
            DELETE_BUTTON = $x("//div[@class='UQ5Z']"),
            ADD_DESCRIPTION_BUTTON = $x("//div[@class='EUkX']");

    @Step("Верифицировать раздел")
    public Section verifySection() {
        SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        NAME_SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EDIT_SAVE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADD_DESCRIPTION_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return this;
    }

    @Step("Ввести в поле названия раздела '{0}'")
    public Section fillTitleField(String title) {
        FIELD_TITLE.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(title);
        return this;
    }

    @Step("Нажать на кнопку сохранения")
    public Section clickButtonEditSaveTitle() {
        EDIT_SAVE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return this;
    }

    @Step("Очистить поле названия раздела")
    public Section clearTitleField() {
        FIELD_TITLE.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .sendKeys(Keys.CONTROL, "a");
        FIELD_TITLE.sendKeys(Keys.BACK_SPACE);
        return this;
    }

    @Step("Получить текст поля названия раздела")
    public String getTextSection() {
        NAME_SECTION.shouldBe(Condition.visible);
        return NAME_SECTION.getText();
    }

    @Step("Получить поле названия раздела")
    public SelenideElement getSection() {
        return NAME_SECTION;
    }

    @Step("Нажать на кнопку удаления")
    public Section clickButtonDeleteTitle() {
        DELETE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return this;
    }

    @Step("Нажать на кнопку добавления описания")
    public AddInfoDoctorWindow clickButtonAddDescription() {
        ADD_DESCRIPTION_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new AddInfoDoctorWindow();
    }

}
