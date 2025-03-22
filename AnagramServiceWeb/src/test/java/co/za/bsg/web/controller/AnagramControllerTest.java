package co.za.bsg.web.controller;

import co.za.bsg.business.orcherstrator.AnagramAlgorithmOrchestrator;
import co.za.bsg.domain.model.api.AnagramCounter;
import co.za.bsg.domain.model.api.AnagramCounterResponse;
import co.za.bsg.domain.model.api.WordRecord;
import co.za.bsg.persistance.model.Word;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnagramControllerTest {
    @Mock
    private AnagramAlgorithmOrchestrator anagramAlgorithmOrchestrator;

    @InjectMocks
    private AnagramController anagramController;

    @Test
    void getAnagramsForGivenWord_success() {
        ArrayList<Word> words = new ArrayList<>();
        String expectedWord = "dad";
        words.add(new Word().setWordText(expectedWord).setEffectiveFrom(LocalDateTime.now()).setEffectiveTo(LocalDateTime.MAX));
        when(this.anagramAlgorithmOrchestrator.runAlgorithmForFindingAnagramsForGivenWord(any())).thenReturn(words);
        ResponseEntity<List<WordRecord>> returnedListOfAnagrams = this.anagramController.getAnagramsForGivenWord("add");
        assertFalse(returnedListOfAnagrams.getBody().isEmpty());
        assertEquals(expectedWord, returnedListOfAnagrams.getBody().get(0).getWordText());
    }

    @Test
    void countAnagramsOfAllWordLengths_success() {
        List<AnagramCounter> anagramCounterList = new ArrayList<>();
        anagramCounterList.add(new AnagramCounter().setAnagramCounter("4").setAnagramLength("3"));
        when(this.anagramAlgorithmOrchestrator.countAnagramsOfAllWordLengths()).thenReturn(anagramCounterList);
        ResponseEntity<AnagramCounterResponse> anagramCounterResponseResponseEntity = this.anagramController.countAnagramsOfAllWordLengths();
        assertNotNull(anagramCounterResponseResponseEntity.getBody());
        assertEquals(1, anagramCounterResponseResponseEntity.getBody().getAnagramCounterList().size());
        assertNotNull(anagramCounterResponseResponseEntity.getBody().getAlgorithmExecutionTime());
    }
}