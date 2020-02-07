import com.fenchurchtech.messaging.twitter.TwitterStatus;
import com.fenchurchtech.messaging.twitter.TwitterMessage;

import java.io.FileInputStream;
import java.util.Properties;

public class ExecuteTweet {

    public static void main(String[] args) {
        Properties prop = loadProperties();

        TwitterStatus status = new TwitterStatus();

        try {
            assert prop != null;
            status.setConsumerKey(prop.getProperty("twitter.consumerKey"));
            status.setConsumerSecret(prop.getProperty("twitter.consumerSecret"));
            status.setToken(prop.getProperty("twitter.token"));
            status.setSecret(prop.getProperty("twitter.secret"));

            status.setEndpoint(  "https://api.twitter.com/1.1/statuses/update.json?status=" );
            TwitterMessage tm = new TwitterMessage();
            tm.setMessage("hello api test");
            status.SendNotification(tm);

            System.out.println(tm.getStatus());
        }catch  (Exception e){
            System.out.println(e.getMessage());
        }
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

}
