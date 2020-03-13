package com.home.englishnote.models.repositories;

import com.home.englishnote.models.entities.Credentials;
import com.home.englishnote.models.entities.Dictionary;
import com.home.englishnote.models.entities.Member;
import com.home.englishnote.models.entities.Token;

import java.util.List;

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
    public Member getMember(int memberId) {
        return new Member("firstName", "lastName",
                25, "email", "password");
    }

    @Override
    public void updateMemberInfo(String firstName, String lastName, int age) {

    }

    @Override
    public List<Dictionary> getOwnDictionaries(int memberId, int offset, int limit) {
        return null;
    }
}
