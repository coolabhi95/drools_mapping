package com.ril.drools.repository;

import com.ril.drools.domain.DroolFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DroolsRepository extends JpaRepository<DroolFiles, Long> {
}

