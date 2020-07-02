package com.marvel.controllers.v1;

import com.marvel.api.v1.model.ComicDTO;
import com.marvel.api.v1.model.MarvelCharacterDTO;
import com.marvel.api.v1.model.ModelDataWrapper;
import com.marvel.api.v1.model.QueryCharacterModel;
import com.marvel.controllers.v1.api.CharacterControllerApi;
import com.marvel.exceptions.BadParametersException;
import com.marvel.exceptions.CharacterNotFoundException;
import com.marvel.exceptions.ComicNotFoundException;
import com.marvel.services.CharacterService;
import com.marvel.services.ModelHelperService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.format.DateTimeParseException;

import static com.marvel.controllers.v1.CharacterController.BASE_URL;

@Slf4j
@RestController
@RequestMapping(BASE_URL)
public class CharacterController implements CharacterControllerApi {

    public static final String BASE_URL = "/v1/public/characters";

    private final CharacterService characterService;
    private final ModelHelperService modelHelperService;

    public CharacterController(CharacterService characterService, ModelHelperService modelHelperService) {
        this.characterService = characterService;
        this.modelHelperService = modelHelperService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ModelDataWrapper<MarvelCharacterDTO> getCharacters(
            @RequestParam(required = false) String comic_id,
            @RequestParam(required = false) String number_page,
            @RequestParam(required = false) String page_size,
            @RequestParam(required = false) String order_by,
            @RequestParam(required = false) String modified_start,
            @RequestParam(required = false) String modified_end) {

        log.info(modified_end);
        QueryCharacterModel model = modelHelperService
                .setParametersIntoQueryCharacterModel(
                        comic_id, number_page, page_size, order_by, modified_start, modified_end);

        ModelDataWrapper<MarvelCharacterDTO> dataWrapper = new ModelDataWrapper<>();
        dataWrapper.setData(characterService.getCharactersByModel(model));

        return dataWrapper;
    }

    @ApiOperation(value = "This will get Marvel character by id.")
    @GetMapping("/{characterId}")
    @ResponseStatus(HttpStatus.OK)
    public ModelDataWrapper<MarvelCharacterDTO> getCharacter(@PathVariable Long characterId) {

        ModelDataWrapper<MarvelCharacterDTO> dataWrapper = new ModelDataWrapper<>();
        dataWrapper.setData(characterService.getCharacterById(Long.valueOf(characterId)));

        return dataWrapper;
    }

    @ApiOperation(value = "This will get a list of Marvel comics by character id.")
    @GetMapping("/{characterId}/comics")
    @ResponseStatus(HttpStatus.OK)
    public ModelDataWrapper<ComicDTO> getComicsByCharacterId(@PathVariable Long characterId) {

        ModelDataWrapper<ComicDTO> dataWrapper = new ModelDataWrapper<>();
        dataWrapper.setData(characterService.getComicsByCharacterId(Long.valueOf(characterId)));

        return dataWrapper;
    }

    @ApiOperation(value = "This will add a new Marvel character.")
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public ModelDataWrapper<MarvelCharacterDTO> addNewMarvelCharacter(@RequestBody @Valid MarvelCharacterDTO model,
                                                                      BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.error("creating character error");
            log.error(bindingResult.getAllErrors().toString());
            throw new BadParametersException("Creating character error, bad request parameters");
        }

        ModelDataWrapper<MarvelCharacterDTO> dataWrapper = new ModelDataWrapper<>();
        dataWrapper.setData(characterService.saveMarvelCharacterDto(model));
        dataWrapper.setCode(HttpStatus.CREATED.value());
        dataWrapper.setStatus("New character with name: " + model.getName() +
                " and id: " + dataWrapper.getData().getResults().get(0).getId() + " created");

        log.info("New character with name: " + model.getName() +
                " and id: " + dataWrapper.getData().getResults().get(0).getId() + " created");

        return dataWrapper;
    }

    @ApiOperation(value = "This will update Marvel character by id.")
    @PutMapping("/{characterId}/update")
    @ResponseStatus(HttpStatus.OK)
    public ModelDataWrapper<MarvelCharacterDTO> updateMarvelCharacter(@RequestBody @Valid MarvelCharacterDTO model,
                                                                      @PathVariable Long characterId,
                                                                      BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.error("updating character error");
            log.error(bindingResult.getAllErrors().toString());
            throw new BadParametersException("Updating character error, bad request parameters");
        }

        ModelDataWrapper<MarvelCharacterDTO> dataWrapper = new ModelDataWrapper<>();
        dataWrapper.setData(characterService.updateMarvelCharacterById(characterId, model));
        dataWrapper.setCode(HttpStatus.OK.value());
        dataWrapper.setStatus("character with id: " + dataWrapper.getData().getResults().get(0).getId() + " updated");

        log.info("character with id: " + dataWrapper.getData().getResults().get(0).getId() + " updated");

        return dataWrapper;
    }

    @ApiOperation(value = "This will delete Marvel characters by id.")
    @DeleteMapping("/{characterId}/delete")
    @ResponseStatus(HttpStatus.OK)
    public ModelDataWrapper<MarvelCharacterDTO> updateMarvelCharacter(@PathVariable Long characterId) {

        ModelDataWrapper<MarvelCharacterDTO> dataWrapper = new ModelDataWrapper<>();
        dataWrapper.setCode(HttpStatus.OK.value());
        dataWrapper.setStatus("character with id: " + characterId + " remote");

        characterService.deleteCharacterById(characterId);

        log.info("character with id: " + characterId + " remote");

        return dataWrapper;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ComicNotFoundException.class, CharacterNotFoundException.class})
    public ModelDataWrapper<Object> handleNotFound(Exception exception) {
        log.error(exception.getMessage());

        ModelDataWrapper<Object> dataWrapper = new ModelDataWrapper<>();

        dataWrapper.setCode(HttpStatus.NOT_FOUND.value());
        dataWrapper.setStatus(exception.getMessage());

        return dataWrapper;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadParametersException.class, DateTimeParseException.class, NumberFormatException.class})
    public ModelDataWrapper<Object> handleBadParameters(Exception exception) {
        log.error(exception.getMessage());

        ModelDataWrapper<Object> dataWrapper = new ModelDataWrapper<>();

        dataWrapper.setCode(HttpStatus.BAD_REQUEST.value());
        dataWrapper.setStatus(exception.getMessage());

        return dataWrapper;
    }
}
