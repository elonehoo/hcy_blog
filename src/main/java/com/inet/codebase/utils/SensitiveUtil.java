package com.inet.codebase.utils;

import com.github.houbb.sensitive.word.bs.SensitiveWordBs;

public class SensitiveUtil {

    public static Boolean sensitives(String text){
        boolean contains = SensitiveWordBs.newInstance().contains(text);
        return contains;
    }
}
