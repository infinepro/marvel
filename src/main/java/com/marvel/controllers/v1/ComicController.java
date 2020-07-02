package com.marvel.controllers.v1;

import com.marvel.api.v1.model.*;
import com.marvel.exceptions.BadParametersException;
import com.marvel.exceptions.CharacterNotFoundException;
import com.marvel.exceptions.ComicNotFoundException;
import com.marvel.services.CharacterService;
import com.marvel.services.ComicService;
import com.marvel.services.ModelHelperService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.format.DateTimeParseException;

import static com.marvel.controllers.v1.ComicController.BASE_URL;

@Api("Comic controller")
@Slf4j
@RestController
@RequestMapping(BASE_URL)
public class ComicController {

    public static final String BASE_URL = "/v1/public/comics";

    private final ComicService comicService;
    private final ModelHelperService modelHelperService;
    private final CharacterService characterService;

    public ComicController(ComicService comicService,
                           ModelHelperService modelHelperService,
                           CharacterService characterService) {
        this.comicService = comicService;
        this.modelHelperService = modelHelperService;
        this.characterService = characterService;
    }

    @ApiOperation(value = "This will get a list of comics.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ModelDataWrapper<ComicDTO> getComics(
            @RequestParam(required = false) String number_page,
            @RequestParam(required = false) String page_size,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String creating_date_from,
            @RequestParam(required = false) String creating_date_to,
            @RequestParam(required = false) String order_by) {

        QueryComicModel model = modelHelperService
                .setParametersIntoQueryComicModel(
                        number_page, page_size, title, creating_date_from, creating_date_to, order_by);

        ModelDataWrapper<ComicDTO> responseModel = new ModelDataWrapper<>();
        responseModel.setData(comicService.getComics(model));

        return responseModel;
    }

    @ApiOperation(value = "This will get comic by id.")
    @GetMapping("/{comicId}")
    @ResponseStatus(HttpStatus.OK)
    public ModelDataWrapper<ComicDTO> getComic(@PathVariable Long comicId) {
        ModelDataWrapper<ComicDTO> responseModel = new ModelDataWrapper<>();
        responseModel.setData(comicService.getComicById(comicId));

        return responseModel;
    }

    @ApiOperation(value = "This will get a list of Marvel characters by comic id.")
    @GetMapping("/{comicId}/characters")
    @ResponseStatus(HttpStatus.OK)
    public ModelDataWrapper<MarvelCharacterDTO> getCharactersByComicId(
            @PathVariable String comicId,
            @RequestParam(required = false) String number_page,
            @RequestParam(required = false) String page_size,
            @RequestParam(required = false) String order_by,
            @RequestParam(required = false) String modified_from,
            @RequestParam(required = false) String modified_to) {

        QueryCharacterModel model = modelHelperService
                .setParametersIntoQueryCharacterModel(
                        comicId, number_page, page_size, order_by, modified_from, modified_to);

        ModelDataWrapper<MarvelCharacterDTO> dataWrapper = new ModelDataWrapper<>();
        dataWrapper.setData(comicService.getCharactersByModel(model));

        return dataWrapper;
    }

    @ApiOperation(value = "This will add new comic.")
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public ModelDataWrapper<ComicDTO> addNewComic(@RequestBody @Valid ComicDTO model,
                                                  BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.error("creating comic error");
            log.error(bindingResult.getAllErrors().toString());
            throw new BadParametersException("Creating comic error, bad request parameters");
        }

        ModelDataWrapper<ComicDTO> dataWrapper = new ModelDataWrapper<>();
        dataWrapper.setData(comicService.saveComicDto(model));
        dataWrapper.setCode(HttpStatus.CREATED.value());
        dataWrapper.setStatus("New comic with title: " + model.getTitle() +
                " and id: " + dataWrapper.getData().getResults().get(0).getId() + " created");

        log.info("New comic with title: " + model.getTitle() +
                " and id: " + dataWrapper.getData().getResults().get(0).getId() + " created");

        return dataWrapper;
    }

    @ApiOperation(value = "This will update comic by id.")
    @PutMapping("/{comicId}/update")
    @ResponseStatus(HttpStatus.OK)
    public ModelDataWrapper<ComicDTO> updateComic(@RequestBody @Valid ComicDTO model,
                                                  @PathVariable Long comicId,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("updating comic error");
            log.error(bindingResult.getAllErrors().toString());
            throw new BadParametersException("Updating comic error, bad request parameters");
        }

        ModelDataWrapper<ComicDTO> dataWrapper = new ModelDataWrapper<>();
        dataWrapper.setData(comicService.updateComicById(comicId, model));
        dataWrapper.setCode(HttpStatus.OK.value());
        dataWrapper.setStatus("Comic with id: " + comicId + " updated");

        log.info("Comic with id: " + comicId + " updated");

        return dataWrapper;
    }

    @ApiOperation(value = "This will delete comic by id.")
    @DeleteMapping("/{comicId}/delete")
    @ResponseStatus(HttpStatus.OK)
    public ModelDataWrapper<ComicDTO> deleteComicById(@PathVariable Long comicId) {

        ModelDataWrapper<ComicDTO> dataWrapper = new ModelDataWrapper<>();
        dataWrapper.setCode(HttpStatus.OK.value());
        dataWrapper.setStatus("comic with id: " + comicId + " remote");

        comicService.deleteComicById(comicId);

        log.info("character with id: " + comicId + " remote");

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
