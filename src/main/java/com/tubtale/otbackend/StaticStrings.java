package com.tubtale.otbackend;

public class StaticStrings {

    public static String KEYWORD_KEY = "*";
    public static String TWITTER_USER_KEY = "@";
    public static String BURY_KEYWORD = KEYWORD_KEY + "bury";
    public static String LOCK_KEYWORD = KEYWORD_KEY + "lock";
    public static String BURY_KEYWORD_FOR_REGEXP =  "\\" + BURY_KEYWORD;
    public static String LOCK_KEYWORD_FOR_REGEXP =  "\\" + LOCK_KEYWORD;
    public static Integer TWEET_LENGTH_WITH_LINK = 117;
}
