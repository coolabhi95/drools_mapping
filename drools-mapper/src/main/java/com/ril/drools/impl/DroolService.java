package com.ril.drools.impl;


import com.ril.drools.IDroolService;
import com.ril.drools.domain.DroolFiles;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.Collection;
import com.ril.drools.repository.DroolsRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class DroolService implements IDroolService {
    private final DroolsRepository droolsRepository;

    @Override
    @Transactional
    public DroolFiles add(Long precedenceId, MultipartFile file) throws Exception {
        DroolFiles droolFiles = new DroolFiles();
        byte[] fileBytes = file.getBytes();
        droolFiles.setDroolsFile(fileBytes);
        droolFiles.setFileName(file.getOriginalFilename());
        droolFiles.setPrecedenceId(precedenceId);
        log.info("file is {}",droolFiles);
        return droolsRepository.save(droolFiles);
    }

    @Override
    @Transactional
    public Collection<DroolFiles> getAllRules() {
        log.info("getting files");
        return droolsRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(String fileName) throws Exception {
        // deleting from JPA
    }

    @Override
    @Transactional
    public void deleteAll() throws Exception {
        log.info("deleting files");
        droolsRepository.deleteAll();
    }

    @Override
    @Transactional
    public DroolFiles getByFileName(String fileName) throws Exception {
        return null;
    }

}

