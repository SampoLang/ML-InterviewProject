package com.MyCompany.interviewProject.environment;

import com.MyCompany.interviewProject.model.entity.EnvironmentEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.MyCompany.interviewProject.TestEnvironmentFactory.CreateMultipleEnvironments;
import static com.MyCompany.interviewProject.TestEnvironmentFactory.createDefaultEnvironmentEntity;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class EnvironmentDaoTest {
    @Autowired
    private EnvironmentDao dao;

    @AfterEach
    void tearDown(){
        dao.deleteAll();
    }

    @Test
    void findTestAll(){
        List<EnvironmentEntity> environments = CreateMultipleEnvironments();
        for(EnvironmentEntity environment : environments){
            dao.saveOrUpdate(environment);
        }
        assertEquals(environments.size(), dao.findAll().size());

    }
    @Test
    void findTestById(){
        EnvironmentEntity environment = createDefaultEnvironmentEntity();
        dao.saveOrUpdate(environment);
        EnvironmentEntity existsEnvironment = dao.findById(environment.getId());
        checkEnvironmentValues(environment, existsEnvironment);

    }
    @Test
    void findTestByCode(){
        EnvironmentEntity environment = createDefaultEnvironmentEntity();
        dao.saveOrUpdate(environment);
        EnvironmentEntity existsEnvironment = dao.findByCode(environment.getCode());
        checkEnvironmentValues(environment, existsEnvironment);

    }

    @Test
    void saveOrUpdateTestNewEntity(){
            EnvironmentEntity environment = createDefaultEnvironmentEntity();
            environment.setId(null); // Ensure it's treated as a new entity

            dao.saveOrUpdate(environment);

            EnvironmentEntity existsEnvironment = dao.findById(environment.getId());
            assertNotNull(existsEnvironment, "Entity should be saved and retrievable.");
            checkEnvironmentValues(environment, existsEnvironment);
        }


    @Test
    void updateExistingEntityTest(){
        EnvironmentEntity environment = createDefaultEnvironmentEntity();
        dao.saveOrUpdate(environment);

        environment.setDescription("Updated Description");
        dao.saveOrUpdate(environment);

        EnvironmentEntity updatedEnvironment = dao.findById(environment.getId());
        assertNotNull(updatedEnvironment, "Updated entity should exist.");
        assertEquals("Updated Description", updatedEnvironment.getDescription(), "Description should be updated.");
    }

    @Test
    void deleteByIdTest(){
        EnvironmentEntity environment = createDefaultEnvironmentEntity();
        dao.saveOrUpdate(environment);

        dao.deleteById(environment.getId());

        EnvironmentEntity deletedEnvironment = dao.findById(environment.getId());
        assertNull(deletedEnvironment, "Entity should be deleted.");
    }
    @Test
    void findByNonExistentIdTest(){
        EnvironmentEntity environment = dao.findById(999L);
        assertNull(environment, "Should return null for non-existent ID.");
    }
    @Test
    void findByNonExistentCodeTest(){
        EnvironmentEntity environment = dao.findByCode("NonExistentCode");
        assertNull(environment, "Should return null for non-existent code.");
    }


    //helper function to check if the values match.
    public static void checkEnvironmentValues(EnvironmentEntity expected, EnvironmentEntity actual) {
        assertNotNull(actual, "Environment should not be null.");
        assertEquals(expected.getId(), actual.getId(), "The IDs should match.");
        assertEquals(expected.getCode(), actual.getCode(), "The codes should match.");
        assertEquals(expected.getUrl(), actual.getUrl(), "The URLs should match.");
        assertEquals(expected.getUrl2(), actual.getUrl2(), "The URL2s should match.");
        assertEquals(expected.getGroup(), actual.getGroup(), "The groups should match.");
        assertEquals(expected.getDescription(), actual.getDescription(), "The descriptions should match.");
    }
}
