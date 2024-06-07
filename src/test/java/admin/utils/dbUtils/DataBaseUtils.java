package admin.utils.dbUtils;

import admin.data.DataInfo;
import admin.utils.testUtils.DataHelper;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static groovyjarjarantlr4.v4.runtime.misc.MurmurHash.update;


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
    public static Admins selectAdmin(String login) {
        var selectAdminRequest = "SELECT * FROM platform.users INNER JOIN platform.users_roles ON users.id=users_roles.user_id WHERE username = ?";
        var connection = getConnection("platform_db");
        return queryRunner("platform_db").query(connection, selectAdminRequest, login, new BeanHandler<>(Admins.class));
    }

    @SneakyThrows
    public static DoctorCard selectPhotoUriDoctor() {
        var selectPhotoUri = "SELECT * FROM employee_cards WHERE job = ? AND first_name = ? AND second_name = ? AND middle_name = ? ";
        var connection = getConnection("cab_lab_db");
        var doctorSpecialization=DataInfo.DataTest.getDoctorSpecialization();
        var doctorName= DataInfo.DataTest.getDoctorName();
        String[] nameParts = doctorName.split(" ");
        String firstName = nameParts[1];
        String secondName = nameParts[0];
        String middleName = nameParts[2];
        return queryRunner("cab_lab_db").query(connection, selectPhotoUri, new Object[]{doctorSpecialization,firstName, secondName, middleName }, new BeanHandler<>(DoctorCard.class));
    }


    @SneakyThrows
    public static void setDefaultPhotoDoctor() {
        var setDefaultPhoto = "UPDATE employee_cards SET photo_uri = ? WHERE job = ? AND first_name = ? AND second_name = ? AND middle_name = ? ";
        var connection = getConnection("cab_lab_db");
        var doctorName= DataInfo.DataTest.getDoctorName();
        var doctorSpecialization=DataInfo.DataTest.getDoctorSpecialization();
        var defaultPhoto = DataInfo.DataTest.getDefaultPhoto();
        String[] nameParts = doctorName.split(" ");
            String firstName = nameParts[1];
            String secondName = nameParts[0];
            String middleName = nameParts[2];
            queryRunner("cab_lab_db").update(connection, setDefaultPhoto, defaultPhoto, doctorSpecialization, firstName, secondName, middleName);
        }


    @SneakyThrows
    public static void setPhotoDoctor() {
        var setDefaultPhoto = "UPDATE employee_cards SET photo_uri = ? WHERE job = ? AND first_name = ? AND second_name = ? AND middle_name = ? ";
        var connection = getConnection("cab_lab_db");
        var photo = DataInfo.DataTest.getPhoto();
        var doctorName= DataInfo.DataTest.getDoctorName();
        var doctorSpecialization=DataInfo.DataTest.getDoctorSpecialization();;
        String[] nameParts = doctorName.split(" ");
        String firstName = nameParts[1];
        String secondName = nameParts[0];
        String middleName = nameParts[2];
        queryRunner("cab_lab_db").update(connection, setDefaultPhoto, photo, doctorSpecialization, firstName, secondName, middleName);
    }


    @SneakyThrows
    public static BugReports selectBugReports() {
        var selectBugReports = "SELECT * FROM bug_reports";
        var connection = getConnection("cab_lab_db");
        return queryRunner("cab_lab_db").query(connection, selectBugReports, new BeanHandler<>(BugReports.class));
    }

    @SneakyThrows
    public static void addBugReport() {
        var addBugReport="INSERT INTO bug_reports (id,message,email,author,created_at ) VALUES (?, ?, ?, ?, ?)";
        var connection = getConnection("cab_lab_db");
        var message=DataInfo.DataTest.getMessageBugReport();
        var email=DataInfo.DataTest.getEmailPatient();
        var author=DataInfo.DataTest.getNamePatient();
        var time=DataHelper.generateDateTime();
        var id= DataHelper.generateUuid();
        queryRunner("cab_lab_db").update(connection, addBugReport, id, message, email, author, time);
    }

    @SneakyThrows
    public static void clearAllBugReports() {
        var connection = getConnection("cab_lab_db");
        queryRunner("cab_lab_db").update(connection, "DELETE FROM bug_reports");
    }

    @SneakyThrows
    public static Faq selectFaq(int sequence) {
        var selectFaq = "SELECT * FROM faq WHERE sequence = ?";
        var connection = getConnection("cab_lab_db");
        return queryRunner("cab_lab_db").query(connection, selectFaq, sequence, new BeanHandler<>(Faq.class));
    }

    @SneakyThrows
    public static void addFaq(int sequence) {
        var addFaq="INSERT INTO faq (id,question,answer,created_at,updated_at, group_id, sequence ) VALUES (?, ?, ?, ?, ?,?,?)";
        var connection = getConnection("cab_lab_db");
        var id= DataHelper.generateUuid();
        var question=DataInfo.DataTest.getQuestion();
        var answer=DataInfo.DataTest.getAnswer();
        var created_at=DataHelper.generateDateTime();
        var updated_at=DataHelper.generateDateTime();
        var group_id=DataHelper.generateUuid();
        queryRunner("cab_lab_db").update(connection, addFaq, id, question, answer, created_at,updated_at,group_id,sequence);
    }

    @SneakyThrows
    public static void clearAllFaq() {
        var connection = getConnection("cab_lab_db");
        queryRunner("cab_lab_db").execute(connection, "DELETE FROM faq");
    }









    @SneakyThrows
    public static Feedback selectFeedback() {
        var selectFeedbackRequest = "SELECT * FROM feedbacks_employees";
        var connection = getConnection("cab_lab_db");
        return queryRunner("cab_lab_db").query(connection, selectFeedbackRequest, new BeanHandler<>(Feedback.class));
    }

    @SneakyThrows
    public static void addFeedback() {
        var connection = getConnection("cab_lab_db");
        queryRunner("cab_lab_db").update(connection, "INSERT INTO feedbacks_employees (employees_id,author,content,created_at,updated_at,is_published ) VALUES ('111a11cc-daa1-11ee-ae1c-111111db0f1a','Федоров Ф. Ф.','Это тестовый коммент','2024-05-27 12:28:49.000', '2024-05-27 12:28:49.000', 'false'");
    }

    @SneakyThrows
    public static void deleteFeedback() {
        var connection = getConnection("cab_lab_db");
        var feedback = DataInfo.UserData.getLoginSuperAdmin();
        queryRunner("cab_lab_db").update(connection, "DELETE FROM feedbacks_employees WHERE username = ?");
    }


}