package com.marvel.controllers.v1;

import com.marvel.api.v1.model.CharacterDTO;
import com.marvel.api.v1.model.ComicDTO;
import com.marvel.api.v1.model.DataContainerModel;
import com.marvel.api.v1.model.QueryCharacterModel;
import com.marvel.domain.Character;
import com.marvel.services.CharacterService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/public")
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping("/characters")
    @ResponseStatus(HttpStatus.OK)
    public DataContainerModel<Character> getCharacters(QueryCharacterModel model){
        return characterService.getCharacters(model);
    }

    @GetMapping("/{characterId}")
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
