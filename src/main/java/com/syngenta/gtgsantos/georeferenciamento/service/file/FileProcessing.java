package com.syngenta.gtgsantos.georeferenciamento.service.file;

import com.syngenta.gtgsantos.georeferenciamento.service.exception.InvalidFileException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;

@Service
public class FileProcessing {

    @Value("${filename}")
    private String filename;

    public File getFile() {
        try {
            return Paths.get(ClassLoader.getSystemResource(filename).toURI()).toFile();
        } catch (URISyntaxException e) {
            throw new InvalidFileException();
        }
    }
}
