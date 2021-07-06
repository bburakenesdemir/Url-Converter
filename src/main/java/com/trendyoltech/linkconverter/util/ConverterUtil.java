package com.trendyoltech.linkconverter.util;

import java.util.HashMap;

import static com.trendyoltech.linkconverter.constants.LinkConstants.*;
import static com.trendyoltech.linkconverter.util.RegexUtil.getFirstMatch;

public class ConverterUtil {

    public static String getPath(String url) {
        return url.replace(BASE_URL, EMPTY_STRING);
    }

    public static String getPathWithoutQuery(String path) {
        String replacedPath = getFirstMatch(PATH_WITHOUT_QUERY_REGEX, path);
        return replacedPath.replace("?", EMPTY_STRING);
    }

    public static String getSearchQueryParameter(String path) {
        return getFirstMatch(SEARCH_QUERY_REGEX, path).replace("?q=", EMPTY_STRING);
    }

    public static HashMap<String, String> getQueryParameters(String path) {
        HashMap<String, String> queryParameters = new HashMap<>();

        if(path.contains("?")) {
            String query = path.substring(path.indexOf("?") + 1, path.length());

            for (String parameterString : query.split("&")) {
                String[] tokens = parameterString.split("=");
                queryParameters.put(tokens[0], tokens[1]);
            }
        }

        return queryParameters;
    }
}
