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

public class GetRecebimentoEncomendaBDD {

    private Response response;
    private String recebimentoEncomendaId;
    private final String dataEntrega = OffsetDateTime.now().toString();

    @LocalServerPort
    private int port;

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Dado("que existe um recebimentoEncomenda cadastrado - Get")
    public void que_existe_um_recebimento_encomenda_cadastrado() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        RestAssured.basePath = contextPath;

        String apartamentoJson = """
                {
                    "torre": "A",
                    "andar": "20",
                    "numero": "1"
                }
                """;
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
                    "descricao": "Teste",
                    "dataEntrega": "%s",
                    "estadoColeta": "PENDENTE"
                }
                """, apartamentoId, dataEntrega);

        recebimentoEncomendaId = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(recebimentoEncomendaJson)
                .post("/api/recebimento-encomendas")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .path("id");
    }

    @Quando("um recebimentoEncomenda for buscado pelo ID - Get")
    public void um_recebimento_encomenda_for_buscado_pelo_id() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/recebimento-encomendas/" + recebimentoEncomendaId);
    }

    @Então("o recebimentoEncomenda será retornado no sistema - Get")
    public void o_recebimento_encomenda_sera_retornado_no_sistema() {
        response.then().statusCode(HttpStatus.OK.value());
    }

}