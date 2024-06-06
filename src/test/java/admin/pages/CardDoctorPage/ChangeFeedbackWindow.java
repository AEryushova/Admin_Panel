package admin.pages.CardDoctorPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class ChangeFeedbackWindow {

    private final SelenideElement WINDOW = $x("//div[@class='qJe_ OR_i']");
    private final SelenideElement TEXT_FIELD = $x("//textarea");
    private final SelenideElement SAVE_BUTTON = $x("//button[text()='Сохранить']");
    private final SelenideElement CLOSE_WINDOW_BUTTON = $x("//div[@class='UnAf gvNC']");
    private final SelenideElement closeWindowButton1 = $x("//textarea[text()='Так себе врач']//parent::div//parent::div/following-sibling::div/following-sibling::div");

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

    public void closeWindowChangeFeedback() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public boolean isWindowAppear() {
        return WINDOW.exists();
    }


}
