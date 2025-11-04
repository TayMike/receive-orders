package com.fase4.fiap.bdd.recebimentoEncomenda;

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

public class SearchByApartamentoRecebimentoEncomendaBDD {

    private Response response;
    private String apartamentoId;
    private final String dataEntrega = OffsetDateTime.now().toString();

    @LocalServerPort
    private int port;

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Dado("que existem vários recebimentoEncomenda cadastrados - SearchByApartamentoRecebimento")
    public void que_existem_varios_recebimento_encomenda_cadastrados() {
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

            apartamentoId = given()
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

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(recebimentoEncomendaJson)
                    .post("/api/recebimento-encomendas")
                    .then()
                    .statusCode(HttpStatus.CREATED.value());
        }
    }

    @Quando("os recebimentoEncomenda forem buscados - SearchByApartamentoRecebimento")
    public void os_recebimento_encomenda_forem_buscados() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/recebimento-encomendas/apartamento/" + apartamentoId);
    }

    @Então("os recebimentoEncomenda serão retornados no sistema - SearchByApartamentoRecebimento")
    public void os_recebimento_encomenda_serao_retornados_no_sistema() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body("$", not(empty()));
    }

}