package com.fenchurchtech.messaging.twitter;


import com.fenchurchtech.messaging.common.Encoder;
import com.fenchurchtech.messaging.common.QueryParameterCompare;
import com.fenchurchtech.messaging.interfaces.IAuthenication;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.*;
import com.fenchurchtech.messaging.common.QueryParameter;

public class TwitterOAuth implements IAuthenication {
    protected final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    protected final String OAuthParameterPrefix = "oauth_";
    protected final String OAuthConsumerKeyKey = "oauth_consumer_key";
    protected final String OAuthCallbackKey = "oauth_callback";
    protected final String OAuthVersionKey = "oauth_version";
    protected final String OAuthSignatureMethodKey = "oauth_signature_method";
    protected final String OAuthSignatureKey = "oauth_signature";
    protected final String OAuthTimestampKey = "oauth_timestamp";
    protected final String OAuthNonceKey = "oauth_nonce";
    protected final String OAuthTokenKey = "oauth_token";
    protected final String OAuthTokenSecretKey = "oauth_token_secret";

    private String _consumerKey;
    private String _consumerSecret;
    private String _encodedConsumerKey;
    private String _encodedConsumerSecret;
    private String _secret;
    private String _encodedSecret;
    private String _token;
    private String _encodedToken;
    private String _signatureMethod;
    private String _requestMethod;
    private String _uniqueValue;
    private URL _requestUrl;
    private long _epochTime;

    @Override
    public String getSignatureMethod() {
        return this._signatureMethod;
    }

    @Override
    public void setSignatureMethod(String methodType) {
        this._signatureMethod = methodType;
    }

    @Override
    public String getToken() {
        return this._token;
    }

    @Override
    public void setToken(String token ) throws UnsupportedEncodingException {
        this._token = token;
        this._encodedToken = Encoder.UrlEncode(token); //URLEncoder.encode(token, "UTF-8");
    }

    @Override
    public String getSecret() {
        return null;
    }

    @Override
    public void setSecret(String secret) throws Exception {
        if (secret.isBlank()) throw new Exception("Invalid Secret");

        this._secret = secret;
        this._encodedSecret = Encoder.UrlEncode(secret);  //URLEncoder.encode(secret, "UTF-8");

    }

    @Override
    public String getConsumerSecret() {
        return this._consumerKey;
    }

    @Override
    public void setConsumerSecret(String secret) throws Exception {
        if (secret.isBlank()) throw new Exception("Invalid Secret");

        this._consumerSecret = secret;
        this._encodedConsumerSecret =  Encoder.UrlEncode(secret);  //URLEncoder.encode(secret, "UTF-8");
    }

    @Override
    public String getConsumerKey() {
        return null;
    }

    @Override
    public void setConsumerKey(String key) throws Exception {
        if (key.isBlank()) throw new Exception("Invalid Key");

        this._consumerKey = key;
        this._encodedConsumerKey =  Encoder.UrlEncode(key);  //URLEncoder.encode(key, "UTF-8");
    }

    @Override
    public String getRequestMethod() {
        return _requestMethod;
    }

    @Override
    public void setRequestMethod(String httpMethod) throws Exception {
        if (httpMethod.isBlank()) throw new Exception("Invalid Request Method");
        _requestMethod = httpMethod.trim().toUpperCase();
    }

    public void setRequestUrl(String url) throws MalformedURLException {
        _requestUrl = new URL(url);;
    }

    public TwitterOAuth (){
        Instant epochNow = Instant.now();

        _uniqueValue = generateNonce();
        _epochTime = epochNow.getEpochSecond();
    }

    private String generateNonce()
    {
        // Just a simple implementation of a random number between 123400 and 9999999
        Integer i =  new Random().nextInt(976599) + 9999999;
        return  i.toString();
    }

    public String getSignature() throws UnsupportedEncodingException {
        return createSignature(createSigningKey(), createSignatureBase() );
    }

    private String createSigningKey(){
        return this._encodedConsumerSecret + "&" + _encodedSecret;
    }

