package admin.pages.modalWindowServices;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class AddSectionWindow {

    public final SelenideElement windowAddSection = $x("//span[text()='Новый Вопрос']//parent::div//parent::div//parent::div[@class='eV2Y']");
    private final SelenideElement headerWindow = $x("//span[text()='Новый Вопрос']");
    private final SelenideElement nameSectionField = $x("");
    private final SelenideElement addButton=$x("//button[text()='Добавить']");
    private final SelenideElement cancellationButton=$x("//button[text()='Отменить']");
    private final SelenideElement closeWindowButton = $x("//span[text()='Новый Вопрос']//parent::div//parent::div/parent::*/div[@class='UnAf Ee5G']");


    public void addSectionWindow(){
        windowAddSection.shouldBe(Condition.visible, Duration.ofSeconds(5));
        headerWindow.shouldBe(Condition.visible, Duration.ofSeconds(5));
        nameSectionField.shouldBe(Condition.visible, Duration.ofSeconds(5));
        cancellationButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        addButton.shouldBe(Condition.disabled);
        closeWindowButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void fillingNameSectionField(String name){
        nameSectionField.setValue(name);
    }

    public void addSection(){
        addButton.shouldBe(Condition.enabled);
        addButton.click();
        windowAddSection.shouldBe(Condition.hidden, Duration.ofSeconds(5));
    }

    public void cancellationAddSection(){
        cancellationButton.click();
        windowAddSection.shouldBe(Condition.hidden, Duration.ofSeconds(5));
    }

    public void clearNameSectionField() {
        nameSectionField.clear();
    }

    public void closeWindowAddSection() {
        closeWindowButton.click();
        windowAddSection.shouldBe(Condition.hidden, Duration.ofSeconds(5));
    }
}
