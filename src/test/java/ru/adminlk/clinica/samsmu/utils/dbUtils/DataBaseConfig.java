package ru.adminlk.clinica.samsmu.utils.dbUtils;

public class DataBaseConfig {
    public static final String CAB_LAB_DB_URL_DEFAULT="jdbc:postgresql://192.168.6.225:5432/";
    public static final String CAB_LAB_DB_NAME=System.getProperty("cab.lab.db.name");
    public static final String CAB_LAB_DB_URL = CAB_LAB_DB_URL_DEFAULT + CAB_LAB_DB_NAME;
    public static final String CAB_LAB_DB_USERNAME = "username";
    public static final String CAB_LAB_DB_PASSWORD = "password";

    public static final String PLATFORM_DB_URL = "jdbc:postgresql://192.168.6.205:49000/lk_platform";
    public static final String PLATFORM_DB_USERNAME = "postgres";
    public static final String PLATFORM_DB_PASSWORD = "298180";

    public static final String LOG_DB_URL = "jdbc:postgresql://192.168.6.227:15432/logdb";
    public static final String LOG_DB_USERNAME = "launcher_user_admin";
    public static final String LOG_DB_PASSWORD = "Aww%zgW@~sh3";

}
