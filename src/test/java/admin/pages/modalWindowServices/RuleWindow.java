package admin.pages.modalWindowServices;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class RuleWindow {

    public final SelenideElement windowRules = $x("//div[@class='TW3C']");
    public final SelenideElement headerField = $x("//input[@placeholder='Укажите заголовок правила']");
    public final SelenideElement descriptionField = $x("//textarea[@placeholder='Укажите описание правила']");
    public final SelenideElement editButton = $x("//button[text()='Изменить']");
    private final SelenideElement deleteButton = $x("//button[text()='Удалить']");
    private final SelenideElement closeWindowButton = $x("//div[@class='TW3C']/preceding-sibling::div[@class='UnAf Ee5G']");


    public void ruleWindow() {
        windowRules.shouldBe(Condition.visible, Duration.ofSeconds(5));
        headerField.shouldBe(Condition.visible, Duration.ofSeconds(5));
        descriptionField .shouldBe(Condition.visible, Duration.ofSeconds(5));
        editButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        deleteButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        closeWindowButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void fillingFieldHeader(String header) {
        headerField.setValue(header);
    }

    public void fillingFieldDescription(String description) {
        descriptionField.setValue(description);
    }

    public void clearFieldHeader() {
        headerField.clear();
    }

    public void clearFieldDescription() {
        descriptionField.clear();
    }

    public void changeRules(){
        editButton.click();
    }

    public void deleteRules() {
        deleteButton.click();
    }

    public void closeWindowRule() {
        closeWindowButton.click();
        windowRules.shouldBe(Condition.hidden, Duration.ofSeconds(5));
    }

}
