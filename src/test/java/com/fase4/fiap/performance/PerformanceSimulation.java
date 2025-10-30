package com.fase4.fiap.performance;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.time.Duration;
import java.util.UUID;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class PerformanceSimulation extends Simulation {

    // Configuração HTTP
    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8081")
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");

    // Cenário: Cadastrar Morador
    private final ScenarioBuilder scn = scenario("Cadastrar Morador")
            // Passo 1: Gerar dados únicos por usuário
            .exec(session -> {
                long timestamp = System.currentTimeMillis() % 100000;
                long userId = session.getLong("userId");
                int uniqueId = (int) (timestamp + userId);

                String cpf = String.format("123456789%02d", uniqueId % 100); // 12345678900 a 12345678999
                String nome = "Morador " + uniqueId;
                String email = "morador" + uniqueId + "@teste.com";
                UUID apt1 = UUID.randomUUID();
                UUID apt2 = UUID.randomUUID();

                return session
                        .set("cpf", cpf)
                        .set("nome", nome)
                        .set("email", email)
                        .set("telefone1", "1199999" + String.format("%04d", uniqueId % 10000))
                        .set("telefone2", "1188888" + String.format("%04d", uniqueId % 10000))
                        .set("apt1", apt1)
                        .set("apt2", apt2);
            })
            // Passo 2: POST /moradores com JSON
            .exec(http("Criar morador")
                    .post("/moradores")
                    .body(ElFileBody("bodies/cadastrar_morador.json"))
                    .check(status().is(201))
                    .check(jsonPath("$.id").saveAs("moradorId"))
            );

    // Configuração do teste de carga
    {
        setUp(
                scn.injectOpen(
                        rampUsersPerSec(1).to(20).during(Duration.ofSeconds(15)),   // Aquece
                        constantUsersPerSec(20).during(Duration.ofSeconds(30)),     // Pico
                        rampUsersPerSec(20).to(1).during(Duration.ofSeconds(15))    // Desacelera
                )
        )
                .protocols(httpProtocol)
                .assertions(
                        global().responseTime().max().lt(1500),
                        global().responseTime().mean().lt(800),
                        global().successfulRequests().percent().gt(99.0)
                );
    }
}