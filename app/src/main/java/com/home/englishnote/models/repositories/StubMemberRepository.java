package com.home.englishnote.models.repositories;

import com.home.englishnote.models.entities.Credentials;
import com.home.englishnote.models.entities.Member;
import com.home.englishnote.models.entities.Token;

public class StubMemberRepository implements MemberRepository {
    @Override
    public Token signUp(Member member, Credentials credentials) {
        return new Token("token", 0, 365);
    }

    @Override
    public Token signInToken(Credentials credentials) {
        return new Token("token", 0, 365);
    }

    @Override
    public Member signInMember(int memberId) {
        return new Member("firstName", "lastName",
                25, "email", "password");
    }

    @Override
    public void updateMemberInfo(String firstName, String lastName, int age) {

    }
}
