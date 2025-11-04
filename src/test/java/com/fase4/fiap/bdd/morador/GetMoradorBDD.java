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

public class GetMoradorBDD {

    private Response response;
    private String moradorId;

    @LocalServerPort
    private int port;

    @Dado("que existe um morador cadastrado - Get")
    public void que_existe_um_morador_cadastrado() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        String apartamentoJson = """
            {
                "torre": "I",
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
                    "cpf": "12345678902",
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
    }

    @Quando("um morador for buscado pelo ID - Get")
    public void um_morador_for_buscado_pelo_id() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/moradores/" + moradorId);
    }

    @Então("o morador será retornado no sistema - Get")
    public void o_morador_sera_retornado_no_sistema() {
        response.then().statusCode(HttpStatus.OK.value());
    }

}