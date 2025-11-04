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

public class UpdateMoradorBDD {

    private Response response;
    private String moradorId;
    private String moradorJsonPut;

    @LocalServerPort
    private int port;


    @Dado("que existe um morador cadastrado - Update")
    public void que_existe_um_morador_cadastrado() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        String apartamentoJson = """
                {
                    "torre": "L",
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
                    "cpf": "12345678903",
                    "nome": "Teste Total",
                    "telefone": ["11999999999"],
                    "email": "teste@hotmail.com",
                    "apartamentoId": ["%s"]
                }
                """, apartamentoId);

        moradorId = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(moradorJson)
                .post("/api/moradores")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .path("id");

        moradorJsonPut = String.format("""
                {
                    "cpf": "12345678903",
                    "nome": "Teste Update",
                    "telefone": ["11999999999"],
                    "email": "teste@hotmail.com",
                    "apartamentoId": ["%s"]
                }
                """, apartamentoId);
    }

    @Quando("o morador for modificado pelo ID - Update")
    public void o_morador_for_modificado_pelo_id() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(moradorJsonPut)
                .when()
                .put("/api/moradores/" + moradorId);
    }

    @Então("o morador modificado será retornado no sistema - Update")
    public void o_morador_modificado_sera_retornado_no_sistema() {
        response.then().statusCode(HttpStatus.OK.value());
    }

}