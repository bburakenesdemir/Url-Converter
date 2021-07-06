package com.trendyoltech.linkconverter.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class URLDto {
    @NotNull
    String url;
}
