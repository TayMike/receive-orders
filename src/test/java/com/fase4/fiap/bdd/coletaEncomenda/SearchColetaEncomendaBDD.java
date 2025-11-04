package com.fase4.fiap.bdd.coletaEncomenda;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.OffsetDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

public class SearchColetaEncomendaBDD {

    private Response response;
    private final String dataEntrega = OffsetDateTime.now().toString();

    @LocalServerPort
    private int port;

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Dado("que existem vários coletaEncomenda cadastrados - Search")
    public void que_existem_varios_coleta_encomenda_cadastrados() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        RestAssured.basePath = contextPath;

        for (int i = 1; i <= 2; i++) {
            String apartamentoJson = String.format("""
                    {
                        "torre": "A",
                        "andar": "20",
                        "numero": "1%d"
                    }
                    """, i);

            String apartamentoId = given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(apartamentoJson)
                    .post("/api/apartamentos")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .extract()
                    .path("id");

            String recebimentoEncomendaJson = String.format("""
                {
                    "apartamentoId": "%s",
                    "descricao": "Teste%d",
                    "dataEntrega": "%s",
                    "estadoColeta": "PENDENTE"
                }
                """, apartamentoId, i, dataEntrega);

            String recebimentoEncomendaId = given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(recebimentoEncomendaJson)
                    .post("/api/recebimento-encomendas")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .extract()
                    .path("id");

            String coletaEncomendaJson = String.format("""
                {
                    "recebimentoEncomendaId": "%s",
                    "cpfMoradorColeta": "1234567891%d",
                    "nomeMoradorColeta": "Teste",
                    "dataColeta": "%s"
                }
                """, recebimentoEncomendaId, i, dataEntrega);

            given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(coletaEncomendaJson)
            .post("/api/coleta-encomendas")
            .then()
            .statusCode(HttpStatus.CREATED.value());
        }
    }

    @Quando("os coletaEncomenda forem buscados - Search")
    public void os_coleta_encomenda_forem_buscados() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/coleta-encomendas");
    }

    @Então("os coletaEncomenda serão retornados no sistema - Search")
    public void os_coleta_encomenda_serao_retornados_no_sistema() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body("$", not(empty()));
    }

}