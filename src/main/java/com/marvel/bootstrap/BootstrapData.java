package com.marvel.bootstrap;

import com.marvel.domain.Character;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

//@Slf4j
@Component
public class BootstrapData implements ApplicationListener<ContextRefreshedEvent>, DateServiceHelper {

    private final Long nowDateTime;
    private final Long prevDateTime;
    private final CharacterRepository characterRepository;
    private final ComicRepository comicRepository;

    public BootstrapData(CharacterRepository characterRepository, ComicRepository comicRepository) {
        this.nowDateTime = parseStringDateFormatToLong(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));

        this.prevDateTime = parseStringDateFormatToLong(LocalDateTime.of(1995, 3, 25, 12, 55)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));

        this.characterRepository = characterRepository;
        this.comicRepository = comicRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //log.warn("start loading data");
        //loadComics();
        //log.warn("load data success");
    }

    private List<Character> loadCharacters() {
        List<Character> characters = new ArrayList<>();

        characters.add(new Character()
                .setId(33L)
                .setName("Thanos")
                .setDescription("super strong villain")
                .setModified(nowDateTime)
                .setThumbnail(new Byte[]{2, 4, 3})
                .setFullImage(new Byte[]{1, 4, 72}));

        characters.add(new Character()
                .setId(35L)
                .setName("Hulk")
                .setDescription("green hero")
                .setModified(nowDateTime)
                .setThumbnail(new Byte[]{1, 4, 3})
                .setFullImage(new Byte[]{4, 4, 67}));

        return characters;
    }

    private void loadComics() {
        List<ComicDate> dates = new ArrayList<ComicDate>() {{
            add(new ComicDate().setId(1L).setType("hard").setDate(6513186515L));
            add(new ComicDate().setId(2L).setType("very hard").setDate(6512313186515L));
            add(new ComicDate().setId(2L).setType("very easy").setDate(3186515L));
        }};

        List<ComicPrice> prices = new ArrayList<ComicPrice>() {{
            add(new ComicPrice().setId(3L).setType("digital price").setPrice(BigDecimal.valueOf(33.5)));
            add(new ComicPrice().setId(5L).setType("print price").setPrice(BigDecimal.valueOf(15)));
        }};

        //List<Comic> comics = new ArrayList<>();

        Comic newComic = new Comic()
                .setId(5L)
                .setTitle("this is new comic")
                .setDescription("just nice comic")
                .setModified(prevDateTime)
                .setFormat("A4")
                .setPageCount(30)
                .setThumbnail(new Byte[]{2, 4, 3})
                .setFullImage(new Byte[]{1, 4, 72})
                .setDates(dates)
                .setPrices(prices)
                .setCharacters(loadCharacters());

        comicRepository.insert(newComic);
    }
}
