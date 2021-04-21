package com.syngenta.gtgsantos.georeferenciamento.interfaces;

import com.syngenta.gtgsantos.georeferenciamento.service.VegetationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Service
@RestController
@RequestMapping(path="/vegetation-cover", produces = MediaType.APPLICATION_JSON_VALUE)
public class VegetationCoverAPI {

    @Autowired
    private VegetationService vegetationService;


    @GetMapping
    public Map<String, Object> getVegetationData() {
        return vegetationService.getVegetationData();
    }
}