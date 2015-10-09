package com.tubtale.otbackend.twitterpublisher;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by quest on 03/10/15.
 */
public class TwitterStringPublisher {

    private String mConsumerKey = "yB2v9hdznXQoTpujdIzfuNY8s";
    private String mConsumerSecret = "8L6W8JNhYEcIstbklxXoC0qMLcdyXklZC9xspozFWUELx1IZId";
    private String mAccessKey = "3772125382-AgCRKUOGm9KWHqtvG1ddMiRXgt8FciD0cwXVowi";
    private String mAccessSecret = "GVCyDqJY02PrydYjqdKKaT5PO0yehKWLnf8H4pH3Naj9I";

    public void publishTweet(String tweet) throws  Exception {
        ConfigurationBuilder cb=new ConfigurationBuilder();
        cb.setDebugEnabled(true).setOAuthConsumerKey(mConsumerKey)
                .setOAuthConsumerSecret(mConsumerSecret)
                .setOAuthAccessToken(mAccessKey)
                .setOAuthAccessTokenSecret(mAccessSecret);
        TwitterFactory tf=new TwitterFactory(cb.build());
        Twitter twitter=tf.getInstance();
        twitter.updateStatus(tweet);
    }
}
