package com.dung.phan.audit.model;

import com.dung.phan.audit.model.entity.BaseEntity;
import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "audit")
public class AuditData extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "body" , length = 512)
    @Expose
    private String body;

    @Column(name = "method" , length = 512)
    @Expose
    private String method;

    @Column(name = "url" , length = 512)
    private String url;
    @Column(name = "date_time_stamp")
    @Expose
    private long dateTimeStamp;
}
