package admin.data;

import lombok.Getter;

public class AppConfig {
    @Getter
    public static final String uriAdminPanel = "http://192.168.6.48:8083";
    @Getter
    public static final String domain = "192.168.6.48";
    @Getter
    public static final String uriPersonalArea = "https://lk.mdapp.online";
    @Getter
    public static final String environment = "freeze";
}
