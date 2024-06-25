package admin.pages.ServicesPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class SectionCard {

    private final SelenideElement NAME_SECTION=$x("//div[@class='CtIw' and @draggable='true']/div/div[@class='OurO']/span");
    private final SelenideElement EDIT_SECTION_BUTTON = $x("//div[@class='V5So']");
    private final ElementsCollection CONTAINER_SECTIONS = $$x("//div[@class='OurO']/span");
    private final SelenideElement DELETE_SECTION_BUTTON = $x("//div[@class='mJna']");
    private final SelenideElement RULES_PREPARING_SECTION = $x("//div[@class='CtIw' and @draggable='true']/div/div[@class='tSFL']");
    private final SelenideElement EXPAND_SECTION = $x("//div[@class='CtIw' and @draggable='true']/div/div[@class='xrjl']");
    private final SelenideElement ADD_SUBSECTION_BUTTON = $x("//span[text()='Добавить раздел']//parent::div//parent::button/parent::div[@class='gVuT']");
    private final SelenideElement SUBSECTION = $x("//div[@class='CtIw' and @draggable='true']/div");
    private final SelenideElement EMPTY_LIST_SUBSECTION = $x("//div[@class='b8mg']/span");

    public void sectionCard() {
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


    public String getNameSection(){
        NAME_SECTION.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return NAME_SECTION.getText();
    }


    public void openSection() {
        EXPAND_SECTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public AddSectionWindow addSubsection() {
        ADD_SUBSECTION_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new AddSectionWindow();
    }

    public SubsectionCard getSubsection() {
        SUBSECTION.shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.exist, Duration.ofSeconds(5));
        return new SubsectionCard();
    }

    public int getSubsectionIndexByName(String subsectionName) {
        List<SelenideElement> subsectionElements = CONTAINER_SECTIONS;
        for (int i = 0; i < subsectionElements.size(); i++) {
            if (subsectionElements.get(i).getText().equals(subsectionName)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Section not found: " + subsectionName);
    }

    public void changeDisplaySequence(String sourceName, String targetName) {
        SelenideElement subsectionSource = searchSubsection(sourceName);
        SelenideElement subsectionTarget = searchSubsection(targetName);
        Actions actions = actions();
        actions.clickAndHold(subsectionSource)
                .moveToElement(subsectionTarget)
                .release()
                .perform();
    }

    public SelenideElement searchSubsection(String subsectionName){
        SelenideElement SUBSECTION =$x("//span[text()='" + subsectionName + "']//parent::div//parent::div[@class='K9Fo']");
        SUBSECTION.shouldBe(Condition.visible);
        return SUBSECTION;
    }

    public boolean isExistSubsectionCard(){
        return SUBSECTION.isDisplayed();
    }

    public boolean isExistEmptyList() {
        return EMPTY_LIST_SUBSECTION.isDisplayed();
    }
}
