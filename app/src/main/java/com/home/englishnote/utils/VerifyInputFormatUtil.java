package com.home.englishnote.utils;

import android.util.Patterns;

import com.home.englishnote.exceptions.EmailFormatInvalidException;
import com.home.englishnote.exceptions.PasswordFormatInvalidException;
import com.home.englishnote.exceptions.UserInputEmptyException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class VerifyInputFormatUtil {

    private static final String EMAIL_ADDRESS = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+";
    private static final String PASSWORD_PATTERN = "[a-zA-Z0-9]{1,}";
    private static final String INPUT_EMPTY_PATTERN = "[ \\t\\r\\n\\v\\f]{1,}";

    public static void verifyInputEmpty(String... keywords) {
        for (String keyword : keywords) {
            if (keyword.isEmpty()
                    || Pattern.compile(INPUT_EMPTY_PATTERN).matcher(keyword).matches()
                    || VocabularyNoteKeyword.DEFAULT_SPINNER_WORD.equals(keyword)) {
                throw new UserInputEmptyException();
            }
        }
    }

    public static void verifyEmailFormat(String email) {
        Matcher matcher = Pattern.compile(EMAIL_ADDRESS).matcher(email);
        if (!matcher.matches()) {
            throw new EmailFormatInvalidException();
        }
    }

    public static void verifyPasswordFormat(String password) {
        Matcher matcher = Pattern.compile(PASSWORD_PATTERN).matcher(password);
        if (!matcher.matches()) {
            throw new PasswordFormatInvalidException();
        }
    }

}
