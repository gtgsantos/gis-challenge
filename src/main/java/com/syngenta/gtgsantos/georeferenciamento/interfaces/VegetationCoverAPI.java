package com.syngenta.gtgsantos.georeferenciamento.interfaces;

import com.syngenta.gtgsantos.georeferenciamento.RetornoVegetacao;
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


    @GetMapping
    public Map<String, Object> getData() {
        return new RetornoVegetacao().getMapaRetornoJSON();
    }

}



