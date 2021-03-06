package com.fenchurchtech.twitterconsole;

import com.fenchurchtech.messaging.twitter.TwitterFriends;
import com.fenchurchtech.messaging.twitter.TwitterSearch;
import com.fenchurchtech.messaging.twitter.TwitterStatus;
import com.fenchurchtech.messaging.twitter.TwitterMessage;

import java.io.FileInputStream;
import java.util.Properties;

public class ExecuteTweet {

    public static void main(String[] args) throws Exception {
        Properties prop = loadProperties();

        twitterFriendsList(prop);
    }

    private static void twitterSearch(Properties prop) throws Exception {
        TwitterSearch search = new TwitterSearch();
        search.setConsumerKey(prop.getProperty("twitter.consumerKey"));
        search.setConsumerSecret(prop.getProperty("twitter.consumerSecret"));
        search.setToken(prop.getProperty("twitter.token"));
        search.setSecret(prop.getProperty("twitter.secret"));
        search.setEndpoint("https://api.twitter.com/1.1/search/tweets.json?q=");

        search.SendSearch("Jeep");
    }

    public static Properties loadProperties(){
        try {
            String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            String appConfigPath = rootPath + "config.properties";

            Properties appProps = new Properties();
            appProps.load(new FileInputStream(appConfigPath));

            return appProps;
        }
        catch (Exception e ){
            System.out.println("Could not load application properties.");
        }

        return null;
    }

    public static void sendStatusUpdate(String statusMsg, Properties prop){
        TwitterStatus status = new TwitterStatus();

        try {
            assert prop != null;
            status.setConsumerKey(prop.getProperty("twitter.consumerKey"));
            status.setConsumerSecret(prop.getProperty("twitter.consumerSecret"));
            status.setToken(prop.getProperty("twitter.token"));
            status.setSecret(prop.getProperty("twitter.secret"));

            status.setEndpoint(  "https://api.twitter.com/1.1/statuses/update.json?status=" );
            TwitterMessage tm = new TwitterMessage();
            tm.setMessage(statusMsg);
            status.SendNotification(tm);

            System.out.println(tm.getStatus());
        }catch  (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void twitterFriendsList(Properties prop) throws Exception {
        TwitterFriends friends = new TwitterFriends();
        friends.setConsumerKey(prop.getProperty("twitter.consumerKey"));
        friends.setConsumerSecret(prop.getProperty("twitter.consumerSecret"));
        friends.setToken(prop.getProperty("twitter.token"));
        friends.setSecret(prop.getProperty("twitter.secret"));

        friends.RequestFriends();
    }
}
