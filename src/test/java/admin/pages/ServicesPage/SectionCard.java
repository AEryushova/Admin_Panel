package admin.pages.ServicesPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class SectionCard {

    private final SelenideElement NAME_SECTION=$x("//div[@class='CtIw' and @draggable='true']/div/div[@class='OurO']/span");
    private final SelenideElement EDIT_SECTION_BUTTON = $x("//div[@class='V5So']");
    private final ElementsCollection CONTAINER_SUBSECTIONS = $$x("//div[@class='OurO']/span");
    private final SelenideElement DELETE_SECTION_BUTTON = $x("//div[@class='mJna']");
    private final SelenideElement RULES_PREPARING_SECTION = $x("//div[@class='CtIw' and @draggable='true']/div/div[@class='tSFL']");
    private final SelenideElement EXPAND_SECTION = $x("//div[@class='CtIw' and @draggable='true']/div/div[@class='xrjl']");
    private final SelenideElement ADD_SUBSECTION_BUTTON = $x("//span[text()='Добавить раздел']//parent::div//parent::button/parent::div[@class='gVuT']");
    private final SelenideElement SUBSECTION = $x("//div[@class='CtIw' and @draggable='true']/div");
    private final SelenideElement EMPTY_LIST_SUBSECTION = $x("//div[@class='b8mg']/span");
    private final ElementsCollection ruleElements = $$x("//selector_for_rule_elements");

    @Step("Верифицировать карточку раздела")
    public void verifySectionCard() {
        NAME_SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EDIT_SECTION_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_SECTION_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        RULES_PREPARING_SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EXPAND_SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Нажать кнопку редактирования раздела")
    public EditSectionWindow clickButtonEditSection() {
        EDIT_SECTION_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new EditSectionWindow();
    }

    @Step("Нажать кнопку удаления раздела")
    public DeleteSectionWindow clickButtonDeleteSection() {
        DELETE_SECTION_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new DeleteSectionWindow();
    }

    @Step("Получить название раздела")
    public String getNameSection(){
        NAME_SECTION.shouldBe(Condition.visible);
        return NAME_SECTION.getText();
    }

    @Step("Нажать кнопку раскрытия раздела")
    public void clickButtonOpenSection() {
        EXPAND_SECTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать кнопку сворачивания раздела")
    public void clickButtonCloseSection(String nameSection) {
        SelenideElement SECTION=$x("//span[text()='" + nameSection + "']/parent::div/following-sibling::div[@class='xrjl']");
        SECTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать кнопку добавления подраздела")
    public AddSectionWindow clickButtonAddSubsection() {
        ADD_SUBSECTION_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new AddSectionWindow();
    }

    @Step("Нажать кнопку открытия правил подготовки к разделу")
    public RulesPreparingWindow clickButtonOpenRulesPreparingSection(){
        RULES_PREPARING_SECTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new RulesPreparingWindow();
    }

    @Step("Получить подраздел")
    public SubsectionCard getSubsection() {
        SUBSECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return new SubsectionCard();
    }

    public SelenideElement getSubsectionByName(String title) {
        return ruleElements.findBy(Condition.text(title));
    }

    public ElementsCollection getSubsections() {
        return CONTAINER_SUBSECTIONS;
    }

    @Step("Получить индекс подраздела по названию '{0}'")
    public int getSubsectionIndexByName(String subsectionName) {
        List<SelenideElement> subsectionElements = CONTAINER_SUBSECTIONS;
        for (int i = 0; i < subsectionElements.size(); i++) {
            if (subsectionElements.get(i).getText().equals(subsectionName)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Section not found: " + subsectionName);
    }

    @Step("Поменять местами раздел с названием '{0}' и раздел с названием '{1}'")
    public void changeSequenceDisplaySubsections(String sourceName, String targetName) {
        SelenideElement subsectionSource = searchSubsection(sourceName);
        SelenideElement subsectionTarget = searchSubsection(targetName);
        Actions actions = actions();
        actions.clickAndHold(subsectionSource)
                .moveToElement(subsectionTarget)
                .release()
                .perform();
    }

    @Step("Найти подраздел с названием '{0}'")
    public SelenideElement searchSubsection(String subsectionName){
        SelenideElement SUBSECTION =$x("//span[text()='" + subsectionName + "']//parent::div//parent::div[@class='K9Fo']");
        SUBSECTION.shouldBe(Condition.visible);
        return SUBSECTION;
    }

    @Step("Проверить отображение подраздела")
    public boolean isExistSubsectionCard(){
        return SUBSECTION.isDisplayed();
    }

    @Step("Проверить отображение информации о пустом списке подразделов")
    public boolean isExistEmptyList() {
        return EMPTY_LIST_SUBSECTION.isDisplayed();
    }
}
