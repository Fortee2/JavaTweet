package com.fenchurchtech.messaging.twitter;

import com.fenchurchtech.messaging.twitter.base.TwitterAuthProperties;
import com.fenchurchtech.messaging.twitter.common.Encoder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class TwitterSearch extends TwitterAuthProperties {
    private String _endpoint = "";
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public String getEndPoint() {
        return _endpoint;
    }


    public void setEndpoint(String endpoint) {
        //TODO: Add some validation

        _endpoint = endpoint;
    }

    public void SendSearch(String searchTerm){
        String _encodedTerm = Encoder.UrlEncode(searchTerm);   //URLEncoder.encode(message.getMessage(), "UTF-8");

        try{
            TwitterOAuth auth = new TwitterOAuth();
            auth.setConsumerKey(_consumerKey);
            auth.setConsumerSecret(_consumerSecret);
            auth.setRequestMethod("GET");
            auth.setSignatureMethod("HMAC-SHA1");
            auth.setRequestUrl(_endpoint + searchTerm);
            auth.setToken(_token);
            auth.setSecret(_secret);

            String _authorizationHeader = auth.GenerateAuthHeader();

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
            StringBuilder response = new StringBuilder();

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
