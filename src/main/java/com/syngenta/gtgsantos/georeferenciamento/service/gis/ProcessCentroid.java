package com.syngenta.gtgsantos.georeferenciamento.service.gis;

import com.syngenta.gtgsantos.georeferenciamento.service.exception.CentroidException;
import org.geotools.coverage.grid.GridCoordinates2D;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class ProcessCentroid {


    public Map<String, Double> getCentroid(GridEnvelope dimensions, GridCoverage2D coverage)  {

        try {
            CoordinateReferenceSystem wgs84 = DefaultGeographicCRS.WGS84;
            CoordinateReferenceSystem target = coverage.getCoordinateReferenceSystem();
            GeometryFactory gf = new GeometryFactory();
            MathTransform targetToWgs = CRS.findMathTransform(target, wgs84);

            int count = 0;
            BigDecimal latBig = BigDecimal.ZERO;
            BigDecimal lonBig = BigDecimal.ZERO;
            BigDecimal counter = BigDecimal.ZERO;
            for (int j = dimensions.getLow(1); j < dimensions.getHigh(1); j++) {
                for (int i = dimensions.getLow(0); i < dimensions.getHigh(0); i++) {


                    GridCoordinates2D coord = new GridCoordinates2D(i, j);
                    DirectPosition p = coverage.getGridGeometry().gridToWorld(coord);
                    Point point = gf.createPoint(new Coordinate(p.getOrdinate(0), p.getOrdinate(1)));
                    Geometry wgsP = JTS.transform(point, targetToWgs);

                    counter = counter.add(BigDecimal.ONE);

                    latBig = latBig.add(new BigDecimal(wgsP.getCentroid().getCoordinate().x));
                    lonBig = lonBig.add(new BigDecimal(wgsP.getCentroid().getCoordinate().y));

                }
            }

            latBig = latBig.divide(counter, RoundingMode.HALF_DOWN);
            lonBig = lonBig.divide(counter, RoundingMode.HALF_DOWN);

            Map<String, Double> returnMap =  new HashMap<String, Double>();
            returnMap.put("lat", latBig.doubleValue());
            returnMap.put("lon", lonBig.doubleValue());

            return returnMap;

        } catch (FactoryException | TransformException e) {
            throw new CentroidException();
        }
    }

}
