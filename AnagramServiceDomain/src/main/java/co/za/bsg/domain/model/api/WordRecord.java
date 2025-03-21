package co.za.bsg.domain.model.api;

public class WordRecord {
    private String wordText;
    private String effectiveFrom;
    private String effectiveTo;

    public String getWordText() {
        return wordText;
    }

    public WordRecord setWordText(String wordText) {
        this.wordText = wordText;
        return this;
    }

    public String getEffectiveFrom() {
        return effectiveFrom;
    }

    public WordRecord setEffectiveFrom(String effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
        return this;
    }

    public String getEffectiveTo() {
        return effectiveTo;
    }

    public WordRecord setEffectiveTo(String effectiveTo) {
        this.effectiveTo = effectiveTo;
        return this;
    }

    @Override
    public String toString() {
        return "WordRecord{" +
                "wordText='" + wordText + '\'' +
                ", effectiveFrom='" + effectiveFrom + '\'' +
                ", effectiveTo='" + effectiveTo + '\'' +
                '}';
    }
}
