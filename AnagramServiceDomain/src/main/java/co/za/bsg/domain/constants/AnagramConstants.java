package co.za.bsg.domain.constants;

import java.time.format.DateTimeFormatter;

public class AnagramConstants {

    private AnagramConstants() {
    }

    //Assuming that the dictionary is effective from this date, this is to make it easy to track for new words that get added
    //Because those words will have a different effective from date
    public static final String DICTIONARY_START_DATE = "2025-01-01" + "T00:00:00";
    public static final String END_OF_TIME = "9999-12-31" + "T00:00:00";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
}
