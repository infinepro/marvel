package com.marvel.controllers.v1;

import com.marvel.api.v1.model.DataContainerModel;
import com.marvel.domain.Character;
import com.marvel.services.CharacterService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/public")
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping("/characters")
    @ResponseStatus(HttpStatus.OK)
    public DataContainerModel<Character> getVendorList(DataContainerModel<Character> dataContainer){
        return characterService.getCharacters();
    }
}
