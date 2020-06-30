package com.marvel.bootstrap;

import com.marvel.domain.Comic;
import com.marvel.domain.ComicDate;
import com.marvel.domain.ComicPrice;
import com.marvel.domain.MarvelCharacter;
import com.marvel.repositories.CharacterRepository;
import com.marvel.repositories.ComicRepository;
import com.marvel.services.DateHelperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class BootstrapData implements ApplicationListener<ContextRefreshedEvent>, DateHelperService {

    private final LocalDateTime nowDateTime;
    private final LocalDateTime prevDateTime;
    private final CharacterRepository characterRepository;
    private final ComicRepository comicRepository;

    public BootstrapData(CharacterRepository characterRepository, ComicRepository comicRepository) {
        this.nowDateTime = LocalDateTime.now();
        this.prevDateTime = LocalDateTime.of(1995, 3, 25, 12, 55);

        this.characterRepository = characterRepository;
        this.comicRepository = comicRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.warn("start loading data");
        if (comicRepository.count() == 0)
            loadComics();
        log.warn("load data success");
    }

    private List<MarvelCharacter> loadCharactersTwo() {
        List<MarvelCharacter> characters = new ArrayList<>();

        characters.add(characterRepository.findByName("Thanos").get());
        characters.add(characterRepository.findByName("Hulk").get());

        return characters;
    }

    private List<MarvelCharacter> loadCharactersThree() {
        List<MarvelCharacter> characters = new ArrayList<>();

        characters.add(new MarvelCharacter()
                .setId(1L)
                .setName("Thanos")
                .setDescription("super strong villain")
                .setModified(nowDateTime)
                .setThumbnail(new Byte[]{2, 4, 3})
                .setFullImage(new Byte[]{1, 4, 72}));

        characters.add(new MarvelCharacter()
                .setId(2L)
                .setName("Red Hulk")
                .setDescription("red NEGODIAY")
                .setModified(nowDateTime)
                .setThumbnail(new Byte[]{1, 4, 3})
                .setFullImage(new Byte[]{4, 4, 67}));

        characters.add(new MarvelCharacter()
                .setId(3L)
                .setName("Hulk")
                .setDescription("green hero")
                .setModified(nowDateTime)
                .setThumbnail(new Byte[]{1, 4, 3})
                .setFullImage(new Byte[]{4, 4, 67}));

        characterRepository.saveAll(characters);

        return characters;
    }

    private void loadComics() {

        List<MarvelCharacter> charactersTwo = loadCharactersThree();
        List<MarvelCharacter> charactersOne = loadCharactersTwo();


        List<ComicDate> dates = new ArrayList<ComicDate>() {{
            add(new ComicDate().setType("hard").setDate(LocalDateTime.now()));
            add(new ComicDate().setType("very hard").setDate(LocalDateTime.now()));
            add(new ComicDate().setType("very easy").setDate(LocalDateTime.now()));
        }};

        List<ComicPrice> prices = new ArrayList<ComicPrice>() {{
            add(new ComicPrice().setType("digital price").setPrice(BigDecimal.valueOf(33.5)));
            add(new ComicPrice().setType("print price").setPrice(BigDecimal.valueOf(15)));
        }};

        Comic newComic = new Comic()
                .setId(1L)
                .setTitle("this is new comic")
                .setDescription("just nice comic")
                .setModified(prevDateTime)
                .setFormat("A4")
                .setPageCount(30)
                .setThumbnail(new Byte[]{2, 4, 3})
                .setFullImage(new Byte[]{1, 4, 72})
                .setDates(dates)
                .setPrices(prices)
                .setMarvelCharacters(charactersOne);

        Comic oldComic = new Comic()
                .setId(2L)
                .setTitle("this is old comic")
                .setDescription("normal comic")
                .setModified(prevDateTime)
                .setFormat("A3")
                .setPageCount(30)
                .setThumbnail(new Byte[]{2, 4, 3})
                .setFullImage(new Byte[]{1, 4, 72})
                .setDates(dates)
                .setPrices(prices)
                .setMarvelCharacters(charactersTwo);

        List<Comic> list = new ArrayList<>();
        list.add(newComic);
        list.add(oldComic);

        comicRepository.saveAll(list);
    }
}
