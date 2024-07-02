package admin.pages.ServicesPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class SubsectionCard {
    private final SelenideElement NAME_SUBSECTION=$x("//div[@class='CtIw' and @draggable='true']/div/div[@class='OurO']/span");
    private final SelenideElement EDIT_SUBSECTION_BUTTON = $x("//div[@class='V5So']");
    private final SelenideElement DELETE_SUBSECTION_BUTTON = $x("//div[@class='mJna']");
    private final SelenideElement RULES_PREPARING_SUBSECTION = $x("//div[@class='CtIw' and @draggable='true']/div/div[@class='tSFL']");
    private final SelenideElement EXPAND_SUBSECTION = $x("//div[@class='CtIw' and @draggable='true']/div/div[@class='xrjl']");
    private final SelenideElement SERVICE = $x("//div[@class='hzR2' and @draggable='true']");
    private final SelenideElement EMPTY_LIST_SERVICE = $x("//div[@class='b8mg']/span");
    private final ElementsCollection CONTAINER_SERVICES = $$x("//div[@class='D_7z']/span");

    @Step("Верифицировать карточку подраздела")
    public void subsectionCard() {
        NAME_SUBSECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EDIT_SUBSECTION_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_SUBSECTION_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        RULES_PREPARING_SUBSECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EXPAND_SUBSECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Нажать кнопку редактирования подраздела")
    public EditSectionWindow editSubsection() {
        EDIT_SUBSECTION_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new EditSectionWindow();
    }

    @Step("Нажать кнопку удаления подраздела")
    public DeleteSectionWindow deleteSubsection() {
        DELETE_SUBSECTION_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new DeleteSectionWindow();
    }

    @Step("Получить название подраздела")
    public String getNameSubsection(){
        NAME_SUBSECTION.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return NAME_SUBSECTION.getText();
    }

    @Step("Нажать кнопку раскрытия подраздела")
    public void openSubsection() {
        EXPAND_SUBSECTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать кнопку открытия правил подготовки к подразделу")
    public RulesPreparingWindow openRulesPreparingSubsection(){
        RULES_PREPARING_SUBSECTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new RulesPreparingWindow();
    }

    @Step("Получить услугу")
    public ServiceCard getService() {
        SERVICE.shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.exist, Duration.ofSeconds(5));
        return new ServiceCard();
    }


    @Step("Получить индекс услуги по коду '{0}'")
    public int getServiceByCode(String serviceCode) {
        List<SelenideElement> serviceElements = CONTAINER_SERVICES;
        for (int i = 0; i < serviceElements.size(); i++) {
            if (serviceElements.get(i).getText().equals(serviceCode)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Service not found: " + serviceCode);
    }

    @Step("Поменять местами услугу с кодом '{0}' и услугу с кодом '{1}'")
    public void changeDisplaySequence(String sourceService, String targetService) {
        SelenideElement serviceSource = searchService(sourceService);
        SelenideElement serviceTarget = searchService(targetService);
        Actions actions = actions();
        actions.clickAndHold(serviceSource)
                .moveToElement(serviceTarget)
                .release()
                .perform();
    }

    @Step("Найти услугу с кодом '{0}'")
    public SelenideElement searchService(String serviceCode){
        SelenideElement SERVICE =$x("//span[text()='" + serviceCode + "']//parent::div//parent::div[@class='hzR2']");
        SERVICE.shouldBe(Condition.visible);
        return SERVICE;
    }

    @Step("Проверить отображение информации о пустом списке услуг")
    public boolean isExistEmptyList() {
        return EMPTY_LIST_SERVICE.isDisplayed();
    }

    @Step("Проверить отображение услуги")
    public boolean isExistService(){
        return SERVICE.isDisplayed();
    }

}
