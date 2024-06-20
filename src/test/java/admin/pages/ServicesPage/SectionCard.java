package admin.pages.ServicesPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class SectionCard {

    private final SelenideElement NAME_SECTION=$x("//div[@class='OurO']/span");
    private final SelenideElement EDIT_SECTION_BUTTON = $x("//div[@class='V5So']");
    private final SelenideElement DELETE_SECTION_BUTTON = $x("//div[@class='mJna']");
    private final SelenideElement RULES_PREPARING_SECTION = $x("//div[@class='tSFL']");
    private final SelenideElement EXPAND_SECTION = $x("//div[@class='xrjl']");
    private final SelenideElement ADD_SUBSECTION_BUTTON = $x("//span[text()='Добавить раздел']//parent::div//parent::button");


    public void section() {
        NAME_SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EDIT_SECTION_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_SECTION_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        RULES_PREPARING_SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EXPAND_SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public EditSectionWindow editSection() {
        EDIT_SECTION_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new EditSectionWindow();
    }

    public DeleteSectionWindow deleteSection() {
        DELETE_SECTION_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new DeleteSectionWindow();
    }

    public RulesPreparingWindow rulesPreparingSection() {
        RULES_PREPARING_SECTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new RulesPreparingWindow();
    }

    public String getNameSection(){
        NAME_SECTION.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return NAME_SECTION.getText();
    }


    public CategoryCard openSection(String sectionName) {
        SelenideElement EXPANDSECTION= searchSection(sectionName).$x("div[@class='xrjl']");
        EXPANDSECTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new CategoryCard();
    }

    private SelenideElement searchSection(String categoryName){
        SelenideElement SECTION =$x("//span[text()='" + categoryName + "']//parent::div//parent::div[@class='K9Fo']");
        SECTION.shouldBe(Condition.visible);
        return SECTION;
    }


    public AddSectionWindow addSubsection() {
        EXPAND_SECTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                        .click();
        ADD_SUBSECTION_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new AddSectionWindow();
    }

}
