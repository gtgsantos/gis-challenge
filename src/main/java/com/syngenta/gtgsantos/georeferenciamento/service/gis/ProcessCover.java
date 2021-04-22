package com.syngenta.gtgsantos.georeferenciamento.service.gis;

import com.syngenta.gtgsantos.georeferenciamento.service.exception.AreaException;
import com.syngenta.gtgsantos.georeferenciamento.service.exception.CoverException;
import org.geotools.coverage.Category;
import org.geotools.coverage.GridSampleDimension;
import org.geotools.coverage.grid.GridCoordinates2D;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.GridCoverageFactory;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.geotools.referencing.GeodeticCalculator;
import org.geotools.util.NumberRange;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.opengis.coverage.grid.GridEnvelope;
import org.opengis.geometry.DirectPosition;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

import java.awt.*;
import java.awt.image.Raster;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ProcessCover {

    public double getCover(GridEnvelope dimensions, GridCoverage2D coverage) {

            Raster raster = coverage.getRenderedImage().getData();
            BigDecimal sum = BigDecimal.ZERO;
            sum = sum.setScale(10);
            BigDecimal counter = BigDecimal.ZERO;
            for (int width = 0; width < raster.getWidth(); width++) {
                for (int height = 0; height < raster.getHeight(); height++) {
                    BigDecimal red = new BigDecimal(raster.getSampleDouble(width, height, 2));
                    BigDecimal nir = new BigDecimal(raster.getSampleDouble(width, height, 3));

                    BigDecimal nirPlusRed = nir.add(red);
                    BigDecimal nirSubRed = nir.subtract(red);

                    if (!nirPlusRed.equals(BigDecimal.ZERO)) { // avoid Division by zero
                        BigDecimal ndviPixel = BigDecimal.ZERO;
                        ndviPixel = ndviPixel.setScale(10);
                        ndviPixel = nirSubRed.divide(nirPlusRed, RoundingMode.HALF_DOWN);
                        sum = sum.add(ndviPixel);
                    }

                    counter = counter.add(BigDecimal.ONE);

                }
            }

        return sum.divide(counter, RoundingMode.HALF_DOWN).doubleValue();
    }

}
