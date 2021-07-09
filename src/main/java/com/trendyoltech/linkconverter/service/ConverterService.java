package com.trendyoltech.linkconverter.service;

import com.trendyoltech.linkconverter.data.OperationLog;
import com.trendyoltech.linkconverter.data.OperationLogRepository;
import com.trendyoltech.linkconverter.dto.URLDto;
import com.trendyoltech.linkconverter.exception.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;

import static com.trendyoltech.linkconverter.constants.LinkConstants.*;
import static com.trendyoltech.linkconverter.util.ConverterUtil.*;
import static com.trendyoltech.linkconverter.util.ErrorUtil.*;

@Service
@AllArgsConstructor
public class ConverterService {

    private final OperationLogRepository operationLogRepository;

    /**
     * @param urlDto contains url to convert
     * @return url in deep link format
     */
    public URLDto convertToDeepLink(URLDto urlDto) {
        String path = getPath(urlDto.getUrl(), WEB_BASE_URL);
        String[] pathTokens = getPathWithoutQuery(path).split(SLASH);
        LinkedHashMap<String, String> queryParameters = getQueryParameters(path);
        String url = "";
        LinkedHashMap<String, String> deepQueryParameters = new LinkedHashMap<>();
        validateUrl(urlDto.getUrl(), WEB_BASE_URL);

        if (pathTokens[0].equals(SEARCH_PATH_QUALIFIER)) {
            String query = queryParameters.get("q");
            deepQueryParameters.put(PAGE_PARAMETER_DEEP, "Search");
            deepQueryParameters.put(QUERY_PARAMETER_DEEP, query);

            url = "ty://?" + generateQueryString(deepQueryParameters);
            urlDto.setUrl(url);

            saveOperationLogToDb(urlDto.getUrl(), url, new Date(), WEB_URL_TO_DEEP_LINK);
            return urlDto;
        }

        if (path.contains(PRODUCT_PATH_QUALIFIER)) {
            String[] tokens = pathTokens[1].split("-");
            String contentId = tokens[tokens.length - 1];
            deepQueryParameters.put(PAGE_PARAMETER_DEEP, "Product");
            deepQueryParameters.put(CONTENT_ID_PARAMETER_DEEP, contentId);
            deepQueryParameters.put(CAMPAIGN_ID_PARAMETER_DEEP, queryParameters.get(BOUTIQUE_ID_PARAMETER));
            deepQueryParameters.put(MERCHANT_ID_PARAMETER_DEEP, queryParameters.get(MERCHANT_ID_PARAMETER));

            url = "ty://?" + generateQueryString(deepQueryParameters);

            saveOperationLogToDb(urlDto.getUrl(), url, new Date(), WEB_URL_TO_DEEP_LINK);
            urlDto.setUrl(url);

            return urlDto;
        }

        saveOperationLogToDb(urlDto.getUrl(), url, new Date(), WEB_URL_TO_DEEP_LINK);
        urlDto.setUrl(DEEP_BASE_HOME_URL);

        return urlDto;
    }

    /**
     * @param urlDto contains url to convert
     * @return url in web link format
     */
    public URLDto convertToWebUrl(URLDto urlDto) {
        String path = getPath(urlDto.getUrl(), DEEP_BASE_URL);
        LinkedHashMap<String, String> queryParameters = getQueryParameters(path);

        validateUrl(urlDto.getUrl(), DEEP_BASE_URL);

        String page = queryParameters.get(PAGE_PARAMETER_DEEP);

        String url = "";

        switch (page) {
            case "Product":
                url = "https://www.trendyol.com/brand/name-p-" + queryParameters.get(CONTENT_ID_PARAMETER_DEEP);

                LinkedHashMap<String, String> webQueryParameters = new LinkedHashMap<>();

                webQueryParameters.put(BOUTIQUE_ID_PARAMETER, queryParameters.get(CAMPAIGN_ID_PARAMETER_DEEP));
                webQueryParameters.put(MERCHANT_ID_PARAMETER, queryParameters.get(MERCHANT_ID_PARAMETER_DEEP));

                String query = generateQueryString(webQueryParameters);

                if (!query.isEmpty()) {
                    url += "?" + query;
                }

                saveOperationLogToDb(urlDto.getUrl(), url, new Date(), DEEP_LINK_TO_WEB_URL);
                urlDto.setUrl(url);
                break;

            case "Search":
                url = "https://www.trendyol.com/sr?q=" + queryParameters.get(QUERY_PARAMETER_DEEP);
                saveOperationLogToDb(urlDto.getUrl(), url, new Date(), DEEP_LINK_TO_WEB_URL);
                urlDto.setUrl(url);
                break;
            default:
                saveOperationLogToDb(urlDto.getUrl(), WEB_BASE_URL, new Date(), DEEP_LINK_TO_WEB_URL);
                url = WEB_BASE_URL;
                break;
        }

        saveOperationLogToDb(urlDto.getUrl(), url, new Date(), DEEP_LINK_TO_WEB_URL);
        urlDto.setUrl(url);

        return urlDto;
    }

    private void validateUrl(String url, String baseUrl) {
        if (!url.startsWith(baseUrl)) {
            throw new BadRequestException(URL_FORMAT_ERROR + url);
        } else if (url.contains(" ")) {
            throw new BadRequestException(URL_CONTAINS_EMPTY_CHARACTER + url);
        }

    }

    private void saveOperationLogToDb(String requestBody, String responseBody, Date requestTime, String requestUrl) {
        OperationLog operationLog = new OperationLog();
        operationLog.setRequest(requestBody);
        operationLog.setResponse(responseBody);
        operationLog.setRequestTime(requestTime);
        operationLog.setResponseTime(new Date());
        operationLog.setRequestUrl(requestUrl);

        operationLogRepository.save(operationLog);
    }
}
