package com.dung.phan.audit.message;

import com.dung.phan.audit.config.ConcurrencyConfig;
import com.dung.phan.audit.model.AuditData;
import com.dung.phan.audit.service.AuditService;
import com.dung.phan.audit.util.GsonUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
@Slf4j
public class Consumer {
    @Qualifier(ConcurrencyConfig.TASK_EXECUTOR)
    @Autowired
    @Setter
    private Executor executor;

    @Autowired
    private AuditService auditService;

    @KafkaListener(topics = "audit-service-topic")
    public void consume(String message) {
        log.info("Auditor Service received request with data = {}",message);
        JsonObject jsonBody = GsonUtil.singletonGson().fromJson(message, JsonObject.class);
        CompletableFuture.runAsync(()-> processing(jsonBody),executor).exceptionally(ex -> {
            log.error(" could not save {}",ex);
            return null;
        }) ;
    }
    @Transactional
    protected void processing(JsonObject data) {

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        AuditData auditData = gson.fromJson(GsonUtil.singletonGson().toJson(data),AuditData.class);
        auditService.saveData(auditData);
    }

}