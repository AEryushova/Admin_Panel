package admin.utils.decoratorsTest.headerMenu;


import admin.data.DataInfo;
import admin.utils.testUtils.BrowserManager;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class NewAuthDecorator implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        BrowserManager.authGetCookie(DataInfo.UserData.getLoginAdmin(), DataInfo.UserData.getPasswordAdmin());
    }


}
