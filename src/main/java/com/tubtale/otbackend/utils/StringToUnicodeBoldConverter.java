package com.tubtale.otbackend.utils;
import com.ibm.icu.lang.UCharacter;

public class StringToUnicodeBoldConverter {
    private static String requestUnicodeName = "MATHEMATICAL BOLD ";
    private static String lowerCase = "SMALL";
    private static String upperCase = "CAPITAL";

    public static String convertString(String request) {
        String ans = "";
        char[] requestArray = request.toCharArray();
        for (char ch: requestArray) {
            ans += convertASingleCharOrLeaveItAsIsIfUnknown(ch);
        }
        return ans;
    }

    private static String convertASingleCharOrLeaveItAsIsIfUnknown (char c) {
        try {
            return convertASingleChar(c);
        } catch (Exception e){
            return "" + c;
        }
    }

    public static String convertASingleChar(char c) {
        String request, resultString;
        char[] result;
        request = getUnicodeRequest(c);
        result = Character.toChars(UCharacter.getCharFromName(request));
        resultString = new String(result);
        return resultString;
    }

    private static String getUnicodeRequest(char c) {
        String character;
        String request;
        character = "" + c;
        request = requestUnicodeName + getCaseUnicodeRequestStringCode(c) + " " + character;
        return request;
    }

    private static String getCaseUnicodeRequestStringCode(char c) {
        if (Character.isLowerCase(c)) {
            return lowerCase;
        } else {
            return upperCase;
        }
    }


}
