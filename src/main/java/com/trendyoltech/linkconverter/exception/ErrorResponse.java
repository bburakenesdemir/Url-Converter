package com.trendyoltech.linkconverter.exception;

import lombok.Data;

@Data
public class ErrorResponse {
    private String errorMessage;

    private Integer resultCode;

    private String result;

    private String requestUrl;
}
