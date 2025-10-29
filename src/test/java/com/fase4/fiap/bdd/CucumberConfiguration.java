package com.fase4.fiap.bdd;

import com.fase4.fiap.FiapApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@CucumberContextConfiguration
@SpringBootTest(classes = FiapApplication.class)
@ActiveProfiles("test")
public class CucumberConfiguration {
}