    private String createSignature(String key, String encodedString){
        byte[] keyBytes = key.getBytes();

        Mac mac = null;
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, HMAC_SHA1_ALGORITHM);
            mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKeySpec);

            return Base64.getEncoder().encodeToString(mac.doFinal(encodedString.getBytes()));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }

        return "";
    }

    public String GenerateAuthHeader() throws UnsupportedEncodingException {
        return "OAuth oauth_consumer_key=\"" + this._encodedConsumerKey +
                "\",oauth_token=\"" + this._encodedToken +
                "\",oauth_signature_method=\""+ this._signatureMethod +
                "\",oauth_timestamp=\"" + _epochTime +
                "\",oauth_nonce=\"" + _uniqueValue +
                "\",oauth_version=\"1.0\",oauth_signature=\"" +   Encoder.UrlEncode(createSignature(createSigningKey(), createSignatureBase()) + "\"");   //URLEncoder.encode(createSignature(createSigningKey(), createSignatureBase()), "UTF-8") +"\"";
    }

    private static String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();

        for (byte b : bytes) {
            formatter.format("%02x", b);
        }

        return formatter.toString();
    }

    private String createSignatureBase() throws UnsupportedEncodingException{

        List<QueryParameter> parameters = GetQueryParameters(_requestUrl.getQuery().replace('?',' ').trim().split("&")); //Get the url parameters

        parameters.add(new QueryParameter(OAuthVersionKey, "1.0"));
        parameters.add(new QueryParameter(OAuthNonceKey, _uniqueValue));

        System.out.println( "Unique Value : " +  _uniqueValue);

        parameters.add(new QueryParameter(OAuthTimestampKey, Long.toString(_epochTime)));

        System.out.println( "Epoch Time : " +  _epochTime);

        parameters.add(new QueryParameter(OAuthSignatureMethodKey, _signatureMethod));
        parameters.add(new QueryParameter(OAuthConsumerKeyKey, _consumerKey));

        if (!_token.isBlank()) parameters.add(new QueryParameter(OAuthTokenKey, _token));

        parameters.sort(new QueryParameterCompare());  //OAuth requires parameter be in alphabetical order.

        String normalizedUrl =  _requestUrl.getProtocol() + "://"  + _requestUrl.getHost();
/*        if (!((_requestUrl.getProtocol() == "http" && _requestUrl.getPort() == 80) || (_requestUrl.getProtocol() == "https" && _requestUrl.getPort() == 443)))
        {
            normalizedUrl += ":" + _requestUrl.getPort();
        }*/
        normalizedUrl += _requestUrl.getPath();
        String normalizedRequestParameters = NormalizeRequestParameters(parameters);

        String signatureBase = _requestMethod.toUpperCase() + "&" +
                Encoder.UrlEncode(normalizedUrl) + "&" + // URLEncoder.encode(normalizedUrl, "UTF-8") + "&" +
                Encoder.UrlEncode(normalizedRequestParameters); //  URLEncoder.encode(normalizedRequestParameters, "UTF-8");

        System.out.println("signature base: " + signatureBase);
        return signatureBase;
    }

    private  List<QueryParameter> GetQueryParameters(String[] parameters) throws UnsupportedEncodingException {
        List<QueryParameter> result = new ArrayList<>(parameters.length);

        if (parameters.length > 0)
        {
            if(parameters[0].startsWith("?")) {parameters[0] = parameters[0].substring(1);}

            for (String s : parameters)
            {
                if (!s.isBlank() && !s.startsWith(OAuthParameterPrefix))
                {
                    if (s.indexOf('=') > -1)
                    {
                        String[] temp = s.split("=");
                        if(temp[0].toLowerCase().equals("status")){
                            result.add(new QueryParameter(temp[0],   temp[1] ));
                        }else {
                            result.add(new QueryParameter(temp[0], Encoder.UrlEncode(temp[1])));  //   URLEncoder.encode(temp[1], "utf-8")));
                        }
                    }
                    else
                    {
                        result.add(new QueryParameter(s, ""));
                    }
                }
            }
        }

        return result;
    }

    private String NormalizeRequestParameters(List<QueryParameter> parameters)
    {
        StringBuilder sb = new StringBuilder();
        QueryParameter p = null;
        boolean isFirst = true;

        for (QueryParameter qp :parameters) {
            sb.append( ((!isFirst)?"&":"")  + qp.getName() + "=" + qp.getValue());

            if (isFirst)
            {
                isFirst = false;
            }
        }

        return sb.toString();
    }
}
