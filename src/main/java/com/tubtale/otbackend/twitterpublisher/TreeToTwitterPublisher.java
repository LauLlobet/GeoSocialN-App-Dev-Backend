package com.tubtale.otbackend.twitterpublisher;

import java.util.ArrayList;

/**
 * Created by quest on 08/10/15.
 */
public class TreeToTwitterPublisher {
    TwitterStringPublisher twitterStringPublisher;
    TwitterMessageComposer twitterMessageComposer;

    public TreeToTwitterPublisher(){
        twitterStringPublisher = new TwitterStringPublisher();
    }

    public void publishTreeInTwitterUsersMentioned(String text, float lon, float lat, int treeId){
        twitterMessageComposer = new TwitterMessageComposer(text,lon,lat,treeId);
        try{
            ArrayList<String> tweets = twitterMessageComposer.getTweets();
            for( String s: tweets){
                try{
                    twitterStringPublisher.publishTweet(s);
                }catch (Exception e){
                    System.out.println("[OTLOG] impossible to tweet tree:"+treeId);
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            System.out.println("[OTLOG] impossible to tweet whole tree:"+treeId);
            e.printStackTrace();
            return;
        }


    }
}
