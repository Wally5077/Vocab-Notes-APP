package com.home.englishnote.models.repositories;

import com.home.englishnote.models.entities.Credentials;
import com.home.englishnote.models.entities.Dictionary;
import com.home.englishnote.models.entities.Member;
import com.home.englishnote.models.entities.Role;
import com.home.englishnote.models.entities.Token;
import com.home.englishnote.models.entities.Type;
import com.home.englishnote.utils.DelayUtil;
import com.home.englishnote.utils.RandomVacabProducer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class StubMemberRepository implements MemberRepository {

    @Override
    public Token signUp(Member member, Credentials credentials) {
        DelayUtil.delayExecuteThread(300);
        return new Token("token", 0, 365);
    }

    @Override
    public Token signInToken(Credentials credentials) {
        DelayUtil.delayExecuteThread(300);
        return new Token("token", 0, 365);
    }

    @Override
    public Member getMember(int memberId) {
        DelayUtil.delayExecuteThread(300);
        return RandomVacabProducer.randomMember(Role.MEMBER);
    }

    @Override
    public void updateMemberInfo(String firstName, String lastName, int age) {
        DelayUtil.delayExecuteThread(300);
    }

    private LinkedList<Dictionary> dictionaryList = new LinkedList<>();

    @Override
    public void createOwnDictionary(Token token, int memberId, String title, String description) {
        DelayUtil.delayExecuteThread(300);
        Dictionary dictionary = RandomVacabProducer.randomDictionary(
                Type.PUBLIC, 3, 10,
                5, 20);
        dictionary.setTitle(title);
        dictionary.setDescription(description);
        dictionaryList.addFirst(dictionary);
    }

    Set<Integer> dictionaryIdSet = new HashSet<>();

    @Override
    public void addFavoriteDictionary(int dictionaryId, int memberId) {
        DelayUtil.delayExecuteThread(300);
        if (dictionaryIdSet.contains(dictionaryId)) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Dictionary> getOwnDictionaries(int memberId, int offset, int limit) {
        DelayUtil.delayExecuteThread(300);
        return !dictionaryList.isEmpty() ? dictionaryList :
                getFavoriteDictionaries(memberId, offset, limit);
    }

    @Override
    public List<Dictionary> getFavoriteDictionaries(int memberId, int offset, int limit) {
        DelayUtil.delayExecuteThread(300);
        List<Dictionary> dictionaryList = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            dictionaryList.add(RandomVacabProducer.randomDictionary(
                    Type.PUBLIC, 3, 10,
                    5, 20));
        }
        return dictionaryList;
    }
}
