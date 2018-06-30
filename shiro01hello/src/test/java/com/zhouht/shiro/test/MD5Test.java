package com.zhouht.shiro.test;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;

/**
 * Created by zhouht
 */
public class MD5Test {

    @Test
    public void testMd5(){


        String source = "123456";

        String salt = "zhouht";

        int hashIterations = 1024;

        SimpleHash simpleHash = new SimpleHash("md5", source, salt, hashIterations);
        String password = simpleHash.toString();
        System.out.println(password);

    }
}
