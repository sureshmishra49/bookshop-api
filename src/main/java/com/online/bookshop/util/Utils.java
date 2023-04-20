package com.online.bookshop.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.online.bookshop.domain.BooksModel;
import com.online.bookshop.dto.BaseResult;
import com.online.bookshop.logging.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);
    private final Gson gsonObj = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    public void logRequestAndResponse(String methodName, BooksModel model, BaseResult result) {
        if (null != model) {
            logger.debug(LogUtils.logFilter(), "{} - requestJson --> {} ", methodName, gsonObj.toJson(model));
        } else if (null != result) {
            logger.debug(LogUtils.logFilter(), "{} - responseJson --> {} ", methodName, gsonObj.toJson(result));
        }
    }

}
