package com.zb.doamin.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Aes256UtilTest {


    @Test
    void encrypt() {
        String ex = Aes256Util.encrypt("Hello world");
        assertEquals(Aes256Util.decrypt(ex), "Hello world");
    }


}