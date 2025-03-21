package co.za.bsg.business.service.impl;

import co.za.bsg.domain.exceptions.InternalServerErrorException;
import co.za.bsg.persistance.model.Word;
import co.za.bsg.business.utility.FileReaderUtility;
import co.za.bsg.persistance.repository.WordRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DictionaryInitializer implements CommandLineRunner {
    private final WordRepository wordRepository;

    public DictionaryInitializer(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @Override
    public void run(String... args) {
        String path = "C://DO//personal-dev/heckathon/file/dictionary.txt";
        //Read the dictionary file
        List<Word> words = FileReaderUtility.readFile(path);
        //Persist the dictionary
        try {
            this.wordRepository.saveAll(words);
        } catch (Exception ex) {
            throw new InternalServerErrorException("Error occurred while trying to persist the dictionary");
        }
    }
}
