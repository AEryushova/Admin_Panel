package admin.pages.DoctorsPage.CardDoctorPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class NavigateMenu {

    private final SelenideElement NAVIGATE_MENU=$x("//div[@class='_odc']");
    private final SelenideElement PHOTO =$x("//span[text()='Фото']/parent::div[@class='sMBP']");
    private final SelenideElement DESCRIPTION=$x("//span[text()='Описание в карточке']/parent::div[@class='sMBP']");
    private final SelenideElement FEEDBACK=$x("//span[text()='Отзывы']/parent::div[@class='sMBP']");


    @Step("Верифицировать навигационное меню")
    public void verifyNavigateMenu() {
        NAVIGATE_MENU.shouldBe(Condition.visible, Duration.ofSeconds(5));
        PHOTO.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DESCRIPTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        FEEDBACK.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Нажать на раздел фотографии врача")
    public void clickTabOpenPhoto(){
        PHOTO.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать на раздел инфорамации о враче")
    public void clickTabOpenDescription(){
        DESCRIPTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать на раздел отзывов о враче")
    public void clickTabOpenFeedback(){
        FEEDBACK.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();

    }

    @Step("Закрыть навигационное меню")
    public void closeNavigateMenu() {
        PHOTO.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        Selenide.actions()
                .moveToElement(NAVIGATE_MENU)
                .moveByOffset(-200, 200)
                .perform();
        PHOTO.shouldNotBe(Condition.visible, Duration.ofSeconds(10));

    }

    @Step("Проверить отображение навигационного меню")
    public boolean isNavigateMenuDisplayed() {
        return PHOTO.isDisplayed();
    }


}
