package com.home.englishnote.models.repositories;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.home.englishnote.models.entities.Credentials;
import com.home.englishnote.models.entities.Dictionary;
import com.home.englishnote.models.entities.Member;
import com.home.englishnote.models.entities.Role;
import com.home.englishnote.models.entities.Token;
import com.home.englishnote.models.entities.Type;
import com.home.englishnote.utils.RandomVacabProducer;

import java.util.ArrayList;
import java.util.LinkedList;
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
        return RandomVacabProducer.randomMember(Role.MEMBER);
    }

    @Override
    public void updateMemberInfo(String firstName, String lastName, int age) {

    }

    private LinkedList<Dictionary> dictionaryList = new LinkedList<>();

    @Override
    public void createOwnDictionary(Token token, int memberId, String title, String description) {
        Dictionary dictionary = RandomVacabProducer.randomDictionary(
                Type.PUBLIC, 3, 10,
                5, 20);
        dictionary.setTitle(title);
        dictionary.setDescription(description);
        dictionaryList.addFirst(dictionary);
    }

    @Override
    public List<Dictionary> getOwnDictionaries(int memberId, int offset, int limit) {
        return !dictionaryList.isEmpty() ? dictionaryList :
                getFavoriteDictionaries(memberId, offset, limit);
    }

    @Override
    public List<Dictionary> getFavoriteDictionaries(int memberId, int offset, int limit) {
        List<Dictionary> dictionaryList = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            dictionaryList.add(RandomVacabProducer.randomDictionary(
                    Type.PUBLIC, 3, 10,
                    5, 20));
        }
        return dictionaryList;
    }
}
