package admin.utils;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DataBaseUtils {

    private static Map<String, QueryRunner> queryRunnerMap = new HashMap<>();

    private DataBaseUtils() {
    }

    private static Connection getConnection(String dataSourceKey) throws SQLException {
        switch (dataSourceKey) {
            case "cab_lab_db":
                return DriverManager.getConnection("jdbc:postgresql://192.168.6.225:5432/freeze_cab_lab_collection", "username", "password");
            case "platform_db":
                return DriverManager.getConnection("jdbc:postgresql://192.168.6.205:49000/lk_platform", "postgres", "298180");
            case "medical_record_db":
                return DriverManager.getConnection("jdbc:postgresql://192.168.6.205:49000/freeze_processor_medical_record", "username", "password");
            default:
                throw new IllegalArgumentException("Unsupported datasource key: " + dataSourceKey);
        }
    }

    private static QueryRunner queryRunner(String dataSourceKey) {
        return queryRunnerMap.computeIfAbsent(dataSourceKey, key -> new QueryRunner());
    }

    @SneakyThrows
    public static Admin selectAdmin() {
        var selectAdminRequest = "SELECT * FROM platform.users INNER JOIN platform.users_roles ON users.id=users_roles.user_id WHERE username ='ADMIN_TESTUI_2'";
        var connection = getConnection("platform_db");
        return queryRunner("platform_db").query(connection, selectAdminRequest, new BeanHandler<>(Admin.class));
    }

    @SneakyThrows
    public static Admin selectSuperAdmin() {
        var selectAdminRequest = "SELECT * FROM platform.users INNER JOIN platform.users_roles ON users.id=users_roles.user_id WHERE username ='SUPER_ADMIN'";
        var connection = getConnection("platform_db");
        return queryRunner("platform_db").query(connection, selectAdminRequest, new BeanHandler<>(Admin.class));
    }

    @SneakyThrows
    public static void clearAllFeedback() {
        var connection = getConnection("cab_lab_db");
        queryRunner("cab_lab_db").update(connection, "DELETE FROM feedbacks_employees");
    }

    @SneakyThrows
    public static void addFeedback() {
        var connection = getConnection("cab_lab_db");
        queryRunner("cab_lab_db").execute(connection, "INSERT INTO feedbacks_employees (employees_id,author,content,created_at,updated_at,is_published ) VALUES ('787a79cc-daa6-11ee-ae4c-572334db0f4a','Федоров Ф. Ф.','Это тестовый коммент','2024-05-27 12:28:49.000', '2024-05-27 12:28:49.000', 'false'");
    }

    @SneakyThrows
    public static void clearAllBugReports() {
        var connection = getConnection("cab_lab_db");
        queryRunner("cab_lab_db").execute(connection, "DELETE FROM bug_reports");
    }

    @SneakyThrows
    public static void clearAllFaq() {
        var connection = getConnection("cab_lab_db");
        queryRunner("cab_lab_db").execute(connection, "DELETE FROM faq");
    }


    @SneakyThrows
    public static Feedback selectFeedback() {
        var selectFeedbackRequest = "SELECT employees_id, author, content, is_published FROM feedbacks_employees ORDER BY created_at DESC LIMIT 1";
        var connection = getConnection("cab_lab_db");
        return queryRunner("cab_lab_db").query(connection, selectFeedbackRequest, new BeanHandler<>(Feedback.class));
    }

    @SneakyThrows
    public static BugReports selecBugReports() {
        var selectBugReports = "SELECT message, email, author FROM bug_reports ORDER BY created_at DESC LIMIT 1";
        var connection = getConnection("cab_lab_db");
        return queryRunner("cab_lab_db").query(connection, selectBugReports, new BeanHandler<>(BugReports.class));
    }

    @SneakyThrows
    public static Faq selectFaq() {
        var selectFaq = "SELECT question, answer, sequence FROM faq ORDER BY created_at DESC LIMIT 1";
        var connection = getConnection("cab_lab_db");
        return queryRunner("cab_lab_db").query(connection, selectFaq, new BeanHandler<>(Faq.class));
    }
}