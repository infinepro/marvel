package com.marvel.controllers.v1.ApiInterfaces;

import com.marvel.api.v1.model.ComicDTO;
import com.marvel.api.v1.model.MarvelCharacterDTO;
import com.marvel.api.v1.model.ModelDataWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;


@Api("MarvelCharacters Controller")
public interface CharacterApi {

    @ApiOperation(value = "This will get a list of Marvel characters.")
    ModelDataWrapper<MarvelCharacterDTO> getCharacters(
            @ApiParam(value = "The unique ID of the character")
            @RequestParam(required = false) String comic_id,

            @ApiParam(value = "Page number of the list of models, default 0")
            @RequestParam(required = false) String number_page,

            @ApiParam(value = "The number of models per page, default 15")
            @RequestParam(required = false) String page_size,

            @ApiParam(value = "Order the result set by a field or fields. " +
                    "Add - to the value sort in descending order. allowableValues = name, -name, modified, -modified")
            @RequestParam(required = false) String order_by,

            @ApiParam(value = "The min value of this modification of the resource")
            @RequestParam(required = false) String modified_from,

            @ApiParam(value = "The max value of this modification of the resource")
            @RequestParam(required = false) String modified_to);

    @ApiOperation(value = "This will get Marvel character by id.")
    ModelDataWrapper<MarvelCharacterDTO> getCharacter(@PathVariable Long characterId);

    @ApiOperation(value = "This will get a list of Marvel comics by character id.")
    ModelDataWrapper<ComicDTO> getComicsByCharacterId(@PathVariable Long characterId);

    @ApiOperation(value = "This will add a new Marvel character.")
    ModelDataWrapper<MarvelCharacterDTO> addNewMarvelCharacter(@RequestBody @Valid MarvelCharacterDTO model,
                                                               BindingResult bindingResult);

    @ApiOperation(value = "This will update Marvel character by id.")
    ModelDataWrapper<MarvelCharacterDTO> updateMarvelCharacter(@RequestBody @Valid MarvelCharacterDTO model,
                                                               @PathVariable Long characterId,
                                                               BindingResult bindingResult);

    @ApiOperation(value = "This will delete Marvel characters by id.")
    ModelDataWrapper<MarvelCharacterDTO> updateMarvelCharacter(@PathVariable Long characterId);
}
