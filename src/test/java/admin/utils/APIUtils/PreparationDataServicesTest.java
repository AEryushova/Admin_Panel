package admin.utils.APIUtils;


import admin.utils.testUtils.BrowserManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.qameta.allure.internal.shadowed.jackson.databind.JsonNode;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static appData.AppData.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;

public class PreparationDataServicesTest {

    private static final JsonArray sections = new JsonArray();


    public static void addRuleCategory(UUID id, String title, String description) {
        JsonObject section = new JsonObject();
        section.addProperty("title", title);
        section.addProperty("description", description);
        JsonArray sections = new JsonArray();
        sections.add(section);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id.toString());
        jsonObject.add("sections", sections);
        given()
                .baseUri(URI_ADMIN_PANEL)
                .header("Authorization", "Bearer " + BrowserManager.token)
                .header("Environment", ENVIRONMENT)
                .contentType(ContentType.JSON)
                .body(jsonObject.toString())
                .when()
                .put("/api/services/category-preparing-description")
                .then()
                .statusCode(204);
    }


    public static void deleteRuleCategory(UUID id) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id.toString());
        jsonObject.add("sections", sections);
        given()
                .baseUri(URI_ADMIN_PANEL)
                .header("Authorization", "Bearer " + BrowserManager.token)
                .header("Environment", ENVIRONMENT)
                .contentType(ContentType.JSON)
                .body(jsonObject.toString())
                .when()
                .put("/api/services/category-preparing-description")
                .then()
                .statusCode(204);
    }


    public static void addCategory(String nameCategory) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", nameCategory);
        given()
                .baseUri(URI_ADMIN_PANEL)
                .header("Authorization", "Bearer " + BrowserManager.token)
                .header("Environment", ENVIRONMENT)
                .contentType(ContentType.JSON)
                .body(jsonObject.toString())
                .when()
                .post("/api/services/admin/category")
                .then()
                .statusCode(201);
    }


    public static void deleteCategory(UUID id) {
        given()
                .baseUri(URI_ADMIN_PANEL)
                .queryParam("categoryId", id.toString())
                .header("Authorization", "Bearer " + BrowserManager.token)
                .header("Environment", ENVIRONMENT)
                .when()
                .delete("/api/services/admin/category")
                .then()
                .statusCode(204);
    }

    public static void addSection(String nameSection, UUID parentId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("parentId", parentId.toString());
        jsonObject.addProperty("name", nameSection);
        given()
                .baseUri(URI_ADMIN_PANEL)
                .header("Authorization", "Bearer " + BrowserManager.token)
                .header("Environment", ENVIRONMENT)
                .contentType(ContentType.JSON)
                .body(jsonObject.toString())
                .when()
                .post("/api/services/admin/category")
                .then()
                .statusCode(201);
    }


    public static String getRandomOtherService(String categoryId) {
        Response response = given()
                .baseUri(URI_ADMIN_PANEL)
                .header("Authorization", "Bearer " + BrowserManager.token)
                .header("Environment", ENVIRONMENT)
                .when()
                .get("/api/services/admin/all-services")
                .then()
                .statusCode(200)
                .extract()
                .response();

        return getRandomServiceCodeFromOtherCategory(response, categoryId);
    }

    private static String getRandomServiceCodeFromOtherCategory(Response response, String categoryId) {
        List<String> childServices = response.jsonPath()
                .getList("categories.find { it.id == '" + categoryId + "' }.childServices.code");
        if (!childServices.isEmpty()) {
            int randomIndex = ThreadLocalRandom.current().nextInt(0, childServices.size());
            return childServices.get(randomIndex);
        }

        return null;
    }



    public static void transferServices(String codeService, String sourceId, String targetId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("code", codeService);
        jsonObject.addProperty("sourceId", sourceId);
        jsonObject.addProperty("destinationId", targetId);
        given()
                .baseUri(URI_ADMIN_PANEL)
                .header("Authorization", "Bearer " + BrowserManager.token)
                .header("Environment", ENVIRONMENT)
                .contentType(ContentType.JSON)
                .body(jsonObject.toString())
                .when()
                .put("/api/services/admin/transfer")
                .then()
                .statusCode(204);
    }



}
