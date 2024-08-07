package utils.dbUtils;



import lombok.SneakyThrows;

import org.apache.commons.dbutils.handlers.BeanHandler;
import utils.dbUtils.dbaseData.*;

import java.util.UUID;

import static utils.otherUtils.TestHelper.*;

public class DataBaseQuery {


    @SneakyThrows
    public static Admins selectAdmin(String login) {
        var selectAdmin = "SELECT * FROM platform.users INNER JOIN platform.users_roles ON users.id=users_roles.user_id WHERE username = ?";
        try (var connection = DataBaseManager.getConnection("platform_db")) {
            //noinspection deprecation
            return DataBaseManager.queryRunner("platform_db").query(connection, selectAdmin, login, new BeanHandler<>(Admins.class));
        }
    }


    @SneakyThrows
    public static DoctorCard selectInfoDoctor(String doctorName, String doctorSpecialization) {
        var selectInfo = "SELECT * FROM employee_cards WHERE job = ? AND first_name = ? AND second_name = ? AND middle_name = ? ";
        try (var connection = DataBaseManager.getConnection("cab_lab_db")) {
            String[] nameParts = doctorName.split(" ");
            String firstName = nameParts[1];
            String secondName = nameParts[0];
            String middleName = nameParts[2];
            //noinspection deprecation
            return DataBaseManager.queryRunner("cab_lab_db").query(connection, selectInfo, new Object[]{doctorSpecialization, firstName, secondName, middleName}, new BeanHandler<>(DoctorCard.class));
        }
    }


    @SneakyThrows
    public static void setDefaultPhotoDoctor(String doctorName, String doctorSpecialization, String defaultPhoto) {
        var setDefaultPhoto = "UPDATE employee_cards SET photo_uri = ? WHERE job = ? AND first_name = ? AND second_name = ? AND middle_name = ? ";
        try (var connection = DataBaseManager.getConnection("cab_lab_db")) {
            String[] nameParts = doctorName.split(" ");
            String firstName = nameParts[1];
            String secondName = nameParts[0];
            String middleName = nameParts[2];
            DataBaseManager.queryRunner("cab_lab_db").update(connection, setDefaultPhoto, defaultPhoto, doctorSpecialization, firstName, secondName, middleName);
        }
    }


    @SneakyThrows
    public static void setPhotoDoctor(String urlPhoto, String doctorName, String doctorSpecialization) {
        var setPhoto = "UPDATE employee_cards SET photo_uri = ? WHERE job = ? AND first_name = ? AND second_name = ? AND middle_name = ? ";
        try (var connection = DataBaseManager.getConnection("cab_lab_db")) {
            String[] nameParts = doctorName.split(" ");
            String firstName = nameParts[1];
            String secondName = nameParts[0];
            String middleName = nameParts[2];
            DataBaseManager.queryRunner("cab_lab_db").update(connection, setPhoto, urlPhoto, doctorSpecialization, firstName, secondName, middleName);
        }
    }

    @SneakyThrows
    public static Section selectSection(UUID doctorId) {
        var selectSection = "SELECT * FROM employee_details WHERE employee_card_employee_id = ? ";
        try (var connection = DataBaseManager.getConnection("cab_lab_db")) {
            //noinspection deprecation
            return DataBaseManager.queryRunner("cab_lab_db").query(connection, selectSection, doctorId, new BeanHandler<>(Section.class));
        }
    }

    @SneakyThrows
    public static void addSection(UUID doctorId, String title, int sequence) {
        var addSection = "INSERT INTO employee_details (employee_details_id,title,employee_card_employee_id,sequence,created_at,updated_at ) VALUES (?, ?, ?, ?, ?,?)";
        try (var connection = DataBaseManager.getConnection("cab_lab_db")) {
            var employee_details_id = generateUuid();
            var created_at = getDateTime();
            var updated_at = getDateTime();
            DataBaseManager.queryRunner("cab_lab_db").update(connection, addSection, employee_details_id, title, doctorId, sequence, created_at, updated_at);
        }
    }

    @SneakyThrows
    public static void clearSection(UUID doctorId) {
        var deleteSection = "DELETE FROM employee_details WHERE employee_card_employee_id = ? ";
        try (var connection = DataBaseManager.getConnection("cab_lab_db")) {
            DataBaseManager.queryRunner("cab_lab_db").update(connection, deleteSection, doctorId);
        }
    }

    @SneakyThrows
    public static Description selectDescription(UUID sectionId) {
        var selectDescription = "SELECT * FROM employee_expertises WHERE employee_details_id = ? ";
        try (var connection = DataBaseManager.getConnection("cab_lab_db")) {
            //noinspection deprecation
            return DataBaseManager.queryRunner("cab_lab_db").query(connection, selectDescription, sectionId, new BeanHandler<>(Description.class));
        }
    }

