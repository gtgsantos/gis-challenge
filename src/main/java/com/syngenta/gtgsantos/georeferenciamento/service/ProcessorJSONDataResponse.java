package com.syngenta.gtgsantos.georeferenciamento.service;


import com.syngenta.gtgsantos.georeferenciamento.service.gis.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class ProcessorJSONDataResponse {


    public Map<String, Object> process(GisData gisData, String filename) {
        Map<String, Object> map = new HashMap<> ();
        
        prepareArea(gisData, map);
        prepareCentroid(gisData, map);
        prepareFilename(map, filename);
        prepareLocalTime(gisData, filename, map);
        prepareCover(gisData, map);

        return map;
    }

    private void prepareLocalTime(GisData gisData, String filename, Map<String, Object> mapValues) {
        mapValues.put("local_time", new ProcessLocalTime().getLocalTime(gisData, filename));
    }

    private void prepareArea(GisData gisData, Map<String, Object> mapValues) {
        mapValues.put("area", new ProcessArea().getArea(gisData.getDimensions(), gisData.getCoverage()));
    }

    private void prepareCover(GisData gisData, Map<String, Object> mapValues) {
        mapValues.put("cover", new ProcessCover().getCover(gisData.getCoverage()));
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
