package admin.pages.HeaderMenu;

import admin.pages.AdministrationPage.AdministrationPage;
import admin.pages.BasePage.BasePage;
import admin.pages.DoctorsPage.DoctorsPage;
import admin.pages.FaqPage.FaqPage;
import admin.pages.ServicesPage.ServicesPage;
import admin.pages.SettingPage.SettingPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class HeaderMenu extends BasePage {

    private final SelenideElement DOCTOR = $x("//a[text()='Врачи']");
    private final SelenideElement SERVICES = $x("//a[text()='Услуги']");
    private final SelenideElement SETTING = $x("//a[text()='Настройки']");
    private final SelenideElement FAQ = $x("//a[text()='FAQ']");
    private final SelenideElement ADMINISTRATION = $x("//a[text()='Администрирование']");
    private final SelenideElement PROFILE_BUTTON = $x("//div[@class='wrap__dca9 MxFR DropdownWrap']");
    private final SelenideElement ROLE_STATUS = $x("//div[@data-locator='container']/div/div/div/span[1]");
    private final SelenideElement EXIT_BUTTON = $x("//span[text()='Выход']");

    public void headerBarSuperAdmin() {
        DOCTOR.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SERVICES.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SETTING.shouldBe(Condition.visible, Duration.ofSeconds(5));
        FAQ.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADMINISTRATION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        PROFILE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void headerBarAdmin() {
        DOCTOR.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SERVICES.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SETTING.shouldBe(Condition.visible, Duration.ofSeconds(5));
        FAQ.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADMINISTRATION.shouldBe(Condition.hidden);
        PROFILE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public AdministrationPage administrationTabOpen() {
        ADMINISTRATION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new AdministrationPage();
    }

    public DoctorsPage doctorsTabOpen() {
        DOCTOR.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new DoctorsPage();
    }

    public FaqPage faqTabOpen() {
        FAQ.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new FaqPage();
    }

    public ServicesPage servicesTabOpen() {
        SERVICES.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new ServicesPage();
    }

    public SettingPage settingTabOpen() {
        SETTING.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new SettingPage();
    }

    public void openAndCloseProfileAdmin() {
        PROFILE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public String checkProfileInfoUser() {
        ROLE_STATUS.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return ROLE_STATUS.getText();
    }

    public void exitAdminPanel() {
        EXIT_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.enabled)
                .click();

    }


}