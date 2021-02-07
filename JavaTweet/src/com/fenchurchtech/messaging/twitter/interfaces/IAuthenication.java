package com.fenchurchtech.messaging.twitter.interfaces;

import java.io.UnsupportedEncodingException;

public interface IAuthenication {

    public String getSignatureMethod();
    public void setSignatureMethod(String methodType);

    public String getToken();
    public void setToken(String token) throws  UnsupportedEncodingException;

    public String getSecret();
    public void setSecret(String secret) throws Exception;

    public String getConsumerSecret();
    void setConsumerSecret(String secret) throws Exception;

    public String getConsumerKey();
    void setConsumerKey(String key) throws Exception;

    public String getRequestMethod();
    public void setRequestMethod(String httpMethod) throws Exception;
}
