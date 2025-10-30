package com.fase4.fiap.bdd.apartamento;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;

public class GetApartamentoBDD {

    private Response response;
    private String apartamentoId;

    @LocalServerPort
    private int port;

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Dado("que existe um apartamento cadastrado - Get")
    public void que_existe_um_apartamento_cadastrado() {
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

        apartamentoId = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(apartamentoJson)
                .post("/api/apartamentos")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .path("id");
    }

    @Quando("um apartamento for buscado pelo ID - Get")
    public void um_apartamento_for_buscado_pelo_id() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/apartamentos/" + apartamentoId);
    }

    @Então("o apartamento será retornado no sistema - Get")
    public void o_apartamento_sera_retornado_no_sistema() {
        response.then().statusCode(HttpStatus.OK.value());
    }

}
