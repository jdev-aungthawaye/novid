package jdev.novid.component.asmapper;

import com.github.filosganga.geogson.gson.GeometryAdapterFactory;
import com.github.filosganga.geogson.model.MultiPolygon;
import com.github.filosganga.geogson.model.Point;
import com.github.filosganga.geogson.model.Polygon;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GeoGsonUtil {

    private final static Gson GEO_GSON;

    static {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new GeometryAdapterFactory()).create();
        GEO_GSON = gson;
    }

    public static String fromMultiPolygon(MultiPolygon multiPolygon) {

        return GEO_GSON.toJson(multiPolygon);
    }

    public static String fromPoint(Point point) {

        return GEO_GSON.toJson(point);
    }

    public static String fromPolygon(Polygon polygon) {

        return GEO_GSON.toJson(polygon);
    }

    public static MultiPolygon toMultiPolygon(String json) {

        return GEO_GSON.fromJson(json, MultiPolygon.class);
    }

    public static Point toPoint(String json) {

        return GEO_GSON.fromJson(json, Point.class);
    }

    public static Polygon toPolygon(String json) {

        return GEO_GSON.fromJson(json, Polygon.class);
    }
}
