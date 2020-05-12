package com.home.englishnote;


import com.home.englishnote.exceptions.EmailFormatInvalidException;
import com.home.englishnote.exceptions.PasswordFormatInvalidException;
import com.home.englishnote.models.entities.Guest;
import com.home.englishnote.models.entities.User;
import com.home.englishnote.utils.VerifyInputFormatUtil;

public class Test {

    @org.junit.Test
    public void create() throws EmailFormatInvalidException, PasswordFormatInvalidException {
        User user = new Guest();
        VerifyInputFormatUtil.verifyEmailFormat(user.getEmail());
        VerifyInputFormatUtil.verifyPasswordFormat(user.getPassword());
    }

}
