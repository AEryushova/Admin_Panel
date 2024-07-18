package admin.pages.ServicesPage;

import com.codeborne.selenide.Condition;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;


import java.time.Duration;


import static com.codeborne.selenide.Selenide.*;

public class ServiceCard {

    private final SelenideElement NAME_SERVICE =$x("//div[@class='HsQQ']/span");
    private final SelenideElement CODE_SERVICE =$x("//div[@class='j95E']//following-sibling::span");
    private final SelenideElement SERVICE_BUTTON = $x("//div[@class='j95E']");

    @Step("Верифицировать карточку услуг")
    public void verifyServiceCard() {
        NAME_SERVICE.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SERVICE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Получить имя услуги")
    public String getNameService(){
        NAME_SERVICE.shouldBe(Condition.visible);
        return NAME_SERVICE.getText();
    }

    @Step("Получить код услуги")
    public String getCodeService(){
        CODE_SERVICE.shouldBe(Condition.visible);
        return CODE_SERVICE.getText();
    }

    @Step("Нажать на кнопку открытия информации об услуге")
    public ServiceWindow clickButtonOpenServiceInfo(){
        SERVICE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new ServiceWindow();
    }

}
