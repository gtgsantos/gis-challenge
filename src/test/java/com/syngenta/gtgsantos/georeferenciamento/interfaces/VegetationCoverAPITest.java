package com.syngenta.gtgsantos.georeferenciamento.interfaces;

import io.restassured.RestAssured;
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

        given()
                .contentType(io.restassured.http.ContentType.JSON)
                .get(api)
                .then()
                .statusCode(200)
                .body("area", equalTo(1267031.25d));
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

        given()
                .contentType(io.restassured.http.ContentType.JSON)
                .get(api)
                .then()
                .statusCode(200)
                .body("cover", equalTo(00.000d));
    }

    @Test
    public void testGetEndpointCheckLocalTime() {

        given()
                .contentType(io.restassured.http.ContentType.JSON)
                .get(api)
                .then()
                .statusCode(200)
                .body("local_time", equalTo("2021-04-14T22:53:20.523333"));
    }

    @Test
    public void testGetEndpointCheckCentroidCoordinates() {

        given()
                .contentType(io.restassured.http.ContentType.JSON)
                .get(api)
                .then()
                .statusCode(200)
                .body("centroid.coordinates", equalTo("[[-47.59722892155127, -15.858576386589295]]"));
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