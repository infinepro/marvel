package com.marvel.controllers.v1.api;

import com.marvel.api.v1.model.ComicDTO;
import com.marvel.api.v1.model.MarvelCharacterDTO;
import com.marvel.api.v1.model.ModelDataWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.BindingResult;

import static com.marvel.services.DateHelperService.DATE_FORMAT;


@Api("MarvelCharacters Controller")
public interface CharacterControllerApi {

    @ApiOperation(value = "This will get a list of Marvel characters.")
    ModelDataWrapper<MarvelCharacterDTO> getCharacters(
            @ApiParam(value = "The unique ID of the character")
                    String comic_id,
            @ApiParam(value = "Page number of the list of models, default 0")
                    String number_page,
            @ApiParam(value = "The number of models per page, default 15")
                    String page_size,
            @ApiParam(value = "Order the result set by a field or fields. " +
                    "Add - to the value sort in descending order.", allowableValues = "name, -name, modified, -modified")
                    String order_by,
            @ApiParam(value = "The min value date modification or creating of this resource, format:" + DATE_FORMAT)
                    String modified_start,
            @ApiParam(value = "The max value date modification or creating of this resource, format:" + DATE_FORMAT)
                    String modified_end);

    @ApiOperation(value = "This will get Marvel character by id.")
    ModelDataWrapper<MarvelCharacterDTO> getCharacter(Long characterId);

    @ApiOperation(value = "This will get a list of Marvel comics by character id.")
    ModelDataWrapper<ComicDTO> getComicsByCharacterId(Long characterId);

    @ApiOperation(value = "This will add a new Marvel character.")
    ModelDataWrapper<MarvelCharacterDTO> addNewMarvelCharacter(MarvelCharacterDTO model,
                                                               BindingResult bindingResult);

    @ApiOperation(value = "This will update Marvel character by id.")
    ModelDataWrapper<MarvelCharacterDTO> updateMarvelCharacter(MarvelCharacterDTO model,
                                                               Long characterId,
                                                               BindingResult bindingResult);

    @ApiOperation(value = "This will delete Marvel characters by id.")
    ModelDataWrapper<MarvelCharacterDTO> updateMarvelCharacter(Long characterId);
}
