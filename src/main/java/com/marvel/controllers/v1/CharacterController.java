package com.marvel.controllers.v1;

import com.marvel.api.v1.model.ComicDTO;
import com.marvel.api.v1.model.MarvelCharacterDTO;
import com.marvel.api.v1.model.QueryCharacterModel;
import com.marvel.api.v1.model.ResponseDataContainerModel;
import com.marvel.exceptions.BadParametersException;
import com.marvel.exceptions.CharacterNotFoundException;
import com.marvel.services.CharacterService;
import com.marvel.services.ModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public ResponseDataContainerModel<MarvelCharacterDTO> getCharacters(
            @RequestParam(required = false) String number_page,
            @RequestParam(required = false) String page_size,
            @RequestParam(required = false) String order_by,
            @RequestParam(required = false) String modified_from,
            @RequestParam(required = false) String modified_to) {

        log.info(modified_to);
        QueryCharacterModel model = modelService
                .setParametersIntoModel(number_page, page_size, order_by, modified_from, modified_to);

        return characterService.getCharacters(model);
    }

    @GetMapping("/{characterId}")
    @ResponseStatus(HttpStatus.OK)
    public MarvelCharacterDTO getCharacter(@PathVariable String characterId) {

        return characterService.getCharacterById(Long.valueOf(characterId));
    }

    @GetMapping("{characterId}/comics")
    @ResponseStatus(HttpStatus.OK)
    public List<ComicDTO> getComicsByCharacterId(@PathVariable String characterId) {

        return characterService.getComicsByCharacterId(Long.valueOf(characterId));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CharacterNotFoundException.class)
    public String handleNotFound(Exception exception) {
        log.error(exception.getMessage());

        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadParametersException.class)
    public String handleBadParameters(Exception exception) {
        log.error(exception.getMessage());

        return exception.getMessage();
    }
}
