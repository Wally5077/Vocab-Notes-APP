package com.home.englishnote.utils;

import android.os.Build;

import java.util.Random;
import java.util.function.Supplier;

public class RandomGenerator {
    private final static Random RANDOM = new Random();

    public static String randomHttpUrl(boolean requiredHttps) {
        StringBuilder stringBuilder = new StringBuilder(requiredHttps ? "https" : "http");
        stringBuilder.append("://");

        // random domain name
        stringBuilder.append("www.");
        stringBuilder.append(randomString(3, 5)).append(".com");


        // random segments
        int segmentCount = RANDOM.nextInt(6);
        if (segmentCount > 0) {
            stringBuilder.append("/");
        }
        stringBuilder.append(randomSeparatedString(1, 8, segmentCount, "/"));

        int queryParamCount = RANDOM.nextInt(5);
        if (queryParamCount > 0) {
            stringBuilder.append("?");
        }
        stringBuilder.append(randomSeparatedString(queryParamCount, "&&",
                ()-> String.format("%s=%s",
                        randomString(2, 6),
                        // random decide the val of the query param (Integer, or String)
                        RANDOM.nextBoolean() ? RANDOM.nextInt(100) :randomString(2, 6))));

        return stringBuilder.toString();
    }

    public static String randomString(int fixedLength) {
        return randomString(fixedLength, fixedLength, false);
    }

    public static String randomString(int minLen, int maxLen) {
        return randomString(minLen, maxLen, false);
    }

    public static String randomString(int minLen, int maxLen, boolean putSpace) {
        if (minLen == 0 && maxLen <= 0) {
            return "";
        }

        int len = RANDOM.nextInt(maxLen - minLen + 1) + minLen;
        StringBuilder stringBuilder = new StringBuilder();
        boolean hasUsedLetter = false;
        for (int i = 0; i < len; i++) {
            if (putSpace && hasUsedLetter && RANDOM.nextInt(100) > 90) {
                stringBuilder.append(' ');
            } else {
                hasUsedLetter = true;
                stringBuilder.append(randomLetter());
            }
        }
        return stringBuilder.toString();
    }

    /**
     * For example given minLenEach = 1, maxLenEach = 3, count = 5, delimiter = '/'
     * The result might be 'agB/s/gD/ddA/P',
     * where each separated segment's length is within [1, 3],
     * as well as there are 5 segments separated by the delimiter '/'.
     */
    public static String randomSeparatedString(int minLenEach, int maxLenEach,
                                               int count, String delimiter) {
        return randomSeparatedString(count, delimiter,
                ()-> randomString(minLenEach, maxLenEach, false));
    }

    public static String randomSeparatedString(int count, String delimiter,
                                               Supplier<String> stringGenerator) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                stringBuilder.append(stringGenerator.get());
            }
            if (i != count-1) {
                stringBuilder.append(delimiter);
            }
        }
        return stringBuilder.toString();
    }

    public static char randomLetter() {
        return (char) (RANDOM.nextInt(26)+'a');
    }

}
