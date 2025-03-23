package co.za.bsg.business.service.impl;

import co.za.bsg.business.service.AnagramAlgorithm;
import co.za.bsg.persistance.model.Word;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnagramAlgorithmImpl implements AnagramAlgorithm {
    public AnagramAlgorithmImpl() {
    }

    @Override
    public List<Word> getAnagramsForGivenWord(List<Word> activeWords, String givenWord) {
        String givenWordSorted = sortWord(givenWord);
        List<Word> anagrams = new ArrayList<>();
        for (Word aWord : activeWords) {
            String sortedWord = sortWord(aWord.getWordText());
            if (givenWordSorted.equals(sortedWord)) {
                anagrams.add(aWord);
            }
        }
        return anagrams;
    }

    @Override
    public Map<Integer, Integer> countAnagrams(List<Word> words) {
        Map<String, Integer> anagramMap = new HashMap<>();
        Map<Integer, Integer> anagramCounterMap = new HashMap<>();
        this.countAnagramsForEachWordUsingSortedWordAsKey(words, anagramMap);
        this.groupWordOfTheSameLength(anagramMap, anagramCounterMap);
        return anagramCounterMap;
    }

    private void countAnagramsForEachWordUsingSortedWordAsKey(List<Word> words, Map<String, Integer> anagramMap) {
        for (Word word : words) {
            char[] chars = word.getWordText().toLowerCase().toCharArray();
            Arrays.sort(chars);
            String sortedWord = new String(chars);
            Integer anagramCount = anagramMap.get(sortedWord);
            if (anagramCount == null) {
                //Assuming that the word is not an anagram when it is encountered for the first time
                anagramMap.put(sortedWord, 0);
            } else {
                if (anagramCount == 0) {
                    //Because it is assumed above that the word was not an anagram and
                    // that assumption was not correct then we have to do a pre-increment here
                    anagramCount++;
                }
                anagramCount++;
                anagramMap.put(sortedWord, anagramCount);
            }
        }
    }

    private void groupWordOfTheSameLength(Map<String, Integer> anagramMap, Map<Integer, Integer> anagramCounterMap) {
        for (Map.Entry<String, Integer> entry : anagramMap.entrySet()) {
            Integer anagramCounter = entry.getValue();
            if (anagramCounter != 0) {
                int anagramLength = entry.getKey().length();
                Integer counter = anagramCounterMap.get(entry.getKey().length());
                if (counter == null) {
                    anagramCounterMap.put(anagramLength, anagramCounter);
                } else {
                    anagramCounterMap.put(anagramLength, anagramCounter + counter);
                }
            }
        }
    }

    private String sortWord(String aWord) {
        char[] chars = aWord.toLowerCase().toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
}
