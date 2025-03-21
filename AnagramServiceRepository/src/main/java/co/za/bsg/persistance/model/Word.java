package co.za.bsg.persistance.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "DICTIONARY")
public class Word {
    //Having text of the Word as primary key will ensure that words are not repeated
    @Id
    @Column(name = "WORD_TEXT", nullable = false, updatable = false)
    private String wordText;

    @Column(name = "EFFECTIVE_FROM", nullable = false, updatable = true)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime effectiveFrom;

    @Column(name = "EFFECTIVE_TO", nullable = false, updatable = true)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime effectiveTo;

    public String getWordText() {
        return wordText;
    }

    public Word setWordText(String word) {
        this.wordText = word;
        return this;
    }

    public LocalDateTime getEffectiveFrom() {
        return effectiveFrom;
    }

    public Word setEffectiveFrom(LocalDateTime effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
        return this;
    }

    public LocalDateTime getEffectiveTo() {
        return effectiveTo;
    }

    public Word setEffectiveTo(LocalDateTime effectiveTo) {
        this.effectiveTo = effectiveTo;
        return this;
    }

    @Override
    public String toString() {
        return "Word{" +
                "wordText='" + wordText + '\'' +
                ", effectiveFrom=" + effectiveFrom +
                ", effectiveTo=" + effectiveTo +
                '}';
    }
}
