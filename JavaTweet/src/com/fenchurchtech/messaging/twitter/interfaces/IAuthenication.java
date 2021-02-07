package com.fenchurchtech.messaging.twitter.interfaces;

import java.io.UnsupportedEncodingException;

public interface IAuthenication {

    String getSignatureMethod();
    void setSignatureMethod(String methodType);

    String getToken();
    void setToken(String token) throws  UnsupportedEncodingException;

    String getSecret();
    void setSecret(String secret) throws Exception;

    String getConsumerSecret();
    void setConsumerSecret(String secret) throws Exception;

    String getConsumerKey();
    void setConsumerKey(String key) throws Exception;

    String getRequestMethod();
    void setRequestMethod(String httpMethod) throws Exception;
}
