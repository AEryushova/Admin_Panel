package user.pages.HeaderMenu;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import user.pages.DentistryPage.DentistryPage;
import user.pages.DoctorsPage.DoctorsPage;
import user.pages.FaqPage.FaqPage;
import user.pages.HomePage.HomePage;
import user.pages.MedicalCardPage.MedicalCardPage;
import user.pages.ServicesPage.ServicesPageLK;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class HeaderMenuLK {
    private final SelenideElement homeButton = $x("//a[text()='Главная']");
    private final SelenideElement doctorsButton = $x("//a[text()='Врачи']");
    private final SelenideElement serviceButton = $x("//a[text()='Услуги']");
    private final SelenideElement medicalCardButton  = $x("//a[text()='Медкарта']");
    private final SelenideElement dentistryButton = $x("//a[text()='Стоматология']");
    private final SelenideElement faqButton = $x("//a[text()='FAQ']");
    private final SelenideElement cardPatientInfo = $x("//span[text()='Федоров Ф. Ф.']//parent::div");
    private final SelenideElement exitButton = $x("//span[text()='Выход']//parent::div");


    public void headerBarLK(){
        homeButton .shouldBe(Condition.visible, Duration.ofSeconds(5));
        doctorsButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        serviceButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        medicalCardButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        dentistryButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        faqButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        cardPatientInfo.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public HomePage homeTabOpen(){
        homeButton.click();
        return new HomePage();
    }

    public DoctorsPage doctorsTabOpen(){
        doctorsButton.click();
        return new DoctorsPage();
    }

    public ServicesPageLK servicesTabOpen(){
        serviceButton.click();
        return new ServicesPageLK();
    }

    public MedicalCardPage medicalCardTabOpen(){
        medicalCardButton.click();
        return new MedicalCardPage();
    }

    public DentistryPage dentistryTabOpen(){
        dentistryButton.click();
        return new DentistryPage();
    }

    public FaqPage settingTabOpen(){
        faqButton.click();
        return new FaqPage();
    }
    public void openAndCloseProfilePatient(){
        cardPatientInfo.click();
    }

    public void exitPersonalArea() {
        exitButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        exitButton.click();
    }





}
