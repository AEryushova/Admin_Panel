package admin.utils.dbUtils;

public class DataBaseConfig {
    public static final String CAB_LAB_DB_URL_DEFAULT="jdbc:postgresql://192.168.6.225:5432/";
    public static final String CAB_LAB_DB_NAME=System.getProperty("cab.lab.db.name", "freeze_cab_lab_collection");
    public static final String CAB_LAB_DB_URL = CAB_LAB_DB_URL_DEFAULT + CAB_LAB_DB_NAME;
    public static final String CAB_LAB_DB_USERNAME = "username";
    public static final String CAB_LAB_DB_PASSWORD = "password";

    public static final String PLATFORM_DB_URL = "jdbc:postgresql://192.168.6.205:49000/lk_platform";
    public static final String PLATFORM_DB_USERNAME = "postgres";
    public static final String PLATFORM_DB_PASSWORD = "298180";

    public static final String MEDICAL_RECORD_DB_URL = "jdbc:postgresql://192.168.6.205:49000/freeze_processor_medical_record";
    public static final String MEDICAL_RECORD_DB_USERNAME = "username";
    public static final String MEDICAL_RECORD_DB_PASSWORD = "password";
}
