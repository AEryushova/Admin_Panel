package ru.adminlk.clinica.utils.dbUtils.dbUtils;

public class DataBaseConfig {
    protected static final String CAB_LAB_DB_URL_DEFAULT="jdbc:postgresql://192.168.6.225:5432/";
    protected static final String CAB_LAB_DB_URL = CAB_LAB_DB_URL_DEFAULT + System.getProperty("db");
    protected static final String CAB_LAB_DB_USERNAME = "username";
    protected static final String CAB_LAB_DB_PASSWORD = "password";

    protected static final String PLATFORM_DB_URL = "jdbc:postgresql://192.168.6.205:49000/lk_platform";
    protected static final String PLATFORM_DB_USERNAME = "postgres";
    protected static final String PLATFORM_DB_PASSWORD = "298180";

    protected static final String LOG_DB_URL = "jdbc:postgresql://192.168.6.227:15432/logdb";
    protected static final String LOG_DB_USERNAME = "launcher_user_admin";
    protected static final String LOG_DB_PASSWORD = "Aww%zgW@~sh3";

}
