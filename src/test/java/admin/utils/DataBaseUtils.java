package admin.utils;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseUtils {
    private static QueryRunner runner = new QueryRunner();

    private DataBaseUtils() {
    }
    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection(System.getProperty("db.url"),System.getProperty("db.user"), System.getProperty("db.password"));
    }

    @SneakyThrows
    public void clearAllData() {
        var connection = getConn();
        runner.execute(connection, "DELETE FROM feedbacks_employees");
        runner.update(connection, "DELETE FROM bug_reports");
        runner.update(connection, "DELETE FROM faq");
    }

    @SneakyThrows
    public static Feedback selectFeedbackRequest() {
        var selectFeedbackRequest = "SELECT employees_id, author, content, is_published FROM feedbacks_employees ORDER BY created_at DESC LIMIT 1";
        var connection = getConn();
        return runner.query(connection, selectFeedbackRequest, new BeanHandler<>(Feedback.class));
    }

    @SneakyThrows
    public static BugReports selecBugReports() {
        var selectBugReports = "SELECT message, email, author FROM bug_reports ORDER BY created_at DESC LIMIT 1";
        var connection = getConn();
        return runner.query(connection, selectBugReports, new BeanHandler<>(BugReports.class));
    }
    @SneakyThrows
    public static Faq selectFaq() {
        var selectFaq = "SELECT question, answer, sequence FROM faq ORDER BY created_at DESC LIMIT 1";
        var connection = getConn();
        return runner.query(connection, selectFaq , new BeanHandler<>(Faq.class));
    }
}