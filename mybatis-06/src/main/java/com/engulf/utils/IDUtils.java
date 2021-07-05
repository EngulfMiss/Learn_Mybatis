package com.engulf.utils;

import org.junit.Test;

import java.util.Date;
import java.util.UUID;

public class IDUtils {
    public static String getUUID(){
        return UUID.randomUUID().toString();
    }

    @Test
    public void test(){
        System.out.println(IDUtils.getUUID());
    }
}
