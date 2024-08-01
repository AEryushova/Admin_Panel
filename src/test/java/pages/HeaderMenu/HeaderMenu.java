package pages.HeaderMenu;

import pages.AdministrationPage.AdministrationPage;
import pages.BasePage.BasePage;
import pages.DoctorsPage.DoctorsPage;
import pages.FaqPage.FaqPage;
import pages.ServicesPage.ServicesPage;
import pages.SettingPage.SettingPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class HeaderMenu extends BasePage {

    private final SelenideElement DOCTOR = $x("//a[text()='Врачи']");
    private final SelenideElement SERVICES = $x("//a[text()='Услуги']");
    private final SelenideElement SETTING = $x("//a[text()='Настройки']");
    private final SelenideElement FAQ = $x("//a[text()='FAQ']");
    private final SelenideElement ADMINISTRATION = $x("//a[text()='Администрирование']");
    private final SelenideElement PROFILE_BUTTON = $x("//button[@class='MxFR oAei']");
    private final SelenideElement LOGO=$x("//div[@class='AacY']/img");

    @Step("Верифицировать навигационное меню для Суперадмина")
    public void verifyHeaderBarSuperAdmin() {
        DOCTOR.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SERVICES.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SETTING.shouldBe(Condition.visible, Duration.ofSeconds(5));
        FAQ.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADMINISTRATION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        PROFILE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        LOGO.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Верифицировать навигационное меню для Админа")
    public void verifyHeaderBarAdmin() {
        DOCTOR.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SERVICES.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SETTING.shouldBe(Condition.visible, Duration.ofSeconds(5));
        FAQ.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADMINISTRATION.shouldBe(Condition.hidden);
        PROFILE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        LOGO.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Нажать на вкладку Администрирование")
    public AdministrationPage clickAdministrationTab() {
        ADMINISTRATION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new AdministrationPage();
    }

    @Step("Нажать на вкладку Врачи")
    public DoctorsPage clickDoctorsTab() {
        DOCTOR.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new DoctorsPage();
    }

    @Step("Нажать на вкладку FAQ")
    public FaqPage clickFaqTab() {
        FAQ.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new FaqPage();
    }

    @Step("Нажать на вкладку Услуги")
    public ServicesPage clickServicesTab() {
        SERVICES.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new ServicesPage();
    }

    @Step("Нажать на вкладку Настройки")
    public SettingPage clickSettingTab() {
        SETTING.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new SettingPage();
    }

    @Step("Нажать на кнопку открытия и закрытия юзер-панели")
    public UserPanel clickButtonUserPanel() {
        PROFILE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new UserPanel();
    }

    @Step("Получить высоту изображения логотипа")
    public int getHeightLogo(){
        return LOGO.getSize().getHeight();
    }

}