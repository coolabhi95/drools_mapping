package com.ril.drools.service;

import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

import java.util.Map;
import java.util.stream.LongStream;

public class KieService {

    public KieFileSystem kieFileSystem() {
        KieFileSystem kieFileSystem = getKieServices().newKieFileSystem();
        kieFileSystem
                .write(ResourceFactory.newClassPathResource("mapper.drl", "UTF-8"));
        return kieFileSystem;
    }

    public KieFileSystem kieFileSystem(Map<Long, Resource> resourceMap) {
        KieFileSystem kieFileSystem = getKieServices().newKieFileSystem();
        LongStream.range(1,resourceMap.size()+1)
                .forEach(precedence->kieFileSystem
                        .write(resourceMap.get(precedence)));
        return kieFileSystem;
    }


    private KieServices getKieServices() {
        return KieServices.get();
    }

    public KieContainer kieContainer() {
        KieRepository kieRepository = getKieServices().getRepository();

        kieRepository.addKieModule(kieRepository::getDefaultReleaseId);

        return getKieServices().newKieContainer(kieRepository.getDefaultReleaseId());
    }
    public KieSession kieSession() {
        return kieContainer().newKieSession();
    }

}