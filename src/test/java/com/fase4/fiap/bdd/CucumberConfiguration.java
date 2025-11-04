package com.fase4.fiap.bdd;

import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
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
                "port=0",
                "advertised.listeners=PLAINTEXT://localhost:0"
        }
)
public class CucumberConfiguration {
}