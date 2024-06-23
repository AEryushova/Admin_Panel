package admin.pages.ServicesPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class ServiceCard {

    private final SelenideElement NAME_SERVICE =$x("//div[@class='HsQQ']/span");
    private final SelenideElement SERVICE_BUTTON = $x("//div[@class='j95E']");

    public void serviceCard() {
        NAME_SERVICE.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SERVICE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public String getNameService(){
        NAME_SERVICE.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return NAME_SERVICE.getText();
    }

    public ServiceWindow openServiceInfo(){
        SERVICE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new ServiceWindow();
    }




}
