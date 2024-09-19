package ru.adminlk.clinica.samsmu.utils.APIUtils;


import ru.adminlk.clinica.samsmu.test.BaseTest;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static ru.adminlk.clinica.samsmu.data.AppData.*;

import java.util.List;
import java.util.Map;
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
                .baseUri(URL_BACK)
                .header("Authorization", "Bearer " + BaseTest.token)
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
                .baseUri(URL_BACK)
                .header("Authorization", "Bearer " + BaseTest.token)
                .header("Environment", ENVIRONMENT)
                .contentType(ContentType.JSON)
                .body(jsonObject.toString())
                .when()
                .put("/api/services/category-preparing-description")
                .then()
                .statusCode(204);
    }

    public static void addRuleService(String code, String title, String description) {
        JsonObject section = new JsonObject();
        section.addProperty("title", title);
        section.addProperty("description", description);
        JsonArray sections = new JsonArray();
        sections.add(section);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("code", code);
        jsonObject.add("sections", sections);
        given()
                .baseUri(URL_BACK)
                .header("Authorization", "Bearer " + BaseTest.token)
                .header("Environment", ENVIRONMENT)
                .contentType(ContentType.JSON)
                .body(jsonObject.toString())
                .when()
                .put("/api/services/service-preparing-description")
                .then()
                .statusCode(204);
    }

    public static void deleteRuleService(String code) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("code", code);
        jsonObject.add("sections", sections);
        given()
                .baseUri(URL_BACK)
                .header("Authorization", "Bearer " + BaseTest.token)
                .header("Environment", ENVIRONMENT)
                .contentType(ContentType.JSON)
                .body(jsonObject.toString())
                .when()
                .put("/api/services/service-preparing-description")
                .then()
                .statusCode(204);
    }


    public static void addCategory(String nameCategory) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", nameCategory);
        given()
                .baseUri(URL_BACK)
                .header("Authorization", "Bearer " + BaseTest.token)
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
                .baseUri(URL_BACK)
                .queryParam("categoryId", id.toString())
                .header("Authorization", "Bearer " + BaseTest.token)
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
                .baseUri(URL_BACK)
                .header("Authorization", "Bearer " + BaseTest.token)
                .header("Environment", ENVIRONMENT)
                .contentType(ContentType.JSON)
                .body(jsonObject.toString())
                .when()
                .post("/api/services/admin/category")
                .then()
                .statusCode(201);
    }


    private static Response getAllServices() {
        return given()
                .baseUri(URL_BACK)
                .header("Authorization", "Bearer " + BaseTest.token)
                .header("Environment", ENVIRONMENT)
                .when()
                .get("/api/services/admin/all-services")
                .then()
                .statusCode(200)
                .extract()
                .response();
    }


    public static String getRandomService(String categoryName){
        Response response=getAllServices();
        List<String> childServices = response.jsonPath()
                .getList("categories.find { it.name == '" + categoryName + "' }.childServices.code");
        if (!childServices.isEmpty()) {
            int randomIndex = ThreadLocalRandom.current().nextInt(0, childServices.size());
            return childServices.get(randomIndex);
        }

        return null;
    }


    public static String getCategoryIdByName(String categoryName) {
        Response response=getAllServices();
        List<Map<String, Object>> categories = response.jsonPath().getList("categories");
        for (Map<String, Object> category : categories) {
            if (categoryName.equals(category.get("name"))) {
                return category.get("id").toString();
            }
        }
        return null;
    }

    public static void transferServices(String codeService, String sourceId, String targetId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("code", codeService);
        jsonObject.addProperty("sourceId", sourceId);
        jsonObject.addProperty("destinationId", targetId);
        given()
                .baseUri(URL_BACK)
                .header("Authorization", "Bearer " + BaseTest.token)
                .header("Environment", ENVIRONMENT)
                .contentType(ContentType.JSON)
                .body(jsonObject.toString())
                .when()
                .put("/api/services/admin/transfer")
                .then()
                .statusCode(204);
    }



}
