package com.dung.phan.audit.repository;

import com.dung.phan.audit.model.AuditData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AuditRepository extends JpaRepository<AuditData, Long> {

}
