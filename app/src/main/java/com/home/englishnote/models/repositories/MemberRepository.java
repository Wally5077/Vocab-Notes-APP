package com.home.englishnote.models.repositories;

import com.home.englishnote.models.entities.Credentials;
import com.home.englishnote.models.entities.Dictionary;
import com.home.englishnote.models.entities.Member;
import com.home.englishnote.models.entities.Token;

import java.util.List;

public interface MemberRepository {

    Token signUp(Member member, Credentials credentials);

    Token signInToken(Credentials credentials);

    Member getMember(int memberId);

    void updateMemberInfo(String firstName, String lastName, int age);

    void createOwnDictionary(Token token, int memberId, String title, String description);

    List<Dictionary> getOwnDictionaries(int memberId, int offset, int limit);
}
