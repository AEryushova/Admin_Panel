package admin.utils.dbUtils;

import admin.data.DataConfig;
import admin.utils.dbUtils.dbaseData.*;
import admin.utils.testUtils.DataHelper;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.util.UUID;

public class DataBaseQuery {

    @SneakyThrows
    public static Admins selectAdmin(String login) {
        var selectAdmin = "SELECT * FROM platform.users INNER JOIN platform.users_roles ON users.id=users_roles.user_id WHERE username = ?";
        var connection = DataBaseManager.getConnection("platform_db");
        return DataBaseManager.queryRunner("platform_db").query(connection, selectAdmin, login, new BeanHandler<>(Admins.class));
    }

    @SneakyThrows
    public static DoctorCard selectInfoDoctor() {
        var selectInfo = "SELECT * FROM employee_cards WHERE job = ? AND first_name = ? AND second_name = ? AND middle_name = ? ";
        var connection = DataBaseManager.getConnection("cab_lab_db");
        var doctorSpecialization = DataConfig.DataTest.getDoctorSpecialization();
        var doctorName = DataConfig.DataTest.getDoctor();
        String[] nameParts = doctorName.split(" ");
        String firstName = nameParts[1];
        String secondName = nameParts[0];
        String middleName = nameParts[2];
        return DataBaseManager.queryRunner("cab_lab_db").query(connection, selectInfo, new Object[]{doctorSpecialization, firstName, secondName, middleName}, new BeanHandler<>(DoctorCard.class));
    }


    @SneakyThrows
    public static void setDefaultPhotoDoctor() {
        var setDefaultPhoto = "UPDATE employee_cards SET photo_uri = ? WHERE job = ? AND first_name = ? AND second_name = ? AND middle_name = ? ";
        var connection = DataBaseManager.getConnection("cab_lab_db");
        var doctorName = DataConfig.DataTest.getDoctor();
        var doctorSpecialization = DataConfig.DataTest.getDoctorSpecialization();
        var defaultPhoto = DataConfig.DataTest.getDefaultPhoto();
        String[] nameParts = doctorName.split(" ");
        String firstName = nameParts[1];
        String secondName = nameParts[0];
        String middleName = nameParts[2];
        DataBaseManager.queryRunner("cab_lab_db").update(connection, setDefaultPhoto, defaultPhoto, doctorSpecialization, firstName, secondName, middleName);
    }


    @SneakyThrows
    public static void setPhotoDoctor(String urlPhoto) {
        var setPhoto = "UPDATE employee_cards SET photo_uri = ? WHERE job = ? AND first_name = ? AND second_name = ? AND middle_name = ? ";
        var connection = DataBaseManager.getConnection("cab_lab_db");
        var doctorName = DataConfig.DataTest.getDoctor();
        var doctorSpecialization = DataConfig.DataTest.getDoctorSpecialization();
        String[] nameParts = doctorName.split(" ");
        String firstName = nameParts[1];
        String secondName = nameParts[0];
        String middleName = nameParts[2];
        DataBaseManager.queryRunner("cab_lab_db").update(connection, setPhoto, urlPhoto, doctorSpecialization, firstName, secondName, middleName);
    }

    @SneakyThrows
    public static Section selectSection(UUID doctorId) {
        var selectSection = "SELECT * FROM employee_details WHERE employee_card_employee_id = ? ";
        var connection = DataBaseManager.getConnection("cab_lab_db");
        return DataBaseManager.queryRunner("cab_lab_db").query(connection, selectSection, doctorId, new BeanHandler<>(Section.class));
    }

    @SneakyThrows
    public static void addSection(UUID doctorId) {
        var addSection = "INSERT INTO employee_details (employee_details_id,title,employee_card_employee_id,sequence,created_at,updated_at ) VALUES (?, ?, ?, ?, ?,?)";
        var connection = DataBaseManager.getConnection("cab_lab_db");
        var employee_details_id = DataHelper.generateUuid();
        var title = DataConfig.DataTest.getSection();
        var sequence = 0;
        var created_at = DataHelper.generateDateTime();
        var updated_at = DataHelper.generateDateTime();
        DataBaseManager.queryRunner("cab_lab_db").update(connection, addSection, employee_details_id,title,doctorId,sequence, created_at,updated_at );
    }

