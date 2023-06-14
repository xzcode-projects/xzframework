package org.xzframework.security.verification.support;

import org.xzframework.security.verification.VerificationCode;
import org.xzframework.security.verification.VerificationCodeGenerator;

import java.util.Random;
import java.util.UUID;

public class SimpleVerificationCodeGenerator implements VerificationCodeGenerator {

    private final Random random = new Random();
    private char[] characters = new char[]{'a', 'b', 'c', 'c', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm',
            'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9'};

    public SimpleVerificationCodeGenerator() {

    }

    public SimpleVerificationCodeGenerator(String characters) {
        this.characters = characters.toCharArray();
    }

    @Override
    public VerificationCode generate(int length) {
        String id = UUID.randomUUID().toString();
        int count = characters.length;
        char[] result = new char[length];
        for (int i = 0; i < length; i++) {
            result[i] = characters[random.nextInt(count)];
        }
        return new VerificationCode(id, new String(result));
    }

}

