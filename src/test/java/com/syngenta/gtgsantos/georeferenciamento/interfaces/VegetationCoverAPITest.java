package com.syngenta.gtgsantos.georeferenciamento.interfaces;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VegetationCoverAPITest {

    @Value("${api}")
    private String api;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }


    @Test
    public void testGetEndpointCheckArea() {

        Response response = given()
                .contentType(io.restassured.http.ContentType.JSON)
                .get(api);

        response.then()
                .statusCode(200);

        JsonPath jp = new JsonPath(response.asString());
        String output = jp.get("area").toString();

        Assertions.assertEquals("1258503.8", output);
    }

    @Test
    public void testGetEndpointCheckFilename() {

        given()
                .contentType(io.restassured.http.ContentType.JSON)
                .get(api)
                .then()
                .statusCode(200)
                .body("filename", equalTo("319567_2331703_2016-12-07_0c0b-20161207T151953Z.tif"));
    }

    @Test
    public void testGetEndpointCheckCover() {
        Response response = given()
                .contentType(io.restassured.http.ContentType.JSON)
                .get(api);

        response.then()
                .statusCode(200);

        JsonPath jp = new JsonPath(response.asString());
        String output = jp.get("cover").toString();

        Assertions.assertEquals("0.6625432", output);
    }

    @Test
    public void testGetEndpointCheckLocalTime() {

        given()
                .contentType(io.restassured.http.ContentType.JSON)
                .get(api)
                .then()
                .statusCode(200)
                .body("local_time", equalTo("2016-12-07T13:19:53.00000"));
    }

    @Test
    public void testGetEndpointCheckCentroidCoordinates() {
        Response response =
                given()
                        .contentType(io.restassured.http.ContentType.JSON)
                        .get(api);

        JsonPath jp = new JsonPath(response.asString());
        String output = jp.get("centroid.coordinates").toString();

        Assertions.assertEquals("[[-47.597244, -15.858562]]", output);
    }

    @Test
    public void testGetEndpointCheckCentroidType() {

        given()
                .contentType(io.restassured.http.ContentType.JSON)
                .get(api)
                .then()
                .statusCode(200)
                .body("centroid.type", equalTo("Point"));
    }
}