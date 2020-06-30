package com.marvel.bootstrap;

import com.marvel.domain.MarvelCharacter;
import com.marvel.domain.Comic;
import com.marvel.domain.ComicDate;
import com.marvel.domain.ComicPrice;
import com.marvel.repositories.CharacterRepository;
import com.marvel.repositories.ComicRepository;
import com.marvel.services.DateServiceHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class BootstrapData implements ApplicationListener<ContextRefreshedEvent>, DateServiceHelper {

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
        loadComics();
        log.warn("load data success");
    }

    private Set<MarvelCharacter> loadCharacters() {
        Set<MarvelCharacter> characters = new HashSet<>();

        characters.add(new MarvelCharacter()
                .setName("Thanos")
                .setDescription("super strong villain")
                .setModified(nowDateTime)
                .setThumbnail(new Byte[]{2, 4, 3})
                .setFullImage(new Byte[]{1, 4, 72}));

        characters.add(new MarvelCharacter()
                .setName("Hulk")
                .setDescription("green hero")
                .setModified(nowDateTime)
                .setThumbnail(new Byte[]{1, 4, 3})
                .setFullImage(new Byte[]{4, 4, 67}));

        return characters;
    }

    private void loadComics() {
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
                .setTitle("this is new comic")
                .setDescription("just nice comic")
                .setModified(prevDateTime)
                .setFormat("A4")
                .setPageCount(30)
                .setThumbnail(new Byte[]{2, 4, 3})
                .setFullImage(new Byte[]{1, 4, 72})
                .setDates(dates)
                .setPrices(prices)
                .setMarvelCharacters(loadCharacters());

        comicRepository.save(newComic);
    }
}
