package com.marvel.controllers.v1;

import com.marvel.api.v1.model.*;
import com.marvel.exceptions.BadParametersException;
import com.marvel.exceptions.CharacterNotFoundException;
import com.marvel.services.CharacterService;
import com.marvel.services.ModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeParseException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(CharacterController.BASE_URL)
public class CharacterController {

    public static final String BASE_URL = "/v1/public/characters";

    private final CharacterService characterService;
    private final ModelService modelService;

    public CharacterController(CharacterService characterService, ModelService modelService) {
        this.characterService = characterService;
        this.modelService = modelService;
    }

    @GetMapping("33")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDataContainerModel<MarvelCharacterDTO> getCharacters33() {

        QueryCharacterModel model = new QueryCharacterModel()
                .setNumberPage(0)
                .setPageSize(2)
                .setOrderBy("name")
                .setModifiedFrom("2020-06-30 09:47:37")
                .setModifiedTo("2020-06-30 15:47:37");

        return characterService.getCharacters(model);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CharacterDataWrapper<MarvelCharacterDTO> getCharacters(
            @RequestParam(required = false) String number_page,
            @RequestParam(required = false) String page_size,
            @RequestParam(required = false) String order_by,
            @RequestParam(required = false) String modified_from,
            @RequestParam(required = false) String modified_to) {

        log.info(modified_to);
        QueryCharacterModel model = modelService
                .setParametersIntoModel(number_page, page_size, order_by, modified_from, modified_to);

        CharacterDataWrapper<MarvelCharacterDTO> dataWrapper = new CharacterDataWrapper<>();
        dataWrapper.setData(characterService.getCharacters(model));

        return dataWrapper;
    }

    @GetMapping("/{characterId}")
    @ResponseStatus(HttpStatus.OK)
    public CharacterDataWrapper<MarvelCharacterDTO> getCharacter(@PathVariable String characterId) {

        CharacterDataWrapper<MarvelCharacterDTO> dataWrapper = new CharacterDataWrapper<>();
        dataWrapper.setData(characterService.getCharacterById(Long.valueOf(characterId)));

        return dataWrapper;
    }

    @GetMapping("{characterId}/comics")
    @ResponseStatus(HttpStatus.OK)
    public CharacterDataWrapper<ComicDTO> getComicsByCharacterId(@PathVariable String characterId) {

        CharacterDataWrapper<ComicDTO> dataWrapper = new CharacterDataWrapper<>();
        dataWrapper.setData(characterService.getComicsByCharacterId(Long.valueOf(characterId)));

        return dataWrapper;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CharacterNotFoundException.class)
    public CharacterDataWrapper<Object> handleNotFound(Exception exception) {
        log.error(exception.getMessage());

        CharacterDataWrapper<Object> dataWrapper = new CharacterDataWrapper<>();

        dataWrapper.setCode(HttpStatus.NOT_FOUND.value());
        dataWrapper.setStatus(exception.getMessage());

        return dataWrapper;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadParametersException.class, DateTimeParseException.class})
    public CharacterDataWrapper<Object> handleBadParameters(Exception exception) {
        log.error(exception.getMessage());

        CharacterDataWrapper<Object> dataWrapper = new CharacterDataWrapper<>();

        dataWrapper.setCode(HttpStatus.BAD_REQUEST.value());
        dataWrapper.setStatus(exception.getMessage());

        return dataWrapper;
    }
}
