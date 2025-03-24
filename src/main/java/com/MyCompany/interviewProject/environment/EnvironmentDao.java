package com.MyCompany.interviewProject.environment;

import com.MyCompany.interviewProject.model.entity.EnvironmentEntity;

import java.util.List;
public interface EnvironmentDao {
    EnvironmentEntity saveOrUpdate(EnvironmentEntity environmentEntity);

    void deleteById(Long id);
    void deleteAll();

    List<EnvironmentEntity> findAll();

    EnvironmentEntity findById(Long id);

    EnvironmentEntity findByCode(String code);



}
