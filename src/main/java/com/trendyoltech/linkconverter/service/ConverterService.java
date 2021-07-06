package com.trendyoltech.linkconverter.service;

import com.trendyoltech.linkconverter.dto.URLDto;
import com.trendyoltech.linkconverter.util.ConverterUtil;
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
        String path = getPath(urlDto.getUrl());
        String[] pathTokens = getPathWithoutQuery(path).split(SLASH);
        HashMap<String, String> queryParameters = getQueryParameters(path);

        if (pathTokens[0].equals(SEARCH_PATH)) {
            String query = getSearchQueryParameter(path);
            String url = "ty://?Page=Search&Query=" + query;
            urlDto.setUrl(url);
            return urlDto;
        }

        if (path.contains(PRODUCT_PATH_QUALIFIER)) {
            String[] tokens = pathTokens[1].split("-");
            String contentId = tokens[tokens.length - 1];

            String boutiqueId = queryParameters.get("boutiqueId");
            String merchantId = queryParameters.get("merchantId");

            String url = "ty://?Page=Product&ContentId=" + contentId;

            if(boutiqueId != null){
                url += "&CampaignId=" + boutiqueId;
            }

            if(merchantId != null){
                url += "&MerchantId=" + merchantId;
            }
            urlDto.setUrl(url);

            return urlDto;
        }

        urlDto.setUrl("ty://?Page=Home");

        return urlDto;
    }

    /**
     * @param urlDto contains url to convert
     * @return url in web link format
     */
    public URLDto convertToWebUrl(URLDto urlDto) {

        return null;
    }
}
