package admin.pages.SettingPage;

import admin.pages.BasePage.BasePage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class SettingPage extends BasePage {

    private final SelenideElement BAG_REPORTS_SECTION = $x("//span[text()='Раздел для мониторинга сообщений и жалоб пользователей ЛК']/parent::div/preceding-sibling::div/span");
    private final SelenideElement LOGO_APP_SECTION = $x("//span[text()='Логотип Приложения']");
    private final SelenideElement LOGO_APP = $x("//div[@class='D1Px']/img");
    private final SelenideElement AUTHOR = $x("//div[@class='CYEV']/span");
    private final SelenideElement EMAIL_AUTHOR = $x("//div[@class='A23y']/span");
    private final SelenideElement DATE_REPORT = $x("//div[@class='PEGd']/span");
    private final SelenideElement TEXT_REPORT = $x("//div[@class='fEEm']/span");
    private final SelenideElement DELETE_BUTTON = $x("//div[@class='wXIR']");
    private final SelenideElement EDIT_LOGO_BUTTON = $x("//div[@class='jUqF']/div");


    public void settingPage() {
        BAG_REPORTS_SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        LOGO_APP_SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public String getAuthorText() {
        AUTHOR.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return AUTHOR.getText();
    }

    public String getEmailAuthorText() {
        EMAIL_AUTHOR.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return EMAIL_AUTHOR.getText();
    }

    public String getDateText() {
        DATE_REPORT.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return DATE_REPORT.getText();
    }

    public String getReportText() {
        TEXT_REPORT.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return TEXT_REPORT.getText();
    }

    public void deleteBugReport() {
        DELETE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public EditLogoWindow openWindowEditLogo() {
        EDIT_LOGO_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new EditLogoWindow();
    }

    public int getHeightLogo(){
        return LOGO_APP.getSize().getHeight();
    }

}
