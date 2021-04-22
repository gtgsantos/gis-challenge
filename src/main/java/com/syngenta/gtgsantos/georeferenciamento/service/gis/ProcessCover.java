package com.syngenta.gtgsantos.georeferenciamento.service.gis;

import org.geotools.coverage.grid.GridCoverage2D;
import org.opengis.coverage.grid.GridEnvelope;

import java.awt.image.Raster;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ProcessCover {

    public static final int RED_BAND = 2;
    public static final int NIR_BAND = 3;

    public double getCover(GridCoverage2D coverage) {

            Raster raster = coverage.getRenderedImage().getData();
            BigDecimal sum = BigDecimal.ZERO;
            sum = sum.setScale(10);
            BigDecimal counter = BigDecimal.ZERO;
            for (int width = 0; width < raster.getWidth(); width++) {
                for (int height = 0; height < raster.getHeight(); height++) {
                    BigDecimal red = new BigDecimal(raster.getSampleDouble(width, height, RED_BAND));
                    BigDecimal nir = new BigDecimal(raster.getSampleDouble(width, height, NIR_BAND));

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
