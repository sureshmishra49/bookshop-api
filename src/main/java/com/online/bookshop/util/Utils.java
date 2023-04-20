package com.online.bookshop.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.online.bookshop.domain.BooksModel;
import com.online.bookshop.dto.BaseResult;
import com.online.bookshop.logging.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Component
public class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);
    private final Gson gsonObj = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
    private final String SECURITY_TOKEN = "SECURITY_TOKEN";
    private final Environment environment;

    public Utils(Environment environment) {
        this.environment = environment;
    }

    public String getSecurityToken () {
        return environment!=null && environment.getProperty(SECURITY_TOKEN)!=null ? environment.getProperty(SECURITY_TOKEN) : EMPTY;
    }

    public void logRequestAndResponse(String methodName, BooksModel model, BaseResult result) {
        BooksModel logModel = model;
        if (null != logModel) {
            logger.debug(LogUtils.logFilter(), "{} - requestJson --> {} ", methodName, gsonObj.toJson(logModel));
        } else if (null != result) {
            logger.debug(LogUtils.logFilter(), "{} - responseJson --> {} ", methodName, gsonObj.toJson(result));
        }
    }

}
