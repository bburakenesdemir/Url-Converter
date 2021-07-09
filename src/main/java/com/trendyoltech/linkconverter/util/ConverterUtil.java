package com.trendyoltech.linkconverter.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static com.trendyoltech.linkconverter.constants.LinkConstants.*;
import static com.trendyoltech.linkconverter.util.RegexUtil.getFirstMatch;

public class ConverterUtil {

    /**
     * The base url is deleted from the url in the request body and only path field is returned.
     * @param url full url
     * @param baseUrl webUrl base or deepLink base (trendyol.com or ty://)
     * @return url without baseUrl
     */
    public static String getPath(String url,String baseUrl) {
        return url.replace(baseUrl, EMPTY_STRING);
    }

    /**
     * Returns the path without query parameters
     * @param path path without base url
     * @return path without query parameters
     */
    public static String getPathWithoutQuery(String path) {
        String replacedPath = getFirstMatch(PATH_WITHOUT_QUERY_REGEX, path);
        return replacedPath.replace("?", EMPTY_STRING);
    }

    /**
     * Returns the query parameters as a LinkedHashMap
     * @param path path without base url
     * @return LinkedHashMap with query parameters (Page=Product&ContentId=1925865&CampaignId=439892&MerchantId=105064)
     */
    public static LinkedHashMap<String, String> getQueryParameters(String path) {
        LinkedHashMap<String, String> queryParameters = new LinkedHashMap<>();

        if(path.contains("?")) {
            String query = path.substring(path.indexOf("?") + 1, path.length());

            for (String parameterString : query.split("&")) {
                String[] tokens = parameterString.split("=");
                queryParameters.put(tokens[0], tokens[1]);
            }
        }

        return queryParameters;
    }

    /**
     * Returns generated query string from query map
     * @param queryMap as a key value string pairs
     * @return query string
     */
    public static String generateQueryString(LinkedHashMap<String,String> queryMap){
        ArrayList<String> tokens = new ArrayList<>();
        queryMap.forEach((String key, String value) -> {
            if(value != null)
                tokens.add(key + "=" + value);
        });

        return String.join("&", tokens);
    }
}
