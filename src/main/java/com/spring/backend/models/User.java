package com.spring.backend.models;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;

@Entity
@Table(name = "tuser")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    //@JsonIgnore
    @Id
    private String username;

    @Column(length = 100)
    private String fullName;

    @Column(length = 12)
    private String telNo;

    private String email;

    private String password;

    @Column(name = "password", insertable = false, updatable = false)
    private String passwdOrg;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date insertDate;

    @CreatedBy
    @Column(nullable = false, length = 50, updatable = false)
    private String insertId;
    
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date modifyDate;

    @LastModifiedBy
    @Column(nullable = false, length = 50)
    private String modifyId;

}

