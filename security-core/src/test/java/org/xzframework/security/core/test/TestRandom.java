package org.xzframework.security.core.test;

import org.apache.commons.lang3.RandomStringUtils;

public class TestRandom {
    public static void main(String[] args) {
        System.out.println(RandomStringUtils.secure().nextNumeric(100));
    }
}
