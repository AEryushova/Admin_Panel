package admin.pages.modalWindowServices;

import admin.utils.DataHelper;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class CategoryWindow {
    private final SelenideElement addSection= $x("//span[text()='Добавить раздел']//parent::div//parent::button");
    private final SelenideElement editSection= $x("//span[text()='Общеклиническое исследование']/parent::div/following-sibling::div[@class='V5So']");
    private final SelenideElement deleteSection= $x("//span[text()='Общеклиническое исследование']/parent::div/following-sibling::div[@class='mJna']");
    private final SelenideElement rulesPreparingSection= $x("//span[text()='Общеклиническое исследование']/parent::div/following-sibling::div[@class='tSFL']");
    private final SelenideElement expandSection=$x("//span[text()='Общеклиническое исследование']/parent::div/following-sibling::div[@class='xrjl']");

    private final SelenideElement addSubsection= $x("//span[text()='В категории \"Общеклиническое исследование\" отсутствуют разделы или услуги']//parent::div/following-sibling::div/button");
    private final SelenideElement editSubsection= $x("//span[text()='Анализы крови']/parent::div/following-sibling::div[@class='V5So']");
    private final SelenideElement deleteSubsection= $x("//span[text()='Анализы крови']/parent::div/following-sibling::div[@class='mJna']");
    private final SelenideElement rulesPreparingSubsection= $x("//span[text()='Анализы крови']/parent::div/following-sibling::div[@class='tSFL']");
    private final SelenideElement expandSubsection=$x("//span[text()='Анализы крови']/parent::div/following-sibling::div[@class='xrjl']");
    private final SelenideElement serviceInfo = $x("//span[text()='tmvp.9401.01']/preceding-sibling::div");


    public AddSectionWindow addSection(){
        addSection.click();
        return new AddSectionWindow();
    }

    public EditSectionWindow editSection(){
        editSection.click();
        return new EditSectionWindow();
    }

    public DeleteSectionWindow deleteSection(){
        deleteSection.click();
        return new DeleteSectionWindow();
    }

    public RulesPreparingWindow rulesPreparingSection(){
        rulesPreparingSection.click();
        return new RulesPreparingWindow();
    }

    public void expandSection(){
        expandSection.click();
    }

    public AddSectionWindow addSubsection(){
        addSubsection.click();
        return new AddSectionWindow();
    }

    public EditSectionWindow editSubsection(){
        editSubsection.click();
        return new EditSectionWindow();
    }

    public DeleteSectionWindow deleteSubsection(){
        deleteSubsection.click();
        return new DeleteSectionWindow();
    }

    public RulesPreparingWindow rulesPreparingSubsection(){
        rulesPreparingSubsection.click();
        return new RulesPreparingWindow();
    }

    public void expandSubsection(){
        expandSubsection.click();
    }

    public ServiceInfoWindow serviceInfo(){
        serviceInfo.click();
        return new ServiceInfoWindow();
    }


}
