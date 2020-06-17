package com.dung.phan.audit.service;

import com.dung.phan.audit.model.AuditData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuditService {

    Page<AuditData> getAllData(Pageable pageRequest);

    AuditData saveData(AuditData auditData);
}
