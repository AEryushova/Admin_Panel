package admin.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class SettingPage extends BasePage {

    private final SelenideElement TAB_NAME = $x("//span[text()='Сообщения об ошибках']");
    private final SelenideElement AUTHOR = $x("//div[@class='CYEV']/span");
    private final SelenideElement EMAIL_AUTHOR = $x("//div[@class='A23y']/span");
    private final SelenideElement DATE_REPORT = $x("//div[@class='PEGd']/span");
    private final SelenideElement TEXT_REPORT = $x("//div[@class='fEEm']/span");
    private final SelenideElement DELETE_BUTTON = $x("//div[@class='wXIR']");


    public void settingPage() {
        TAB_NAME.shouldBe(Condition.visible, Duration.ofSeconds(5));
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

}
