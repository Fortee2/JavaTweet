package com.fenchurchtech.messaging.twitter;

import com.fenchurchtech.messaging.common.Encoder;
import com.fenchurchtech.messaging.interfaces.APIRequest;
import com.fenchurchtech.messaging.interfaces.INotification;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class TwitterStatus implements APIRequest {
    private String _endpoint = "";
    private String _authorizationHeader ="";
    private String _encodedTweet = "";
    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private String _token;
    private String _secret;
    private String _consumerKey;
    private String _consumerSecret;

    @Override
    public String getEndPoint() {
        return _endpoint;
    }

    @Override
    public void setEndpoint(String endpoint) {
        //TODO: Add some validation

         _endpoint = endpoint;
    }

    public String getToken() {
        return this._token;
    }

    public void setToken(String token ) throws UnsupportedEncodingException {
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

    @Override
    public INotification SendNotification(INotification message) throws Exception {

        //TODO:Validate message length if long than 280 character tweet over multiple lines.
        //URLs are automatically shortened to 22 characters with bit.ly

         _encodedTweet = Encoder.UrlEncode(message.getMessage());   //URLEncoder.encode(message.getMessage(), "UTF-8");

        TwitterOAuth auth = new TwitterOAuth();
        auth.setConsumerKey(_consumerKey);
        auth.setConsumerSecret(_consumerSecret);
        auth.setRequestMethod("POST");
        auth.setSignatureMethod("HMAC-SHA1");
        auth.setRequestUrl(_endpoint + _encodedTweet);
        auth.setToken(_token);
        auth.setSecret(_secret);

        _authorizationHeader = auth.GenerateAuthHeader();

        System.out.println(_authorizationHeader);
        message.setStatus(sendToTwitter());
        return message;
    }

    private String sendToTwitter() throws IOException {
        HttpPost postNotification = new HttpPost(_endpoint + _encodedTweet);

        postNotification.addHeader("Authorization", _authorizationHeader);
        postNotification.addHeader("User-Agent","FenchurchRuntime 0.01");
        postNotification.addHeader("Host", "api.twitter.com");
        postNotification.addHeader("Accept", "*/*");
        postNotification.addHeader("Accept-Encoding", "utf-8");

        CloseableHttpResponse resp = httpClient.execute(postNotification);

        System.out.println("POST Response Status:: "
                + resp.getStatusLine().getStatusCode());

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                resp.getEntity().getContent()));

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();

        // print result
        System.out.println(response.toString());
        httpClient.close();

        return response.toString();
    }


}
