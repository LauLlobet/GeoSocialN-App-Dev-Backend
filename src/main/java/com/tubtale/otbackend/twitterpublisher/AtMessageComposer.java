package com.tubtale.otbackend.twitterpublisher;

import com.tubtale.otbackend.StaticStrings;

import java.util.ArrayList;

/**
 * Created by quest on 03/10/15.
 */
public class AtMessageComposer {
    ArrayList<String> usersToNotify;
    String textBeforeLockOrBury ="";
    public AtMessageComposer(String text){
        usersToNotify = this.findUsersInText(text);
        textBeforeLockOrBury = this.findTextbeforeLockOrBury(text);
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
        try {
            String[] splittedText = text.split(StaticStrings.BURY_KEYWORD_FOR_REGEXP);
            String[] ans = splittedText[0].split(StaticStrings.LOCK_KEYWORD_FOR_REGEXP);
            return ans[0].trim();
        }catch (Exception e){
            return text.trim();
        }
    }

    public String getTextbeforeLockOrBury() {
        return textBeforeLockOrBury;
    }

}
