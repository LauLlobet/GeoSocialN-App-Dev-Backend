package com.tubtale.otbackend.twitterpublisher;

import com.tubtale.otbackend.StaticStrings;
import com.tubtale.otbackend.utils.StringToUnicodeBoldConverter;

import javax.naming.directory.Attributes;
import java.util.ArrayList;

/**
 * Created by quest on 03/10/15.
 */
public class TwitterMessageComposer {

    private String distanceString;
    private ArrayList<String> usersToNotify;
    private String textBeforeLockOrBury ="";
    private int maxLengthTweet = 117;
    private int treeId;

    public TwitterMessageComposer(String text, float longitude, float latitude, int treeId){
        usersToNotify = this.findUsersInText(text);
        textBeforeLockOrBury = StringToUnicodeBoldConverter.convertString("\""+ this.findTextbeforeLockOrBury(text)+ "\"");
        DistanceStringComposer distanceStringComposer = new DistanceStringComposer(longitude,latitude);
        distanceString = distanceStringComposer.toString();
        this.treeId = treeId;
    }

    private ArrayList<String> findUsersInText(String text) {
        ArrayList<String> users = new ArrayList<String>();
        String[] words = text.split(" ");
        for(String word : words){
            if(word.startsWith(StaticStrings.TWITTER_USER_KEY)){
                users.add(word.substring(1,word.length()));
            }
        }
        return users;
    }

    public ArrayList<String> getUsersToNotify() {
        return usersToNotify;
    }

    public String findTextbeforeLockOrBury(String text) {
        text = text.trim();
        if(text.startsWith(StaticStrings.KEYWORD_KEY))
            return "";
        try {
            String[] splittedText = text.split(StaticStrings.BURY_KEYWORD_FOR_REGEXP);
            String[] ans = splittedText[0].split(StaticStrings.LOCK_KEYWORD_FOR_REGEXP);
            return ans[0].trim().replace("@","");
        }catch (Exception e){
            return text.trim();
        }
    }

    public String getTextbeforeLockOrBury() {
        return textBeforeLockOrBury;
    }

    public ArrayList<String> getTweets() throws Exception {
        ArrayList<String > tweets = new ArrayList<String>();
        ArrayList<String> users = this.getUsersToNotify();
        for(String user : users){
            tweets.add(composeTweetForUser(user));
        }
        return tweets;
    }

    private String composeTweetForUser(String user) throws Exception {
        String pt0 = "@"+user;
        String pt1 = " a ";
        String pt2 = distanceString;
        String pt3 = " te han escrito";
        String pt4 = ":";
        String pt6 = " ";
        String pt7 = "descubrelo:";
        String pt8ItsLengthnotCounts = "www.wiressly.com/"+treeId;

        int forcedCharNumbers = pt0.length() +
                pt1.length() +
                pt2.length() +
                pt3.length() +
                pt4.length() +
                pt6.length() +
                pt7.length();
        int restOfCharactersAvaliable = maxLengthTweet - forcedCharNumbers;
        if(!( restOfCharactersAvaliable <= 10 || textBeforeLockOrBury.length() == 0)) {
            String trimmedTextBeforeLockOrBury = textBeforeLockOrBury.substring(0,restOfCharactersAvaliable);
            return pt0 + pt1 + pt2 + pt3 + pt4 + trimmedTextBeforeLockOrBury + pt6 + pt7 + pt8ItsLengthnotCounts;
        }
        String firstAlternativeMessage = pt0 + pt1 + pt2 + pt3 + " un mensaje secreto, " + pt7 + pt8ItsLengthnotCounts;
        if( textBeforeLockOrBury.length() == 0 && firstAlternativeMessage.length() < maxLengthTweet) {
            return firstAlternativeMessage;
        }
        String secondAlternativeMessage = pt0+" "+pt8ItsLengthnotCounts;
        if(! (secondAlternativeMessage.length() > maxLengthTweet )) {
            return secondAlternativeMessage;
        }
        throw new Exception("username is as long as a tweet");
    }
}
