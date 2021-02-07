package com.fenchurchtech.messaging.twitter.base;

import java.io.UnsupportedEncodingException;

public class TwitterAuthProperties {
    protected String _token;
    protected String _secret;
    protected String _consumerKey;
    protected String _consumerSecret;

    public String getToken() {
        return this._token;
    }

    public void setToken(String token) throws UnsupportedEncodingException {
        this._token = token;
    }

    public String getSecret() {
        return null;
    }

    public void setSecret(String secret) throws Exception {
        if (secret.isBlank()) throw new Exception("Invalid Secret");
        this._secret = secret;
    }

    public String getConsumerSecret() {
        return this._consumerKey;
    }

    public void setConsumerSecret(String secret) throws Exception {
        if (secret.isBlank()) throw new Exception("Invalid Secret");

        this._consumerSecret = secret;
    }

    public String getConsumerKey() {
        return null;
    }

    public void setConsumerKey(String key) throws Exception {
        if (key.isBlank()) throw new Exception("Invalid Key");

        this._consumerKey = key;
    }
}
