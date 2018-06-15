package ua.marchenko.a_rest_tests_bugify_apache_client.helpers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonHelper {

    public JsonObject stringJsonToJsonObject(String json) {
        return new JsonParser().parse(json).getAsJsonObject();
    }
}
