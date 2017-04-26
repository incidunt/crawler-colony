package com.dang.crawler.core.fetcher.service;


public enum PostTypeEnum {
    XML("xml"),
    JSON("json"),
    NAME_VALUE("NameValue"),
    STRING("String");

    private final String value;

    private PostTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static String getTitleByValue(String value) {
        PostTypeEnum[] arr$ = values();
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            PostTypeEnum d = arr$[i$];
            if(d.getValue().equals(value)) {
                return d.toString();
            }
        }

        return null;
    }
}
