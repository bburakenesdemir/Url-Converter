package com.trendyoltech.linkconverter.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.trendyoltech.linkconverter.constants.LinkConstants.EMPTY_STRING;

public class RegexUtil {

    public static String getFirstMatch(String regex,String path){
        Matcher m = Pattern.compile(regex).matcher(path);
        return m.find() ? m.group(0) : EMPTY_STRING;
    }
}
