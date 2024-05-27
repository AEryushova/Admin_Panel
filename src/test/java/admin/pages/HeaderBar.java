package admin.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class HeaderBar {

    private final SelenideElement doctorButton = $x("//a[text()='Врачи']");
    private final SelenideElement servicesButton = $x("//a[text()='Услуги']");
    private final SelenideElement settingButton = $x("//a[text()='Настройки']");
    private final SelenideElement faqButton = $x("//a[text()='FAQ']");
    private final SelenideElement administrationButton = $x("//a[text()='Администрирование']");
    private final SelenideElement profileButton = $x("//div[@class='wrap__dca9 MxFR DropdownWrap']");
    private final SelenideElement roleStatus = $x("//div[@data-locator='container']/div/div/div/span[1]");
    private final SelenideElement exitButton = $x("//span[text()='Выход']");

    public void headerBarSuperAdmin() {
        doctorButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        servicesButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        settingButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        faqButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        administrationButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        profileButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void headerBarAdmin() {
        doctorButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        servicesButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        settingButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        faqButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        administrationButton.shouldBe(Condition.hidden);
        profileButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public AdministrationPage administrationTabOpen(){
        administrationButton.click();
        return new AdministrationPage();
    }

    public DoctorsPage doctorsTabOpen(){
        doctorButton.click();
        return new DoctorsPage();
    }

    public FaqPage faqTabOpen(){
        faqButton.click();
        return new FaqPage();
    }

    public ServicesPage servicesTabOpen(){
        servicesButton.click();
        return new ServicesPage();
    }

    public SettingPage settingTabOpen(){
        settingButton.click();
        return new SettingPage();
    }
    public void openAndCloseProfileAdmin(){

        profileButton.click();
    }

    public String checkProfileInfoUser() {
        profileButton.click();
        roleStatus.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return roleStatus.getText();
    }

    public void exitAdminPanel() {
        exitButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        exitButton.click();

    }


}