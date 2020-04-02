package com.home.englishnote.utils;


import com.home.englishnote.models.entities.Dictionary;
import com.home.englishnote.models.entities.Member;
import com.home.englishnote.models.entities.Role;
import com.home.englishnote.models.entities.Type;
import com.home.englishnote.models.entities.Word;
import com.home.englishnote.models.entities.WordGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static com.home.englishnote.utils.RandomGenerator.randomHttpUrl;
import static com.home.englishnote.utils.RandomGenerator.randomString;

public class RandomVacabProducer {

    private final static Random RANDOM = new Random();
    private static Set<String> wordGroupTitles = Collections.synchronizedSet(new HashSet<>());

    public static Member randomMember(Role role) {
        Member member = new Member();
        member.setRole(role);
        member.setAge(RANDOM.nextInt(80) + 1);
        member.setEmail(randomString(5, 10) + "@email.com");
        member.setFirstName(randomString(5, 10));
        member.setLastName(randomString(5, 10));
        member.setPassword(randomString(128));
        member.setExp(RANDOM.nextInt(100000));
        return member;
    }

    public static Dictionary randomDictionary(Type type,
                                              int minWordGroupCount, int maxWordGroupCount,
                                              int minWordCount, int maxWordCount) {
        Dictionary dictionary = new Dictionary();
        dictionary.setId(RANDOM.nextInt(10000) + 1);
        dictionary.setTitle(randomString(4, 10, false));
        dictionary.setDescription(randomString(10, 100, true));
        dictionary.setType(type);

        if (minWordGroupCount > 0) {
            int wordGroupCount = RANDOM.nextInt(maxWordGroupCount - minWordGroupCount + 1) + minWordGroupCount;
            Set<WordGroup> wordGroups = new HashSet<>();
            for (int i = 0; i < wordGroupCount; i++) {
                wordGroups.add(randomWordGroup(minWordCount, maxWordCount));
            }
            dictionary.setWordGroups(new ArrayList<>(wordGroups));
        }
        return dictionary;
    }

    public static WordGroup randomWordGroup(int minWordCount, int maxWordCount) {
        WordGroup wordGroup = new WordGroup();

        // make random unique title
        do {
            wordGroup.setTitle(randomString(4, 15, false));
        } while (wordGroupTitles.contains(wordGroup.getTitle()));
        wordGroupTitles.add(wordGroup.getTitle());

        if (minWordCount > 0) {
            int wordCount = RANDOM.nextInt(maxWordCount - minWordCount + 1) + minWordCount;
            Set<Word> words = new HashSet<>();
            for (int i = 0; i < wordCount; i++) {
                words.add(randomWord());
            }
            wordGroup.setWords(new ArrayList<>(words));
        }
        return wordGroup;
    }

    public static Word randomWord() {
        Word word = new Word();
        word.setName(randomString(1, 20, false));

        int synonymCount = RANDOM.nextInt(6) + 1;
        List<String> synonyms = new ArrayList<>(synonymCount);
        for (int i = 0; i < synonymCount; i++) {
            synonyms.add(randomString(3, 5, false));
        }
        word.setDescription(randomString(20, 40, false));
        word.setSynonyms(synonyms);
        word.setImageUrl(randomHttpUrl(false));
        return word;
    }

    public static void main(String[] args) {
        Dictionary dictionary = randomDictionary(Type.PUBLIC,
                1, 15,
                2, 10);
        Member member = randomMember(Role.ADMIN);
        dictionary.setOwner(member);
        System.out.println(dictionary);
    }
}
