package com.fenchurchtech.messaging.common;

public class Encoder {
    private static String unreservedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.~";

    public static String UrlEncode(String value)
    {
        StringBuilder result = new StringBuilder();

        for (char symbol: value.toCharArray())
        {
            if (unreservedChars.indexOf(symbol) != -1)
            {
                result.append(symbol);
            }
            else
            {
                result.append('%' +  Integer.toHexString(symbol).toUpperCase());
            }
        }

        return result.toString();
    }

}
