package com.fase4.fiap.bdd.apartamento;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;

public class DeleteApartamentoBDD {

    private Response response;
    private String apartamentoId;

    @LocalServerPort
    private int port;

    @Dado("que existe um apartamento cadastrado - Delete")
    public void que_existe_um_apartamento_cadastrado() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        String apartamentoJson = """
            {
                "torre": "B",
                "andar": "20",
                "numero": "1"
            }
            """;

        apartamentoId = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(apartamentoJson)
                .post("/api/apartamentos")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .path("id");
    }

    @Quando("o apartamento for deletado pelo ID - Delete")
    public void o_apartamento_for_deletado_pelo_id() {
        response = given()
                .when()
                .delete("/api/apartamentos/" + apartamentoId);
    }

    @Então("o apartamento deletado não será retornado no sistema - Delete")
    public void o_apartamento_deletado_nao_sera_retornado_no_sistema() {
        response.then().statusCode(HttpStatus.NO_CONTENT.value());
    }

}