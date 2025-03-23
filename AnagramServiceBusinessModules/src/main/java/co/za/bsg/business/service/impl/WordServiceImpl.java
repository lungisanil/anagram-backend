package co.za.bsg.business.service.impl;

import co.za.bsg.domain.constants.AnagramConstants;
import co.za.bsg.domain.exceptions.InternalServerErrorException;
import co.za.bsg.domain.exceptions.NotFoundException;
import co.za.bsg.persistance.model.Word;
import co.za.bsg.persistance.repository.WordRepository;
import co.za.bsg.business.service.WordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WordServiceImpl implements WordService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WordServiceImpl.class);
    private final WordRepository wordRepository;

    @Autowired
    public WordServiceImpl(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @CacheEvict(value="allActiveWords", allEntries=true)
    @Override
    public Word addWord(final String word) {
        try {
            Word entity = new Word()
                    .setWordText(word)
                    .setEffectiveFrom(LocalDateTime.now())
                    .setEffectiveTo(LocalDateTime.parse(AnagramConstants.END_OF_TIME, AnagramConstants.DATE_TIME_FORMATTER));
            return this.wordRepository.save(entity);
        } catch (Exception exception) {
            String msg = "Cannot add the new word";
            LOGGER.error(msg);
            throw new InternalServerErrorException(msg);
        }
    }

    @CacheEvict(value="allActiveWords", allEntries=true)
    @Override
    public void removeWord(final String word) {
        try {
            this.wordRepository.retireWord(word, LocalDateTime.now());
        } catch (Exception exception) {
            String msg = String.format("Error occurred when trying to remove the word :%s", word);
            LOGGER.error(msg);
            throw new InternalServerErrorException(msg);
        }
    }

    @Override
    public Page<Word> pageAllActiveWords(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("wordText").ascending());
        try {
            return this.wordRepository.pageAllActiveWords(LocalDateTime.now(), pageable);
        } catch (Exception exception) {
            String msg = "Cannot find the pages for active words";
            LOGGER.error(msg);
            throw new NotFoundException(msg);
        }
    }

    @Cacheable("allActiveWords")
    @Override
    public List<Word> getAllActiveWords() {
        try {
            return this.wordRepository.findAllActiveWords(LocalDateTime.now());
        } catch (Exception exception) {
            String msg = "Cannot find active words";
            LOGGER.error(msg);
            throw new NotFoundException(msg);
        }
    }

    @Override
    public Word getWord(final String word) {
        try {
            return this.wordRepository.findActiveWord(word, LocalDateTime.now());
        } catch (Exception exception) {
            String msg = "Cannot find the word";
            LOGGER.error(msg);
            throw new NotFoundException(msg);
        }
    }

    @Override
    public Page<Word> findAllAddedWords(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("wordText").ascending());
        try {
            return this.wordRepository.findAllAddedWords(LocalDateTime.parse(AnagramConstants.DICTIONARY_START_DATE, AnagramConstants.DATE_TIME_FORMATTER),
                    pageable);
        } catch (Exception exception) {
            String msg = "Cannot find added words";
            LOGGER.error(msg);
            throw new NotFoundException(msg);
        }
    }

    @Override
    public Page<Word> findAllRemovedWords(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("wordText").ascending());
        try {
            return this.wordRepository.findAllRemovedWords(LocalDateTime.parse(AnagramConstants.END_OF_TIME, AnagramConstants.DATE_TIME_FORMATTER),
                    pageable);
        } catch (Exception exception) {
            String msg = "Cannot find removed words";
            LOGGER.error(msg);
            throw new NotFoundException(msg);
        }
    }
}
