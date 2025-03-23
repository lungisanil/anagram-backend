package co.za.bsg.web.controller;

import co.za.bsg.domain.model.api.ErrorResponse;
import co.za.bsg.domain.model.api.PaginatedResponse;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Activities-Controller", description = "Activities Controller")
@RestController
@RequestMapping("/activities")
public class ActivitiesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiesController.class);
    private final WordService wordService;

    public ActivitiesController(WordService wordService) {
        this.wordService = wordService;
    }

    @Operation(
            summary = "Get all added words"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found all added words", content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = WordRecord.class)))),
            @ApiResponse(responseCode = "404", description = "words not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/added")
    @CrossOrigin(origins = "http://localhost:4200/")
    public ResponseEntity<PaginatedResponse<WordRecord>> findAllAddedWords(@RequestParam("pageNumber") Integer pageNumber,
                                                                           @RequestParam("pageSize") Integer pageSize) {
        Page<Word> allAddedWords = this.wordService.findAllAddedWords(pageNumber, pageSize);
        LOGGER.info(String.format("Successfully found added words for page : %s", pageNumber));
        return ResponseEntity.status(HttpStatus.OK).body(WordTranslator.getPaginatedWordsResponse(allAddedWords));
    }

    @Operation(
            summary = "Get all removed words"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found all removed words", content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = WordRecord.class)))),
            @ApiResponse(responseCode = "404", description = "words not found", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/removed")
    @CrossOrigin(origins = "http://localhost:4200/")
    public ResponseEntity<PaginatedResponse<WordRecord>> findAllRemovedWords(@RequestParam("pageNumber") Integer pageNumber,
                                                                             @RequestParam("pageSize") Integer pageSize) {
        Page<Word> allRemovedWords = this.wordService.findAllRemovedWords(pageNumber, pageSize);
        LOGGER.info(String.format("Successfully found removed words for page : %s", pageNumber));
        return ResponseEntity.status(HttpStatus.OK).body(WordTranslator.getPaginatedWordsResponse(allRemovedWords));
    }
}
