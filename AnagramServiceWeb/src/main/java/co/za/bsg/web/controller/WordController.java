package co.za.bsg.web.controller;

import co.za.bsg.domain.model.api.ErrorResponse;
import co.za.bsg.domain.model.api.PaginatedResponse;
import co.za.bsg.domain.model.api.SuccessResponse;
import co.za.bsg.domain.model.api.WordRecord;
import co.za.bsg.persistance.model.Word;
import co.za.bsg.business.service.WordService;
import co.za.bsg.web.translator.WordTranslator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Word-Controller", description = "Work Controller")
@RestController
@RequestMapping("/word")
public class WordController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WordController.class);
    private final WordService wordService;

    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @Operation(
            summary = "Get all words"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found all words", content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = WordRecord.class)))),
            @ApiResponse(responseCode = "404", description = "words not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/retrieve/all")
    @CrossOrigin(origins = "http://localhost:4200/")
    public ResponseEntity<PaginatedResponse<WordRecord>> pageAllActiveWords(@RequestParam("pageNumber") Integer pageNumber,
                                                                            @RequestParam("pageSize") Integer pageSize) {
        Page<Word> wordPage = this.wordService.pageAllActiveWords(pageNumber, pageSize);
        LOGGER.info(String.format("Successfully retrieved page number : %s for active words", pageNumber));
        return ResponseEntity.status(HttpStatus.OK).body(WordTranslator.getPaginatedWordsResponse(wordPage));
    }

    @Operation(
            summary = "Remove a word"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully removed a word", content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = SuccessResponse.class)))),
            @ApiResponse(responseCode = "404", description = "Word not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/remove/{word}")
    @CrossOrigin(origins = "http://localhost:4200/")
    public ResponseEntity<SuccessResponse> removeWord(@PathVariable String word) {
        Boolean status = this.wordService.removeWord(word);
        LOGGER.info(String.format("Successfully removed the word : %s", word));
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse().setMessage(status ? "Success" : "Failed"));
    }

    @Operation(
            summary = "Add a word"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added a word", content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Word.class)))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add/{word}")
    @CrossOrigin(origins = "http://localhost:4200/")
    public ResponseEntity<WordRecord> addWord(@PathVariable String word) {
        Word persistedWord = this.wordService.addWord(word);
        LOGGER.info(String.format("Successfully added the word : %s", word));
        return ResponseEntity.status(HttpStatus.CREATED).body(WordTranslator.getWordRecord(persistedWord));
    }
}