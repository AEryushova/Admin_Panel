package ru.adminlk.clinica.samsmu.data;

import lombok.Data;

@Data
public class AppData {
    public static final String
            URL_ADMIN_PANEL = "http://192.168.6.48:8083",
            URL_BACK = "http://192.168.6.31:8080",
            URL_PERSONAL_AREA = "https://lk.mdapp.online",
            ENVIRONMENT = System.getProperty("environment");
}
