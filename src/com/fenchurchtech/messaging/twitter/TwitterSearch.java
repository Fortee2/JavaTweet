package com.fenchurchtech.messaging.twitter;

import com.fenchurchtech.messaging.common.Encoder;
import com.fenchurchtech.messaging.interfaces.APIRequest;
import com.fenchurchtech.messaging.interfaces.INotification;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class TwitterSearch implements APIRequest {
    private String _endpoint = "";
    private String _authorizationHeader ="";
    private String _encodedTerm = "";
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

    @Override
    public INotification SendNotification(INotification message) throws Exception {
        return null;
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

    public void SendSearch(String searchTerm){
        _encodedTerm = Encoder.UrlEncode(searchTerm);   //URLEncoder.encode(message.getMessage(), "UTF-8");

        try{
            TwitterOAuth auth = new TwitterOAuth();
            auth.setConsumerKey(_consumerKey);
            auth.setConsumerSecret(_consumerSecret);
            auth.setRequestMethod("GET");
            auth.setSignatureMethod("HMAC-SHA1");
            auth.setRequestUrl(_endpoint + searchTerm);
            auth.setToken(_token);
            auth.setSecret(_secret);

            _authorizationHeader = auth.GenerateAuthHeader();

            HttpGet getRequest = new HttpGet(_endpoint + searchTerm);

            System.out.println(_endpoint + searchTerm);

            getRequest.addHeader("Authorization", _authorizationHeader);
            getRequest.addHeader("User-Agent","FenchurchRuntime 0.01");
            getRequest.addHeader("Host", "api.twitter.com");
            getRequest.addHeader("Accept", "*/*");
            getRequest.addHeader("Accept-Encoding", "utf-8");

            CloseableHttpResponse resp = httpClient.execute(getRequest);

            System.out.println("GET Response Status:: "
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

            //return response.toString();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }


    }
}