    @SneakyThrows
    public static void addDescription(UUID sectionId, String title, int sequence) {
        var addDescription = "INSERT INTO employee_expertises (employee_expertises_id,title,employee_details_id,sequence,created_at,updated_at ) VALUES (?, ?, ?, ?, ?,?)";
        try (var connection = DataBaseManager.getConnection("cab_lab_db")) {
            var employee_expertises_id = generateUuid();
            var created_at = getDateTime();
            var updated_at = getDateTime();
            DataBaseManager.queryRunner("cab_lab_db").update(connection, addDescription, employee_expertises_id, title, sectionId, sequence, created_at, updated_at);
        }
    }

    @SneakyThrows
    public static void clearDescription(UUID sectionId) {
        var deleteDescription = "DELETE FROM employee_expertises WHERE employee_details_id = ? ";
        try (var connection = DataBaseManager.getConnection("cab_lab_db")) {
            DataBaseManager.queryRunner("cab_lab_db").update(connection, deleteDescription, sectionId);
        }
    }

    @SneakyThrows
    public static BugReports selectBugReports() {
        var selectBugReports = "SELECT * FROM bug_reports";
        try (var connection = DataBaseManager.getConnection("cab_lab_db")) {
            return DataBaseManager.queryRunner("cab_lab_db").query(connection, selectBugReports, new BeanHandler<>(BugReports.class));
        }
    }

    @SneakyThrows
    public static void addBugReport(String message, String email, String author) {
        var addBugReport = "INSERT INTO bug_reports (id,message,email,author,created_at ) VALUES (?, ?, ?, ?, ?)";
        try (var connection = DataBaseManager.getConnection("cab_lab_db")) {
            var created_at = getDateTime();
            var id = generateUuid();
            DataBaseManager.queryRunner("cab_lab_db").update(connection, addBugReport, id, message, email, author, created_at);
        }
    }

    @SneakyThrows
    public static void clearAllBugReports() {
        try (var connection = DataBaseManager.getConnection("cab_lab_db")) {
            DataBaseManager.queryRunner("cab_lab_db").update(connection, "DELETE FROM bug_reports");
        }
    }

    @SneakyThrows
    public static Faq selectFaq() {
        var selectFaq = "SELECT * FROM faq";
        try (var connection = DataBaseManager.getConnection("cab_lab_db")) {
            return DataBaseManager.queryRunner("cab_lab_db").query(connection, selectFaq, new BeanHandler<>(Faq.class));
        }
    }

    @SneakyThrows
    public static Faq selectFaqBySequence(int sequence) {
        var selectFaq = "SELECT * FROM faq WHERE sequence = ?";
        try (var connection = DataBaseManager.getConnection("cab_lab_db")) {
            //noinspection deprecation
            return DataBaseManager.queryRunner("cab_lab_db").query(connection, selectFaq, sequence, new BeanHandler<>(Faq.class));
        }
    }

    @SneakyThrows
    public static void addFaq(int sequence, String question, String answer) {
        var addFaq = "INSERT INTO faq (id,question,answer,created_at,updated_at, group_id, sequence ) VALUES (?, ?, ?, ?, ?,?,?)";
        try (var connection = DataBaseManager.getConnection("cab_lab_db")) {
            var id = generateUuid();
            var created_at = getDateTime();
            var updated_at = getDateTime();
            var group_id = generateUuid();
            DataBaseManager.queryRunner("cab_lab_db").update(connection, addFaq, id, question, answer, created_at, updated_at, group_id, sequence);
        }
    }

    @SneakyThrows
    public static void addFaqSome(int sequence, String question, String answer) {
        var addFaq = "INSERT INTO faq (id,question,answer,created_at,updated_at, group_id, sequence ) VALUES (?, ?, ?, ?, ?,?,?)";
        try (var connection = DataBaseManager.getConnection("cab_lab_db")) {
            var id = generateUuid();
            var created_at = getDateTime();
            var updated_at = getDateTime();
            var group_id = generateUuid();
            DataBaseManager.queryRunner("cab_lab_db").update(connection, addFaq, id, question, answer, created_at, updated_at, group_id, sequence);
        }
    }

    @SneakyThrows
    public static void clearAllFaq() {
        try (var connection = DataBaseManager.getConnection("cab_lab_db")) {
            DataBaseManager.queryRunner("cab_lab_db").update(connection, "DELETE FROM faq");
        }
    }

