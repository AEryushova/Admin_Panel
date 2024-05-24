package admin.pages.modalWindowDoctors;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class ChangeFeedbackWindow {

    private final SelenideElement windowChangeFeedback=$x("//div[@class='qJe_ OR_i']");
    private final SelenideElement fieldText =$x("//textarea");
    private final SelenideElement saveChanges =$x("//button[text()='Сохранить']");
    private final SelenideElement closeWindowButton=$x("//div[@class='UnAf gvNC']");
    private final SelenideElement closeWindowButton1=$x("//textarea[text()='Так себе врач']//parent::div//parent::div/following-sibling::div/following-sibling::div");

    public void changeFeedbackWindow(){
        windowChangeFeedback.shouldBe(Condition.visible, Duration.ofSeconds(10));
        fieldText.shouldBe(Condition.visible, Duration.ofSeconds(10));
        saveChanges.shouldBe(Condition.visible, Duration.ofSeconds(10));
        closeWindowButton.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    public void clearFieldText(){
        fieldText.clear();
    }

    public void fillingFieldText(String text){
        fieldText.setValue(text);
    }

    public void saveChanges(){
        saveChanges.click();
windowChangeFeedback.shouldBe(Condition.hidden, Duration.ofSeconds(10));
    }

    public void closeWindowButton(){
        closeWindowButton.click();
    }





}
