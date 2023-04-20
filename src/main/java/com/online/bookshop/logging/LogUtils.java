package com.online.bookshop.logging;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.math.BigDecimal;

public class LogUtils {

    private static final Logger logger = LoggerFactory.getLogger(LogUtils.class);

    /*
    * Define this log filter marker in Log4j2.xml
    * */
    public static Marker logFilter() {
        Marker marker = MarkerFactory.getMarker("partnerIntegration");
        return marker;
    }

    public static String toJson(String property, String value) {
        if (value == null)
            return null;

        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(property, value);
        return jsonObject.toString();
    }

    public static String toJson(String property, BigDecimal value) {
        if (value == null)
            return null;

        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(property, value);
        return jsonObject.toString();
    }

    public static String toJson(String property, Long value) {
        if (value == null)
            return null;

        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(property, value);
        return jsonObject.toString();
    }
}
