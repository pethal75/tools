package com.javaservices.tools.utils;

/**
 * Utility class for String operations.
 */
public class StringUtils {

    /**
     * Trims the input string to a specified maximum length and appends "..." if the string exceeds that length.
     *
     * @param str The input string to be processed. If this is null, an empty string will be returned.
     * @param maxLength The maximum allowable length for the output string. If this is -1, the entire input string is returned.
     * @return The processed string, trimmed to the specified maximum length with "..." appended if necessary.
     */
    public static String getPrintableString(String str, int maxLength) {
        if (str == null)
            return "";

        if (maxLength == -1)
            return str;

        if (str.length() < maxLength)
            return str;

        return str.substring(0,maxLength) + "...";
    }
}
