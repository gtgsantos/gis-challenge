package com.syngenta.gtgsantos.georeferenciamento.service;


import com.syngenta.gtgsantos.georeferenciamento.service.file.FileProcessing;
import com.syngenta.gtgsantos.georeferenciamento.service.gis.GisData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class VegetationService {

    @Autowired
    private FileProcessing fileProcessing;

    @Value("${filename}")
    private String filename;

    public Map<String, Object> getVegetationData() {

        GisData gisData = new GisData(fileProcessing.getFile());

        return new ProcessorJSONDataResponse().process(gisData, filename);
    }

}
