package co.za.bsg.web.controller;

import co.za.bsg.business.service.WordService;
import co.za.bsg.persistance.model.Word;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActivitiesControllerTest {
    @Mock
    private WordService wordService;

    @InjectMocks
    private ActivitiesController activitiesController;

    @Test
    void findAllAddedWords_success() {
        ArrayList<Word> words = new ArrayList<>();
        words.add(new Word().setWordText("word"));
        Page<Word> wordPages = new PageImpl<>(words);
        when(this.wordService.findAllAddedWords(any(), any())).thenReturn(wordPages);
        ResponseEntity<Page<Word>> wordServiceAllAddedWords = this.activitiesController.findAllAddedWords(0, 5);
        assertFalse(wordServiceAllAddedWords.getBody().getContent().isEmpty());
    }

    @Test
    void findAllRemovedWords_success() {
        ArrayList<Word> words = new ArrayList<>();
        words.add(new Word().setWordText("word"));
        Page<Word> wordPages = new PageImpl<>(words);
        when(this.wordService.findAllRemovedWords(any(), any())).thenReturn(wordPages);
        ResponseEntity<Page<Word>> wordServiceAllAddedWords = this.activitiesController.findAllRemovedWords(0, 5);
        assertFalse(wordServiceAllAddedWords.getBody().getContent().isEmpty());
    }
}