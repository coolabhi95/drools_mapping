package com.ril.drools;

import com.google.gson.JsonElement;
import com.ril.drools.model.MappingInfo;

public interface IDroolsMappingService {
    public JsonElement map(JsonElement requestJson, MappingInfo mappingInfo, JsonElement responseJson);

}
