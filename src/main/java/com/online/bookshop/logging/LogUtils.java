package com.online.bookshop.logging;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.math.BigDecimal;

public class LogUtils {

    /*
    * Define this log filter marker in Log4j2.xml
    * */
    public static Marker logFilter() {
        Marker marker = MarkerFactory.getMarker("partnerIntegration");
        return marker;
    }
}
