package com.zbank.card.util;

import java.util.Random;

public class CardNumberGenerator {

    private static final Random RANDOM =
            new Random();

    public static String generate() {

        StringBuilder builder =
                new StringBuilder("4556");

        while(builder.length() < 16) {

            builder.append(
                    RANDOM.nextInt(10));
        }

        return builder.toString();
    }
}