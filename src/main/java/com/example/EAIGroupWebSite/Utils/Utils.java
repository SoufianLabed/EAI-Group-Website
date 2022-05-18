package com.example.EAIGroupWebSite.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private static String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static Pattern pattern = Pattern.compile(regexPattern);

    public static Boolean emailCheck(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
