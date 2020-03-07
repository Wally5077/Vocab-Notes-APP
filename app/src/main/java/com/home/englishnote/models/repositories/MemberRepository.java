package com.home.englishnote.models.repositories;

import com.home.englishnote.models.entities.Credentials;
import com.home.englishnote.models.entities.Member;
import com.home.englishnote.models.entities.Token;

public interface MemberRepository {

    Token signUp(Member member, Credentials credentials);

    Token signInToken(Credentials credentials);

    Member getMember(int memberId);

    void updateMemberInfo(String firstName, String lastName, int age);
}
