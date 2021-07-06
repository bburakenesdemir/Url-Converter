package com.trendyoltech.linkconverter.service;

import com.trendyoltech.linkconverter.dto.URLDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import static com.trendyoltech.linkconverter.constants.LinkConstants.*;
import static com.trendyoltech.linkconverter.util.ConverterUtil.*;

@Log4j2
@Service
@AllArgsConstructor
public class ConverterService {

    /**
     * @param urlDto contains url to convert
     * @return url in deep link format
     */
    public URLDto convertToDeepLink(URLDto urlDto) {
        String path = getPath(urlDto.getUrl(), WEB_BASE_URL);
        String[] pathTokens = getPathWithoutQuery(path).split(SLASH);
        HashMap<String, String> queryParameters = getQueryParameters(path);

        if (pathTokens[0].equals(SEARCH_PATH_QUALIFIER)) {
            String query = getSearchQueryParameter(path);
            String url = "ty://?Page=Search&Query=" + query;
            urlDto.setUrl(url);
            return urlDto;
        }

        if (path.contains(PRODUCT_PATH_QUALIFIER)) {
            String[] tokens = pathTokens[1].split("-");
            String contentId = tokens[tokens.length - 1];

            String boutiqueId = queryParameters.get(BOUTIQUE_ID_PARAMETER);
            String merchantId = queryParameters.get(MERCHANT_ID_PARAMETER);

            String url = "ty://?Page=Product&ContentId=" + contentId;

            if (boutiqueId != null) {
                url += "&CampaignId=" + boutiqueId;
            }

            if (merchantId != null) {
                url += "&MerchantId=" + merchantId;
            }
            urlDto.setUrl(url);

            return urlDto;
        }

        urlDto.setUrl(DEEP_BASE_HOME_URL);

        return urlDto;
    }

    /**
     * @param urlDto contains url to convert
     * @return url in web link format
     */
    public URLDto convertToWebUrl(URLDto urlDto) {
        String path = getPath(urlDto.getUrl(), DEEP_BASE_URL);
        HashMap<String, String> queryParameters = getQueryParameters(path);
        HashMap<String, String> keyMapping = new HashMap<>();

        String page = queryParameters.get(PAGE_PARAMETER_DEEP);
        queryParameters.remove(PAGE_PARAMETER_DEEP);
        keyMapping.put(CAMPAIGN_ID_PARAMETER_DEEP, BOUTIQUE_ID_PARAMETER);
        keyMapping.put(MERCHANT_ID_PARAMETER_DEEP, MERCHANT_ID_PARAMETER);
        transformKeys(keyMapping,queryParameters);

        String url;

        switch (page) {
            case "Product":
                url = "https://www.trendyol.com/brand/name-p-" + queryParameters.get(CONTENT_ID_PARAMETER_DEEP);
                queryParameters.remove(CONTENT_ID_PARAMETER_DEEP);
                String query = generateQueryString(queryParameters);

                if (!query.isEmpty()) {
                    url += "?" + query;
                }
                urlDto.setUrl(url);
                break;

            case "Search":
                url = "https://www.trendyol.com/sr?q=" + queryParameters.get(QUERY_PARAMETER_DEEP);
                urlDto.setUrl(url);
                break;
            default:
                url = WEB_BASE_URL;
                break;
        }

        urlDto.setUrl(url);

        return urlDto;
    }
}
