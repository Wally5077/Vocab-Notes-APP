package com.home.englishnote.models.repositories;

import com.home.englishnote.exceptions.DictionaryNotFoundException;
import com.home.englishnote.models.entities.Credentials;
import com.home.englishnote.models.entities.Dictionary;
import com.home.englishnote.models.entities.Member;
import com.home.englishnote.models.entities.Role;
import com.home.englishnote.models.entities.Token;
import com.home.englishnote.models.entities.Type;
import com.home.englishnote.utils.DelayUtil;
import com.home.englishnote.utils.RandomVacabProducer;

import java.util.LinkedList;
import java.util.List;

public class StubMemberRepository implements MemberRepository {

    public StubMemberRepository() {
        dictionaryList = new LinkedList<>();
        for (int addTime = 0; addTime < 20; addTime++) {
            dictionaryList.add(RandomVacabProducer.randomDictionary(
                    Type.PUBLIC, 4, 6,
                    3, 9));
        }
    }

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

    private LinkedList<Dictionary> dictionaryList;

    @Override
    public void createOwnDictionary(Token token, int memberId, String title, String description) {
        DelayUtil.delayExecuteThread(300);
        Dictionary dictionary = RandomVacabProducer.randomDictionary(
                Type.PUBLIC, 5, 10,
                5, 20);
        dictionary.setTitle(title);
        dictionary.setDescription(description);
        dictionaryList.addFirst(dictionary);
    }

    @Override
    public void addFavoriteDictionary(int dictionaryId, int memberId) {
        DelayUtil.delayExecuteThread(300);
    }

    @Override
    public List<Dictionary> getOwnDictionaries(int memberId, int offset, int limit) {
        DelayUtil.delayExecuteThread(500);
        if (offset < dictionaryList.size()) {
            if (offset > -1) {
                return dictionaryList.subList(
                        Math.max(0, offset), Math.min(offset + limit, dictionaryList.size()));
            }
        }
        return dictionaryList;
    }

    @Override
    public Dictionary getOwnDictionary(int memberId, int dictionaryId) {
        return dictionaryList.stream()
                .filter(dictionary -> dictionary.getId() == dictionaryId)
                .findFirst()
                .orElseThrow(DictionaryNotFoundException::new);
    }

    @Override
    public List<Dictionary> getFavoriteDictionaries(int memberId, int offset, int limit) {
        DelayUtil.delayExecuteThread(500);
        if (offset < dictionaryList.size()) {
            if (offset > -1) {
                return dictionaryList.subList(
                        Math.max(0, offset), Math.min(offset + limit, dictionaryList.size()));
            }
        }
        return dictionaryList;
    }
}
