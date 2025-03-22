package co.za.bsg.business.orcherstrator;

import co.za.bsg.business.service.AnagramAlgorithm;
import co.za.bsg.business.service.WordService;
import co.za.bsg.domain.model.api.AnagramCounter;
import co.za.bsg.persistance.model.Word;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnagramAlgorithmOrchestratorTest {
    @Mock
    private AnagramAlgorithm anagramAlgorithm;

    @Mock
    private WordService wordService;

    @InjectMocks
    private AnagramAlgorithmOrchestrator anagramAlgorithmOrchestrator;

    @Test
    void runAlgorithmForFindingAnagramsForGivenWord_success() {
        when(this.wordService.getAllActiveWords()).thenReturn(new ArrayList<>());
        ArrayList<Word> words = new ArrayList<>();
        String expectedWord = "dad";
        words.add(new Word().setWordText(expectedWord));
        when(this.anagramAlgorithm.getAnagramsForGivenWord(anyList(), anyString())).thenReturn(words);
        List<Word> returnedListOfAnagrams = this.anagramAlgorithmOrchestrator.runAlgorithmForFindingAnagramsForGivenWord("add");
        assertEquals(expectedWord, returnedListOfAnagrams.get(0).getWordText());
    }

    @Test
    void countAnagramsOfAllWordLengths_success() {
        Map<Integer, Integer> anagramCountMap = new HashMap<>();
        anagramCountMap.put(1, 4);
        anagramCountMap.put(3, 8);
        when(this.wordService.getAllActiveWords()).thenReturn(new ArrayList<>());
        when(this.anagramAlgorithm.countAnagrams(anyList())).thenReturn(anagramCountMap);
        List<AnagramCounter> anagramCounterList = this.anagramAlgorithmOrchestrator.countAnagramsOfAllWordLengths();
        assertFalse(anagramCounterList.isEmpty());
        assertEquals("Words with the character length of 1 had 4 anagrams", anagramCounterList.get(0).getAnagramCounterDescription());
        assertEquals("Words with the character length of 3 had 8 anagrams", anagramCounterList.get(1).getAnagramCounterDescription());
    }
}