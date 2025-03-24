package com.MyCompany.interviewProject.environment;

import com.MyCompany.interviewProject.exception.EnvironmentNotFoundException;
import com.MyCompany.interviewProject.model.entity.EnvironmentEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class EnvironmentDaoImpl implements EnvironmentDao {
    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger logger = LoggerFactory.getLogger(EnvironmentDaoImpl.class);
    //TODO method for null checks DRY

    public EnvironmentEntity saveOrUpdate(EnvironmentEntity environment) {

        if (environment.getId() == null) {
            entityManager.persist(environment);
            logger.info("Created new Environment entity with code: {}", environment.getCode());

        } else {
            entityManager.merge(environment);
            logger.info("Updated Environment entity with ID: {}", environment.getId());
        }
        return environment;
    }

    public EnvironmentEntity findById(Long id) {
        EnvironmentEntity environment = entityManager.find(EnvironmentEntity.class, id);
        if (environment == null) {
            logger.warn("Environment entity not found with ID: {}", id);
            throw new EnvironmentNotFoundException("Environment entity with ID " + id + " not found.");
        }
        return environment;
    }

    public EnvironmentEntity findByCode(String code) {
        try {
            return entityManager.createQuery("SELECT e FROM EnvironmentEntity e WHERE e.code = :code", EnvironmentEntity.class)
                    .setParameter("code", code)
                    .getSingleResult();
        }
        catch (jakarta.persistence.NoResultException e)
        {
            logger.warn("Environment entity not found with CODE: {}", code);
            throw new EnvironmentNotFoundException("Environment entity with CODE " + code + " not found.");
        }
        catch (Exception e) {
            logger.error("An unexpected error occurred while fetching environment entity by CODE: {}", code, e);

            throw new RuntimeException("An error occurred while fetching environment entity by CODE", e);
        }
    }

    public List<EnvironmentEntity> findAll() {
        List<EnvironmentEntity> environments = entityManager.createQuery("SELECT e FROM EnvironmentEntity e", EnvironmentEntity.class).getResultList();
        logger.info("Retrieved {} environment entities.", environments.size());
        return environments;
    }

    public void deleteById(Long id) {
        EnvironmentEntity environment = entityManager.find(EnvironmentEntity.class, id);
        if (environment != null) {
            entityManager.remove(environment);
            logger.info("Deleted Environment entity with ID: {}", id);
        } else {
            throw new EnvironmentNotFoundException("Environment entity with ID " + id + " not found.");
        }
    }

    public void deleteAll(){
        int deletedCount = entityManager.createQuery("DELETE FROM EnvironmentEntity").executeUpdate();
        logger.info("Deleted {} environment entities. ", deletedCount);
    }
}
