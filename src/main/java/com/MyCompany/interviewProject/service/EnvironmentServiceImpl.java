package com.MyCompany.interviewProject.service;

import com.MyCompany.interviewProject.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.MyCompany.interviewProject.mapper.EnvironmentMapper;
import com.MyCompany.interviewProject.model.dto.EnvironmentDTO;
import com.MyCompany.interviewProject.model.entity.EnvironmentEntity;
import com.MyCompany.interviewProject.environment.EnvironmentDao;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;


@Service
public class EnvironmentServiceImpl implements EnvironmentService {

    private static final Logger logger = LoggerFactory.getLogger(EnvironmentServiceImpl.class);
    private final EnvironmentDao environmentDao;

    public EnvironmentServiceImpl(EnvironmentDao environmentDao) {
        this.environmentDao = environmentDao;
    }

    @Override
    public List<EnvironmentDTO> getAllEnvironments() {
        List<EnvironmentEntity> environments = environmentDao.findAll();
        if (environments.isEmpty()) {
            logger.warn("No environments found.");
            return Collections.emptyList();
        }
        return EnvironmentMapper.convertToDTOs(environments);
    }

    @Override
    public EnvironmentDTO getEnvironmentById(Long id) {
        validateId(id);
        logger.info("Fetching environment by id: {}", id);
        EnvironmentEntity environment = findEnvironmentByIdOrThrow(id);
        return EnvironmentMapper.convertToDTO(environment);
    }

    @Override
    public EnvironmentDTO getEnvironmentByCode(String code) {
        validateCode(code);

        logger.info("Fetching environment by code: {}", code);
        EnvironmentEntity environment = findEnvironmentByCodeOrThrow(code);
        return EnvironmentMapper.convertToDTO(environment);
    }

    @Override
    public EnvironmentDTO createEnvironment(EnvironmentDTO environmentDTO) {
        if (environmentDTO == null) {
            throw new IllegalArgumentException("EnvironmentDTO cannot be null");
        }
        validateCode(environmentDTO.getCode());
        logger.info("Creating new environment with code: {}", environmentDTO.getCode());
        EnvironmentEntity entity = EnvironmentMapper.convertToEntity(normalizeEnvironmentDTO(environmentDTO));
        entity = environmentDao.saveOrUpdate(entity);
        return EnvironmentMapper.convertToDTO(entity);
    }

    @Override
    public EnvironmentDTO updateEnvironment(EnvironmentDTO environmentDTO) {
        if (environmentDTO == null) {
            throw new IllegalArgumentException("EnvironmentDTO cannot be null for update");
        }

        validateId(environmentDTO.getId());
        validateCode(environmentDTO.getCode());
        logger.info("Updating environment with id: {}", environmentDTO.getId());
        EnvironmentEntity existingEnvironment = findEnvironmentByIdOrThrow(environmentDTO.getId());
        existingEnvironment.setCode(environmentDTO.getCode());
        existingEnvironment.setDescription(environmentDTO.getDescription());
        existingEnvironment.setGroup(environmentDTO.getGroup());
        existingEnvironment.setUrl(environmentDTO.getUrl());
        existingEnvironment.setUrl2(environmentDTO.getUrl2());

        environmentDao.saveOrUpdate(existingEnvironment);

        return EnvironmentMapper.convertToDTO(existingEnvironment);
    }

    @Override
    public void deleteEnvironment(Long id) {

        validateId(id);
        logger.info("Deleting environment with id: {}", id);
        EnvironmentEntity existingEnvironment = findEnvironmentByIdOrThrow(id);
        try {
            environmentDao.deleteById(id);
            logger.info("Successfully deleted environment with id: {}", id);
        } catch (Exception e) {
            logger.error("Failed to delete environment with id: {}", id, e);
            throw new RuntimeException("An error occurred while deleting the environment with id: " + id, e);
        }
    }

    // Reusable validation methods
    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID must be a positive number.");
        }
    }

    private void validateCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Code cannot be null or empty.");
        }
    }
    // Reusable method for entity retrieval
    private EnvironmentEntity findEnvironmentByIdOrThrow(Long id) {
        EnvironmentEntity entity = environmentDao.findById(id);
        if (entity == null) {
            logger.warn("Environment not found with id: {}", id);
            throw new EntityNotFoundException("Environment not found with id: " + id);
        }
        return entity;
    }

    private EnvironmentEntity findEnvironmentByCodeOrThrow(String code) {
        EnvironmentEntity entity = environmentDao.findByCode(code);
        if (entity == null) {
            logger.warn("Environment not found with code: {}", code);
            throw new EntityNotFoundException("Environment not found with code: " + code);
        }
        return entity;
    }
    // helper function to normalize the data
    private EnvironmentDTO normalizeEnvironmentDTO(EnvironmentDTO dto) {
        if (dto.getCode() != null) {
            dto.setCode(dto.getCode().toLowerCase());
        }
        if (dto.getUrl() != null) {
            dto.setUrl(dto.getUrl().toLowerCase());
        }
        if (dto.getUrl2() != null) {
            dto.setUrl2(dto.getUrl2().toLowerCase());
        }

        if (dto.getGroup() != null && !dto.getGroup().isEmpty()) {
            dto.setGroup(dto.getGroup().substring(0, 1).toUpperCase() + dto.getGroup().substring(1).toLowerCase());
        }
        if (dto.getDescription() != null && !dto.getDescription().isEmpty()) {
            dto.setDescription(dto.getDescription().substring(0, 1).toUpperCase() + dto.getDescription().substring(1).toLowerCase());
        }
        return dto;
    }
}
