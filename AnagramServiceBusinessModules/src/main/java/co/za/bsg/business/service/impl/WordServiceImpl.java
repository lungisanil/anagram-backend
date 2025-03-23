package co.za.bsg.business.service.impl;

import co.za.bsg.domain.constants.AnagramConstants;
import co.za.bsg.domain.exceptions.InternalServerErrorException;
import co.za.bsg.domain.exceptions.NotFoundException;
import co.za.bsg.persistance.model.Word;
import co.za.bsg.persistance.repository.WordRepository;
import co.za.bsg.business.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WordServiceImpl implements WordService {
    private final WordRepository wordRepository;

    @Autowired
    public WordServiceImpl(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @Override
    public Word addWord(final String word) {
        try {
            Word entity = new Word()
                    .setWordText(word)
                    .setEffectiveFrom(LocalDateTime.now())
                    .setEffectiveTo(LocalDateTime.parse(AnagramConstants.END_OF_TIME, AnagramConstants.DATE_TIME_FORMATTER));
            return this.wordRepository.save(entity);
        } catch (Exception exception) {
            throw new InternalServerErrorException("Cannot add the new word");
        }
    }

    @Override
    public void removeWord(final String word) {
        try {
            this.wordRepository.retireWord(word, LocalDateTime.now());
        } catch (Exception exception) {
            throw new InternalServerErrorException(String.format("Error occurred when trying to remove the word :%s", word));
        }
    }

    @Override
    public Page<Word> pageAllActiveWords(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("wordText").ascending());
        return Optional.ofNullable(this.wordRepository.pageAllActiveWords(LocalDateTime.now(), pageable))
                .orElseThrow(() -> new NotFoundException("Cannot find the active words"));
    }

    @Cacheable("allActiveWords")
    @Override
    public List<Word> getAllActiveWords() {
        return Optional.ofNullable(this.wordRepository.findAllActiveWords(LocalDateTime.now()))
                .orElseThrow(() -> new NotFoundException("Cannot find the words"));
    }

    @Override
    public Word getWord(final String word) {
        return Optional.ofNullable(this.wordRepository.findActiveWord(word, LocalDateTime.now()))
                .orElseThrow(() -> new NotFoundException("Cannot find the word"));
    }

    @Override
    public Page<Word> findAllAddedWords(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("wordText").ascending());
        return Optional.ofNullable(this.wordRepository.findAllAddedWords(LocalDateTime.parse(AnagramConstants.DICTIONARY_START_DATE, AnagramConstants.DATE_TIME_FORMATTER),
                        pageable))
                .orElseThrow(() -> new NotFoundException("Cannot added words"));
    }

    @Override
    public Page<Word> findAllRemovedWords(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("wordText").ascending());
        return Optional.ofNullable(this.wordRepository.findAllRemovedWords(LocalDateTime.parse(AnagramConstants.END_OF_TIME, AnagramConstants.DATE_TIME_FORMATTER),
                        pageable))
                .orElseThrow(() -> new NotFoundException("Cannot added words"));
    }
}
