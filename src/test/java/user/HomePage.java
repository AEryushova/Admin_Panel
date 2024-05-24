package user;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import user.pages.modalWindowReportBug.ReportBug;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class HomePage {
    private final SelenideElement makeAppointmentButton = $x("//button[@data-locator='chose-doctor']");
    private final SelenideElement medicalCardButton = $x("//button[@data-locator='redirect-slots']");
    private final SelenideElement laboratoryButton = $x("//div[@data-locator='redirect-services-laboratory']");
    private final SelenideElement diagnosticsButton = $x("//div[@data-locator='redirect-services-diagnosis']");
    private final SelenideElement dentistryButton = $x("//div[@data-locator='redirect-doctors-dentestry']");
    private final SelenideElement faqButton = $x("//div[@data-locator='redirect-faq']");
    private final SelenideElement reportBug = $x("//span[@class='VyRFRd0O']/span[text()='Сообщить об ошибке']");
    private final SelenideElement notification=$x("//div[@role='alert']/div//following-sibling::div");
    private final SelenideElement closeNotification = $x("//button[@aria-label='close']");
    private final SelenideElement footerDoctorsPage = $x("//span[text()='© Клиники СамГМУ, 2024.']");
    private final SelenideElement returnToStartButton = $x("//div[@class='UnAfS9cF h4QzdVU7']");

    public void homePage(){
        makeAppointmentButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        medicalCardButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        laboratoryButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        diagnosticsButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        dentistryButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        faqButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public DoctorsPage makeAnAppointment(){
        makeAppointmentButton.click();
        return new DoctorsPage();
    }
    public MedicalCardPage openMedicalCard(){
        medicalCardButton.click();
        return new MedicalCardPage();
    }

    public ServicesPageLK openLaboratory(){
        laboratoryButton.click();
        return new ServicesPageLK();
    }

    public ServicesPageLK openDiagnostics(){
        diagnosticsButton.click();
        return new ServicesPageLK();
    }

    public DentistryPage openDentistry(){
        dentistryButton.click();
        return new DentistryPage();
    }

    public FaqPage openFaq(){
        faqButton.click();
        return new FaqPage();
    }

    public ReportBug sendReportBug() {
        reportBug.shouldBe(Condition.visible, Duration.ofSeconds(5));
        reportBug.click();
        return new ReportBug();
    }

    public String getNotification() {
        notification.shouldBe(Condition.visible, Duration.ofSeconds(20));
        return notification.getText();
    }
    public void returnToStartPage() {
        footerDoctorsPage.scrollTo();
        returnToStartButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        returnToStartButton.click();
    }
}
