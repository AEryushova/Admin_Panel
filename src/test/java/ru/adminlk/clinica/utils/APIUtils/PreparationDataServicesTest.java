package ru.adminlk.clinica.utils.APIUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import static ru.adminlk.clinica.data.AppData.*;

import com.google.gson.Gson;
import ru.adminlk.clinica.test.BaseTest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;


import static ru.adminlk.clinica.test.BaseTest.token;

public class PreparationDataServicesTest {

    private static final JsonArray SECTIONS = new JsonArray();
    private static final Gson GSON = new Gson();


    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static void addRuleCategory(UUID id, String title, String description) {
        JsonObject section = new JsonObject();
        section.addProperty("title", title);
        section.addProperty("description", description);
        JsonArray sectionsArray = new JsonArray();
        sectionsArray.add(section);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id.toString());
        jsonObject.add("sections", sectionsArray);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL_BACK + "/api/services/category-preparing-description"))
                .header("Authorization", "Bearer " + token)
                .header("Environment", ENVIRONMENT)
                .header("Content-Type", "application/json")
                .PUT(BodyPublishers.ofString(jsonObject.toString()))
                .build();
        try {
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 204) {
                System.out.println("Rule for category/section/subsection added successfully");
            } else {
                System.out.println("Failed to add rule for category/section/subsection. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void deleteRuleCategory(UUID id) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id.toString());
        jsonObject.add("sections", SECTIONS);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL_BACK + "/api/services/category-preparing-description"))
                .header("Authorization", "Bearer " + BaseTest.token)
                .header("Environment", ENVIRONMENT)
                .header("Content-Type", "application/json")
                .PUT(BodyPublishers.ofString(jsonObject.toString()))
                .build();
        try {
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 204) {
                System.out.println("Rule for category/section/subsection deleted successfully");
            } else {
                System.out.println("Failed to delete rule for category/section/subsection. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void addRuleService(String code, String title, String description) {
        JsonObject section = new JsonObject();
        section.addProperty("title", title);
        section.addProperty("description", description);
        JsonArray sectionsArray = new JsonArray();
        sectionsArray.add(section);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("code", code);
        jsonObject.add("sections", sectionsArray);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL_BACK + "/api/services/service-preparing-description"))
                .header("Authorization", "Bearer " + token)
                .header("Environment", ENVIRONMENT)
                .header("Content-Type", "application/json")
                .PUT(BodyPublishers.ofString(jsonObject.toString()))
                .build();
        try {
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 204) {
                System.out.println("Rule for service added successfully");
            } else {
                System.out.println("Failed to add rule for service. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void deleteRuleService(String code) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("code", code);
        jsonObject.add("sections", SECTIONS);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL_BACK + "/api/services/service-preparing-description"))
                .header("Authorization", "Bearer " + BaseTest.token)
                .header("Environment", ENVIRONMENT)
                .header("Content-Type", "application/json")
                .PUT(BodyPublishers.ofString(jsonObject.toString()))
                .build();
        try {
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 204) {
                System.out.println("Rule for service deleted successfully");
            } else {
                System.out.println("Failed to delete rule for service. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void addCategory(String nameCategory) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", nameCategory);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL_BACK + "/api/services/admin/category"))
                .header("Authorization", "Bearer " + token)
                .header("Environment", ENVIRONMENT)
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(jsonObject.toString()))
                .build();
        try {
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 201) {
                System.out.println("Category added successfully");
            } else {
                System.out.println("Failed to add category. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void deleteCategory(UUID id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL_BACK + "/api/services/admin/category?categoryId=" + id))
                .header("Authorization", "Bearer " + token)
                .header("Environment", ENVIRONMENT)
                .DELETE()
                .build();
        try {
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 204) {
                System.out.println("Category/section/subsection deleted successfully");
            } else {
                System.out.println("Failed to delete category. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void addSection(String nameSection, UUID parentId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("parentId", parentId.toString());
        jsonObject.addProperty("name", nameSection);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL_BACK + "/api/services/admin/category"))
                .header("Authorization", "Bearer " + BaseTest.token)
                .header("Environment", ENVIRONMENT)
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(jsonObject.toString()))
                .build();
        try {
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 201) {
                System.out.println("Section/subsection added successfully");
            } else {
                System.out.println("Failed to add section/subsection. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static HttpResponse<String> getAllServices() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL_BACK + "/api/services/admin/all-services"))
                .header("Authorization", "Bearer " + BaseTest.token)
                .header("Environment", ENVIRONMENT)
                .GET()
                .build();
        return HTTP_CLIENT.send(request, BodyHandlers.ofString());
    }


    public static String getRandomService2(String categoryName) throws IOException, InterruptedException {
        HttpResponse<String> response = getAllServices();
        JsonObject jsonResponse = GSON.fromJson(response.body(), JsonObject.class);
        JsonArray categories = jsonResponse.getAsJsonArray("categories");
        for (var category : categories) {
            JsonObject categoryObj = category.getAsJsonObject();
            if (categoryObj.get("name").getAsString().equals(categoryName)) {
                JsonArray childServices = categoryObj.getAsJsonArray("childServices");
                if (!childServices.isEmpty()) {
                    int randomIndex = ThreadLocalRandom.current().nextInt(0, childServices.size());
                    return childServices.get(randomIndex).getAsJsonObject().get("code").getAsString();
                }
            }
        }
        return null;
    }


    public static String getCategoryIdByName(String categoryName) throws IOException, InterruptedException {
        HttpResponse<String> response = getAllServices();
        JsonObject jsonResponse = GSON.fromJson(response.body(), JsonObject.class);
        JsonArray categories = jsonResponse.getAsJsonArray("categories");
        for (var category : categories) {
            JsonObject categoryObj = category.getAsJsonObject();
            if (categoryName.equals(categoryObj.get("name").getAsString())) {
                return categoryObj.get("id").getAsString();
            }
        }
        return null;
    }


    public static void transferServices(String codeService, String sourceId, String targetId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("code", codeService);
        jsonObject.addProperty("sourceId", sourceId);
        jsonObject.addProperty("destinationId", targetId);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL_BACK + "/api/services/admin/transfer"))
                .header("Authorization", "Bearer " + BaseTest.token)
                .header("Environment", ENVIRONMENT)
                .header("Content-Type", "application/json")
                .PUT(BodyPublishers.ofString(jsonObject.toString()))
                .build();
        try {
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 204) {
                System.out.println("Service transferred successfully");
            } else {
                System.out.println("Failed to transfer section. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
