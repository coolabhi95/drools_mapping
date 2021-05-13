package com.ril.drools.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ril.drools.IDroolService;
import com.ril.drools.domain.DroolFiles;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class DroolsController {

    private final ObjectMapper objectMapper;

    private final IDroolService droolsService;

    @PostMapping(path = "/drools/add", produces = "application/json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public DroolFiles addRule(
            @RequestHeader Map<String, String> headerMap,
            @RequestParam(value = "precedence") Long precdence,
            @RequestParam(value = "file") MultipartFile droolsFile) throws Exception {
        return droolsService.add(precdence,droolsFile);
    }

    @GetMapping(path = "/drools/get")
    public Collection<DroolFiles> getAllRules(){
        return droolsService.getAllRules();
    }

    @DeleteMapping(path = "/drools/flush")
    public void deleteFiles() throws NotFoundException{droolsService.deleteAll();}

}
