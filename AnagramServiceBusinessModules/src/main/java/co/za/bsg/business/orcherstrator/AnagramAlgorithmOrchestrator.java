package co.za.bsg.business.orcherstrator;

import co.za.bsg.business.service.AnagramAlgorithm;
import co.za.bsg.business.service.WordService;
import co.za.bsg.business.translator.AnagramTranslator;
import co.za.bsg.domain.model.api.AnagramCounter;
import co.za.bsg.persistance.model.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AnagramAlgorithmOrchestrator {
    private final AnagramAlgorithm anagramAlgorithm;
    private final WordService wordService;

    @Autowired
    public AnagramAlgorithmOrchestrator(AnagramAlgorithm anagramAlgorithm, WordService wordService) {
        this.anagramAlgorithm = anagramAlgorithm;
        this.wordService = wordService;
    }

    public List<Word> runAlgorithmForFindingAnagramsForGivenWord(String word) {
        List<Word> allWords = this.wordService.getAllActiveWords();
        return this.anagramAlgorithm.getAnagramsForGivenWord(allWords, word);
    }

    public List<AnagramCounter> countAnagramsOfAllWordLengths() {
        List<Word> allWords = this.wordService.getAllActiveWords();
        Map<Integer, Integer> countAnagramsMap = this.anagramAlgorithm.countAnagrams(allWords);
        return AnagramTranslator.getAnagramCounter(countAnagramsMap);
    }
}
