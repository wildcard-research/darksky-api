package com.github.wildcardresearch.darkskyapi.util;

public class Assert {
    public static void notNullOrEmpty(String str) {
        if(str == null || str.isEmpty()){
            throw new AssertionError("Input String is either null or empty");
        }
    }
}
