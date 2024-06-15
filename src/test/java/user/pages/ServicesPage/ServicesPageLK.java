package user.pages.ServicesPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class ServicesPageLK {

    private final SelenideElement tabNameService = $x("//span[text()='Услуги']");
    private final SelenideElement laboratoriesCategory = $x("//button[@data-locator='get-laboratories']");
    private final SelenideElement diagnosticsCategory = $x("//button[@data-locator='get-diagnosis']");
    private final SelenideElement rulesPreparing= $x("//span[text()='Правила оказания услуги']/parent::div/following-sibling::div");


    public void servicesPageLK() {
        tabNameService.shouldBe(Condition.visible, Duration.ofSeconds(5));
        laboratoriesCategory.shouldBe(Condition.visible, Duration.ofSeconds(5));
        diagnosticsCategory .shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public RulesPreparingWindowLK openRulesPreparingCategory(){
    rulesPreparing.shouldBe(Condition.visible, Duration.ofSeconds(5));
        rulesPreparing.click();
        return new RulesPreparingWindowLK();
    }

    public void openSectionListLaboratories() {
        laboratoriesCategory.click();
    }

    public void openSectionListDiagnostics() {
        diagnosticsCategory.click();
    }



}
