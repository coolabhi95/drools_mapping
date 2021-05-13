package com.ril.drools.impl;

import com.google.gson.JsonElement;
import com.ril.drools.IDroolsMappingService;
import com.ril.drools.domain.DroolFiles;
import com.ril.drools.model.MappingInfo;
import com.ril.drools.service.KieService;
import com.ril.drools.utils.DroolsObjectUtils;
import lombok.RequiredArgsConstructor;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
public class DroolsMappingService implements IDroolsMappingService {
  private final KieService kieService = new KieService();
  private final DroolService droolService;

  @Override
  public JsonElement map(
      JsonElement requestJson, MappingInfo mappingInfo, JsonElement responseJson) {
    DroolsObjectUtils request = new DroolsObjectUtils(requestJson.toString());
    Map<Long, Resource> resourceMap = new HashMap<>();
    List<DroolFiles> droolFiles = (List<DroolFiles>) droolService.getAllRules();
    droolFiles.forEach(
        dfile -> {
          Resource resource =
              ResourceFactory.newInputStreamResource(
                  new ByteArrayInputStream(dfile.getDroolsFile()), "UTF-8");
          resource.setSourcePath(dfile.getFileName());
          resourceMap.put(dfile.getPrecedenceId(), resource);
        });
    KieSession kieSession = kieService.kieSession();
    kieSession.insert(request);
    kieSession.setGlobal("pdpResponse", responseJson);
    kieSession.setGlobal("request", request);
    kieSession.fireAllRules();
    return responseJson;
  }
    }

