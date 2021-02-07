package com.fenchurchtech.messaging.twitter;

import com.fenchurchtech.messaging.twitter.base.TwitterAuthProperties;
import com.fenchurchtech.messaging.twitter.common.Encoder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class TwitterFriends extends TwitterAuthProperties {
    private final String _endpoint = "https://api.twitter.com/1.1/friends/list.json";
    private String _authorizationHeader ="";
    private String _encodedTerm = "";
    private CloseableHttpClient httpClient = HttpClients.createDefault();


    public void RequestFriends(String searchTerm){
        _encodedTerm = Encoder.UrlEncode(searchTerm);   //URLEncoder.encode(message.getMessage(), "UTF-8");

        try{
            _authorizationHeader = getTwitterOAuth().GenerateAuthHeader();
            CloseableHttpResponse resp = httpClient.execute(getHttpGet());

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

    private HttpGet getHttpGet() {
        HttpGet getRequest = new HttpGet(_endpoint);

        System.out.println(_endpoint);

        getRequest.addHeader("Authorization", _authorizationHeader);
        getRequest.addHeader("User-Agent","FenchurchRuntime 0.01");
        getRequest.addHeader("Host", "api.twitter.com");
        getRequest.addHeader("Accept", "*/*");
        getRequest.addHeader("Accept-Encoding", "utf-8");
        return getRequest;
    }

    private TwitterOAuth getTwitterOAuth() throws Exception {
        TwitterOAuth auth = new TwitterOAuth();
        auth.setConsumerKey(_consumerKey);
        auth.setConsumerSecret(_consumerSecret);
        auth.setRequestMethod("GET");
        auth.setSignatureMethod("HMAC-SHA1");
        auth.setRequestUrl(_endpoint );
        auth.setToken(_token);
        auth.setSecret(_secret);
        return auth;
    }
}
