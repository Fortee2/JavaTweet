package com.fenchurchtech.test;

import org.junit.Assert;
import org.junit.Test;
import com.fenchurchtech.messaging.twitter.TwitterOAuth;

public class authentication {

    private TwitterOAuth oAuth;

    public authentication(){
        try {
            oAuth = new TwitterOAuth();

            oAuth.setConsumerKey("consumerkey");
            oAuth.setConsumerSecret("consumerSecret");
            oAuth.setRequestMethod("POST");
            oAuth.setRequestUrl("http://update.test.com/test?action=action");
            oAuth.setSecret("secret");
            oAuth.setToken("token");
            oAuth.setSignatureMethod("HMAC-1");
        }
        catch (Exception e){

        }
    }


    @Test
    public void createdAuthHeader(){
        try {
            String s = oAuth.GenerateAuthHeader();
            Assert.assertTrue(!s.isBlank());
        }catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void validateAuthHeader(){
        try {
            String s = oAuth.GenerateAuthHeader();
            Assert.assertTrue(s.contains("OAuth oauth_consumer_key=\"consumerkey\",oauth_token=\"token\",oauth_signature_method=\"HMAC-1\""));
        }catch (Exception e){
            Assert.fail();
        }
    }
}
