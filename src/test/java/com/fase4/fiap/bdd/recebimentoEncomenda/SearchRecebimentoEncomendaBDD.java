package com.fase4.fiap.bdd.recebimentoEncomenda;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.OffsetDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

public class SearchRecebimentoEncomendaBDD {

    private Response response;
    private final String dataEntrega = OffsetDateTime.now().toString();

    @LocalServerPort
    private int port;

    @Dado("que existem vários recebimentoEncomenda cadastrados - Search")
    public void que_existem_varios_recebimento_encomenda_cadastrados() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        for (int i = 1; i <= 2; i++) {
            String apartamentoJson = String.format("""
                    {
                        "torre": "P",
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
                    "descricao": "Test%d",
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

    @Quando("os recebimentoEncomenda forem buscados - Search")
    public void os_recebimento_encomenda_forem_buscados() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/recebimento-encomendas");
    }

    @Então("os recebimentoEncomenda serão retornados no sistema - Search")
    public void os_recebimento_encomenda_serao_retornados_no_sistema() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body("$", not(empty()));
    }

}