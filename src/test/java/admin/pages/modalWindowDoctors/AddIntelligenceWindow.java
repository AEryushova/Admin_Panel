package admin.pages.modalWindowDoctors;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class AddIntelligenceWindow {

    private final SelenideElement fieldText=$x("//input[@placeholder='Укажите название пункта']");
    private final SelenideElement saveButton=$x("//button[text()='Сохранить']");
    private final SelenideElement cancellationButton=$x("//button[text()='Отмена']");
    private final SelenideElement textWindow=$x("//input[@placeholder='Укажите название пункта']//parent::div//parent::div[@class='ijoD']");


    public void addIntelligenceWindow(){
        fieldText.shouldBe(Condition.visible, Duration.ofSeconds(10));
        saveButton.shouldBe(Condition.visible, Duration.ofSeconds(10));
        cancellationButton.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    public Section addSection(String title){
        fieldText.setValue(title);
        saveButton.click();
        textWindow.shouldBe(Condition.hidden, Duration.ofSeconds(10));
        return new Section();
    }

    public Description addDescription(String description){
        fieldText.setValue(description);
        saveButton.click();
        textWindow.shouldBe(Condition.hidden, Duration.ofSeconds(10));
        return new Description();
    }

    public void fillingFieldSectionDescription(String title){
        fieldText.setValue(title);
    }

    public void clearFieldSectionDescription(){
        fieldText.clear();
    }

    public String getValueSectionDescription(){
        return fieldText.getValue();
    }

    public void saveValueSectionDescription(){
        saveButton.click();
    }

    public void cancellationAddSectionDescription(){
        cancellationButton.click();
        textWindow.shouldBe(Condition.hidden, Duration.ofSeconds(10));

    }

}
