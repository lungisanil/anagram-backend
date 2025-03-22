package co.za.bsg.business.translator;

import co.za.bsg.domain.exceptions.InternalServerErrorException;
import co.za.bsg.domain.model.api.AnagramCounter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AnagramTranslator {
    private AnagramTranslator() {
        throw new InternalServerErrorException("This class only has static method(s), it should not be instantiated");
    }

    public static List<AnagramCounter> getAnagramCounter(Map<Integer, Integer> anagramCounterMap) {
        List<AnagramCounter> anagramCounterList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> anagramCount : anagramCounterMap.entrySet()) {
            String anagramLength = anagramCount.getKey().toString();
            String anagramCounter = anagramCount.getValue().toString();
            anagramCounterList.add(new AnagramCounter()
                    .setAnagramLength(anagramLength)
                    .setAnagramCounter(anagramCounter)
                    .setAnagramCounterDescription(String.format("Words with the character length of %s had %s anagrams", anagramLength, anagramCounter)));
        }
        return anagramCounterList;
    }
}
