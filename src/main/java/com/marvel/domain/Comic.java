package com.marvel.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Accessors(chain = true)
@Document
public class Comic {

    @Id
    private Long Id;
    private String title;
    private String description;
    private Long modified;
    private String format;
    private Integer pageCount;
    private Byte[] thumbnail;
    private Byte[] fullImage;
    private List<ComicDate> dates = new ArrayList<>();
    private List<ComicPrice> prices = new ArrayList<>();

    @DBRef
    private List<Character> characters = new ArrayList<>();

    public void addComicDate(ComicDate comicDate) {
        if (comicDate != null) {
            this.dates.add(comicDate);
        }
    }

    public void addComicPrice(ComicPrice comicPrice) {
        if (comicPrice != null) {
            this.prices.add(comicPrice);
        }
    }

    public void addCharacter(Character character) {
        if (character != null) {
            this.characters.add(character);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comic)) return false;
        Comic comic = (Comic) o;
        return Objects.equals(getId(), comic.getId()) &&
                Objects.equals(getTitle(), comic.getTitle()) &&
                Objects.equals(getDescription(), comic.getDescription()) &&
                Objects.equals(getModified(), comic.getModified()) &&
                Objects.equals(getFormat(), comic.getFormat()) &&
                Objects.equals(getPageCount(), comic.getPageCount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getDescription(), getModified(), getFormat(), getPageCount());
    }
}


