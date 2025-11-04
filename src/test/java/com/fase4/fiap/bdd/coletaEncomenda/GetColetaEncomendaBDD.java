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

public class GetColetaEncomendaBDD {

    private Response response;
    private String coletaEncomendaId;
    private final String dataEntrega = OffsetDateTime.now().toString();

    @LocalServerPort
    private int port;

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Dado("que existe um coletaEncomendaId cadastrado - Get")
    public void que_existe_um_coleta_encomenda_cadastrado() {
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
                    "cpfMoradorColeta": "12345678919",
                    "nomeMoradorColeta": "Teste",
                    "dataColeta": "%s"
                }
                """, recebimentoEncomendaId, dataEntrega);

        coletaEncomendaId = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(coletaEncomendaJson)
                .post("/api/coleta-encomendas")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .path("id");
    }

    @Quando("um coletaEncomendaId for buscado pelo ID - Get")
    public void um_coleta_encomenda_for_buscado_pelo_id() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/coleta-encomendas/" + coletaEncomendaId);
    }

    @Então("o coletaEncomendaId será retornado no sistema - Get")
    public void o_coleta_encomenda_sera_retornado_no_sistema() {
        response.then().statusCode(HttpStatus.OK.value());
    }

}
