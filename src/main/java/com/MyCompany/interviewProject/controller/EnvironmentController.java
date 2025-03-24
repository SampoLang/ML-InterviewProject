package com.MyCompany.interviewProject.controller;

import com.MyCompany.interviewProject.model.dto.EnvironmentDTO;
import com.MyCompany.interviewProject.service.EnvironmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/environments")
public class EnvironmentController {

    private final EnvironmentService environmentService;
    private static final Logger logger = LoggerFactory.getLogger(EnvironmentController.class);

    @Autowired
    public EnvironmentController(EnvironmentService environmentService) {
        this.environmentService = environmentService;
    }

    @Operation(
            summary = "Retrieve all environments",
            description = "Get a list of all environments in the system",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved environments"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping
    public ResponseEntity<List<EnvironmentDTO>> getEnvironments() {
        List<EnvironmentDTO> environments = environmentService.getAllEnvironments();
        return new ResponseEntity<>(environments, HttpStatus.OK);
    }

    @Operation(
            summary = "Retrieve an environment by ID",
            description = "Get environment details by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved environment"),
                    @ApiResponse(responseCode = "400", description = "Invalid ID provided"),
                    @ApiResponse(responseCode = "404", description = "Environment not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<EnvironmentDTO> getEnvironmentById(
            @Parameter(description = "ID of the environment to retrieve", required = true)
            @PathVariable Long id) {

        if (id == null || id <= 0) {
            logger.warn("Invalid environment ID: {}", id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        EnvironmentDTO environment = environmentService.getEnvironmentById(id);
        if (environment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(environment, HttpStatus.OK);
    }

    @Operation(
            summary = "Retrieve an environment by code",
            description = "Get environment details by code",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved environment"),
                    @ApiResponse(responseCode = "404", description = "Environment not found")
            }
    )
    @GetMapping("/code/{code}")
    public ResponseEntity<EnvironmentDTO> getEnvironmentByCode(
            @Parameter(description = "Code of the environment to retrieve", required = true)
            @PathVariable String code) {

        EnvironmentDTO environment = environmentService.getEnvironmentByCode(code);
        if (environment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(environment, HttpStatus.OK);
    }

    @Operation(
            summary = "Create a new environment",
            description = "Create a new environment with the provided details",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Environment created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    @PostMapping()
    public ResponseEntity<EnvironmentDTO> createEnvironment(
            @Parameter(description = "Environment details to create", required = true)
            @Valid @RequestBody EnvironmentDTO environmentDTO) {

        EnvironmentDTO updatedEnvironment = environmentService.createEnvironment(environmentDTO);
        return new ResponseEntity<>(updatedEnvironment, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update an existing environment",
            description = "Update the details of an existing environment",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Environment updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data"),
                    @ApiResponse(responseCode = "404", description = "Environment not found")
            }
    )
    @PutMapping()
    public ResponseEntity<EnvironmentDTO> updateEnvironment(
            @Parameter(description = "Updated environment details", required = true)
            @Valid @RequestBody EnvironmentDTO environmentDTO) {

        EnvironmentDTO updatedEnvironment = environmentService.updateEnvironment(environmentDTO);
        return new ResponseEntity<>(updatedEnvironment, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete an environment",
            description = "Delete an environment from the system by ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Successfully deleted the environment"),
                    @ApiResponse(responseCode = "400", description = "Invalid ID provided"),
                    @ApiResponse(responseCode = "404", description = "Environment not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<EnvironmentDTO> deleteEnvironment(
            @Parameter(description = "ID of the environment to delete", required = true)
            @PathVariable Long id) {

        if (id == null || id <= 0) {
            logger.warn("Invalid delete request for ID: {}", id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        environmentService.deleteEnvironment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Bulk create environments",
            description = "Create multiple environments in a batch request",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Environments saved successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    @PostMapping("/bulk")
    public ResponseEntity<?> saveEnvironments(
            @Parameter(description = "List of environments to create", required = true)
            @RequestBody List<EnvironmentDTO> environments) {

        for (EnvironmentDTO environmentDTO : environments) {
            environmentService.createEnvironment(environmentDTO);  // Save each environment
        }
        return ResponseEntity.ok("Environments saved successfully");
    }
}
