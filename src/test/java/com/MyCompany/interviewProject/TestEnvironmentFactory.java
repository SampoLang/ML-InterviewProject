package com.MyCompany.interviewProject;

import com.MyCompany.interviewProject.model.entity.EnvironmentEntity;
import com.MyCompany.interviewProject.environment.EnvironmentDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class TestEnvironmentFactory {
    @Autowired
    private static EnvironmentDao dao;

    public static EnvironmentEntity createDefaultEnvironmentEntity(){
        EnvironmentEntity environment = new EnvironmentEntity();
        environment.setCode("7700,trktesti");
        environment.setUrl("https://test.mlog.info/");
        environment.setGroup("Turku");
        environment.setDescription("Trk, testi");
        return environment;
    }

    public static EnvironmentEntity createCustomEnvironmentEntity(String code, String url, String group, String description){
        EnvironmentEntity environment = new EnvironmentEntity();
        environment.setCode(code);
        environment.setGroup(group);
        environment.setUrl(url);
        environment.setDescription(description);

        return environment;
    }

    public static List<EnvironmentEntity> CreateMultipleEnvironments(){
        List<EnvironmentEntity> environments = new ArrayList<>();

        // Creating 5 different environments with unique values
        environments.add(createCustomEnvironmentEntity("7701,turkuenv1", "https://turku1.mlog.info/", "Turku", "Turku environment 1"));
        environments.add(createCustomEnvironmentEntity("7702,turkuenv2", "https://turku2.mlog.info/", "Turku", "Turku environment 2"));
        environments.add(createCustomEnvironmentEntity("7703,tampereenv1", "https://tampere1.mlog.info/", "Tampere", "Tampere environment 1"));
        environments.add(createCustomEnvironmentEntity("7704,tampereenv2", "https://tampere2.mlog.info/", "Tampere", "Tampere environment 2"));
        environments.add(createCustomEnvironmentEntity("7705,helsinki", "https://helsinki.mlog.info/", "Helsinki", "Helsinki environment"));

        return environments;
    }
}
