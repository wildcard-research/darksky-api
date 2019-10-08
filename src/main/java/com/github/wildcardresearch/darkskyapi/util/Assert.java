package com.github.wildcardresearch.darkskyapi.util;

/**
 * Class for static assertions.
 */
public class Assert {

    /**
     * Throw Assertion error if object is null with the given message.
     * @param obj Object to check
     * @param messageIfNull Message to display if obj is null
     */
    public static void notNull(Object obj, String messageIfNull) {
        if(obj == null) {
            throw new AssertionError(messageIfNull);
        }
    }

    /**
     * Throw Assertion error if String is null.
     * @param str String to check
     */
    public static void notNullOrEmpty(String str) {
        if(str == null || str.isEmpty()){
            throw new AssertionError("Input String is either null or empty");
        }
    }
}
