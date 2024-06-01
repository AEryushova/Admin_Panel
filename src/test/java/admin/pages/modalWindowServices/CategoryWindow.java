package admin.pages.modalWindowServices;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class CategoryWindow {
    private final SelenideElement ADD_SECTION_BUTTON = $x("//span[text()='Добавить раздел']//parent::div//parent::button");
    private final SelenideElement EDIT_SECTION_BUTTON = $x("//span[text()='Общеклиническое исследование']/parent::div/following-sibling::div[@class='V5So']");
    private final SelenideElement DELETE_SECTION_BUTTON = $x("//span[text()='Общеклиническое исследование']/parent::div/following-sibling::div[@class='mJna']");
    private final SelenideElement RULES_PREPARING_SECTION = $x("//span[text()='Общеклиническое исследование']/parent::div/following-sibling::div[@class='tSFL']");
    private final SelenideElement EXPAND_SECTION = $x("//span[text()='Общеклиническое исследование']/parent::div/following-sibling::div[@class='xrjl']");

    private final SelenideElement ADD_SUBSECTION_BUTTON = $x("//span[text()='В категории \"Общеклиническое исследование\" отсутствуют разделы или услуги']//parent::div/following-sibling::div/button");
    private final SelenideElement EDIT_SUBSECTION_BUTTON = $x("//span[text()='Анализы крови']/parent::div/following-sibling::div[@class='V5So']");
    private final SelenideElement DELETE_SUBSECTION_BUTTON = $x("//span[text()='Анализы крови']/parent::div/following-sibling::div[@class='mJna']");
    private final SelenideElement RULES_PREPARING_SUBSECTION = $x("//span[text()='Анализы крови']/parent::div/following-sibling::div[@class='tSFL']");
    private final SelenideElement EXPAND_SUBSECTION = $x("//span[text()='Анализы крови']/parent::div/following-sibling::div[@class='xrjl']");
    private final SelenideElement SERVICE_INFO = $x("//span[text()='tmvp.9401.01']/preceding-sibling::div");


    public AddSectionWindow addSection() {
        ADD_SECTION_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new AddSectionWindow();
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

    public void expandSection() {
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

    public EditSectionWindow editSubsection() {
        EDIT_SUBSECTION_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new EditSectionWindow();
    }

    public DeleteSectionWindow deleteSubsection() {
        DELETE_SUBSECTION_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new DeleteSectionWindow();
    }

    public RulesPreparingWindow rulesPreparingSubsection() {
        RULES_PREPARING_SUBSECTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new RulesPreparingWindow();
    }

    public void expandSubsection() {
        EXPAND_SUBSECTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public ServiceInfoWindow serviceInfo() {
        SERVICE_INFO.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new ServiceInfoWindow();
    }


}
