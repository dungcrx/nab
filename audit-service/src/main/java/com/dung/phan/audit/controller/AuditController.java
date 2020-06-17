package com.dung.phan.audit.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequestMapping("/api/v1/auditor")
public class AuditController {

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void auditor(@RequestBody @NotNull String data) {
        //TODO
    }

}
