package admin.utils.APIUtils;


import admin.config.AppConfig;
import admin.utils.testUtils.BrowserManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.restassured.http.ContentType;


import java.util.UUID;

import static io.restassured.RestAssured.given;

public class PreparationDataServicesTest {

    private static final Gson gson = new Gson();
    private static final JsonObject section = new JsonObject();
    private static final JsonObject jsonObject = new JsonObject();
    private static final JsonArray sections = new JsonArray();


    public static void addRuleCategory(UUID id, String title, String description) {
        String rule = getAddRuleJson(id, title, description);
        given()
                .baseUri(AppConfig.getURI_ADMIN_PANEL())
                .header("Authorization", "Bearer " + BrowserManager.token)
                .header("Environment", AppConfig.getENVIRONMENT())
                .contentType(ContentType.JSON)
                .body(rule)
                .when()
                .put("/api/services/category-preparing-description")
                .then()
                .statusCode(204);
    }


    private static String getAddRuleJson(UUID id, String title, String description) {
        section.addProperty("title", title);
        section.addProperty("description", description);
        sections.add(section);
        jsonObject.addProperty("id", id.toString());
        jsonObject.add("sections", sections);
        return gson.toJson(jsonObject);
    }


    public static void deleteRuleCategory(UUID id) {
        String rule = getDeleteRuleJson(id);
        given()
                .baseUri(AppConfig.getURI_ADMIN_PANEL())
                .header("Authorization", "Bearer " + BrowserManager.token)
                .header("Environment", AppConfig.getENVIRONMENT())
                .contentType(ContentType.JSON)
                .body(rule)
                .when()
                .put("/api/services/category-preparing-description")
                .then()
                .statusCode(204);
    }

    private static String getDeleteRuleJson(UUID id) {
        jsonObject.addProperty("id", id.toString());
        jsonObject.add("sections", sections);
        return gson.toJson(jsonObject);
    }

    public static void addCategory(String nameCategory) {
        String categoryName = addCategoryJson(nameCategory);
        given()
                .baseUri(AppConfig.getURI_ADMIN_PANEL())
                .header("Authorization", "Bearer " + BrowserManager.token)
                .header("Environment", AppConfig.getENVIRONMENT())
                .contentType(ContentType.JSON)
                .body(categoryName)
                .when()
                .post("/api/services/admin/category")
                .then()
                .statusCode(201);
    }

    private static String addCategoryJson(String nameCategory) {
        jsonObject.addProperty("name", nameCategory);
        return gson.toJson(jsonObject);
    }

    public static void deleteCategory(UUID id) {
        given()
                .baseUri(AppConfig.getURI_ADMIN_PANEL())
                .queryParam("categoryId", id.toString())
                .header("Authorization", "Bearer " + BrowserManager.token)
                .header("Environment", AppConfig.getENVIRONMENT())
                .when()
                .delete("/api/services/admin/category")
                .then()
                .statusCode(204);
    }

    public static void addSection(String nameSection,UUID parentId) {
        given()
                .baseUri(AppConfig.getURI_ADMIN_PANEL())
                .header("Authorization", "Bearer " + BrowserManager.token)
                .header("Environment", AppConfig.getENVIRONMENT())
                .contentType(ContentType.JSON)
                .body(addSectionJson(nameSection,parentId))
                .when()
                .post("/api/services/admin/category")
                .then()
                .statusCode(201);
    }

    private static String addSectionJson(String nameSection,UUID parentId) {
        jsonObject.addProperty("parentId", parentId.toString());
        jsonObject.addProperty("name", nameSection);
        return gson.toJson(jsonObject);
    }


}
