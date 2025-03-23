package co.za.bsg.business.service;

import co.za.bsg.persistance.model.Word;
import org.springframework.data.domain.Page;

import java.util.List;

public interface WordService {

    /**
     * @param word - word to add
     * @return Word
     */
    Word addWord(final String word);

    /**
     * @param word - word to delete
     */
    Boolean removeWord(final String word);

    /**
     * @return List<Word>
     */
    Page<Word> pageAllActiveWords(Integer pageNumber, Integer pageSize);

    /**
     * Get all active words for the algorithm to use
     *
     * @return List<Word>
     */
    List<Word> getAllActiveWords();

    /**
     * @param word - word to add
     * @return Word
     */
    Word getWord(final String word);

    /**
     * @param pageNumber pageNumber
     * @param pageSize   pageSize
     * @return Page<Word>
     */
    Page<Word> findAllAddedWords(Integer pageNumber, Integer pageSize);

    /**
     * @param pageNumber pageNumber
     * @param pageSize   pageSize
     * @return Page<Word>
     */
    Page<Word> findAllRemovedWords(Integer pageNumber, Integer pageSize);
}
