package com.MyCompany.interviewProject.service;

import com.MyCompany.interviewProject.model.dto.EnvironmentDTO;

import java.util.List;

public interface EnvironmentService {
    List<EnvironmentDTO> getAllEnvironments();

    EnvironmentDTO getEnvironmentById(Long id);

    EnvironmentDTO getEnvironmentByCode(String code);

    EnvironmentDTO createEnvironment(EnvironmentDTO environmentDTO);

    EnvironmentDTO updateEnvironment(EnvironmentDTO environmentDTO);

    void deleteEnvironment(Long id);



}