    @SneakyThrows
    public static Feedback selectFeedback() {
        var selectFeedbackRequest = "SELECT * FROM feedbacks_employees";
        try (var connection = DataBaseManager.getConnection("cab_lab_db")) {
            return DataBaseManager.queryRunner("cab_lab_db").query(connection, selectFeedbackRequest, new BeanHandler<>(Feedback.class));
        }
    }

    @SneakyThrows
    public static void addFeedback(UUID doctorId, String author, String content, Boolean is_published) {
        var addFeedback = "INSERT INTO feedbacks_employees (id,employees_id,author,content,created_at,updated_at,is_published ) VALUES (?,?,?,?,?,?,?)";
        var id = generateUuid();
        var created_at = getDateTime();
        var updated_at = getDateTime();
        try (var connection = DataBaseManager.getConnection("cab_lab_db")) {
            DataBaseManager.queryRunner("cab_lab_db").update(connection, addFeedback, id, doctorId, author, content, created_at, updated_at, is_published);
        }
    }

    @SneakyThrows
    public static void addYesterdayFeedback(UUID doctorId, String author, String content, Boolean is_published) {
        var addFeedback = "INSERT INTO feedbacks_employees (id,employees_id,author,content,created_at,updated_at,is_published ) VALUES (?,?,?,?,?,?,?)";
        var id = generateUuid();
        var created_at = getPreviousDateTime();
        var updated_at = getPreviousDateTime();
        try (var connection = DataBaseManager.getConnection("cab_lab_db")) {
            DataBaseManager.queryRunner("cab_lab_db").update(connection, addFeedback, id, doctorId, author, content, created_at, updated_at, is_published);
        }
    }

    @SneakyThrows
    public static void publishedFeedback(UUID feedbackId) {
        var publishedFeedback = "UPDATE feedbacks_employees SET is_published = true WHERE id = ? ";
        try (var connection = DataBaseManager.getConnection("cab_lab_db")) {
            DataBaseManager.queryRunner("cab_lab_db").update(connection, publishedFeedback, feedbackId);
        }
    }

    @SneakyThrows
    public static void clearAllFeedback() {
        try (var connection = DataBaseManager.getConnection("cab_lab_db")) {
            DataBaseManager.queryRunner("cab_lab_db").update(connection, "DELETE FROM feedbacks_employees");
        }
    }


    @SneakyThrows
    public static ServiceCategories selectServicesCategories(String nameCategorySection) {
        var selectInfo = "SELECT * FROM service_categories WHERE name = ? ";
        try (var connection = DataBaseManager.getConnection("cab_lab_db")) {
            //noinspection deprecation
            return DataBaseManager.queryRunner("cab_lab_db").query(connection, selectInfo, nameCategorySection, new BeanHandler<>(ServiceCategories.class));
        }
    }

    @SneakyThrows
    public static PreparingDescriptions selectDescriptionService(String codeService) {
        var selectInfo = "SELECT * FROM preparing_descriptions WHERE service_code = ? ";
        try (var connection = DataBaseManager.getConnection("cab_lab_db")) {
            //noinspection deprecation
            return DataBaseManager.queryRunner("cab_lab_db").query(connection, selectInfo, codeService, new BeanHandler<>(PreparingDescriptions.class));
        }
    }


    @SneakyThrows
    public static AllServices selectAllService(String codeService) {
        var selectInfo = "SELECT * FROM all_services WHERE code = CAST(? AS ltree)";
        try (var connection = DataBaseManager.getConnection("cab_lab_db")) {
            //noinspection deprecation
            return DataBaseManager.queryRunner("cab_lab_db")
                    .query(connection, selectInfo, new Object[]{codeService}, new BeanHandler<>(AllServices.class));
        }
    }

    @SneakyThrows
    public static Logs selectLog(String userName) {
        var selectInfo = "SELECT * FROM logs WHERE user_name=? ORDER BY log_time DESC LIMIT 1";
        try (var connection = DataBaseManager.getConnection("lod_db")) {
            //noinspection deprecation
            return DataBaseManager.queryRunner("lod_db").query(connection, selectInfo, new Object[]{userName}, new BeanHandler<>(Logs.class));
        }
    }

    @SneakyThrows
    public static Logs selectLogPhoto(String userName) {
        var selectInfo = "SELECT * FROM (" +
                "    SELECT * FROM logs WHERE user_name=? ORDER BY log_time DESC LIMIT 2" +
                ") subquery ORDER BY log_time ASC LIMIT 1";
        try (var connection = DataBaseManager.getConnection("lod_db")) {
            //noinspection deprecation
            return DataBaseManager.queryRunner("lod_db").query(connection, selectInfo, new Object[]{userName}, new BeanHandler<>(Logs.class));
        }
    }

}

