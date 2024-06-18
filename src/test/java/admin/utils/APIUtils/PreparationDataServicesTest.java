package admin.utils.APIUtils;


import admin.data.AppConfig;
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
                .baseUri(AppConfig.getUriAdminPanel())
                .header("Authorization", "Bearer " + BrowserManager.token)
                .header("Environment", AppConfig.getEnvironment())
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
                .baseUri(AppConfig.getUriAdminPanel())
                .header("Authorization", "Bearer " + BrowserManager.token)
                .header("Environment", AppConfig.getEnvironment())
                .contentType(ContentType.JSON)
                .body(rule)
                .when()
                .put("/api/services/category-preparing-description")
                .then()
                .statusCode(204);
    }

    public static String getDeleteRuleJson(UUID id) {
        jsonObject.addProperty("id", id.toString());
        jsonObject.add("sections", sections);
        return gson.toJson(jsonObject);
    }

}
