package com.trendyoltech.linkconverter.controller;

import com.trendyoltech.linkconverter.dto.URLDto;
import com.trendyoltech.linkconverter.service.ConverterService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/converter")
@AllArgsConstructor
public class ConvertController {

    private final ConverterService converterService;

    @PostMapping("/web-url-to-deep-link")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<URLDto> convertToDeepLink(@RequestBody @Valid URLDto urlDto) {
        return ResponseEntity.ok(converterService.convertToDeepLink(urlDto));
    }

    @PostMapping("/deep-link-to-web-url")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<URLDto> convertToWebUrl(@RequestBody @Valid URLDto urlDto){
        return ResponseEntity.ok(converterService.convertToWebUrl(urlDto));
    }

}
