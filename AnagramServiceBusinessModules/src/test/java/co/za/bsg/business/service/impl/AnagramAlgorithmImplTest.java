package co.za.bsg.business.service.impl;

import co.za.bsg.business.service.AnagramAlgorithm;
import co.za.bsg.persistance.model.Word;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AnagramAlgorithmImplTest {
    private final AnagramAlgorithm anagramAlgorithm = new AnagramAlgorithmImpl();

    @Test
    public void givenWordExpectToFindAllItsAnagrams() {
        String word = "dad";
        List<Word> activeWords = new ArrayList<>();
        String expectedAnagram = "add";
        activeWords.add(new Word().setWordText(expectedAnagram));

        List<Word> anagramsForGivenWord = this.anagramAlgorithm.getAnagramsForGivenWord(activeWords, word);
        assertFalse(anagramsForGivenWord.isEmpty());
        assertEquals(expectedAnagram, anagramsForGivenWord.get(0).getWordText());
    }

    @Test
    public void givenDictionaryExpectToFindAnagramsOfDifferentLengths() {
        List<Word> activeWords = new ArrayList<>();
        activeWords.add(new Word().setWordText("ALERTING"));
        activeWords.add(new Word().setWordText("ALTERING"));
        activeWords.add(new Word().setWordText("INTEGRAL"));
        activeWords.add(new Word().setWordText("RELATING"));
        activeWords.add(new Word().setWordText("TANGLIER"));
        activeWords.add(new Word().setWordText("TRIANGLE"));
        activeWords.add(new Word().setWordText("FHJFD"));
        activeWords.add(new Word().setWordText("AA"));

        Map<Integer, Integer> anagramMap = this.anagramAlgorithm.countAnagrams(activeWords);
        assertFalse(anagramMap.isEmpty());
        assertEquals(1, anagramMap.size());
        assertEquals(6, anagramMap.get(8));
    }
}
