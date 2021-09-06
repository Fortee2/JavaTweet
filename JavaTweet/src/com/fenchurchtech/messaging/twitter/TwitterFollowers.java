package com.fenchurchtech.messaging.twitter;

import com.fenchurchtech.messaging.twitter.base.TwitterAuthProperties;
import com.fenchurchtech.messaging.twitter.common.Friends.FriendList;
import com.google.gson.Gson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TwitterFollowers  extends TwitterAuthProperties {
    private final String _endpoint  = "https://api.twitter.com/1.1/followers/list.json?skip_status=true";

    private String _authorizationHeader ="";
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public FriendList RequestMyFollowers(){
        FriendList fl = new FriendList();

        try{
            TwitterOAuth auth = getTwitterOAuth();
            _authorizationHeader = auth.GenerateAuthHeader();
            CloseableHttpResponse resp = httpClient.execute(getHttpGet());

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
            httpClient.close();

            System.out.println(response.toString());

            Gson gson = new Gson();
            fl = gson.fromJson(response.toString(), FriendList.class);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return fl;
    }

    private HttpGet getHttpGet() {
        HttpGet getRequest = new HttpGet(_endpoint);
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
