package co.za.bsg.business.service;

import co.za.bsg.persistance.model.Word;

import java.util.List;
import java.util.Map;

public interface AnagramAlgorithm {
    List<Word> getAnagramsForGivenWord(final List<Word> activeWords, final String givenWord);

    Map<Integer, Integer> countAnagrams(final List<Word> words);
}
