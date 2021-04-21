package com.syngenta.gtgsantos.georeferenciamento.service;


import com.syngenta.gtgsantos.georeferenciamento.service.file.FileProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class VegetationService {

    @Autowired
    private FileProcessing fileProcessing;

    @Value("${filename}")
    private String filename;

    public Map<String, Object> getVegetationData() {
        //        VegetationData vegetationData = new VegetationData();
//        vegetationData.setCover(12312312d);
//        vegetationData.setArea(3333333333d);
//        vegetationData.setFilename("teste.teste");
//        vegetationData.setLocalTime("ninino nononon");

        Map<String, Object> coordinates = new HashMap<String, Object>();
        coordinates.put("type", "Point");


        double[] innerArray = {111d, 222d};
        double[][] array = {innerArray};
        coordinates.put("coordinates", array);

//        coordinates.put("coordinates", 22);

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
