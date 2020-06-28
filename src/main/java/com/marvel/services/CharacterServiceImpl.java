package com.marvel.services;

import com.marvel.api.v1.converters.CharacterToCharacterDtoConverter;
import com.marvel.api.v1.converters.ComicToComicDtoConverter;
import com.marvel.api.v1.model.CharacterDTO;
import com.marvel.api.v1.model.ComicDTO;
import com.marvel.api.v1.model.DataContainerModel;
import com.marvel.api.v1.model.QueryCharacterModel;
import com.marvel.domain.Character;
import com.marvel.repositories.CharacterRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;
    private final CharacterToCharacterDtoConverter characterToDtoConverter;
    private final ComicToComicDtoConverter comicToDtoConverter;

    public CharacterServiceImpl(CharacterRepository characterRepository,
                                CharacterToCharacterDtoConverter characterToDtoConverter,
                                ComicToComicDtoConverter comicToDtoConverter) {
        this.characterRepository = characterRepository;
        this.characterToDtoConverter = characterToDtoConverter;
        this.comicToDtoConverter = comicToDtoConverter;
    }

    @Override
    public DataContainerModel<Character> getCharacters(QueryCharacterModel model) {

        Sort sort;
        List<Character> characters;

        if (model.getOrderBy() == null || model.getOrderBy().equals("name") || model.getOrderBy().isEmpty())
            sort = Sort.by("name");
        else if (model.getOrderBy().equals("-name"))
            sort = Sort.by("name").descending();
        else if (model.getOrderBy().equals("modified"))
            sort = Sort.by("modified");
        else
            sort = Sort.by("modified").descending();

        PageRequest pageable = PageRequest.of(model.getNumberPage(), model.getPageSize(), sort);

        if (model.getName() != null && model.getName().isEmpty()) {
            characters = characterRepository.findByName(pageable, model.getName());
        }

        characters = characterRepository.findAll(pageable)
                .stream()
                .collect(Collectors.toList());


        return new DataContainerModel<Character>()
                .setResults(characters)
                .setCount(characters.size())
                .setNumberPage(model.getNumberPage())
                .setPageSize(model.getPageSize());
    }

    @Override
    public CharacterDTO getCharacterById(Long id) {
        Optional<Character> optionalCharacter = characterRepository.findById(id);

        if (optionalCharacter.isPresent())
            return characterToDtoConverter.convert(optionalCharacter.get());
        else
            return new CharacterDTO();

    }

    @Override
    public List<ComicDTO> getComicsByCharacterId(Long characterId) {
        Optional<Character> optionalCharacter = characterRepository.findById(characterId);

        return optionalCharacter.map(character -> character.getComicList()
                .stream()
                .map(comicToDtoConverter::convert)
                .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }
}
