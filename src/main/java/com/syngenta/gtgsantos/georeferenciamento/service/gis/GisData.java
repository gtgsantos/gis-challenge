package com.syngenta.gtgsantos.georeferenciamento.service.gis;

import com.syngenta.gtgsantos.georeferenciamento.service.exception.ProcessingException;
import lombok.Getter;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.coverage.grid.io.GridCoverage2DReader;
import org.geotools.coverage.grid.io.OverviewPolicy;
import org.geotools.gce.geotiff.GeoTiffReader;
import org.geotools.map.GridReaderLayer;
import org.opengis.coverage.grid.GridEnvelope;
import org.opengis.parameter.GeneralParameterValue;
import org.opengis.parameter.ParameterValue;

import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.IOException;

@Getter
public class GisData {

    private GridCoverage2D coverage;
    private GridEnvelope dimensions;

    public GisData(ImageInputStream imageInputStream) {

        try {
            GridCoverage2DReader reader = new GeoTiffReader(imageInputStream);
            dimensions = reader.getOriginalGridRange();

            ParameterValue<OverviewPolicy> policy = AbstractGridFormat.OVERVIEW_POLICY.createValue();
            policy.setValue(OverviewPolicy.IGNORE);
            ParameterValue<String> gridSize = AbstractGridFormat.SUGGESTED_TILE_SIZE.createValue();
            ParameterValue<Boolean> useJaiRead = AbstractGridFormat.USE_JAI_IMAGEREAD.createValue();
            useJaiRead.setValue(true);

            coverage = reader.read(
                    new GeneralParameterValue[]{policy, gridSize, useJaiRead}
            );

        } catch (IOException e) {
            e.printStackTrace();
            throw new ProcessingException();
        }
    }
}
