package co.za.bsg.business.utility;

import co.za.bsg.domain.constants.AnagramConstants;
import co.za.bsg.domain.exceptions.InternalServerErrorException;
import co.za.bsg.persistance.model.Word;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReaderUtility {
    public FileReaderUtility() {
    }

    public static List<Word> readFile(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            List<Word> dictionary = new ArrayList<>();
            while (scanner.hasNextLine()) {
                Word word = new Word()
                        .setWordText(scanner.nextLine())
                        .setEffectiveFrom(LocalDateTime.parse(AnagramConstants.DICTIONARY_START_DATE, AnagramConstants.DATE_TIME_FORMATTER))
                        .setEffectiveTo(LocalDateTime.parse(AnagramConstants.END_OF_TIME, AnagramConstants.DATE_TIME_FORMATTER));
                dictionary.add(word);
            }
            return dictionary;
        } catch (IOException e) {
            throw new InternalServerErrorException("Failed to read the file: " + e.getMessage());
        }
    }
}
