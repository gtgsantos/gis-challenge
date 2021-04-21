package com.syngenta.gtgsantos.georeferenciamento.service;


import com.syngenta.gtgsantos.georeferenciamento.service.gis.GisData;
import com.syngenta.gtgsantos.georeferenciamento.service.gis.ProcessArea;
import com.syngenta.gtgsantos.georeferenciamento.service.gis.ProcessCentroid;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class ProcessorData {


    public Map<String, Object> process(GisData gisData, String filename) {
        Map<String, Object> map = new HashMap<> ();
        
        prepareArea(gisData, map);
        prepareCentroid(gisData, map);
        prepareFilename(map, filename);

        map.put("cover", 0.23123213d);

        map.put("local_time", "2020 10 10");

        return map;
    }

    private void prepareArea(GisData gisData, Map<String, Object> mapValues) {
        mapValues.put("area", new ProcessArea().getArea(gisData.getDimensions(), gisData.getCoverage()));
    }

    private void prepareFilename(Map<String, Object> mapValues, String filename) {
        mapValues.put("filename", filename);
    }

    private void prepareCentroid(GisData gisData, Map<String, Object> mapValues) {
        Map<String, Double> centroid = new ProcessCentroid().getCentroid(gisData.getDimensions(), gisData.getCoverage());

        Map<String, Object> coordinates = new HashMap<>();
        coordinates.put("type", "Point");

        double lat = centroid.get(("lat"));
        double lon = centroid.get(("lon"));

        coordinates.put("coordinates", Arrays.asList(Arrays.asList(lat, lon)));
        mapValues.put("centroid", coordinates);
    }

}
