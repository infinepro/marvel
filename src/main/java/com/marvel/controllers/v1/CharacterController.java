package com.marvel.controllers.v1;

import com.marvel.api.v1.model.CharacterDTO;
import com.marvel.api.v1.model.ComicDTO;
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

    @GetMapping("/characters")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDataContainerModel<CharacterDTO> getCharacters(QueryCharacterModel model) {

        return characterService.getCharacters(model);
    }

    @GetMapping("/characters/{characterId}")
    @ResponseStatus(HttpStatus.OK)
    public CharacterDTO getCharacter(@PathVariable String characterId) {

        return characterService.getCharacterById(Long.valueOf(characterId));
    }

    @GetMapping("/{characterId}/comics")
    @ResponseStatus(HttpStatus.OK)
    public List<ComicDTO> getComicsByCharacterId(@PathVariable String characterId) {

        return characterService.getComicsByCharacterId(Long.valueOf(characterId));
    }
}
