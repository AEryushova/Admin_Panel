package admin.pages.ServicesPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.List;


import static com.codeborne.selenide.Selenide.*;

public class CategoryCard {
    private final SelenideElement ADD_SECTION_BUTTON = $x("//span[text()='Добавить раздел']//parent::div//parent::button/parent::div[@class='nwH0']");
    private final ElementsCollection CONTAINER_SECTIONS = $$x("//div[@class='OurO']/span");
    private final SelenideElement SECTION = $x("//div[@class='CtIw' and @draggable='true']/div");
    private final SelenideElement EMPTY_LIST_SECTION = $x("//div[@class='kblo']/span");
    private final SelenideElement SERVICE = $x("//div[@class='hzR2' and @draggable='false']");

    @Step("Верифицировать карточку категории")
    public void categoryCard() {
        ADD_SECTION_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Нажать кнопку добавление раздела")
    public AddSectionWindow addSection() {
        ADD_SECTION_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new AddSectionWindow();
    }

    @Step("Получить раздел")
    public SectionCard getSection() {
        SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.exist, Duration.ofSeconds(5));
        return new SectionCard();
    }

    @Step("Получить индекс раздела по названию '{0}'")
    public int getSectionByName(String sectionName) {
        List<SelenideElement> sectionElements = CONTAINER_SECTIONS;
        for (int i = 0; i < sectionElements.size(); i++) {
            if (sectionElements.get(i).getText().equals(sectionName)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Section not found: " + sectionName);
    }

    @Step("Поменять местами раздел с названием '{0}' и раздел с названием '{1}'")
    public void changeDisplaySequence(String sourceName, String targetName) {
        SelenideElement sectionSource = searchSection(sourceName);
        SelenideElement sectionTarget = searchSection(targetName);
        Actions actions = actions();
        actions.clickAndHold(sectionSource)
                .moveToElement(sectionTarget)
                .release()
                .perform();
    }

    @Step("Найти раздел с названием '{0}'")
    public SelenideElement searchSection(String sectionName){
        SelenideElement SECTION =$x("//span[text()='" + sectionName + "']//parent::div//parent::div[@class='K9Fo']");
        SECTION.shouldBe(Condition.visible);
        return SECTION;
    }


    @Step("Получить услугу")
    public ServiceCard getService() {
        SERVICE.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return new ServiceCard();
    }

    @Step("Проверить отображение раздела")
    public boolean isExistSectionCard(){
        return SECTION.isDisplayed();
    }

    @Step("Проверить отображение информации о пустом списке разделов")
    public boolean isExistEmptyList() {
        return EMPTY_LIST_SECTION.isDisplayed();
    }

    @Step("Проверить отображение услуги")
    public boolean isExistService(String nameService) {
        SelenideElement SERVICE = $x("//span[text()='" + nameService + "']/parent::div/parent::div[@class='hzR2']");
       return SERVICE.isDisplayed();
    }

}

