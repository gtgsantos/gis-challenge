package com.syngenta.gtgsantos.georeferenciamento.service.gis;

import com.syngenta.gtgsantos.georeferenciamento.service.exception.LocalTimeException;
import net.iakovlev.timeshape.TimeZoneEngine;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class ProcessLocalTime {

    public static final String ISO_8601_OUTPUT = "yyyy-MM-dd'T'HH:mm:ss.SSSSS";

    public String getLocalTime(GisData gisData, String filename) {

        Map<String, Double> centroid = new ProcessCentroid().getCentroid(gisData.getDimensions(), gisData.getCoverage());

        int indexDash = filename.lastIndexOf("-") + 1;
        int indexFormatFile = filename.indexOf(".tif", indexDash);

        validateDataOnFilename(indexDash, indexFormatFile);

        TimeZoneEngine engine = TimeZoneEngine.initialize();
        Optional<ZoneId> optionalShotZone = engine.query(centroid.get("lat"), centroid.get("lon"));
        ZoneId shotZone = optionalShotZone.get();

        String localTimeSubstring = filename.substring(indexDash, indexFormatFile);
        DateTimeFormatter formatador = new DateTimeFormatterBuilder()
                .appendPattern("yyyyMMdd")
                .appendLiteral("T")
                .appendPattern("HHmmss")
                .appendLiteral("Z")
                .parseCaseInsensitive()
                .toFormatter(Locale.US);


        LocalDateTime localDateTime = LocalDateTime.parse(localTimeSubstring, formatador);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, shotZone);

        ZonedDateTime changedZonedTimeZone = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault());

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern(ISO_8601_OUTPUT);


        return changedZonedTimeZone.format(formatter2);
    }

    private void validateDataOnFilename(int indexDash, int indexFormatFile) {
        if (indexDash < 0 || indexFormatFile < indexDash || ((indexFormatFile - indexDash) != 16)) {
            throw new LocalTimeException();
        }
    }
}
