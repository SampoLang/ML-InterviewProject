package com.MyCompany.interviewProject.environment;

import com.MyCompany.interviewProject.mapper.EnvironmentMapper;
import com.MyCompany.interviewProject.model.dto.EnvironmentDTO;
import com.MyCompany.interviewProject.model.entity.EnvironmentEntity;
import com.MyCompany.interviewProject.service.EnvironmentService;
import com.MyCompany.interviewProject.service.EnvironmentServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static com.MyCompany.interviewProject.TestEnvironmentFactory.createCustomEnvironmentEntity;
import static com.MyCompany.interviewProject.TestEnvironmentFactory.createDefaultEnvironmentEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EnvironmentServiceTest {
    @Mock
    private EnvironmentDao environmentDao;

    @InjectMocks
    private EnvironmentServiceImpl environmentService;
    @Captor
    ArgumentCaptor<EnvironmentEntity> entityCaptor;
    static EnvironmentDTO environmentDTO;
    static EnvironmentEntity environmentEntity;

    @BeforeAll
    static void setUp() {
        environmentDTO = EnvironmentMapper.convertToDTO(createDefaultEnvironmentEntity());
        environmentEntity = createDefaultEnvironmentEntity();
    }

    @Test
    void testGetAllEnvironments() {
        List<EnvironmentEntity> entities = Arrays.asList(environmentEntity);
        when(environmentDao.findAll()).thenReturn(entities);

        List<EnvironmentDTO> result = environmentService.getAllEnvironments();

        assertEquals(1, result.size());
        assertEquals(environmentDTO.getCode(), result.getFirst().getCode());
        verify(environmentDao, times(1)).findAll();
    }

    @Test
    void testGetEnvironmentById() {
        when(environmentDao.findById(1L)).thenReturn(environmentEntity);

        EnvironmentDTO result = environmentService.getEnvironmentById(1L);

        assertNotNull(result);
        assertEquals(environmentDTO.getId(), result.getId());
        verify(environmentDao, times(1)).findById(1L);
    }

    @Test
    void testGetEnvironmentByCode() {
        when(environmentDao.findByCode("7700,trktesti")).thenReturn(environmentEntity);
        EnvironmentDTO result = environmentService.getEnvironmentByCode("7700,trktesti");
        assertNotNull(result);
        assertEquals(environmentDTO.getCode(), result.getCode());
        verify(environmentDao, times(1)).findByCode("7700,trktesti");
    }

    @Test
    void testUpdateEnvironment() {
        EnvironmentEntity existingEntity = createDefaultEnvironmentEntity();
        existingEntity.setId(1L);

        EnvironmentEntity updatedEntity = createCustomEnvironmentEntity("newCode", "http://newurl.com", "New Group", "New Desc");
        EnvironmentDTO updatedDTO = EnvironmentMapper.convertToDTO(updatedEntity);
        updatedDTO.setId(1L);

        when(environmentDao.findById(1L)).thenReturn(existingEntity);

        environmentService.updateEnvironment(updatedDTO);

        verify(environmentDao, times(1)).findById(1L);
        verify(environmentDao).saveOrUpdate(entityCaptor.capture());

        EnvironmentEntity savedEntity = entityCaptor.getValue();
        assertEquals(1L, savedEntity.getId());
        assertEquals("newCode", savedEntity.getCode());
        assertEquals("New Group", savedEntity.getGroup());
        assertEquals("New Desc", savedEntity.getDescription());
    }
    @Test
    void testDeleteEnvironment() {
        EnvironmentEntity entityToDelete = createDefaultEnvironmentEntity();
        entityToDelete.setId(1L);

        when(environmentDao.findById(1L)).thenReturn(entityToDelete);

        environmentService.deleteEnvironment(1L);

        verify(environmentDao, times(1)).findById(1L);
        verify(environmentDao, times(1)).deleteById(1L);
    }
}