    @SneakyThrows
    public static void clearSection(UUID doctorId) {
        var deleteSection = "DELETE FROM employee_details WHERE employee_card_employee_id = ? ";
        var connection = DataBaseManager.getConnection("cab_lab_db");
        DataBaseManager.queryRunner("cab_lab_db").update(connection, deleteSection, doctorId);
    }

    @SneakyThrows
    public static Description selectDescription(UUID sectionId) {
        var selectDescription = "SELECT * FROM employee_expertises WHERE employee_details_id = ? ";
        var connection = DataBaseManager.getConnection("cab_lab_db");
        return DataBaseManager.queryRunner("cab_lab_db").query(connection, selectDescription, sectionId, new BeanHandler<>(Description.class));
    }

    @SneakyThrows
    public static void addDescription(UUID sectionId) {
        var addDescription = "INSERT INTO employee_expertises (employee_expertises_id,title,employee_details_id,sequence,created_at,updated_at ) VALUES (?, ?, ?, ?, ?,?)";
        var connection = DataBaseManager.getConnection("cab_lab_db");
        var employee_expertises_id = DataHelper.generateUuid();
        var title = DataConfig.DataTest.getDescription();
        var sequence = 0;
        var created_at = DataHelper.generateDateTime();
        var updated_at = DataHelper.generateDateTime();
        DataBaseManager.queryRunner("cab_lab_db").update(connection, addDescription, employee_expertises_id,title,sectionId,sequence, created_at,updated_at );
    }

    @SneakyThrows
    public static void clearDescription(UUID sectionId) {
        var deleteDescription = "DELETE FROM employee_expertises WHERE employee_details_id = ? ";
        var connection = DataBaseManager.getConnection("cab_lab_db");
        DataBaseManager.queryRunner("cab_lab_db").update(connection, deleteDescription, sectionId);
    }

    @SneakyThrows
    public static BugReports selectBugReports() {
        var selectBugReports = "SELECT * FROM bug_reports";
        var connection = DataBaseManager.getConnection("cab_lab_db");
        return DataBaseManager.queryRunner("cab_lab_db").query(connection, selectBugReports, new BeanHandler<>(BugReports.class));
    }

    @SneakyThrows
    public static void addBugReport() {
        var addBugReport = "INSERT INTO bug_reports (id,message,email,author,created_at ) VALUES (?, ?, ?, ?, ?)";
        var connection = DataBaseManager.getConnection("cab_lab_db");
        var message = DataConfig.DataTest.getMessageBugReport();
        var email = DataConfig.DataTest.getEmailPatient();
        var author = DataConfig.DataTest.getNamePatient();
        var created_at = DataHelper.generateDateTime();
        var id = DataHelper.generateUuid();
        DataBaseManager.queryRunner("cab_lab_db").update(connection, addBugReport, id, message, email, author, created_at);
    }

    @SneakyThrows
    public static void clearAllBugReports() {
        var connection = DataBaseManager.getConnection("cab_lab_db");
        DataBaseManager.queryRunner("cab_lab_db").update(connection, "DELETE FROM bug_reports");
    }

    @SneakyThrows
    public static Faq selectFaq() {
        var selectFaq = "SELECT * FROM faq";
        var connection = DataBaseManager.getConnection("cab_lab_db");
        return DataBaseManager.queryRunner("cab_lab_db").query(connection, selectFaq, new BeanHandler<>(Faq.class));
    }

    @SneakyThrows
    public static Faq selectFaqBySequence(int sequence) {
        var selectFaq = "SELECT * FROM faq WHERE sequence = ?";
        var connection = DataBaseManager.getConnection("cab_lab_db");
        return DataBaseManager.queryRunner("cab_lab_db").query(connection, selectFaq, sequence, new BeanHandler<>(Faq.class));
    }

    @SneakyThrows
    public static void addFaq(int sequence) {
        var addFaq = "INSERT INTO faq (id,question,answer,created_at,updated_at, group_id, sequence ) VALUES (?, ?, ?, ?, ?,?,?)";
        var connection = DataBaseManager.getConnection("cab_lab_db");
        var question=DataConfig.DataTest.getQuestion();
        var answer= DataConfig.DataTest.getAnswer();
        var id = DataHelper.generateUuid();
        var created_at = DataHelper.generateDateTime();
        var updated_at = DataHelper.generateDateTime();
        var group_id = DataHelper.generateUuid();
        DataBaseManager.queryRunner("cab_lab_db").update(connection, addFaq, id, question, answer, created_at, updated_at, group_id, sequence);
    }

