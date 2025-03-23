package co.za.bsg.business.service.impl;

import co.za.bsg.domain.exceptions.InternalServerErrorException;
import co.za.bsg.persistance.model.Word;
import co.za.bsg.business.utility.FileReaderUtility;
import co.za.bsg.persistance.repository.WordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DictionaryInitializer implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(DictionaryInitializer.class);
    private final WordRepository wordRepository;

    @Value("${DICTIONARY_PATH}")
    String dictionaryPath;

    public DictionaryInitializer(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @Override
    public void run(String... args) {
        //Read the dictionary file
        List<Word> words = FileReaderUtility.readFile(dictionaryPath);
        try {
            //Persist the dictionary
            this.wordRepository.saveAll(words);
        } catch (Exception ex) {
            String msg = "Error occurred while trying to persist the dictionary";
            LOGGER.error(msg);
            throw new InternalServerErrorException(msg);
        }
    }
}
