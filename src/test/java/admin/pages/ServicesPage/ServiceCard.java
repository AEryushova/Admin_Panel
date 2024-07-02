package admin.pages.ServicesPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class ServiceCard {

    private final SelenideElement NAME_SERVICE =$x("//div[@class='HsQQ']/span");
    private final SelenideElement CODE_SERVICE =$x("//div[@class='j95E']//following-sibling::span");
    private final SelenideElement SERVICE_BUTTON = $x("//div[@class='j95E']");

    @Step("Верифицировать карточку услуг")
    public void serviceCard() {
        NAME_SERVICE.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SERVICE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Получить имя услуги")
    public String getNameService(){
        NAME_SERVICE.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return NAME_SERVICE.getText();
    }

    @Step("Получить код услуги")
    public String getCodeService(){
        CODE_SERVICE.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return CODE_SERVICE.getText();
    }

    @Step("Нажать на кнопку открытия информации об услуге")
    public ServiceWindow openServiceInfo(){
        SERVICE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new ServiceWindow();
    }

}
