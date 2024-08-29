package appData;

import lombok.Data;

@Data
public class AppData {
    public static final String
            URI_ADMIN_PANEL = "http://192.168.6.48:8083",
            URI_BACK = "http://192.168.6.31:8080",
            URI_PERSONAL_AREA = "https://lk.mdapp.online",
            ENVIRONMENT = System.getProperty("environment");
}
