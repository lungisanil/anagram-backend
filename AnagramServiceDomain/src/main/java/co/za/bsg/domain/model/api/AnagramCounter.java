package co.za.bsg.domain.model.api;

public class AnagramCounter {
    private String anagramLength;
    private String anagramCounter;
    private String anagramCounterDescription;

    public String getAnagramLength() {
        return anagramLength;
    }

    public AnagramCounter setAnagramLength(String anagramLength) {
        this.anagramLength = anagramLength;
        return this;
    }

    public String getAnagramCounter() {
        return anagramCounter;
    }

    public AnagramCounter setAnagramCounter(String anagramCounter) {
        this.anagramCounter = anagramCounter;
        return this;
    }

    public String getAnagramCounterDescription() {
        return anagramCounterDescription;
    }

    public AnagramCounter setAnagramCounterDescription(String anagramCounterDescription) {
        this.anagramCounterDescription = anagramCounterDescription;
        return this;
    }

    @Override
    public String toString() {
        return "AnagramCounter{" +
                "anagramLength='" + anagramLength + '\'' +
                ", anagramCounter='" + anagramCounter + '\'' +
                ", anagramCounterDescription='" + anagramCounterDescription + '\'' +
                '}';
    }
}