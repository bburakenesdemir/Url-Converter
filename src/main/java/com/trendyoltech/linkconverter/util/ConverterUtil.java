package com.trendyoltech.linkconverter.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static com.trendyoltech.linkconverter.constants.LinkConstants.*;
import static com.trendyoltech.linkconverter.util.RegexUtil.getFirstMatch;

public class ConverterUtil {

    public static String getPath(String url,String baseUrl) {
        return url.replace(baseUrl, EMPTY_STRING);
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

    public static String generateQueryString(HashMap<String,String> queryMap){
        ArrayList<String> tokens = new ArrayList<>();
        queryMap.forEach((String key, String value) -> {
            tokens.add(key + "=" + value);
        });

        Collections.reverse(tokens);
        return String.join("&", tokens);
    }

    public static <K,V> HashMap<K,V> transformKeys(HashMap<K,K> keyMapping,  HashMap<K,V> sourceMap){
        keyMapping.forEach((oldKey,newKey) -> {
            if(sourceMap.containsKey(oldKey)){
                sourceMap.put(newKey, sourceMap.get(oldKey));
                sourceMap.remove(oldKey);
            }
        });
        return sourceMap;
    }
}
