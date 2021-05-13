package com.ril.drools.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ril.drools.stream.MapperStream;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
public class DroolsObjectUtils {

    public static final JsonParser parser = new JsonParser();
    JsonElement root;
    public DroolsObjectUtils(String json) {
        root = parser.parse(json);
    }


    public static JsonElement getJsonElement(String jsonPath, JsonElement jsonElement) {
        String[] paths = jsonPath.split("/");
        JsonElement node = jsonElement;
        log.info("nodeis {}",node);
        for (int i = 0; i < paths.length-1; i++)
            node = node.getAsJsonObject().getAsJsonObject(paths[i]);
        log.info("nodesis {}",node);
        return node.getAsJsonObject().get(paths[paths.length - 1]);
    }

    public String getValue(String jsonPath) {
        return getValue(jsonPath,root);
    }
    public String getValue(String jsonPath,JsonElement jsonElement) {
        return getJsonElement(jsonPath,jsonElement).getAsString();
    }

    public Optional<JsonElement> getOptionalValue(String jsonPath) {
        return Optional.ofNullable(getJsonElement(jsonPath,root));
    }

    public static Optional<JsonElement> getOptionalValue(String jsonPath, JsonElement jsonElement) {
        return Optional.ofNullable(getJsonElement(jsonPath,jsonElement));
    }
    public MapperStream<JsonElement> getOptionalValueStream(String jsonPath) {
        return getOptionalValueStream(jsonPath,root);
    }
    public MapperStream<JsonElement> getOptionalValueStream(String jsonPath, JsonElement jsonElement) {
        Stream<JsonElement> jsonElementOptional = StreamSupport.stream(getJsonElement(jsonPath,jsonElement).getAsJsonArray().spliterator(),false);
        return new MapperStream<>(jsonElementOptional);

    }
    public JsonObject getObject(String jsonPath, JsonElement response){
        String[] paths = jsonPath.split("/");
        JsonObject node = response.getAsJsonObject();
        for (int i=0;i<paths.length-1;i++) {
            if(node.getAsJsonObject(paths[i])==null)
                node.add(paths[i],new JsonObject());
            node = node.getAsJsonObject(paths[i]);
        }
        return node;
    }

    public  JsonElement setFields(String jsonPath, JsonElement value, JsonElement response) {
        String[] paths = jsonPath.split("/");
        JsonObject node = getObject(jsonPath,response);
        node.add(paths[paths.length-1], value);
        return node;
    }
    public  JsonElement setFields(String jsonPath, String preValue, JsonElement value, String addedValue, JsonElement response) {
        JsonElement jsonElement =parser.parse(preValue+value.getAsString()+addedValue);
        return setFields(jsonPath,jsonElement,response);
    }
    public  JsonElement setFieldsInArray(String jsonPath, String jsonPathArray, JsonElement value, JsonElement response) {
        JsonObject node = getObject(jsonPath,response);
        if(node.getAsJsonArray(jsonPathArray)==null)
            node.add(jsonPathArray,new JsonArray());
        node.getAsJsonArray(jsonPathArray).add(value);
        return node;
    }
    public JsonArray getList(String jsonPath) {
        String[] paths = jsonPath.split("/");
        JsonElement node = root;
        for (int i=0;i<paths.length-1;i++) {
            node = node.getAsJsonObject().getAsJsonObject(paths[i]);
        }
        return node.getAsJsonObject().getAsJsonArray(paths[paths.length-1]);
    }
    public JsonObject mapToObject(Map.Entry<String,JsonElement> map){
        JsonObject jsonElement = new JsonObject();
        jsonElement.addProperty("name",map.getKey());
        jsonElement.addProperty("featureValue",map.getValue().getAsJsonObject().get("attributeValue").getAsString());
        return jsonElement;
    }

    private String getSeverity(String severity){
        return "warning";
    }
}
