package co.za.bsg.web.controller;

import co.za.bsg.business.service.WordService;
import co.za.bsg.domain.model.api.PaginatedResponse;
import co.za.bsg.domain.model.api.SuccessResponse;
import co.za.bsg.domain.model.api.WordRecord;
import co.za.bsg.persistance.model.Word;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WordControllerTest {
    @Mock
    private WordService wordService;

    @InjectMocks
    private WordController wordController;

    @Test
    void pageAllActiveWords_success() {
        ArrayList<Word> words = new ArrayList<>();
        words.add(new Word().setWordText("word").setEffectiveFrom(LocalDateTime.now()).setEffectiveTo(LocalDateTime.MAX));
        Page<Word> wordPages = new PageImpl<>(words);
        when(this.wordService.pageAllActiveWords(any(), any())).thenReturn(wordPages);
        ResponseEntity<PaginatedResponse<WordRecord>> pageAllActiveWords = this.wordController.pageAllActiveWords(0, 10);
        assertFalse(pageAllActiveWords.getBody().getContent().isEmpty());
    }

    @Test
    void removeWord_success() {
        String wordText = "ABCD";
        when(this.wordService.removeWord(anyString())).thenReturn(true);
        ResponseEntity<SuccessResponse> response = this.wordController.removeWord(wordText);
        ArgumentCaptor<String> wordTextArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(this.wordService, times(1)).removeWord(wordTextArgumentCaptor.capture());
        assertEquals(wordText, wordTextArgumentCaptor.getValue());
        assertEquals("Success", response.getBody().getMessage());
    }

    @Test
    void removeWord_failed() {
        String wordText = "ABCD";
        when(this.wordService.removeWord(anyString())).thenReturn(false);
        ResponseEntity<SuccessResponse> response = this.wordController.removeWord(wordText);
        ArgumentCaptor<String> wordTextArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(this.wordService, times(1)).removeWord(wordTextArgumentCaptor.capture());
        assertEquals(wordText, wordTextArgumentCaptor.getValue());
        assertEquals("Failed", response.getBody().getMessage());
    }

    @Test
    void addWord_success() {
        String wordText = "ABCD";
        Word word = new Word().setWordText(wordText).setEffectiveFrom(LocalDateTime.now()).setEffectiveTo(LocalDateTime.MAX);
        when(this.wordService.addWord(any())).thenReturn(word);
        ResponseEntity<WordRecord> addedWord = this.wordController.addWord(wordText);
        assertEquals(wordText, addedWord.getBody().getWordText());
    }
}