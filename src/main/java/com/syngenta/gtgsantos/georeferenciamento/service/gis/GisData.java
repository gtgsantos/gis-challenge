package com.syngenta.gtgsantos.georeferenciamento.service.gis;

import com.syngenta.gtgsantos.georeferenciamento.service.exception.ProcessingException;
import lombok.Getter;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.coverage.grid.io.GridCoverage2DReader;
import org.geotools.coverage.grid.io.OverviewPolicy;
import org.geotools.data.DataSourceException;
import org.geotools.gce.geotiff.GeoTiffReader;
import org.opengis.coverage.grid.GridCoordinates;
import org.opengis.coverage.grid.GridEnvelope;
import org.opengis.parameter.GeneralParameterValue;
import org.opengis.parameter.ParameterValue;
import org.opengis.referencing.operation.TransformException;

import java.io.File;
import java.io.IOException;

@Getter
public class GisData {

    private GridCoverage2D coverage;
    private GridEnvelope dimensions;

    public GisData(File file) {

        try {
            GridCoverage2DReader reader = new GeoTiffReader(file);

            dimensions = reader.getOriginalGridRange();

            ParameterValue<OverviewPolicy> policy = AbstractGridFormat.OVERVIEW_POLICY.createValue();
            policy.setValue(OverviewPolicy.IGNORE);
            ParameterValue<String> gridSize = AbstractGridFormat.SUGGESTED_TILE_SIZE.createValue();
            ParameterValue<Boolean> useJaiRead = AbstractGridFormat.USE_JAI_IMAGEREAD.createValue();
            useJaiRead.setValue(true);

            coverage = reader.read(
                    new GeneralParameterValue[]{policy, gridSize, useJaiRead}
            );


//            new Centroide(dimensions, coverage);
//            new AreaCalculo(dimensions, coverage);
//            new TZone(dimensions, coverage);


        } catch (IOException e) {
            throw new ProcessingException();
        }
    }
}