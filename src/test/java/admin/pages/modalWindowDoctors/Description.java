package admin.pages.modalWindowDoctors;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class Description {
    private final SelenideElement description=$x("//div[@class='IrCo'][last()]");
    private final SelenideElement descriptionName=$x("//div[@class='IrCo'][last()]/span");
    private final SelenideElement editDescription=$x("//div[@class='J9zY'][last()]");
    private final SelenideElement fieldDescription=$x("//div[@class='aksW']/input");
    private final SelenideElement deleteDescription=$x("//div[@class='TRfT'][last()]");



    public void description(){
        description.shouldBe(Condition.visible, Duration.ofSeconds(10));
        editDescription.shouldBe(Condition.visible, Duration.ofSeconds(10));
        deleteDescription .shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    public void editDescription(String title){
        editDescription.click();
        fieldDescription.shouldBe(Condition.visible, Duration.ofSeconds(10));
        fieldDescription.setValue(title);
    }

    public String getDescriptionName(){
        descriptionName.shouldBe(Condition.visible, Duration.ofSeconds(10));
        return descriptionName.getText();
    }

    public void deleteDescription(){
        deleteDescription.click();
        description.shouldBe(Condition.hidden, Duration.ofSeconds(10));
    }

}
