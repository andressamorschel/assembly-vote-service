package com.assembly.vote.service;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.is;

import com.assembly.vote.service.config.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("local")
class AppTest extends IntegrationTest {

    @Test
    void healthCheck() {
        given()
                .accept("application/json")
                .when()
                .get("/actuator/health")
                .then()
                .statusCode(SC_OK)
                .body("status", is("UP"));
    }
}
