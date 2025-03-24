package com.MyCompany.interviewProject.model.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class EnvironmentEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //pitäskö olla unique?
    @Column(nullable = false, unique = true)
    private String code;

    private String url;
    private String url2;
    @Column(name = "environmentGroup")
    private String group;
    private String description;
    private LocalDateTime createdAtDate;
    private LocalDateTime modifiedAtDate;
    private String createdBy;
    private String modifiedBy;
    private boolean isActive;

    public EnvironmentEntity() {
    }

    public EnvironmentEntity(String code, String url, String url2, String group, String description, LocalDateTime createdAtDate, LocalDateTime modifiedAtDate, String createdBy, String modifiedBy, boolean isActive) {
        this.code = code;
        this.url = url;
        this.url2 = url2;
        this.group = group;
        this.description = description;
        this.createdAtDate = LocalDateTime.now();
        this.modifiedAtDate = modifiedAtDate;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAtDate() {
        return createdAtDate;
    }

    public void setCreatedAtDate(LocalDateTime createdAtDate) {
        this.createdAtDate = createdAtDate;
    }

    public LocalDateTime getModifiedAtDate() {
        return modifiedAtDate;
    }

    public void setModifiedAtDate(LocalDateTime modifiedAtDate) {
        this.modifiedAtDate = modifiedAtDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
