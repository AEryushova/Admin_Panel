package admin.pages.ServicesPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class SubsectionCard {
    private final SelenideElement NAME_SUBSECTION=$x("//div[@class='CtIw' and @draggable='true']/div/div[@class='OurO']/span");
    private final SelenideElement EDIT_SUBSECTION_BUTTON = $x("//div[@class='V5So']");
    private final SelenideElement DELETE_SUBSECTION_BUTTON = $x("//div[@class='mJna']");
    private final SelenideElement RULES_PREPARING_SUBSECTION = $x("//div[@class='CtIw' and @draggable='true']/div/div[@class='tSFL']");
    private final SelenideElement EXPAND_SUBSECTION = $x("//div[@class='CtIw' and @draggable='true']/div/div[@class='xrjl']");
    private final SelenideElement SERVICE = $x("//div[@class='hzR2']");
    private final SelenideElement EMPTY_LIST_SERVICE = $x("//div[@class='b8mg']/span");

    public void subsectionCard() {
        NAME_SUBSECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EDIT_SUBSECTION_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_SUBSECTION_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        RULES_PREPARING_SUBSECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EXPAND_SUBSECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
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

    public String getNameSubsection(){
        NAME_SUBSECTION.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return NAME_SUBSECTION.getText();
    }


    public void openSubsection() {
        EXPAND_SUBSECTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public ServiceCard getService() {
        SERVICE.shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.exist, Duration.ofSeconds(5));
        return new ServiceCard();
    }


    public boolean isExistEmptyList() {
        return EMPTY_LIST_SERVICE.isDisplayed();
    }

    public boolean isExistService(){
        return SERVICE.isDisplayed();
    }

}
