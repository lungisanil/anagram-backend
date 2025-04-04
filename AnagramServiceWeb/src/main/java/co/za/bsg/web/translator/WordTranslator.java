package co.za.bsg.web.translator;

import co.za.bsg.domain.exceptions.InternalServerErrorException;
import co.za.bsg.domain.model.api.PaginatedResponse;
import co.za.bsg.domain.model.api.WordRecord;
import co.za.bsg.persistance.model.Word;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WordTranslator {

    private WordTranslator() {
        throw new InternalServerErrorException("This class only has static method(s), it should not be instantiated");
    }

    public static WordRecord getWordRecord(Word retrievedWord) {
        return new WordRecord()
                .setWordText(retrievedWord.getWordText())
                .setEffectiveFrom(retrievedWord.getEffectiveFrom().toString())
                .setEffectiveTo(retrievedWord.getEffectiveTo().toString());
    }

    public static List<WordRecord> getAllWordRecords(List<Word> words) {
        return words.stream().map(getWordRecordFunction()).collect(Collectors.toList());
    }

    public static PaginatedResponse<WordRecord> getPaginatedWordsResponse(Page<Word> page) {
        return new PaginatedResponse<WordRecord>()
                .setPageNumber(page.getNumber())
                .setPageSize(page.getSize())
                .setTotalPages(page.getTotalPages())
                .setTotalElements(page.getTotalElements())
                .setContent(page.getContent().stream().map(getWordRecordFunction()).collect(Collectors.toList()));

    }

    private static Function<Word, WordRecord> getWordRecordFunction() {
        return word -> new WordRecord()
                .setWordText(word.getWordText())
                .setEffectiveFrom(word.getEffectiveFrom().toString())
                .setEffectiveTo(word.getEffectiveTo().toString());
    }
}
