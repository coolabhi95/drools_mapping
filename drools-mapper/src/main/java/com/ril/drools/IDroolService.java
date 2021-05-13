package com.ril.drools;

import com.ril.drools.domain.DroolFiles;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

 public interface IDroolService {
    DroolFiles add(Long precedenceId, MultipartFile file) throws Exception;

    Collection<DroolFiles> getAllRules();

    void delete(String fileName) throws Exception;

    void deleteAll() throws Exception;

    DroolFiles getByFileName(String fileName) throws Exception;

}
