package com.fenchurchtech.test;

import com.fenchurchtech.messaging.common.Encoder;
import org.junit.Assert;
import org.junit.Test;

public class encoderTest {

    @Test
    public void testEncoder(){
        String s = Encoder.UrlEncode("?CodeValue=");
        Assert.assertTrue(s.equals("%3FCodeValue%3D"));
    }
}
