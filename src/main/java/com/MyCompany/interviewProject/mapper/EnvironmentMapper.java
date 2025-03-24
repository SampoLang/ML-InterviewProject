package com.MyCompany.interviewProject.mapper;

import com.MyCompany.interviewProject.model.dto.EnvironmentDTO;
import com.MyCompany.interviewProject.model.entity.EnvironmentEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EnvironmentMapper {
    public static EnvironmentDTO convertToDTO(EnvironmentEntity environmentEntity) {
        EnvironmentDTO environmentDTO = new EnvironmentDTO();
        environmentDTO.setId(environmentEntity.getId());
        environmentDTO.setCode(environmentEntity.getCode());
        environmentDTO.setDescription(environmentEntity.getDescription());
        environmentDTO.setGroup(environmentEntity.getGroup());
        environmentDTO.setUrl(environmentEntity.getUrl());
        environmentDTO.setUrl2(environmentEntity.getUrl2());
        return environmentDTO;
    }

    public static List<EnvironmentDTO> convertToDTOs(List<EnvironmentEntity> environmentEntityList) {
        return environmentEntityList.stream().map(EnvironmentMapper::convertToDTO).collect(Collectors.toList());
    }

    public static EnvironmentEntity convertToEntity(EnvironmentDTO environmentDTO) {
        EnvironmentEntity environmentEntity = new EnvironmentEntity();
        if (environmentDTO.getId() != null) {
            environmentEntity.setId(environmentDTO.getId());
        }
        environmentEntity.setCode(environmentDTO.getCode());
        environmentEntity.setDescription(environmentDTO.getDescription());
        environmentEntity.setGroup(environmentDTO.getGroup());
        environmentEntity.setUrl(environmentDTO.getUrl());
        environmentEntity.setUrl2(environmentDTO.getUrl2());
        return environmentEntity;
    }

    public static List<EnvironmentEntity> convertToEntities(List<EnvironmentDTO> environmentDTOList) {
        return environmentDTOList.stream().map(EnvironmentMapper::convertToEntity).collect(Collectors.toList());
    }

}
