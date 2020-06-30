package com.marvel.controllers.v1;

import com.marvel.api.v1.model.ComicDTO;
import com.marvel.api.v1.model.MarvelCharacterDTO;
import com.marvel.api.v1.model.QueryCharacterModel;
import com.marvel.api.v1.model.ResponseDataContainerModel;
import com.marvel.services.CharacterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/public")
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping("/characters33")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDataContainerModel<MarvelCharacterDTO> getCharacters33() {

        QueryCharacterModel model = new QueryCharacterModel()
                //.setName("Hulk")
                .setComicId(1L)
                .setNumberPage(0)
                .setPageSize(2)
                .setOrderBy("name")
                .setModifiedFrom("2020-06-30 09:47:37")
                .setModifiedTo("2020-06-30 15:47:37");

        return characterService.getCharacters(model);
    }


    @GetMapping("/characters")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDataContainerModel<MarvelCharacterDTO> getCharacters(QueryCharacterModel model) {

        return characterService.getCharacters(model);
    }

    @GetMapping("/characters/{characterId}")
    @ResponseStatus(HttpStatus.OK)
    public MarvelCharacterDTO getCharacter(@PathVariable String characterId) {

        return characterService.getCharacterById(Long.valueOf(characterId));
    }

    @GetMapping("/characters/{characterId}/comics")
    @ResponseStatus(HttpStatus.OK)
    public List<ComicDTO> getComicsByCharacterId(@PathVariable String characterId) {

        return characterService.getComicsByCharacterId(Long.valueOf(characterId));
    }
}
