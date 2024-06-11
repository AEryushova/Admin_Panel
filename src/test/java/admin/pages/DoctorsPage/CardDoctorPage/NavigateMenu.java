package admin.pages.DoctorsPage.CardDoctorPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class NavigateMenu {

    private final SelenideElement NAVIGATE_MENU=$x("//div[@class='_odc']");
    private final SelenideElement FOTO=$x("//span[text()='Фото']/parent::div[@class='sMBP']");
    private final SelenideElement DESCRIPTION=$x("//span[text()='Описание в карточке']/parent::div[@class='sMBP']");
    private final SelenideElement FEEDBACK=$x("//span[text()='Отзывы']/parent::div[@class='sMBP']");


    public void navigateMenu() {
        NAVIGATE_MENU.shouldBe(Condition.visible, Duration.ofSeconds(5));
        FOTO.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DESCRIPTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        FEEDBACK.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }


    public void openFoto(){
        FOTO.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void openDescription(){
        DESCRIPTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void openFeedback(){
        FEEDBACK.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void closeNavigateMenu() {
        NAVIGATE_MENU.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .hover();
    }




}
