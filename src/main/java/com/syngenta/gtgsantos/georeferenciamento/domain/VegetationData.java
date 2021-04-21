package com.syngenta.gtgsantos.georeferenciamento.domain;

import lombok.Data;

@Data
public class VegetationData {

    private double area;
    private double cover;
    private String filename;
    private String localTime;
    private Coordinate coordinate;
}