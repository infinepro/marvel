package com.marvel.services;

import com.marvel.api.v1.model.DataContainerModel;
import com.marvel.api.v1.model.QueryCharacterModel;
import com.marvel.domain.Character;
import com.marvel.repositories.CharacterRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;

    public CharacterServiceImpl(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @Override
    public DataContainerModel<Character> getCharacters(QueryCharacterModel model) {

        Sort sort;

        if (model.getOrderBy().equals("name"))
            sort = Sort.by("name");
        else if (model.getOrderBy().equals("-name"))
            sort = Sort.by("name").descending();
        else if (model.getOrderBy().equals("modified"))
            sort = Sort.by("modified");
        else
            sort = Sort.by("modified").descending();

        PageRequest pageable = PageRequest.of(model.getNumberPage(), model.getPageSize(), sort);

        List<Character> characters = characterRepository.findAll(pageable)
                .stream()
                .collect(Collectors.toList());

        return new DataContainerModel<Character>()
                .setResults(characters)
                .setCount(characters.size())
                .setNumberPage(model.getNumberPage())
                .setPageSize(model.getPageSize());
    }
}
