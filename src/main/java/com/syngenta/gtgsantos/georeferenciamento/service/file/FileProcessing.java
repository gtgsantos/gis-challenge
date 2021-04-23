package com.syngenta.gtgsantos.georeferenciamento.service.file;

import com.syngenta.gtgsantos.georeferenciamento.service.exception.InvalidFileException;
import com.syngenta.gtgsantos.georeferenciamento.service.exception.ProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

@Service
public class FileProcessing {

    @Value("${filename}")
    private String filename;


    @Value("${filepath}")
    private String filepath;

    @Autowired
    private ResourceLoader resourceLoader;

    public ImageInputStream getImageInputStream() {
        try {
            Resource resource1 = new ClassPathResource(filepath + filename);
            InputStream inputStream = resource1.getInputStream();
            return ImageIO.createImageInputStream(inputStream);
        } catch (IOException e) {
            throw new ProcessingException();
        }
    }
}
