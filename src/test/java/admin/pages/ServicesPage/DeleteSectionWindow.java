package admin.pages.ServicesPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$x;

public class DeleteSectionWindow {

    public final SelenideElement WINDOW = $x("//div[@class='sCwo']");
    private final SelenideElement HEADER_WINDOW = $x("//span[text()='Удалить Раздел']");
    private final SelenideElement TEXT_WINDOW = $x("//div[@class='Fujp']/span");
    private final SelenideElement DELETE_BUTTON = $x("//button[text()='Удалить']");
    private final SelenideElement CANCEL_BUTTON = $x("//button[text()='Отменить']");
    private final SelenideElement CLOSE_WINDOW_BUTTON = $x("//span[text()='Удалить Раздел']//parent::div//parent::div/parent::*/div[@class='UnAf Ee5G']");


    public void deleteSectionWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        HEADER_WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TEXT_WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CANCEL_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }


    public void deleteSection(){
        DELETE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void cancelDeleteSection(){
        CANCEL_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public boolean verifyNameSection(String nameSection){
        return TEXT_WINDOW.has(text("Раздел \"" + nameSection + "\" будет безвозвратно удален и все имеющиеся услуги внутри него будут перенесены в категорию \"Иные услуги\". \n" +
                "Заранее убедитесь, что нет услуг которые должны быть отображены клиенту."));
    }

    public void closeWindowDeleteSection() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW.shouldBe(Condition.hidden, Duration.ofSeconds(5));
    }

    public boolean isWindowAppear() {
        return WINDOW.isDisplayed();
    }


}
