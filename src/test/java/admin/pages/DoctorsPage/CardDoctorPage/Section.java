package admin.pages.DoctorsPage.CardDoctorPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class Section {
    private final SelenideElement SECTION = $x("//div[@class='aksW']");
    private final SelenideElement NAME_SECTION = $x("//div[@class='aksW']/span");
    private final SelenideElement EDIT_SAVE_BUTTON = $x("//div[@class='wmqb']");
    private final SelenideElement FIELD_TITLE=$x("//input[@class='yxN5']");
    private final SelenideElement DELETE_BUTTON = $x("//div[@class='UQ5Z']");
    private final SelenideElement ADD_DESCRIPTION_BUTTON = $x("//div[@class='EUkX']");

    @Step("Верифицировать раздел")
    public void verifySection() {
        SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        NAME_SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EDIT_SAVE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADD_DESCRIPTION_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Ввести в поле названия раздела '{0}'")
    public void fillTitleField(String title){
        FIELD_TITLE.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(title);
    }

    @Step("Нажать на кнопку сохранения")
    public void clickButtonEditSaveTitle() {
        EDIT_SAVE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Очистить поле названия раздела")
    public void clearTitleField(){
        FIELD_TITLE.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .sendKeys(Keys.CONTROL, "a");
        FIELD_TITLE.sendKeys(Keys.BACK_SPACE);
    }

    @Step("Получить текст поля названия раздела")
    public String getTextSection() {
        NAME_SECTION.shouldBe(Condition.visible);
        return NAME_SECTION.getText();
    }

    @Step("Получить поле названия раздела")
    public SelenideElement getSection() {
        return FIELD_TITLE;
    }

    @Step("Нажать на кнопку удаления")
    public void clickButtonDeleteTitle() {
        DELETE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать на кнопку добавления описания")
    public AddInfoDoctorWindow clickButtonAddDescription() {
        ADD_DESCRIPTION_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new AddInfoDoctorWindow();
    }

}
