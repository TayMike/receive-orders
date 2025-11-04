package com.fase4.fiap.bdd.morador;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;

public class DeleteMoradorBDD {

    private Response response;
    private String moradorId;

    @LocalServerPort
    private int port;

    @Dado("que existe um morador cadastrado - Delete")
    public void que_existe_um_morador_cadastrado() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        String apartamentoJson = """
                {
                    "torre": "H",
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

        String moradorJson = String.format("""
                {
                    "cpf": "12345678901",
                    "nome": "Teste Total",
                    "telefone": ["11999999999"],
                    "email": "teste@hotmail.com",
                    "apartamentoId": ["%s"]
                }
                """, apartamentoId);

        moradorId = given()
                .contentType("application/json")
                .body(moradorJson)
                .post("/api/moradores")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .path("id");
    }

    @Quando("o morador for deletado pelo ID - Delete")
    public void o_morador_for_deletado_pelo_id() {
        response = given()
                .when()
                .delete("/api/moradores/" + moradorId);
    }

    @Então("o morador deletado não será retornado no sistema - Delete")
    public void o_morador_deletado_nao_sera_retornado_no_sistema() {
        response.then().statusCode(HttpStatus.NO_CONTENT.value());
    }

}