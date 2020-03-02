package com.home.englishnote.presenters;

import android.util.Patterns;

import com.home.englishnote.exceptions.EmailFormatInvalidException;
import com.home.englishnote.exceptions.PasswordFormatInvalidException;
import com.home.englishnote.exceptions.UserInputEmptyException;
import com.home.englishnote.utils.HashUtil;
import com.home.englishnote.utils.VocabularyNoteKeyword;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class VerifyFormatPresenter {

    private final String PASSWORD_PATTERN = "[a-zA-Z0-9]{1,}";
    private final String INPUT_EMPTY_PATTERN = "[ \\t\\r\\n\\v\\f]{1,}";

    protected void verifyUserInfoEmpty(String... keywords) {
        for (String keyword : keywords) {
            if (keyword.isEmpty()
                    || Pattern.compile(INPUT_EMPTY_PATTERN).matcher(keyword).matches()
                    || VocabularyNoteKeyword.DEFAULT_SPINNER_WORD.equals(keyword)) {
                throw new UserInputEmptyException();
            }
        }
    }

    protected void verifyEmailFormat(String email) {
        Matcher matcher = Patterns.EMAIL_ADDRESS.matcher(email);
        if (!matcher.matches()) {
            throw new EmailFormatInvalidException();
        }
    }

    protected void verifyPasswordFormat(String password) {
        Matcher matcher = Pattern.compile(PASSWORD_PATTERN).matcher(password);
        if (!matcher.matches()) {
            throw new PasswordFormatInvalidException();
        }
    }

    protected String hashPassword(String password) {
        return HashUtil.hashEncode(password);
    }
}
