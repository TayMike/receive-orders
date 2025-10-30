package com.fase4.fiap.performance;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class PerformanceSimulation extends Simulation {

    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8081")
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");

    private final ScenarioBuilder scn = scenario("Criar e Deletar Apartamento")
            .exec(session -> session.set("num", (int) (System.currentTimeMillis() % 100)))

            .exec(http("Criar apartamento")
                    .post("/api/apartamentos")
                    .body(StringBody(session ->
                            "{ \"torre\": \"A\", \"andar\": 1, \"numero\": " + session.getInt("num") + " }"
                    )).asJson()
                    .check(status().is(201))
                    .check(jsonPath("$.id").saveAs("aptId"))
            )

            .exec(http("Deletar apartamento")
                    .delete(session -> "/api/apartamentos/" + session.getString("aptId"))
                    .check(status().is(204))
            );
    {
        setUp(
                scn.injectOpen(
                        rampUsersPerSec(1).to(20).during(Duration.ofSeconds(15)),
                        constantUsersPerSec(20).during(Duration.ofSeconds(30)),
                        rampUsersPerSec(20).to(1).during(Duration.ofSeconds(15))
                )
        )
                .protocols(httpProtocol)
                .assertions(
                        global().responseTime().max().lt(1500),
                        global().successfulRequests().percent().gt(99.0)
                );
    }
}