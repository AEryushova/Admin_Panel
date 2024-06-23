package admin.pages.ServicesPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class CategoryCard {
    private final SelenideElement ADD_SECTION_BUTTON = $x("//span[text()='Добавить раздел']//parent::div//parent::button/parent::div[@class='nwH0']");
    private final SelenideElement SECTION = $x("//div[@class='CtIw' and @draggable='true']/div");
    private final SelenideElement EMPTY_LIST_SECTION = $x("//div[@class='kblo']/span");


    public void categoryCard() {
        ADD_SECTION_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public AddSectionWindow addSection() {
        ADD_SECTION_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new AddSectionWindow();
    }

    public SectionCard getSection() {
        SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.exist, Duration.ofSeconds(5));
        return new SectionCard();
    }


    public boolean isExistSectionCard(){
        return SECTION.isDisplayed();
    }

    public boolean isExistEmptyList() {
        return EMPTY_LIST_SECTION.isDisplayed();
    }

}

