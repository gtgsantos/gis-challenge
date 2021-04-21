package com.syngenta.gtgsantos.georeferenciamento.service.gis;

import com.syngenta.gtgsantos.georeferenciamento.service.exception.AreaException;
import org.geotools.coverage.grid.GridCoordinates2D;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.geotools.referencing.GeodeticCalculator;
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

public class ProcessArea {

    public double getArea(GridEnvelope dimensions, GridCoverage2D coverage)  {
        try {

            Coordinate coordInitX = getCoordenates(coverage,  new GridCoordinates2D(dimensions.getLow(0), dimensions.getLow(1)));
            Coordinate coordEndX = getCoordenates(coverage, new GridCoordinates2D(dimensions.getHigh(0), dimensions.getLow(1)));
            Coordinate coordInitY = getCoordenates(coverage, new GridCoordinates2D(dimensions.getLow(0), dimensions.getLow(1)));
            Coordinate coordEndY = getCoordenates(coverage, new GridCoordinates2D(dimensions.getLow(0), dimensions.getHigh(1)));

            double distanceX = getDistance(coordInitX, coordEndX);
            double distanceY = getDistance(coordInitY, coordEndY);

            return calculateArea(distanceX, distanceY);

        } catch (FactoryException | TransformException e) {
            throw new AreaException();
        }
    }

    private double calculateArea(double distanceX, double distanceY) {
        return distanceX * distanceY;
    }

    private double getDistance(Coordinate coordenadaInicio, Coordinate coordenadaFim) throws FactoryException, TransformException {
        CoordinateReferenceSystem crs = CRS.decode("EPSG:4326");
        GeodeticCalculator calculator = new GeodeticCalculator(crs);
        calculator.setStartingPosition(JTS.toDirectPosition(coordenadaInicio, crs));
        calculator.setDestinationPosition(JTS.toDirectPosition(coordenadaFim, crs));

        return calculator.getOrthodromicDistance();
    }

    private Coordinate getCoordenates(GridCoverage2D coverage, GridCoordinates2D coord2d) throws FactoryException, TransformException {
        CoordinateReferenceSystem crs = CRS.decode("EPSG:4326");
        CoordinateReferenceSystem target = coverage.getCoordinateReferenceSystem();
        GeometryFactory gf = new GeometryFactory();
        MathTransform targetToWgs = CRS.findMathTransform(target, crs);

        DirectPosition p = coverage.getGridGeometry().gridToWorld(coord2d);
        Point point = gf.createPoint(new Coordinate(p.getOrdinate(0), p.getOrdinate(1)));
        Geometry wgsP = JTS.transform(point, targetToWgs);

        return wgsP.getCentroid().getCoordinate();
    }

}
