package co.za.bsg.business.service.impl;

import co.za.bsg.domain.exceptions.InternalServerErrorException;
import co.za.bsg.domain.exceptions.NotFoundException;
import co.za.bsg.persistance.model.Word;
import co.za.bsg.persistance.repository.WordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WordServiceImplTest {
    @Mock
    private WordRepository wordRepository;

    @InjectMocks
    private WordServiceImpl wordService;

    @Test
    void testThatWordIsAddedSuccessfully() {
        String wordText = "ABCD";
        Word word = new Word().setWordText(wordText);
        when(this.wordRepository.save(any())).thenReturn(word);
        Word addedWord = this.wordService.addWord(wordText);
        assertEquals(wordText, addedWord.getWordText());
    }

    @Test
    void testThatWordIsNotAddedSuccessfully() {
        String wordText = "ABCD";
        when(this.wordRepository.save(any())).thenThrow(new InternalServerErrorException());
        try {
            this.wordService.addWord(wordText);
        } catch (Exception ex) {
            assertEquals(InternalServerErrorException.class, ex.getClass());
            assertTrue(ex.getMessage().contains("Cannot add the new word"));
        }
    }

    @Test
    void testThatWordIsRemovedWordSuccessfully() {
        String wordText = "ABCD";
        when(this.wordRepository.findActiveWord(any(), any())).thenReturn(new Word().setWordText(wordText));
        doNothing().when(this.wordRepository).retireWord(anyString(), any());
        Boolean isWordRemoved = this.wordService.removeWord(wordText);
        ArgumentCaptor<String> wordTextArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(this.wordRepository, times(1)).retireWord(anyString(), any());
        verify(this.wordRepository).retireWord(wordTextArgumentCaptor.capture(), any());
        assertEquals(wordText, wordTextArgumentCaptor.getValue());
        assertTrue(isWordRemoved);
    }

    @Test
    void testThatWordAskedToBeRemovedDoesNotExist() {
        String wordText = "ABCD";
        when(this.wordRepository.findActiveWord(any(), any())).thenReturn(null);
        Boolean isWordRemoved = this.wordService.removeWord(wordText);
        assertFalse(isWordRemoved);
    }

    @Test
    void testThatWordIsNotRemovedWordSuccessfully() {
        String wordText = "ABCD";
        when(this.wordRepository.findActiveWord(any(), any())).thenReturn(new Word().setWordText(wordText));
        doThrow(new InternalServerErrorException()).when(this.wordRepository).retireWord(any(), any());
        try {
            this.wordService.removeWord(wordText);
        } catch (Exception ex) {
            assertEquals(InternalServerErrorException.class, ex.getClass());
            assertTrue(ex.getMessage().contains("Error occurred when trying to remove the word"));
        }
    }

    @Test
    void testThatPagesForAllActiveWordsAreRetrievedSuccessfully() {
        ArrayList<Word> words = new ArrayList<>();
        words.add(new Word().setWordText("word"));
        Page<Word> wordPages = new PageImpl<>(words);
        when(this.wordRepository.pageAllActiveWords(any(), any())).thenReturn(wordPages);
        Page<Word> pageAllActiveWords = this.wordService.pageAllActiveWords(0, 10);
        assertFalse(pageAllActiveWords.getContent().isEmpty());
    }

    @Test
    void testThatPagesForAllActiveWordsAreNotRetrievedSuccessfully() {
        when(this.wordRepository.pageAllActiveWords(any(), any())).thenThrow(new NotFoundException());
        try {
            this.wordService.pageAllActiveWords(0, 10);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertTrue(ex.getMessage().contains("Cannot find the pages for active words"));
        }
    }

    @Test
    void testThatAllActiveWordsAreRetrievedSuccessfully() {
        ArrayList<Word> words = new ArrayList<>();
        words.add(new Word().setWordText("word"));
        when(this.wordRepository.findAllActiveWords(any())).thenReturn(words);
        List<Word> allActiveWords = this.wordService.getAllActiveWords();
        assertFalse(allActiveWords.isEmpty());
    }

    @Test
    void testThatAllActiveWordsAreNotRetrievedSuccessfully() {
        doThrow(new NotFoundException()).when(this.wordRepository).findAllActiveWords(any());
        try {
            this.wordService.getAllActiveWords();
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertTrue(ex.getMessage().contains("Cannot find active words"));
        }
    }

    @Test
    void testThatWordIsRetrievedSuccessfully() {
        String word = "word";
        when(this.wordRepository.findActiveWord(any(), any())).thenReturn(new Word().setWordText(word));
        Word wordServiceWord = this.wordService.getWord(word);
        assertEquals(word, wordServiceWord.getWordText());
    }

    @Test
    void testThatWordIsNotRetrievedSuccessfully() {
        doThrow(new NotFoundException()).when(this.wordRepository).findActiveWord(any(), any());
        try {
            this.wordService.getWord("word");
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertTrue(ex.getMessage().contains("Cannot find the word"));
        }
    }

    @Test
    void testThatAddedWordsAreRetrievedSuccessfully() {
        ArrayList<Word> words = new ArrayList<>();
        words.add(new Word().setWordText("word"));
        Page<Word> wordPages = new PageImpl<>(words);
        when(this.wordRepository.findAllAddedWords(any(), any())).thenReturn(wordPages);
        Page<Word> wordServiceAllAddedWords = this.wordService.findAllAddedWords(0, 5);
        assertFalse(wordServiceAllAddedWords.getContent().isEmpty());
    }

    @Test
    void testThatAddedWordsAreNotRetrievedSuccessfully() {
        doThrow(new NotFoundException()).when(this.wordRepository).findAllAddedWords(any(), any());
        try {
            this.wordService.findAllAddedWords(0, 5);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertTrue(ex.getMessage().contains("Cannot find added words"));
        }
    }

    @Test
    void testThatRemovedWordsAreRetrievedSuccessfully() {
        ArrayList<Word> words = new ArrayList<>();
        words.add(new Word().setWordText("word"));
        Page<Word> wordPages = new PageImpl<>(words);
        when(this.wordRepository.findAllRemovedWords(any(), any())).thenReturn(wordPages);
        Page<Word> wordServiceAllAddedWords = this.wordService.findAllRemovedWords(0, 5);
        assertFalse(wordServiceAllAddedWords.getContent().isEmpty());
    }

    @Test
    void testThatRemovedWordsAreNotRetrievedSuccessfully() {
        doThrow(new NotFoundException()).when(this.wordRepository).findAllRemovedWords(any(), any());
        try {
            this.wordService.findAllRemovedWords(0, 5);
        } catch (Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertTrue(ex.getMessage().contains("Cannot find removed words"));
        }
    }
}