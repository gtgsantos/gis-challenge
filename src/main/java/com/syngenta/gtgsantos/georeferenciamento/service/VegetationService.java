package com.syngenta.gtgsantos.georeferenciamento.service;


import com.syngenta.gtgsantos.georeferenciamento.service.file.FileProcessing;
import com.syngenta.gtgsantos.georeferenciamento.service.gis.GisData;
import com.syngenta.gtgsantos.georeferenciamento.service.gis.ProcessCentroid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class VegetationService {

    @Autowired
    private FileProcessing fileProcessing;

    @Value("${filename}")
    private String filename;

    public Map<String, Object> getVegetationData() {

        GisData gisData = new GisData(fileProcessing.getFile());

        Map<String, Object> centroid = new ProcessCentroid().getCentroid(gisData.getDimensions(), gisData.getCoverage());

        Map<String, Object> coordinates = new HashMap<String, Object>();
        coordinates.put("type", "Point");

        double lat = (Double) centroid.get(("lat"));
        double lon = (Double) centroid.get(("lon"));
//        String[] innerArray = {String.valueOf(lat), String.valueOf(lon)};
//        String[][] array = {innerArray};

        coordinates.put("coordinates", Arrays.asList(Arrays.asList(lat, lon)));

        Map<String, Object> mapaRetorno = new HashMap<String, Object>();
        mapaRetorno.put("area", 2131123d);
        mapaRetorno.put("centroid", coordinates);
        mapaRetorno.put("cover", 0.23123213d);
        mapaRetorno.put("filename", filename);
        mapaRetorno.put("local_time", "2020 10 10");

//        return vegetationData;
        return mapaRetorno;
    }

}