    @SneakyThrows
    public static void addFaqSome(int sequence,String question,String answer) {
        var addFaq = "INSERT INTO faq (id,question,answer,created_at,updated_at, group_id, sequence ) VALUES (?, ?, ?, ?, ?,?,?)";
        var connection = DataBaseManager.getConnection("cab_lab_db");
        var id = DataHelper.generateUuid();
        var created_at = DataHelper.generateDateTime();
        var updated_at = DataHelper.generateDateTime();
        var group_id = DataHelper.generateUuid();
        DataBaseManager.queryRunner("cab_lab_db").update(connection, addFaq, id, question, answer, created_at, updated_at, group_id, sequence);
    }

    @SneakyThrows
    public static void clearAllFaq() {
        var connection = DataBaseManager.getConnection("cab_lab_db");
        DataBaseManager.queryRunner("cab_lab_db").update(connection, "DELETE FROM faq");
    }

    @SneakyThrows
    public static Feedback selectFeedback() {
        var selectFeedbackRequest = "SELECT * FROM feedbacks_employees";
        var connection = DataBaseManager.getConnection("cab_lab_db");
        return DataBaseManager.queryRunner("cab_lab_db").query(connection, selectFeedbackRequest, new BeanHandler<>(Feedback.class));
    }

    @SneakyThrows
    public static void addFeedback(UUID doctorId) {
        var addFeedback = "INSERT INTO feedbacks_employees (id,employees_id,author,content,created_at,updated_at,is_published ) VALUES (?,?,?,?,?,?,?)";
        var id = DataHelper.generateUuid();
        var author= DataConfig.DataTest.getNamePatient();
        var content = DataConfig.DataTest.getFeedback();
        var created_at = DataHelper.generateDateTime();
        var updated_at = DataHelper.generateDateTime();
        var is_published= false;
        var connection = DataBaseManager.getConnection("cab_lab_db");
        DataBaseManager.queryRunner("cab_lab_db").update(connection, addFeedback,id,doctorId,author,content,created_at,updated_at,is_published);
    }

    @SneakyThrows
    public static void addYesterdayFeedback(UUID doctorId) {
        var addFeedback = "INSERT INTO feedbacks_employees (id,employees_id,author,content,created_at,updated_at,is_published ) VALUES (?,?,?,?,?,?,?)";
        var id = DataHelper.generateUuid();
        var author= DataConfig.DataTest.getNamePatient();
        var content = DataConfig.DataTest.getFeedback();
        var created_at = DataHelper.generatePreviousDateTime();
        var updated_at = DataHelper.generatePreviousDateTime();
        var is_published= false;
        var connection = DataBaseManager.getConnection("cab_lab_db");
        DataBaseManager.queryRunner("cab_lab_db").update(connection, addFeedback,id,doctorId,author,content,created_at,updated_at,is_published);
    }

    @SneakyThrows
    public static void publishedFeedback(UUID feedbackId) {
        var publishedFeedback = "UPDATE feedbacks_employees SET is_published = true WHERE id = ? ";
        var connection = DataBaseManager.getConnection("cab_lab_db");
        DataBaseManager.queryRunner("cab_lab_db").update(connection, publishedFeedback,feedbackId);
    }

    @SneakyThrows
    public static void clearAllFeedback() {
        var connection = DataBaseManager.getConnection("cab_lab_db");
        DataBaseManager.queryRunner("cab_lab_db").update(connection, "DELETE FROM feedbacks_employees");
    }


    @SneakyThrows
    public static ServiceCategories selectRulesPreparing() {
        var selectInfo = "SELECT * FROM service_categories WHERE name = ? ";
        var connection = DataBaseManager.getConnection("cab_lab_db");
        var nameCategory = DataConfig.DataTest.getCategoryName();
        return DataBaseManager.queryRunner("cab_lab_db").query(connection, selectInfo, nameCategory, new BeanHandler<>(ServiceCategories.class));
    }


}