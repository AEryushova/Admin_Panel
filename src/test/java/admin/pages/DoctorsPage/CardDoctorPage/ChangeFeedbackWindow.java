package admin.pages.DoctorsPage.CardDoctorPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class ChangeFeedbackWindow {

    private final SelenideElement WINDOW = $x("//div[@class='qJe_ OR_i']");
    private final SelenideElement TEXT_FIELD = $x("//div[@class='zxOH']/textarea");
    private final SelenideElement SAVE_BUTTON = $x("//button[text()='Сохранить']");
    private final SelenideElement CLOSE_WINDOW_BUTTON = $x("//div[@class='UnAf gvNC']");


    public void changeFeedbackWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TEXT_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SAVE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void fillFieldText(String text) {
        TEXT_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(text);
    }

    public void saveChanges() {
        SAVE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public String getValueTextFeedbackField() {
        TEXT_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return TEXT_FIELD.getValue();
    }

    public void clearTextField(){
        TEXT_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .sendKeys(Keys.CONTROL, "a");
        TEXT_FIELD.sendKeys(Keys.BACK_SPACE);
    }

    public boolean isEnabledSaveButton(){
        return SAVE_BUTTON.isEnabled();
    }

    public void closeWindowChangeFeedback() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW.shouldBe(Condition.hidden, Duration.ofSeconds(5));
    }

    public boolean isWindowAppear() {
        return WINDOW.isDisplayed();
    }


}
