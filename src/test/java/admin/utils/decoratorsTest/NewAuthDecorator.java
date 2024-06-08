package admin.utils.decoratorsTest;


import admin.data.DataInfo;
import admin.utils.testUtils.BrowserManager;
import admin.utils.testUtils.DataPreparationService;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class NewAuthDecorator implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        BrowserManager.authGetCookie(DataInfo.UserData.getLoginAdmin(), DataInfo.UserData.getPasswordAdmin());
    }


}
