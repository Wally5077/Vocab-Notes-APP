package com.home.englishnote.models.repositories;


import com.home.englishnote.models.entities.Type;
import com.home.englishnote.models.entities.WordGroup;
import com.home.englishnote.utils.DelayUtil;
import com.home.englishnote.utils.Global;
import com.home.englishnote.utils.RandomVacabGenerator;

import java.util.ArrayList;
import java.util.List;

public class StubWordGroupRepository implements WordGroupRepository {

    private List<WordGroup> wordGroupList;

    public StubWordGroupRepository() {
        wordGroupList = new ArrayList<>(100);
        for (int index = 0; index < 100; index++) {
            wordGroupList.add(RandomVacabGenerator.randomWordGroup(3, 9));
        }
    }

    @Override
    public WordGroup getWordGroup(int wordGroupId) {
        DelayUtil.delayExecuteThread(500);
        return RandomVacabGenerator.randomDictionary(
                Type.PUBLIC, 3, 10,
                5, 20).getWordGroups().get(0);
    }

    @Override
    public List<WordGroup> getWordGroupsFromPublicDictionary(int dictionaryId, int offset, int limit) {
        DelayUtil.delayExecuteThread(500);
        return Global.dictionaryRepository().getDictionary(dictionaryId).getWordGroups();
    }

    @Override
    public List<WordGroup> getWordGroupsFromOwnDictionary(int memberId, int dictionaryId, int offset, int limit) {
        DelayUtil.delayExecuteThread(500);
        return Global.memberRepository().getOwnDictionary(memberId, dictionaryId).getWordGroups();
    }

    @Override
    public List<WordGroup> getWordGroupsFromFavoriteDictionary(int memberId, int offset, int limit) {
        DelayUtil.delayExecuteThread(500);
        return RandomVacabGenerator.randomDictionary(
                Type.PUBLIC, 1, 3,
                5, 20).getWordGroups();
    }
}
