package admin.pages.SettingPage;

import admin.pages.BasePage.BasePage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class SettingPage extends BasePage {

    private final SelenideElement BUG_REPORTS_SECTION = $x("//span[text()='Раздел для мониторинга сообщений и жалоб пользователей ЛК']/parent::div/preceding-sibling::div/span");
    private final SelenideElement LOGO_APP_SECTION = $x("//span[text()='Логотип Приложения']");
    private final SelenideElement LOGO_APP = $x("//div[@class='D1Px']/img");
    private final SelenideElement EDIT_LOGO_BUTTON = $x("//div[@class='jUqF']/div");
    private final SelenideElement BUG_REPORT=$x("//div[@class='eYxe']");
    private final SelenideElement EMPTY_LIST_BUG_REPORT=$x("//span[text()='Нет сообщений от пользователей']");


    public void settingPage() {
        BUG_REPORTS_SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        LOGO_APP_SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public BugReport bugReportCard(){
        BUG_REPORT.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return new BugReport();
    }

    public boolean isExistsBugReport() {
        return BUG_REPORT.isDisplayed();
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

    public boolean isExistsEmptyList(){
        return EMPTY_LIST_BUG_REPORT.isDisplayed();
    }
}
