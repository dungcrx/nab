package com.dung.phan.audit.service.impl;

import com.dung.phan.audit.model.AuditData;
import com.dung.phan.audit.repository.AuditRepository;
import com.dung.phan.audit.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AuditServiceImpl implements AuditService {

    @Autowired
    private AuditRepository auditRepository;


    @Override
    public Page<AuditData> getAllData(Pageable pageRequest) {
        return auditRepository.findAll(pageRequest);
    }

    @Override
    public AuditData saveData(AuditData auditData) {
        return auditRepository.save(auditData);
    }

}
