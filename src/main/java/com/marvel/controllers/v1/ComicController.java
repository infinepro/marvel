package com.marvel.controllers.v1;

import com.marvel.api.v1.model.ComicDTO;
import com.marvel.api.v1.model.QueryComicModel;
import com.marvel.api.v1.model.ModelDataWrapper;
import com.marvel.exceptions.BadParametersException;
import com.marvel.exceptions.CharacterNotFoundException;
import com.marvel.exceptions.ComicNotFoundException;
import com.marvel.services.ComicService;
import com.marvel.services.ModelHelperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.format.DateTimeParseException;

import static com.marvel.controllers.v1.ComicController.BASE_URL;

@Slf4j
@RestController
@RequestMapping(BASE_URL)
public class ComicController {

    public static final String BASE_URL = "/v1/public/comics";

    private final ComicService comicService;
    private final ModelHelperService modelHelperService;

    public ComicController(ComicService comicService, ModelHelperService modelHelperService) {
        this.comicService = comicService;
        this.modelHelperService = modelHelperService;
    }

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
        responseModel.setData(comicService.findComics(model));

        return responseModel;
    }

    @GetMapping("/{comicId}")
    @ResponseStatus(HttpStatus.OK)
    public ModelDataWrapper<ComicDTO> getComic(@PathVariable Long comicId) {

        ModelDataWrapper<ComicDTO> responseModel = new ModelDataWrapper<>();
        responseModel.setData(comicService.findComicById(comicId));

        return responseModel;
    }

    @GetMapping("/{comicId}/characters")
    @ResponseStatus(HttpStatus.OK)
    public ModelDataWrapper<ComicDTO> getCharactersByComicId(@PathVariable Long comicId) {
        //todo:`1
        return null;
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public ModelDataWrapper<ComicDTO> addNewComic(@RequestBody @Valid ComicDTO model) {
        //todo:`1
        return null;
    }

    @PutMapping("/{comicId}/update")
    @ResponseStatus(HttpStatus.OK)
    public ModelDataWrapper<ComicDTO> updateComic(@RequestBody @Valid ComicDTO model,
                                                  @PathVariable Long comicId) {
        //todo:`1
        return null;
    }

    @DeleteMapping("/{comicId}/delete")
    @ResponseStatus(HttpStatus.OK)
    public ModelDataWrapper<ComicDTO> deleteComicById(@PathVariable Long comicId) {
        //todo:`1
        return null;
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
