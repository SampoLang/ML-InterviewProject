package com.MyCompany.interviewProject.environment;

import com.MyCompany.interviewProject.controller.EnvironmentController;
import com.MyCompany.interviewProject.mapper.EnvironmentMapper;
import com.MyCompany.interviewProject.model.dto.EnvironmentDTO;
import com.MyCompany.interviewProject.service.EnvironmentService;
import com.MyCompany.interviewProject.service.EnvironmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.MyCompany.interviewProject.TestEnvironmentFactory.createDefaultEnvironmentEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EnvironmentControllerTest {
    @Mock
    private EnvironmentService environmentService;

    @InjectMocks
    private EnvironmentController environmentController;

    private EnvironmentDTO environmentDTO;

    @BeforeEach
    void setUp() {
        environmentDTO = new EnvironmentDTO();
        environmentDTO.setId(1L);
        environmentDTO.setCode("defaultCode");
        environmentDTO.setGroup("defaultGroup");
        environmentDTO.setDescription("defaultDescription");
    }

    @Test
    public void testGetAllEnvironments() {

        when(environmentService.getAllEnvironments()).thenReturn(Arrays.asList(environmentDTO));

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        ResponseEntity<List<EnvironmentDTO>> responseEntity = environmentController.getEnvironments();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, Objects.requireNonNull(responseEntity.getBody()).size());
        assertEquals("defaultCode", Objects.requireNonNull(responseEntity.getBody()).getFirst().getCode());
    }
    @Test
    public void testGetEnvironmentById() {
        // Arrange
        when(environmentService.getEnvironmentById(1L)).thenReturn(environmentDTO);

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        // Act
        ResponseEntity<EnvironmentDTO> responseEntity = environmentController.getEnvironmentById(1L);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1L, Objects.requireNonNull(responseEntity.getBody()).getId());
        assertEquals("defaultCode", Objects.requireNonNull(responseEntity.getBody()).getCode());
    }
    @Test
    public void testCreateEnvironment() {

        when(environmentService.createEnvironment(any(EnvironmentDTO.class))).thenReturn(environmentDTO);

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        ResponseEntity<EnvironmentDTO> responseEntity = environmentController.createEnvironment(environmentDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(1L, Objects.requireNonNull(responseEntity.getBody()).getId());
    }



}
