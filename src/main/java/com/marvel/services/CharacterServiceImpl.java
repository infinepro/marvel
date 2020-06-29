package com.marvel.services;

import com.marvel.api.v1.converters.CharacterToCharacterDtoConverter;
import com.marvel.api.v1.converters.ComicToComicDtoConverter;
import com.marvel.api.v1.model.CharacterDTO;
import com.marvel.api.v1.model.ComicDTO;
import com.marvel.api.v1.model.QueryCharacterModel;
import com.marvel.api.v1.model.ResponseDataContainerModel;
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
public class CharacterServiceImpl implements CharacterService, DateServiceHelper {

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
    public ResponseDataContainerModel<Character> getCharacters(QueryCharacterModel model) {

        Sort sort;
        List<Character> characters;
        Long modifiedSince;

        if (model.getOrderBy() == null || model.getOrderBy().equals("name") || model.getOrderBy().isEmpty())
            sort = Sort.by("name");
        else if (model.getOrderBy().equals("-name"))
            sort = Sort.by("name").descending();
        else if (model.getOrderBy().equals("modified"))
            sort = Sort.by("modified");
        else
            sort = Sort.by("modified").descending();

        PageRequest pageable = PageRequest.of(model.getNumberPage(), model.getPageSize(), sort);

        if (model.getModifiedSince() == null || model.getModifiedSince().isEmpty())
            modifiedSince = Long.MIN_VALUE;
        else
            modifiedSince = parseStringDateFormatToLong(model.getModifiedSince());

        characters = characterRepository
                .findAllByNameAndComicIdAndModifiedDateSince(
                        model.getName(),
                        model.getComicId(),
                        modifiedSince, pageable)
                .stream()
                .collect(Collectors.toList());


        return new ResponseDataContainerModel<Character>()
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
