package com.fase4.fiap.bdd;

import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.fase4.fiap.bdd")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty,summary,html:target/cucumber-report.html")
@CucumberContextConfiguration
@SpringBootTest(
        classes = com.fase4.fiap.FiapApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
@EmbeddedKafka(
        partitions = 1,
        topics = {"notificacao-topic"},
        brokerProperties = {
                "listeners=PLAINTEXT://localhost:0",
                "port=0"
        }
)
public class CucumberTest {
}