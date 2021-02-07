package com.fenchurchtech.messaging.twitter.common;

public class Encoder {

    public static String UrlEncode(String value)
    {
        StringBuilder result = new StringBuilder();

        for (char symbol: value.toCharArray())
        {
            String unreservedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.~";
            if (unreservedChars.indexOf(symbol) != -1)
            {
                result.append(symbol);
            }
            else
            {
                result.append('%').append(Integer.toHexString((int) symbol).toUpperCase());
            }
        }

        return result.toString();
    }

}
