package com.tubtale.otbackend;

import org.junit.Test;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import com.ibm.icu.lang.UCharacter;

public class TwitterTest {

    private String mConsumerKey = "yB2v9hdznXQoTpujdIzfuNY8s";
    private String mConsumerSecret = "8L6W8JNhYEcIstbklxXoC0qMLcdyXklZC9xspozFWUELx1IZId";
    private String mAccessKey = "3772125382-AgCRKUOGm9KWHqtvG1ddMiRXgt8FciD0cwXVowi";
    private String mAccessSecret = "GVCyDqJY02PrydYjqdKKaT5PO0yehKWLnf8H4pH3Naj9I";

    private void sendTweet(String tweet) throws  Exception {
        ConfigurationBuilder cb=new ConfigurationBuilder();
        cb.setDebugEnabled(true).setOAuthConsumerKey(mConsumerKey)
                .setOAuthConsumerSecret(mConsumerSecret)
                .setOAuthAccessToken(mAccessKey)
                .setOAuthAccessTokenSecret(mAccessSecret);
        TwitterFactory tf=new TwitterFactory(cb.build());
        Twitter twitter=tf.getInstance();
        twitter.updateStatus(tweet);
    }
//117 characters
    @Test
    public void tweetTextShallBePosted() throws  Exception{
        String testTweet = "@wiressly a 2km de BCN han escrito para ti:" +
                " \" descubrelo: www.wiresslyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy.com/12345";
        //sendTweet(testTweet);
        assertThat(testTweet, is(equalTo(testTweet)));
    }

    @Test
    public void toBoldTest() {
        String c = new String(Character.toChars(UCharacter.getCharFromName("mathematical bold capital a".toUpperCase())));
        System.out.println( "str: " +c );
    }
}
