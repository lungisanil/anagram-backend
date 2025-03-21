package co.za.bsg.web.controller;

import co.za.bsg.business.orcherstrator.AnagramAlgorithmOrchestrator;
import co.za.bsg.domain.model.api.AnagramCounter;
import co.za.bsg.domain.model.api.AnagramCounterResponse;
import co.za.bsg.domain.model.api.ErrorResponse;
import co.za.bsg.domain.model.api.WordRecord;
import co.za.bsg.persistance.model.Word;
import co.za.bsg.web.translator.WordTranslator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Anagram-Controller", description = "Anagram Controller")
@RestController
@RequestMapping("/anagram")
public class AnagramController {
    private final AnagramAlgorithmOrchestrator anagramAlgorithmOrchestrator;

    public AnagramController(AnagramAlgorithmOrchestrator anagramAlgorithmOrchestrator) {
        this.anagramAlgorithmOrchestrator = anagramAlgorithmOrchestrator;
    }

    @Operation(
            summary = "Get a list of Anagrams for a given word"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found all anagrams", content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = WordRecord.class)))),
            @ApiResponse(responseCode = "404", description = "No anagrams found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/retrieve/anagrams/{word}")
    @CrossOrigin(origins = "http://localhost:4200/")
    public ResponseEntity<List<WordRecord>> getAnagramsForGivenWord(@PathVariable("word") String word) {
        List<Word> anagramsForGivenWord = this.anagramAlgorithmOrchestrator.runAlgorithmForFindingAnagramsForGivenWord(word);
        return ResponseEntity.status(HttpStatus.OK)
                .body(WordTranslator.getAllWordRecords(anagramsForGivenWord));
    }

    @Operation(
            summary = "Count anagrams of all word lengths"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found all anagrams", content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = WordRecord.class)))),
            @ApiResponse(responseCode = "404", description = "No anagrams found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/retrieve/anagrams/count")
    @CrossOrigin(origins = "http://localhost:4200/")
    public ResponseEntity<AnagramCounterResponse> countAnagramsOfAllWordLengths() {
        // Start measuring execution time
        long startTime = System.nanoTime();
        List<AnagramCounter> anagramsOfAllWordLengths = this.anagramAlgorithmOrchestrator.countAnagramsOfAllWordLengths();
        // Stop measuring execution time
        long endTime = System.nanoTime();
        // Calculate the execution time in milliseconds
        long executionTime = (endTime - startTime) / 1000000;

        return ResponseEntity.status(HttpStatus.OK)
                .body(new AnagramCounterResponse()
                        .setAnagramCounterList(anagramsOfAllWordLengths)
                        .setAlgorithmExecutionTime(executionTime));
    }
}
